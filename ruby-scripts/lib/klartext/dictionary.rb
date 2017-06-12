require 'nokogiri'

module Klartext; end

module Klartext
  class Dictionary
    include XmlStream

    StartToken = '<word'
    StopToken  = '</word>'
      
    def initialize(dict_url)
      @dict_url = dict_url
    end

    def download
      parse_stream @dict_url,StartToken,StopToken do |xml|
        word_node = Nokogiri::Slop(xml,nil,'utf-8').word
        new_word = Word.new word_node
        binding.pry
      end
    end
  end

  class Word
    attr_accessor :value, :klass, :lang, :translation

    def initialize(word_node)
      @value = word_node['value']
      @klass = word_node['class']
      @lang  = word_node['lang']
      @translations = word_node.translation.map{|t| t["value"]}
    end

    def as_json
      {value: value,klass: klass,lang: lang, translations: translations }
    end
    
  end
end
