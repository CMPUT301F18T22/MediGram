package medigram.medigram;

public class Comment {
    private String text;
    private String sender;

    public Comment(String text, String sender){
        this.text = text;
        this.sender = sender;
    }

    public void editComment(String text){
        this.text = text;
    }

}
