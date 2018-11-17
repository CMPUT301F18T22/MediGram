package medigram.medigram;

public class Comment {
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
