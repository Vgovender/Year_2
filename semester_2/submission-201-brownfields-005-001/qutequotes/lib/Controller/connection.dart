import 'dart:convert';

import "package:http/http.dart" as http;
import 'package:qute_quotes/Model/quote_model.dart';

Future<List<Quote>> fetchAlbum() async{
  var getResponse = await http.get(Uri.parse('http://localhost:5000/quotes',));

  if (getResponse.statusCode == 200){

    List listOfQuotes = json.decode(getResponse.body);
    print("LLLLL");
    return listOfQuotes.map((e) => Quote.fromJson(e)).toList();
  }
  else{
    print("PPPPP");
    throw Exception("Failed to load quotes");
  }
}

Future<Quote> createAlbum(String text,name) async {
  final response = await http.post(
    Uri.parse('http://localhost:5000/quotes'),
    headers: <String, String>{
      'Content-Type': 'application/json',
    },
    body: jsonEncode(<String, String>{
      'text': text,
      'name': name,
    }),
  );
  if (response.statusCode == 201) {
    // If the server did return a 201 CREATED response,
    // then parse the JSON.
    return Quote.fromJson(jsonDecode(response.body));
  } else {
    // If the server did not return a 201 CREATED response,
    // then throw an exception.
    print(response.statusCode);
    print("VVV");
    throw Exception('Failed to create album.');
  }

}