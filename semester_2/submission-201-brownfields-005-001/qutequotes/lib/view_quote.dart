import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
// import 'quote.dart';
import 'Model/quote_model.dart';

class ViewQuote extends StatelessWidget{

  final Quote QuoteData;
  const ViewQuote({Key? key,required this.QuoteData}): super(key:key);


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[900],
      floatingActionButtonLocation: FloatingActionButtonLocation.centerTop,
      appBar: AppBar(
          title: const Text("QuteQuotes"),
          titleTextStyle: const TextStyle(color: Colors.white,fontSize: 48.0,fontWeight: FontWeight.bold),
          centerTitle: true,
          backgroundColor: Colors.grey[850],
          elevation: 50.0
        // shadowColor: Colors.transparent,
      ),
      body: Container(
        margin: const EdgeInsets.fromLTRB(30.0, 5.0, 30.0, 30.0),
        padding: const EdgeInsets.all(10.0),
        decoration: BoxDecoration(
          border: Border.all(
            width: 3,
          ),
        ),
        child: SizedBox.expand(
          child: Wrap(
            children: <Widget>[
              Text(
                QuoteData.text+"\n -"+QuoteData.name,
                style: const TextStyle(
                  fontSize: 50,
                  color: Colors.white70
                ),
              ),
            ]
          ),
        ),
      ),
      bottomNavigationBar: FloatingActionButton.extended(
        backgroundColor: Colors.white10,
        label: const Text('Close'),
        shape:const RoundedRectangleBorder(),
        onPressed: () {
          Navigator.maybePop(context);
        },
      ),
    );
  }
}