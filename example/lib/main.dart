import 'dart:io';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:image_picker/image_picker.dart';
import 'package:palette/palette.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _infoToShow = 'Unknown';

  @override
  void initState() {
    super.initState();
    getImageAndPalette();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> getImageAndPalette() async {
    File imageFile = await ImagePicker.pickImage(source: ImageSource.gallery);
    Palette palette;
    palette = await PaletteLib.getPalette(imageFile.path);

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _infoToShow = palette.toString();
    });
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
        appBar: new AppBar(
          title: const Text('Palette Plugin example app'),
        ),
        body: new Center(
          child: new Text('Palette: $_infoToShow\n'),
        ),
      ),
    );
  }
}
