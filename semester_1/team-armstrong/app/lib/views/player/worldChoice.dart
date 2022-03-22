// ignore_for_file: file_names, camel_case_types
// Layout for choosing which world to launch robot into
// Listview with the world name - click to see world details
// If player wishes to launch robot in that world a button should be pushed
// Else there should be an option to go back to the list of worlds

import 'package:flutter/material.dart';

import 'package:app/widgets/addWorld.dart';
import 'package:app/routes.dart';

class worldChoice extends StatelessWidget {
  const worldChoice({Key? key}) : super(key: key);

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
              title: choosePort(),
              backgroundColor: Colors.blueGrey,
              // Allows the user to reveal the app bar if they begin scrolling
              // back up the list of items.
              floating: true,
              // Display a placeholder widget to visualize the shrinking size.
              // Make the initial height of the SliverAppBar larger than normal.
              expandedHeight: 70,
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

class choosePort extends StatelessWidget {
  const choosePort({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          alignment: Alignment.topLeft,
          padding: const EdgeInsets.only(left: 65.0, right: 70.0, bottom: 0.0),
          child: TextFormField(
            decoration: const InputDecoration(
              filled: true,
              fillColor: Colors.white,
              hintText: "Which port?",
            ),
          ),
        ),
        Container(
          alignment: Alignment.topLeft,
          padding: const EdgeInsets.only(bottom: 19.5),
          child: ElevatedButton(
            onPressed: () => Navigator.of(context).pushNamed('/player'),
            child: const Icon(Icons.arrow_back),
          ),
        ),
      ],
    );
  }
}
