#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#
Pod::Spec.new do |s|
  s.name             = 'palette'
  s.version          = '0.0.1'
  s.summary          = 'Palette implementation for Flutter. It uses native iOS and Android implementations'
  s.description      = <<-DESC
Palette implementation for Flutter. It uses native iOS and Android implementations
                       DESC
  s.homepage         = 'https://postmuseapp.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Andrei from PostMuse' => 'noreply@postmuseapp.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  s.dependency 'ImagePalette'

  s.ios.deployment_target = '8.0'
end

