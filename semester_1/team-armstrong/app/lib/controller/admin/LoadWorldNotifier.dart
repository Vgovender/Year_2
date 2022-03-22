// ignore_for_file: file_names, avoid_print

import 'dart:convert';
import 'package:flutter/widgets.dart';
import 'package:http/http.dart';

import 'package:app/models/world.dart';

class LoadWorldNotifier with ChangeNotifier {
  List<WorldList> _worldList = [];

  get getWorlds {
    return [..._worldList];
  }

  noWorlds() {
    _worldList = [];
  }

  fetchByName(String name) async {
    var baseUrl = Uri.parse('http://127.0.0.1:7000/admin/load/' + name);
    try {
      Response response = await get(baseUrl);
      String json = response.body;
      _worldList =
          (jsonDecode(json) as List).map((i) => WorldList.fromJson(i)).toList();
      notifyListeners();
    } catch (e) {
      print(e);
    }
  }
}
