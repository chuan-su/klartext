# coding: utf-8
require 'klartext'

Klartext::User.find_or_create_by(name: 'chuan') do |user|
  user.email = 'chuan.su@outlook.com'
  user.password = 'China2017'
end

post = Klartext::Post.find_or_create_by(body: 'This is a post') do |post|
  post.interp = 'post interp'
  post.created_by = 1
end

(1..10).each do |index|
  body = "comment#{index}"
  Klartext::Comment.find_or_create_by(body: body) do |comment|
    comment.created_by = 1
  end
end

[
  {ancestor_id: 1,descendant_id: 1, parent_id: 1,length: 0,ancestor_type: 'post',descendant_type: 'post'},
  {ancestor_id: 1,descendant_id: 2, parent_id: 1,length: 1,ancestor_type: 'post',descendant_type: 'comment'},
  {ancestor_id: 1,descendant_id: 3, parent_id: 1,length: 1,ancestor_type: 'post',descendant_type: 'comment'},
  {ancestor_id: 1,descendant_id: 4, parent_id: 3,length: 2,ancestor_type: 'post',descendant_type: 'comment'},
  {ancestor_id: 1,descendant_id: 5, parent_id: 2,length: 2,ancestor_type: 'post',descendant_type: 'comment'},

  {ancestor_id: 2,descendant_id: 2, parent_id: 1,length: 0,ancestor_type: 'comment',descendant_type: 'comment'},
  {ancestor_id: 2,descendant_id: 5, parent_id: 2,length: 1,ancestor_type: 'comment',descendant_type: 'comment'},
  {ancestor_id: 5,descendant_id: 5, parent_id: 2,length: 0,ancestor_type: 'comment',descendant_type: 'comment'},

  {ancestor_id: 3,descendant_id: 3, parent_id: 1,length: 0,ancestor_type: 'comment',descendant_type: 'comment'},
  {ancestor_id: 3,descendant_id: 4, parent_id: 3,length: 1,ancestor_type: 'comment',descendant_type: 'comment'},
  {ancestor_id: 4,descendant_id: 4, parent_id: 3,length: 0,ancestor_type: 'comment',descendant_type: 'comment'},
].each do |path|
  comment = Klartext::CommentTreePath.find_or_initialize_by(ancestor_id: path[:ancestor_id], descendant_id: path[:descendant_id])

  comment.length = path[:length]
  comment.parent_id = path[:parent_id]
  comment.ancestor_type = path[:ancestor_type]
  comment.descendant_type = path[:descendant_type]
  comment.save
end
