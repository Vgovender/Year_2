// ignore_for_file: file_names, avoid_print

import 'dart:convert';
import 'package:flutter/widgets.dart';
import 'package:http/http.dart';

import 'package:app/models/world.dart';

class SaveWorldNotifier with ChangeNotifier {
  List<WorldList> _worldList = [];

  get getWorld {
    return [..._worldList];
  }

  noWorlds() {
    _worldList = [];
  }

  fetchByName(String name) async {
    var baseUrl = Uri.parse('http://localhost:7000/admin/save/' + name);
    try {
      Response response = await post(baseUrl);
      String json = response.body;
      _worldList =
          (jsonDecode(json) as List).map((i) => WorldList.fromJson(i)).toList();
      notifyListeners();
    } catch (e) {
      print(e);
    }
  }
}
