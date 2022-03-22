class RealPosition {
  RealPosition({
    required this.x,
    required this.y,
  });

  int x;
  int y;

  factory RealPosition.fromJson(Map<String, dynamic> json) => RealPosition(
        x: json["x"],
        y: json["y"],
      );

  Map<String, dynamic> toJson() => {
        "x": x,
        "y": y,
      };
}
