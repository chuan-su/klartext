# coding: utf-8
lib = File.expand_path('../lib', __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.include?(lib)
require 'klartext/version'

Gem::Specification.new do |spec|
  spec.name          = "klartext-cli"
  spec.version       = Klartext::VERSION
  spec.authors       = ["Chuan"]
  spec.email         = ["chuansu@icloud.com"]

  spec.summary       = %q{Cli tool for Klartext-spring}
  spec.description   = %q{include migration tasks}
  spec.homepage      = "https://github.com/chuan-su/klartext-spring"
  spec.license       = "MIT"

  # Prevent pushing this gem to RubyGems.org. To allow pushes either set the 'allowed_push_host'
  # to allow pushing to a single host or delete this section to allow pushing to any host.
  if spec.respond_to?(:metadata)
    spec.metadata['allowed_push_host'] = "TODO: Set to 'http://mygemserver.com'"
  else
    raise "RubyGems 2.0 or newer is required to protect against " \
      "public gem pushes."
  end

  spec.bindir        = "exe"
  spec.executables   = spec.files.grep(%r{^exe/}) { |f| File.basename(f) }
  spec.require_paths = ["lib"]

  spec.add_dependency 'mysql2'
  spec.add_dependency "activerecord"
  spec.add_dependency 'standalone_migrations'
  spec.add_dependency 'elasticsearch'
  spec.add_dependency 'excon'
  
  spec.add_development_dependency "bundler", "~> 1.13"
  spec.add_development_dependency "rake", "~> 10.0"
  spec.add_development_dependency "rspec", "~> 3.0"
  spec.add_development_dependency "pry"

end
