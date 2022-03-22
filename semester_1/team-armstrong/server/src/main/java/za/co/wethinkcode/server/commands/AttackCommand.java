package za.co.wethinkcode.server.commands;

import java.util.*;
import za.co.wethinkcode.communication.*;
import za.co.wethinkcode.server.RequestErrorException;
import za.co.wethinkcode.server.*;

public class AttackCommand extends Command {

    private Request clientRequest;
    private Server server;

    public AttackCommand(Request Client, Server server) {
        this.clientRequest = Client;
        this.server = server;
    }

    @Override
    public Response execute() throws RequestErrorException {
        try {
            Db target = MultiServer.dataBase.get(0);
            Map<String, Object> data = new HashMap<>();
            // Get Robot
            Robots robot = target.findOne(clientRequest.getRobot());
            if (robot.getShots() <= 0)
                return new Response("OK", null, buildState(robot));
            robot.fire();

            Direction direction = robot.getCurrentDirection();
            int robotXs = robot.getRealPosition().getX();
            int robotYxs = robot.getRealPosition().getY();
            int[] max_XY = getMaximuxDistance(direction, robotXs, robotYxs);
            getAllTheNearByRobots(target, max_XY[0], max_XY[1], direction, robotXs, robotYxs);
            robot.setStatus(Status.NORMAL);
            data.put("message", "hit");
            //putDatabase(target);
            return new Response("OK", data, buildState(robot));
        } catch (Exception e) {
            throw new RequestErrorException("Could not parse arguments");
        }
    }

    public int[] getMaximuxDistance(Direction roboDirection, int x, int y) {
        if (roboDirection.equals(Direction.NORTH)) {
            int maxY = y + 5;
            return new int[] { x, maxY };
        } else if (roboDirection.equals(Direction.SOUTH)) {
            int maxY = y - 5;
            return new int[] { x, maxY };
        } else if (roboDirection.equals(Direction.EAST)) {
            int maxX = x + 5;
            return new int[] { maxX, y };
        } else if (roboDirection.equals(Direction.WEST)) {
            int maxX = x - 5;
            return new int[] { maxX, y };
        } else {
            return new int[] { x, y };
        }
    }

    public void getAllTheNearByRobots(Db db, int max_X, int max_Y, Direction robotDirection, int currentX,
            int currentY) {

        for (Robots x : db.getRobotList()) {
            int[] closeByPosition = x.getPosition();
            if (ICanSeeICanShoot(currentX, currentY, max_X, max_Y, closeByPosition[0], closeByPosition[1],
                    robotDirection)) {
                x.damage();
                if (x.getShields() < 0) {
                    x.setStatus(Status.DEAD);
                    db.KillRobot(x);
                }
                server.sendResponse(x.getServerThreadName(), new Response(null, null, buildState(x)));

            }
        }

    }

    public boolean ICanSeeICanShoot(int currentX, int currentY, int max_X, int max_Y, int closeByX, int closeByY,
            Direction direction) {
        if (direction.equals(Direction.EAST) || direction.equals(Direction.WEST)) {
            if (currentY == closeByY) {
                if (currentX < closeByX)
                    return (closeByX > currentX && closeByX <= max_X);
                return closeByX < currentX && closeByX >= max_X;
            }
        } else if (direction.equals(Direction.NORTH) || direction.equals(Direction.SOUTH)) {
            if (currentX == closeByX) {
                if (currentY < max_Y)
                    return closeByY > currentY && closeByY <= max_Y;
                return closeByY < currentY && closeByY >= max_Y;
            }
        }
        return false;
    }
}
