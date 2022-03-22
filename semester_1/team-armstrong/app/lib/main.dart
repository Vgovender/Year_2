import 'package:app/controller/admin/DeleteRobotNotifier.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import './views/homepage.dart' as home;
import 'controller/admin/LoadWorldNotifier.dart';
import 'controller/admin/SaveWorldNotifier.dart';
import 'controller/admin/GetRobotNotifier.dart';
import 'controller/admin/AddObstacleNotifier.dart';
import 'controller/admin/DeleteObstacleNotifier.dart';
import 'controller/admin/GetWorldsNotifier.dart';
import 'controller/player/WorldNotifier.dart';
import 'controller/player/LaunchRobotNotifier.dart';
import 'controller/player/RobotMoveNotifier.dart';

void main() => runApp(MultiProvider(providers: [
      ChangeNotifierProvider(create: (_) => DeleteRobotNotifier()),
      ChangeNotifierProvider(create: (_) => GetRobotNotifier()),
      ChangeNotifierProvider(create: (_) => AddObstacleNotifier()),
      ChangeNotifierProvider(create: (_) => DeleteObstacleNotifier()),
      ChangeNotifierProvider(create: (_) => GetWorldsNotifier()),
      ChangeNotifierProvider(create: (_) => LoadWorldNotifier()),
      ChangeNotifierProvider(create: (_) => SaveWorldNotifier()),
      ChangeNotifierProvider(create: (_) => LaunchRobotNotifier()),
      ChangeNotifierProvider(create: (_) => RobotMoveNotifier()),
      ChangeNotifierProvider(create: (_) => WorldNotifier()),
    ], child: const home.homepage()));
