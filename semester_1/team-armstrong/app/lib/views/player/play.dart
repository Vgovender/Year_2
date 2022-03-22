// For gameplay view...

import 'package:flutter/material.dart';
import 'package:app/routes.dart';

class Play extends StatelessWidget {
  const Play({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Stack(
          alignment: Alignment.center,
          children: const <Widget>[
            // ignore: prefer_const_constructors
            Scaffold(
              body: BackgroundImage(),
            ),
          ],
        ),
      ),
      debugShowCheckedModeBanner: false,
      onGenerateRoute: routePath,
      initialRoute: '/',
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
              'https://i.pinimg.com/564x/61/8b/54/618b540c0b1c9f2fae7a8350f5348937.jpg'),
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
