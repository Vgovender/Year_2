package za.co.wethinkcode.linklists.motd;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MotdResponse {
    private String motd = "~~=[Welcome to the LinkLists Service!]=~~";

    @JsonCreator
    public MotdResponse(String motd){
        this.motd = motd;
    }
    public MotdResponse(){
//        this.motd = motd;
        getMotd();
    }
    public String getMotd() {
        return motd;
    }

}
