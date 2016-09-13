package net.gringrid.siso.network.restapi;

/**
 * Created by choijiho on 16. 9. 1..
 */

public class APIError {

    private int statusCode;
    private String message;
    private String msgCode;
    private String msgText;

    public APIError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
    public String msgCode() {
        return msgCode;
    }
    public String msgText() {
        return msgText;
    }
}
