# coding: utf-8
require 'json'
require 'elasticsearch'

module Klartext; end

class Klartext::ESClient
      
  def initialize
    @client = Elasticsearch::Client.new host: 'elasticsearch'
  end
  
  def create_indices(*indices)
    indices.each do |index|
      data = setting_body index
      @client.indices.create index: index, body: data
    end
  end

  def delete_indices(*indices)
    indices.each do |index|
      @client.indices.delete index: index if index_exists?(index)
    end
  end

  def index_exists?(index)
    @client.indices.exists? index: index
  end

  def put_mapping(index,types = [])
    types.each do |type|
      data = mapping_body type
      @client.indices.put_mapping index: index, type: type, body: data
    end
  end
  
  private

  def setting_body(index)
    data = JSON.parse File.read(File.join(Klartext::ES_CONFIG_DIR,"index_#{index}_setting.json"))
    symbolize_keys data
  end

  def mapping_body(type)
    data = JSON.parse File.read(File.join(Klartext::ES_CONFIG_DIR,"type_#{type}_mapping.json"))
    symbolize_keys data
  end
  
  def symbolize_keys(hash)
    hash.inject({}){|memo,(k,v)| memo[k.to_sym] = v; memo}
  end

end
