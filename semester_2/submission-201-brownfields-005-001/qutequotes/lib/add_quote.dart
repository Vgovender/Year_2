import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:qute_quotes/main.dart';

import 'Model/quote_model.dart';
import 'Controller/connection.dart';

class AddQuote extends StatefulWidget {
  const AddQuote({Key? key}) : super(key: key);

  @override
  _AddQ createState() => _AddQ();
}

class _AddQ extends State<AddQuote> {
  final myController = TextEditingController();
  final myController2 = TextEditingController();

  @override
  void dispose() {
    // Clean up the controller when the widget is disposed.
    myController.dispose();
    myController2.dispose();
    super.dispose();
  }
  Future<Quote>? _futureAlbum;
  String a = "";
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Expanded(
            flex: 7,
            child: Container(
              padding: const EdgeInsets.all(0.0),
              decoration: BoxDecoration(
                color: Colors.black12,
                border: Border.all(width: 2,),
              ),
              child: Column(
                children: [
                  Expanded(
                    flex: 2,
                    child:Row(
                        children: [
                          const Expanded(flex: 8, child: Text('            Add Quote',
                            textAlign: TextAlign.center,
                            style: TextStyle(
                              fontSize: 25,
                              fontWeight: FontWeight.bold,),),),
                          Expanded(
                            flex: 2,
                            child: CloseButton(
                              onPressed: (){Navigator.push(context, MaterialPageRoute(builder: (context) => const Home()));},
                            ),
                          ),
                        ]
                    ),

                  ),
                  Expanded(
                    flex: 17,
                    child:Container(
                      padding: const EdgeInsets.all(50),
                      child: Column(
                          children: [
                            Expanded(
                              flex: 6,
                              child: Align(
                                alignment: Alignment.center,
                                child: TextField(
                                  controller: myController,
                                  keyboardType: TextInputType.multiline,
                                  maxLines: null,
                                  decoration: const InputDecoration(
                                    hintText: 'Enter Quote',
                                    border: OutlineInputBorder(
                                    ),
                                    labelText: "Enter Quote",
                                    fillColor: Colors.white,
                                  ),
                                ),
                              ),
                            ),
                            Expanded(
                              flex: 3,
                              child: Align(
                                alignment: Alignment.bottomCenter,
                                child: TextField(
                                  controller: myController2,
                                  keyboardType: TextInputType.multiline,
                                  maxLines: null,
                                  decoration: const InputDecoration(
                                    hintText: 'Enter Author',
                                    border: OutlineInputBorder(
                                    ),
                                    labelText: "Enter Author",
                                    fillColor: Colors.white,
                                  ),
                                  onChanged: (value){
                                  },
                                ),
                              ),
                            ),
                          ]
                      ),
                    ),
                  ),
                  Expanded(
                    flex: 5,
                    child:Row(
                        children: [
                          Expanded(flex: 5, child: FloatingActionButton(
                            heroTag: 'Add_btn2',
                            child: const Text('Add'),
                            onPressed: (){
                              setState(() {
                                print("Clicked");
                                _futureAlbum = createAlbum(myController.text,myController2.text);
                                Navigator.push(context, MaterialPageRoute(builder: (context) => const Home()));
                              });
                            },
                          ),
                          ),
                          Expanded(
                            flex: 5, child: FloatingActionButton(
                              child: const Text('Cancel'),
                            onPressed: (){Navigator.push(context, MaterialPageRoute(builder: (context) => const Home()));},
                            ),
                          ),
                        ]
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}