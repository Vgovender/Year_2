package za.co.wethinkcode.communication;

import java.util.Map;

public class Response {
    private String result;
    private Map<String, Object> data;
    private Map<String, Object> state;
    // private String message;

    public Response(){}

    public Response(String result, Map<String, Object> data, Map<String, Object> state){
        this.result = result;
        this.data = data;
        this.state = state;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }
    
    /**
     * @return the data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * @return the state
     */
    public Map<String, Object> getState() {
        return state;
    }

    // /**
    //  * @param message the message to set
    //  */
    // public void setMessage(String message) {
    //     this.message = message;
    // }

    // /**
    //  * @return the message
    //  */
    // public String getMessage() {
    //     return message;
    // }

}
