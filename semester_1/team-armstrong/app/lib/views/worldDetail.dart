// ignore_for_file: file_names, camel_case_types

import 'package:app/controller/admin/GetWorldsNotifier.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class worldDetail extends StatelessWidget {
  const worldDetail({
    Key? key,
  }) : super(key: key);

  String get name => "";

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('CuteQuotes'),
        ),
        body: Card(
          elevation: 8.0,
          margin: const EdgeInsets.symmetric(
            horizontal: 10.0,
            vertical: 6.0,
          ),
          child: Container(
            decoration:
                const BoxDecoration(color: Color.fromRGBO(64, 75, 96, .9)),
            child: ListTile(
                contentPadding: const EdgeInsets.symmetric(
                    horizontal: 20.0, vertical: 10.0),
                title: Text(
                    context.watch<GetWorldsNotifier>().fetchByName(name),
                    style: const TextStyle(
                        color: Colors.white, fontWeight: FontWeight.bold)),
                subtitle: Row(children: <Widget>[
                  Expanded(
                    flex: 4,
                    child: Padding(
                        padding: const EdgeInsets.only(left: 10.0),
                        child: Text(
                          context.watch<GetWorldsNotifier>().fetchByName(name),
                          textAlign: TextAlign.end,
                          style: const TextStyle(color: Colors.white),
                        )),
                  )
                ])),
          ),
        ));
  }
}
