package base.Models;

import java.time.LocalDateTime;

public class Comment implements BaseModel{
    private int id = -1;
    private String userEmail = null;
    private int movieId = -1;
    private String text = null;
    private LocalDateTime time = null;

    private String userNickname = null;
    private int likes = 0;
    private int dislikes = 0;

    public Comment() {
    }

    public Comment(String userEmail, int movieId, String text) {
        this.userEmail = userEmail;
        this.movieId = movieId;
        this.text = text;
    }

    public Boolean isFieldsOk(){
        if(movieId == -1 || userEmail == null || text == null)
            return false;
        return true;
    }

    public String getUserEmail() { return this.userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; } 

    public int getMovieId() { return this.movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId;}

    public String getText() { return this.text; }
    public void setText(String text) { this.text = text; }     

    public void setId(int id) { this.id = id; }
    public int getId() { return this.id; }
    
    public void setTime(LocalDateTime time) { this.time = time; }

    public Object getKey(){
        return id;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}
