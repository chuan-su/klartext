require 'nokogiri'
require 'csv'
module Klartext; end

module Klartext
  class Dictionary
    attr_accessor :file
    include XmlStream

    StartToken = '<word'
    StopToken  = '</word>'
      
    def initialize(dict_url)
      @dict_url = dict_url
    end

    def file
      matched = @dict_url.match /\/.+\/(.+)\.xml$/
      matched.captures[0]
    end
    
    def download
      open(file,'w') do |f|
        parse_stream @dict_url,StartToken,StopToken do |xml|
          word_node = Nokogiri::Slop(xml,nil,'utf-8').word
          next xml if word_node[:value].length <= 1
          f << {index: {_index: "klartext", _type: "word"}}.to_json
          f << "\n"
          f << Word.new(word_node)
          f << "\n"
        end
      end
    end

    def download_examples
      CSV.open('examples.csv','wb') do |csv|
        csv << ['body','interp']
        parse_stream @dict_url,StartToken,StopToken do |xml|
          word_node = Nokogiri::Slop(xml,nil,'utf-8').word
          word_node.xpath('//example').each do |example|
            csv << Example.new(example).to_csv
          end
        end
      end
    end
  end

  class Example
    attr_accessor :text,:translation
    def initialize(example_node)
      @text = example_node[:value]
      translation = example_node.xpath('.//translation').first and @translation = translation[:value]
    end

    def to_csv
      [@text,@translation]
    end
  end
  
  class Word
    attr_accessor :value, :klass, :lang, :translation,:inflection
    
    CLASS = {
      nn: "n",
      jj: "adj",
      vb: "verb",
      pp: "prep",
      pn: "pron",
      ab: "adv",
      rg: "base",
      abbrev: "abbrev",
      'in': "int"
    }
    def initialize(word_node)
      @value = word_node[:value]
      @klass = word_node[:class]
      @lang  = word_node[:lang]
      @inflection  = word_node.xpath("//inflection").map{|e| e[:value]}
      @translation = word_node.xpath("//translation")
                       .select{|e| e.parent.name == 'word' }
                       .map{|e| e[:value] }
    end

    def klass
      CLASS[@klass.to_sym] if @klass
    end
    
    def to_s
      h = {value: value,klass: klass,lang: lang,inflection: inflection, translation: translation }
      h.to_json
    end
  end
end
