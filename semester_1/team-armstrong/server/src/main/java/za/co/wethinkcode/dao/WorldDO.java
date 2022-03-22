package za.co.wethinkcode.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lemnik.eodsql.ResultColumn;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class WorldDO implements Serializable {
    @ResultColumn(value = "id")
    private int worldId;

    @ResultColumn(value = "size")
    private int worldSize;

    @ResultColumn(value = "visibility")
    private int visibility;

    @ResultColumn(value = "name")
    private String worldName;

}
