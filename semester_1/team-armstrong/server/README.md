# 0023-robot-worlds

This will consist of two programs: the server and the client. The server program is responsible for managing a world with its obstacles, robots and anything else we program into the world. The client program is responsible for launching a robot into the world and controlling the robot in the world by sending messages to the server program and receiving messages back in response.

### IntelliJ
To open it in `IntelliJ` IDE:
1. _File_ -> _New_ -> _Project from Existing Sources..._
1. Select the directory where this code has been checked out to by the LMS
1. Choose _External Model_ as *Maven*

## Build, Test & Run
You may use IntelliJ to run your code and tests, but alternatively you can use the Maven build tool:
* First ensure you are in the root directory of the project
* To compile your code, run: `mvn compile` 
* To run the tests: `mvn test`
* To run your application: `mvn compile exec:java`
