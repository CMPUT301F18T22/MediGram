package medigram.medigram;

import java.io.Serializable;

public class Comment implements Serializable {
    private String text;
    private String sender;

    public Comment(String text, String sender){
        this.text = text;
        this.sender = sender;
    }

    public void editText(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }
}
