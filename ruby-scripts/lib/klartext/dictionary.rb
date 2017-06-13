require 'nokogiri'

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
    
  end

  class Word
    attr_accessor :value, :klass, :lang, :translations,:inflections
    
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
      @translations = word_node.xpath("//translation").map{|t| t[:value]}
      @inflections  = word_node.xpath("//inflection").map{|t| t[:value]}
    end

    def klass
      CLASS[@klass.to_sym] if @klass
    end
    
    def to_s
      h = {value: value,klass: klass,lang: lang,inflections: inflections, translations: translations }
      h.to_json
    end
  end
end
