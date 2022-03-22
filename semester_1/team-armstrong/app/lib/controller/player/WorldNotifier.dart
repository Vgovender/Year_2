// ignore_for_file: file_names, avoid_print

import 'dart:convert';
import 'package:flutter/widgets.dart';
import 'package:http/http.dart';

import 'package:app/models/obstacle.dart';

class WorldNotifier with ChangeNotifier {
  List<ObstacleList> _obstacleList = [];



  get getObstacles {
    return [..._obstacleList];
  }

  emptyObstacles() {
    _obstacleList = [];
  }

  fetchByName(String name) async { 
    var baseUrl = Uri.parse('http://localhost:7000/user/load/' + name);
    try {
      Response response = await get(baseUrl);
      String json = response.body;
      _obstacleList = (jsonDecode(json) as List)
          .map((i) => ObstacleList.fromJson(i))
          .toList();
      notifyListeners();
    } catch (e) {
      print(e);
    }
  }
}
