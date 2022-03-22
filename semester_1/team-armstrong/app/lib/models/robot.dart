import 'package:app/models/real_position.dart';

class RobotList {
  RobotList({
    required this.name,
    required this.position,
    required this.currentDirection,
    required this.shots,
    required this.shields,
    required this.state,
    required this.repair,
    required this.realPosition,
    required this.direction,
  });

  String name;
  List<int> position;
  String currentDirection;
  int shots;
  int shields;
  String state;
  int repair;
  RealPosition realPosition;
  String direction;

  factory RobotList.fromJson(Map<String, dynamic> json) => RobotList(
        name: json["name"],
        position: List<int>.from(json["position"].map((x) => x)),
        currentDirection: json["currentDirection"],
        shots: json["shots"],
        shields: json["shields"],
        state: json["state"],
        repair: json["repair"],
        realPosition: RealPosition.fromJson(json["realPosition"]),
        direction: json["direction"],
      );

  Map<String, dynamic> toJson() => {
        "name": name,
        "position": List<dynamic>.from(position.map((x) => x)),
        "currentDirection": currentDirection,
        "shots": shots,
        "shields": shields,
        "state": state,
        "repair": repair,
        "realPosition": realPosition.toJson(),
        "direction": direction,
      };
}
