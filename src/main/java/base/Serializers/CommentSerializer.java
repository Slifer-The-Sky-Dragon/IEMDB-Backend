package base.Serializers;

import base.Models.Comment;

public class CommentSerializer {
    private Comment comment = null;
    private int like = 0;
    private int dislike = 0;

    public CommentSerializer(Comment comment, int like, int dislike){
        this.comment = comment;
        this.like = like;
        this.dislike = dislike;
    }

    public int getId(){
        return comment.getId();
    }

    public String getUserEmail(){
        return comment.getUserEmail();
    }

    public String getText(){
        return comment.getText();
    }
    
    public int getLike(){
        return this.like;
    }

    public int getDislike(){
        return this.dislike;
    }
}
