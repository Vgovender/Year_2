// ignore_for_file: camel_case_types, file_names, unnecessary_null_comparison, unnecessary_new, avoid_print

import 'package:app/controller/admin/GetWorldsNotifier.dart';
import 'package:app/controller/admin/SaveWorldNotifier.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'package:app/views/worldDetail.dart';

void main() => runApp(MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => SaveWorldNotifier()),
        ChangeNotifierProvider(create: (_) => GetWorldsNotifier()),
      ],
      child: const worldListView(),
    ));

class worldListView extends StatefulWidget {
  const worldListView({Key? key}) : super(key: key);

  @override
  _worldListViewState createState() => _worldListViewState();
}

class _worldListViewState extends State<worldListView> {
  @override
  Widget build(BuildContext context) {
    GetWorldsNotifier worlds = Provider.of<GetWorldsNotifier>(context);
    return MaterialApp(
        home: Scaffold(
      body: (worlds == null)
          ? const Center(child: CircularProgressIndicator())
          : ListView.builder(
              scrollDirection: Axis.vertical,
              shrinkWrap: true,
              itemCount: worlds.getWorlds.length,
              itemBuilder: (context, int index) {
                return Card(
                  elevation: 8.0,
                  margin: const EdgeInsets.symmetric(
                    horizontal: 10.0,
                    vertical: 6.0,
                  ),
                  child: Container(
                    decoration: const BoxDecoration(
                        color: Color.fromRGBO(64, 75, 96, .9)),
                    child: ListTile(
                        contentPadding: const EdgeInsets.symmetric(
                            horizontal: 20.0, vertical: 10.0),
                        title: Consumer<GetWorldsNotifier>(
                          builder: (_, notifier, __) => Text(
                              notifier.getWorlds[index].fetchByName,
                              style: const TextStyle(
                                  color: Colors.white,
                                  fontWeight: FontWeight.bold)),
                        ),
                        subtitle: Row(children: <Widget>[
                          Expanded(
                            flex: 4,
                            child: Padding(
                                padding: const EdgeInsets.only(left: 10.0),
                                child: Consumer<GetWorldsNotifier>(
                                    builder: (_, notifier, __) => Text(
                                          notifier.getWorlds[index].fetchByName,
                                          textAlign: TextAlign.end,
                                          style: const TextStyle(
                                              color: Colors.white),
                                        ))),
                          )
                        ]),
                        onTap: () {
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => const worldDetail()));
                        }),
                  ),
                );
              }),
    ));
  }
}
