import 'dart:convert';

import 'package:app/models/obstacle.dart';
import 'package:app/models/robot.dart';

WorldList worldFromJson(String str) => WorldList.fromJson(json.decode(str));

String worldToJson(WorldList data) => json.encode(data.toJson());

class WorldList {
  WorldList({
    required this.obstacleList,
    required this.robotList,
    required this.visibility,
    required this.reload,
    required this.repair,
    required this.mine,
    required this.shields,
    required this.height,
    required this.width,
    required this.gun,
  });

  List<ObstacleList> obstacleList;
  List<RobotList> robotList;
  int visibility;
  int reload;
  int repair;
  int mine;
  int shields;
  int height;
  int width;
  int gun;

  factory WorldList.fromJson(Map<String, dynamic> json) => WorldList(
        obstacleList: List<ObstacleList>.from(
            json["obstacleList"].map((x) => ObstacleList.fromJson(x))),
        robotList: List<RobotList>.from(
            json["robotList"].map((x) => RobotList.fromJson(x))),
        visibility: json["visibility"],
        reload: json["reload"],
        repair: json["repair"],
        mine: json["mine"],
        shields: json["shields"],
        height: json["height"],
        width: json["width"],
        gun: json["gun"],
      );

  Map<String, dynamic> toJson() => {
        "obstacleList": List<dynamic>.from(obstacleList.map((x) => x.toJson())),
        "robotList": List<dynamic>.from(robotList.map((x) => x.toJson())),
        "visibility": visibility,
        "reload": reload,
        "repair": repair,
        "mine": mine,
        "shields": shields,
        "height": height,
        "width": width,
        "gun": gun,
      };
}
