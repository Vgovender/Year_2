// ignore_for_file: camel_case_types, file_names
// import 'package:flutter/cupertino.dart';

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'package:app/controller/admin/SaveWorldNotifier.dart';

class addWorld extends StatelessWidget {
  const addWorld({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: FloatingActionButton(
        child: const Icon(Icons.add_circle_outline),
        onPressed: () {
          showAlertDialog(context);
        },
      ),
    );
  }
}

showAlertDialog(BuildContext context) {
  AlertDialog alert = const AlertDialog(
    title: Text('Create a new world...'),
    actions: [
      worldForm(),
    ],
  );

  showDialog(
    context: context,
    builder: (BuildContext context) {
      return alert;
    },
  );
}

class worldForm extends StatefulWidget {
  const worldForm({Key? key}) : super(key: key);

  @override
  worldFormState createState() {
    return worldFormState();
  }
}

// Create a corresponding State class.
// This class holds data related to the form.
class worldFormState extends State<worldForm> {
  // Create a global key that uniquely identifies the Form widget
  // and allows validation of the form.
  //
  // Note: This is a GlobalKey<FormState>,
  // not a GlobalKey<MyCustomFormState>.
  final _formKey = GlobalKey<FormState>();

  String? get name => null;

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
              hintText: "What is your world called?",
            ),
            // The validator receives the text that the user has entered.
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Please name your world';
              }
              return null;
            },
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 16.0),
            child: ElevatedButton(
              onPressed: () {
                if (_formKey.currentState!.validate()) {
                  Navigator.of(context).pop();
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Discovering world...')),
                  );
                  Provider.of<SaveWorldNotifier>(context, listen: false)
                      .fetchByName(name!);
                }
              },
              child: const Text('Submit world'),
            ),
          ),
        ],
      ),
    );
  }
}
