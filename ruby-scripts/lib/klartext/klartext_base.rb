# coding: utf-8
require "active_record"
require "csv"

module Klartext

  class User < ActiveRecord::Base
  end

  class Post < ActiveRecord::Base
    belongs_to :user, foreign_key: :created_by

    def self.import(file)
      CSV.foreach(file, headers: true,header_converters: :symbol,col_sep: ",") do |row|
        post = Post.create! body: row[:body].gsub(/\R+/,''),interp: row[:interp],created_by: 1
        yield post if block_given?
      end
    end
  end
  class Comment < ActiveRecord::Base
  end

  class CommentTreePath < ActiveRecord::Base
  end
end
