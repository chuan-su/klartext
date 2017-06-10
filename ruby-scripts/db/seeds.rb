# coding: utf-8
require 'klartext'

Klartext::User.find_or_create_by(name: 'chuan') do |user|
  user.email = 'chuan.su@outlook.com'
  user.password = 'China2017'
end

(0..20).each do |num|
  Klartext::Post.find_or_create_by(body: "svenska_#{num}") do |post|
    post.translation = "english_#{num}"
    post.created_by = Klartext::User.first.id
  end
end
