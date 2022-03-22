// import 'dart:convert';
// import 'dart:io';

import 'package:flutter/material.dart';
import 'add_quote.dart';
import 'Controller/connection.dart';
// import 'quote.dart';
import 'view_quote.dart';
import '/Model/quote_model.dart';

void main() => runApp(const MaterialApp(
    home: Home()
  )
);

class Home extends StatefulWidget {

  const Home({Key? key}) : super(key: key);

  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {

  List<Quote> fuckQuotes = [];
  late Future<List<Quote>> futureAlbum;

  @override
  void initState() {
    super.initState();
    futureAlbum = fetchAlbum();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[900],
        appBar: AppBar(
          title: const Text("QuteQuotes"),
          titleTextStyle: const TextStyle(color: Colors.white,fontSize: 48.0,fontWeight: FontWeight.bold),
          centerTitle: true,
          backgroundColor: Colors.grey[850],
          elevation: 50.0
          // shadowColor: Colors.transparent,
        ),
        body: FutureBuilder<List<Quote>>(
          future: futureAlbum,
          builder: (context, snapshot) {
              // print("OOOOOO");
            if (snapshot.connectionState==ConnectionState.done) {
              // print("KKKKK");
              fuckQuotes = snapshot.data ?? [];
              // print(snapshot.data);
              // print(fuckQuotes);
              return
                Scrollbar(
                  child: ListView.builder(
                    itemCount: fuckQuotes.length,
                    itemBuilder: (context, index){
                      return Card(
                        margin: const EdgeInsets.fromLTRB(20.0, 20.0, 20.0, 0.0),
                        child: GestureDetector(
                          onTap: (){
                            Navigator.push(
                                context,
                                MaterialPageRoute(builder: (context) =>  ViewQuote(QuoteData: (Quote(text: fuckQuotes[index].text, name: fuckQuotes[index].name))))
                            );
                          },
                          child: Padding(
                            padding: const EdgeInsets.all(12.0),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.stretch,
                              children: <Widget>[
                                Text(
                                    fuckQuotes[index].text,
                                    style: const TextStyle(
                                      fontSize: 18.0,
                                      color: Colors.black,
                                    )
                                ),
                                const SizedBox(height: 6.0),
                                Text(
                                  fuckQuotes[index].name,
                                  style: const TextStyle(
                                    fontSize: 14.0,
                                    color: Colors.black,
                                  ),
                                )
                              ],
                            ),
                          ),
                        ),
                      );
                    }
                    ),
                );
            } else if (snapshot.hasError) {
              // print("ERROR");
              return Text('${snapshot.error}');
            }
          //      print("LOADING");
            // By default, show a loading spinner.
            return const CircularProgressIndicator();
          },
        ),
      bottomNavigationBar: FloatingActionButton.extended(
        heroTag: 'Add_btn',
        label: const Text(
          'Add Quote',
          style: TextStyle(
            color: Colors.black45,
            fontSize: 20,
          ),
        ),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(5),
        ),
        onPressed: (){
          Navigator.push(context, MaterialPageRoute(builder: (context) => const AddQuote()));
        },
      ),
    );
  }
}