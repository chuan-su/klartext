# coding: utf-8
require 'klartext'

Klartext::User.find_or_create_by(name: 'chuan') do |user|
  user.email = 'chuan.su@outlook.com'
  user.password = 'China2017'
end
