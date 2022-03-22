package za.co.wethinkcode.dao;

import lombok.*;
import net.lemnik.eodsql.ResultColumn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObstacleDO {
    private int id;
    private int x_val;
    private int y_val;
    private int size;
    private int obstacle_world;


}
