package main.java.org.acme;

public class Message {
    
    private boolean status;
    private String msg;


    public Message(boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    



}
