import 'package:flutter/material.dart';

import 'package:app/views/homepage.dart';
import 'package:app/views/player/createRobot.dart';
// import 'package:app/views/player/play.dart';
import 'package:app/views/player/worldChoice.dart';
import 'package:app/views/admin/chooseWorld.dart';
// import 'package:app/views/admin/options.dart';

MaterialPageRoute routePath(RouteSettings settings) {
  switch (settings.name) {
    case '/':
    case '/home':
      return MaterialPageRoute(builder: (context) => const homepage());
    case '/player':
      return MaterialPageRoute(builder: (context) => const createRobot());
    case '/admin':
      return MaterialPageRoute(builder: (context) => const chooseWorld());
    // case '/options':
    //   return MaterialPageRoute(builder: (context) => const options());
    // case '/play':
    //   return MaterialPageRoute(builder: (context) => const Play());
    case '/world':
      return MaterialPageRoute(builder: (context) => const worldChoice());
    default:
      return MaterialPageRoute(builder: (context) => const homepage());
  }
}
