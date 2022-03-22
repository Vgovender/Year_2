// ignore_for_file: file_names, avoid_print

import 'dart:convert';
import 'package:flutter/widgets.dart';
import 'package:http/http.dart';

import 'package:app/models/robot.dart';

class GetRobotNotifier with ChangeNotifier {
  List<RobotList> _robotList = [];

  get getRobots {
    return [..._robotList];
  }

  noRobots() {
    _robotList = [];
  }

  fetchByName(String name) async {
    var baseUrl = Uri.parse('http://localhost:7000/admin/robots');
    try {
      Response response = await get(baseUrl);
      String json = response.body;
      _robotList =
          (jsonDecode(json) as List).map((i) => RobotList.fromJson(i)).toList();
      notifyListeners();
    } catch (e) {
      print(e);
    }
  }
}
