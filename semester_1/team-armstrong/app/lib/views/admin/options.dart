// For admin functions...

import 'package:flutter/material.dart';
import 'package:app/routes.dart';

// ignore_for_file: camel_case_types

class options extends StatelessWidget {
  const options({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Stack(
          alignment: Alignment.center,
          children: const <Widget>[
            Scaffold(
              body: BackgroundImage(),
            ),
            returnButton(),
          ],
        ),
      ),
      debugShowCheckedModeBanner: false,
      onGenerateRoute: routePath,
      initialRoute: '/',
    );
  }
}

class returnButton extends StatelessWidget {
  const returnButton({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Container(
          alignment: Alignment.bottomLeft,
          child: ElevatedButton(
            onPressed: () => Navigator.of(context).pushNamed('/admin'),
            child: const Text("Back"),
          ),
        ),
      ],
    );
  }
}

class BackgroundImage extends StatelessWidget {
  const BackgroundImage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: const Color(0xff7c94b6),
        image: const DecorationImage(
          image: NetworkImage(
              'https://i.pinimg.com/736x/18/83/f4/1883f4a4b161ea99f6cf48f7c7d4b789.jpg'),
          fit: BoxFit.cover,
        ),
        border: Border.all(
          color: Colors.black,
          width: 8,
        ),
        borderRadius: BorderRadius.circular(12),
      ),
    );
  }
}
