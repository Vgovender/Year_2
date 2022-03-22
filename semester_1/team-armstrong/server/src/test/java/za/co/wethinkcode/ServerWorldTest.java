package za.co.wethinkcode;

import za.co.wethinkcode.server.world.ServerWorld;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerWorldTest {
    @Test
    void checkObstacleTest()
    {
        ServerWorld world = new ServerWorld(Map.of("obstacle", "20,20"), 5, 7);
        assertTrue(world.checkObstacles(9, 10));
    }
//    @Test
//    void checkPitsTest()
//    {
//        ServerWolrd world = new ServerWolrd(5, 7);
//        assertFalse(world.checkPits(9, 10));
//    }
//    @Test
//    void checkMinesTest()
//    {
//        ServerWolrd world = new ServerWolrd(5, 7);
//        assertTrue(world.checkMines(9, 10));
//    }

}
