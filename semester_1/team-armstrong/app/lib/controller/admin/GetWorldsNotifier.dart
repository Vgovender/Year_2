// ignore_for_file: file_names, avoid_print

import 'dart:convert';
import 'package:app/models/world.dart';
import 'package:flutter/widgets.dart';
import 'package:http/http.dart';

class GetWorldsNotifier with ChangeNotifier {
  List<WorldList> _worldList = [];

  get getWorlds {
    return [..._worldList];
  }

  emptyObstacles() {
    _worldList = [];
  }

  fetchByName() async {
    var baseUrl = Uri.parse('http://localhost:7000/world');
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
