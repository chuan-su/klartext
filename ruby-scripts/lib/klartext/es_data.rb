# coding: utf-8
module Klartext; end
module Klartext::ESData; end

module Klartext::ESData
  module PostDoc
    def as_json(options = nil)
      {
        body: self.body,
        interp: self.interp,
        created_at: self.created_at.strftime("%Y-%m-%dT%H:%M:%S"),
        updated_at: self.updated_at.strftime("%Y-%m-%dT%H:%M:%S"),
        user: {
          id: self.user.id,
          name: self.user.name,
          created_at: self.user.created_at.strftime("%Y-%m-%dT%H:%M:%S")
        }
      }
    end
  end
end

module Klartext::ESData
  def sync_posts
    Klartext::Post.find_in_batches(batch_size: 500).with_index do |group,batch|
      data = group.reduce([]) do |result,post|
        result << {index: {_index: 'klartext',_type: 'post',_id: post.id.to_s }}
        post.extend PostDoc
        result << post.as_json
        result
      end
      @client.bulk body: data
    end
  end

  def sync_post(post)
    if post.is_a?(Klartext::Post)
      post.extend PostDoc
      @client.index index: 'klartext',type: "post",id: post.id.to_s, body: post.as_json
    else
      raise 'cannot index,invalid data type'
    end
  end

  def sync_dictionary
  end
  
end
