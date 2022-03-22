// To parse this JSON data, do
//
//     final world = worldFromJson(jsonString);

class ObstacleList {
  ObstacleList({
    required this.size,
    required this.bottomLeftY,
    required this.bottomLeftX,
  });

  int size;
  int bottomLeftY;
  int bottomLeftX;

  factory ObstacleList.fromJson(Map<String, dynamic> json) => ObstacleList(
        size: json["size"],
        bottomLeftY: json["bottomLeftY"],
        bottomLeftX: json["bottomLeftX"],
      );

  Map<String, dynamic> toJson() => {
        "size": size,
        "bottomLeftY": bottomLeftY,
        "bottomLeftX": bottomLeftX,
      };
}
