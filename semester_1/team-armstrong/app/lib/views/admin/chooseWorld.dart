// // Select world to be manipulated by the admin

// ignore_for_file: file_names, unnecessary_const, camel_case_types

import 'package:app/widgets/addWorld.dart';
import 'package:flutter/material.dart';

import 'package:app/widgets/backButton.dart';
import 'package:app/routes.dart';

class chooseWorld extends StatelessWidget {
  const chooseWorld({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        // No appbar provided to the Scaffold, only a body with a
        // CustomScrollView.
        body: CustomScrollView(
          slivers: [
            // Add the app bar to the CustomScrollView.
            const SliverAppBar(
              // Provide a standard title.
              title: title(),
              backgroundColor: Colors.blueGrey,
              // Allows the user to reveal the app bar if they begin scrolling
              // back up the list of items.
              floating: true,
              // Display a placeholder widget to visualize the shrinking size.
              // Make the initial height of the SliverAppBar larger than normal.
              expandedHeight: 60,
            ),
            // Next, create a SliverList
            SliverList(
              // Use a delegate to build items as they're scrolled on screen.
              delegate: SliverChildBuilderDelegate(
                // The builder function returns a ListTile with a title that
                // displays the index of the current item.
                (context, index) => ListTile(title: Text('World #$index')),
                // Builds 1000 ListTiles
                childCount: 1000,
              ),
            ),
          ],
        ),
        bottomNavigationBar: const addWorld(),
      ),
      debugShowCheckedModeBanner: false,
      onGenerateRoute: routePath,
      initialRoute: '/',
    );
  }
}

class title extends StatelessWidget {
  const title({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(
      children: const <Widget>[
        Expanded(
          child: returnButton(),
        ),
        Expanded(
          child: Text("Choose a world", textAlign: TextAlign.left),
        ),
      ],
    );
  }
}
