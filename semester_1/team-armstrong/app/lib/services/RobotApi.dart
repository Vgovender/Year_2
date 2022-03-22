// ignore_for_file: file_names

import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

import 'package:app/models/robot.dart';

// Connecting API to robots

Future<List<RobotList>> fetchRobots() async {
  final response = await http.get(Uri.parse('http://127.0.0.1:5000/'));

  if (response.statusCode == 200) {
    // If the server did return a 200 OK response,
    // then parse the JSON.
    // return ObstacleList.fromJson(jsonDecode(response.body));
    List responseJson = json.decode(response.body);
    return responseJson.map((data) => RobotList.fromJson(data)).toList();
  } else {
    // If the server did not return a 200 OK response,
    // then throw an exception.
    throw Exception('Failed to load');
  }
}
