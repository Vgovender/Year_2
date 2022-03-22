package za.co.wethinkcode.communication;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ObstacleRequest {
    String worldName;
    String command;
    String[] arguments;
}
