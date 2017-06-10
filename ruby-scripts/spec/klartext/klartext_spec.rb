require "spec_helper"

describe Klartext do
  it "has a version number" do
    expect(Klartext::VERSION).not_to be nil
  end

  it "autocomplete word" do
    client = Elasticsearch::Client.new host: 'localhost:9200'
    results = client.search index: 'klartext',
                  type: 'word',
                  body: {
                    query: {
                      multi_match: {
                        query: 'lagt',
                        fields: ["value","inflection","translation"]
                      }
                    },
                    highlight: {
                      fields: {
                        "*" => {force_source: true}
                      }
                    }
                  }
    
  end
end
