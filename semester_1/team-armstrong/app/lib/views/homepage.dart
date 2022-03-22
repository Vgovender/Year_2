import 'package:flutter/material.dart';
import 'package:app/routes.dart';

// ignore_for_file: camel_case_types

class homepage extends StatelessWidget {
  const homepage({Key? key}) : super(key: key);

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
            BottomSheet(),
          ],
        ),
      ),
      debugShowCheckedModeBanner: false,
      onGenerateRoute: routePath,
      initialRoute: '/',
    );
  }
}

class BottomSheet extends StatelessWidget {
  const BottomSheet({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: ElevatedButton(
        child: const Icon(Icons.menu),
        onPressed: () {
          showModalBottomSheet<void>(
            context: context,
            builder: (BuildContext context) {
              return SizedBox(
                height: 100,
                child: Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      ListTile(
                        leading: const Icon(Icons.accessibility_new_rounded),
                        title: const Text('Player'),
                        onTap: () => Navigator.of(context).pushNamed('/player'),
                      ),
                      ListTile(
                        leading: const Icon(Icons.admin_panel_settings_rounded),
                        title: const Text('Admin'),
                        onTap: () => Navigator.of(context).pushNamed('/admin'),
                      ),
                    ],
                  ),
                ),
              );
            },
          );
        },
      ),
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
