// To parse this JSON data, do
//
//     final quote = quoteFromJson(jsonString);

import 'dart:convert';

Quote quoteFromJson(String str) => Quote.fromJson(json.decode(str));

String quoteToJson(Quote data) => json.encode(data.toJson());

class Quote {
  Quote({
    required this.text,
    required this.name,
  });

  String text;
  String name;

  factory Quote.fromJson(Map<String, dynamic> json) => Quote(
    text: json["text"],
    name: json["name"],
  );

  Map<String, dynamic> toJson() => {
    "text": text,
    "name": name,
  };
}