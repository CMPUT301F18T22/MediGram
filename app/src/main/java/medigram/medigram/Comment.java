package medigram.medigram;

import java.io.Serializable;
/**
 * Represents a comment that the user is having.
 * Comments can have a text and a sender
 */
public class Comment implements Serializable {
    private String text;
    private String sender;

    public Comment(String text, String sender){
        this.text = text;
        this.sender = sender;
    }

    /**
     * edit the text in a comment
     * @param text
     */
    public void editText(String text){
        this.text = text;
    }

    /**
     * get the text in a comment
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * get the sender of a comment
     * @return sender
     */
    public String getSender() {
        return sender;
    }

    public String toString(){
        return this.sender + "~" + this.getText();
    }
}
