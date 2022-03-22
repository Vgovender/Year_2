package za.co.wethinkcode.dao.WorldsDAI;

import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Select;
import net.lemnik.eodsql.Update;
import za.co.wethinkcode.dao.ObstacleDO;
import za.co.wethinkcode.dao.WorldDO;

import java.util.List;

public interface WorldDAI extends BaseQuery {
    @Select(
            "SELECT w.id, w.name, w.visibility, w.size " +
                    "FROM world_data w " +
                    "WHERE w.name = ?{1}")
    WorldDO getWorld(String name);

    @Select(
            "SELECT o.id, o.x_val, o.y_val, o.size , o.obstacle_world " +
                    "FROM obstacles o " +
                    "WHERE o.obstacle_world = ?{1}")
    List<ObstacleDO> getObstacles(int id);

    @Update("INSERT INTO obstacles (x_val, y_val, size, obstacle_world) " +
            "VALUES (?{1},?{2},?{3},?{4})")
    void addObstacles(int x, int y, int size, int obs_world);

    @Update("INSERT INTO world_data (name, visibility, size) " +
            "VALUES(?{1},?{2},?{3})")
    void save(String name, int visibility, int size);
    
    @Select(
            "SELECT w.id, w.name, w.visibility, w.size " +
            "FROM world_data w")
    List<WorldDO> getAllWorldDOs();

    @Select(
            "SELECT o.id, o.x_val, o.y_val, o.size, o.obstacle_world " +
                    "FROM obstacles o ")
    List<ObstacleDO> getAllObstacles();
}
