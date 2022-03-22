// ignore_for_file: file_names, camel_case_types

import 'package:flutter/material.dart';

class returnButton extends StatelessWidget {
  const returnButton({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Container(
          alignment: Alignment.bottomLeft,
          child: ElevatedButton(
            onPressed: () => Navigator.of(context).pushNamed('/homepage'),
            child: const Icon(Icons.arrow_back),
          ),
        ),
      ],
    );
  }
}
