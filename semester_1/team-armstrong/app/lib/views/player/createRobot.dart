// For player to set the name of their robot
// ignore_for_file: file_names, camel_case_types, deprecated_member_use

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'package:app/routes.dart';
import 'package:app/widgets/backButton.dart';
import 'package:app/controller/player/LaunchRobotNotifier.dart';

class createRobot extends StatelessWidget {
  const createRobot({Key? key}) : super(key: key);

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
            addRobot(),
            returnButton(),
            nextChoice(),
          ],
        ),
      ),
      debugShowCheckedModeBanner: false,
      onGenerateRoute: routePath,
      initialRoute: '/',
    );
  }
}

// Create a Form widget.
class addRobot extends StatefulWidget {
  const addRobot({Key? key}) : super(key: key);

  @override
  addRobotState createState() {
    return addRobotState();
  }
}

// Create a corresponding State class.
// This class holds data related to the form.
class addRobotState extends State<addRobot> {
  // Create a global key that uniquely identifies the Form widget
  // and allows validation of the form.
  //
  // Note: This is a GlobalKey<FormState>,
  // not a GlobalKey<MyCustomFormState>.
  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    // Build a Form widget using the _formKey created above.
    return Form(
      key: _formKey,
      child: Column(
        children: [
          TextFormField(
            decoration: const InputDecoration(
              filled: true,
              fillColor: Colors.white,
              hintText: "What is your robot's name?",
            ),
            // The validator receives the text that the user has entered.
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Please name your robot';
              }
              return null;
            },
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 16.0),
            child: ElevatedButton(
              onPressed: () {
                // const continueButton();
                if (_formKey.currentState!.validate()) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Processing robot...')),
                  );
                  Provider.of<LaunchRobotNotifier>(context, listen: true);
                }
              },
              child: const Text('Launch'),
            ),
          ),
        ],
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
              'https://i.pinimg.com/564x/c1/24/07/c1240701e3a51fbcc42ee76f563e3fe1.jpg'),
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

class nextChoice extends StatelessWidget {
  const nextChoice({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Container(
          alignment: Alignment.bottomRight,
          child: ElevatedButton(
            onPressed: () => Navigator.of(context).pushNamed('/world'),
            child: const Icon(Icons.arrow_forward),
          ),
        ),
      ],
    );
  }
}
