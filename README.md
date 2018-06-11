# Flutter Palette

Palette implementation for Flutter. It uses native iOS and Android implementations.

- iOS implementation uses: https://github.com/shnhrrsn/ImagePalette
- Android implementation uses Android Support palette-v7 [docs](https://developer.android.com/reference/android/support/v7/graphics/Palette)

# Known issues

- iOS library has not been updated to Swift 4.0 (it uses an old SwiftPriorityQueue version)
 - There is a [pull request](https://github.com/shnhrrsn/ImagePalette/pull/7) open for that.
 - Temporary solution: Run the project in xcode and fix the 5 errors that pop up, unlocking the library files. Sorry, I'm not an experienced iOS developer, perhaps there are better solutions and I am open to suggestions. Thank you!
 - Eg. `swap(&heap[A], &heap[B])` becomes `heap.swapAt(A, B)`

```
public var hashValue: Int {
    let maxInt = Int64(Int32.max)
    return Int((31 * self.color.hashValue + self.population) % maxInt)
}
```
becomes:
```
public var hashValue: Int {
    let maxInt = Int(exactly: Int32.max)!
    return (31 * self.color.hashValue).advanced(by: Int(self.population)) % maxInt
}
```

- Library is not published. I'll do this soon. In the meantime use this repo as inspiration

## Getting Started

- There is a small example app in this repo.