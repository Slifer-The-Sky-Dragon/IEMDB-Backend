package base.Models;

import base.Utilities.Pair;

public class Rating implements BaseModel{
    private String userEmail = null;
    private int movieId = -1;
    private int score = -1;

    public Rating(String userEmail, int movieId, int score) {
        this.userEmail = userEmail;
        this.movieId = movieId;
        this.score = score;
    }

    public Boolean isFieldsOk(){
        if(movieId == -1 || userEmail == null || score == -1)
            return false;
        return true;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Object getKey(){
        return new Pair<String, Integer>(this.userEmail, this.movieId);
    }

}
