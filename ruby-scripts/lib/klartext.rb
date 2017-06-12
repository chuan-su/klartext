require "klartext/version"
require 'klartext/klartext_base'
require 'klartext/klartext_es'
require 'klartext/xml_stream'
require 'klartext/dictionary'

require 'pry'

module Klartext
  ES_CONFIG_DIR = File.join(Dir.pwd,'es')
end
