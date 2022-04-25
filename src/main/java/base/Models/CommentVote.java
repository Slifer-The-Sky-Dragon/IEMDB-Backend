package base.Models;

import base.Utilities.Pair;

public class CommentVote implements BaseModel{
    private String userEmail = null;
    private int commentId = -1;
    private int vote = -10;

    public CommentVote(String userEmail, int commentId, int vote) {
        this.userEmail = userEmail;
        this.commentId = commentId;
        this.vote = vote;
    }

    public Boolean isFieldsOk(){
        if(commentId == -1 || userEmail == null || vote == -10)
            return false;
        return true;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public Object getKey(){
        return new Pair<String, Integer>(this.userEmail, this.commentId);
    }
}
