# Flutter Palette

Palette implementation for Flutter. It uses native iOS and Android implementations.

```
Palette palette = await PaletteLib.getPalette(imageFile.path);
print(palette.vibrant);
```

# DEPRECATED - There's something better out there
Use https://pub.dartlang.org/packages/palette_generator instead of this. 

While we still use this implementation in our app and will keep maintaining it for now, the `palette_generator` plugin is really cool and fully written in Dart. We intend to move our own app to use it and consolidate any bug fixing in one good plugin instead of many smaller, not so well maintained plugins. You can still look around this repo if you want to see a decent example for creating an async, computation heavy plugin example for Flutter.

# Native implementation
- iOS implementation uses: https://github.com/shnhrrsn/ImagePalette
- Android implementation uses Android Support palette-v7 [docs](https://developer.android.com/reference/android/support/v7/graphics/Palette)

# Known issues
- Library is not published. You can use it by declaring it as a git dependency in your pubspec
- iOS library has not been updated to Swift 4.0 (it uses an old SwiftPriorityQueue version). We manually fixed the few issues reported by xcode on our machines. This is not ideal. There is a [pull request](https://github.com/shnhrrsn/ImagePalette/pull/7) open for that.

## Getting Started

- There is a [small example app](https://github.com/andreidiaconu/FlutterPalette/blob/master/example/lib/main.dart) in this repo.

## License

This repo and library is published under [Apache License 2.0](https://github.com/andreidiaconu/FlutterPalette/blob/master/LICENSE). Feel free to use it.
