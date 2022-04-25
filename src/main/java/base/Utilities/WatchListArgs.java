package base.Utilities;

public class WatchListArgs {
    private String userEmail = null;
    private int movieId = -1;

    public Boolean isFieldsOk(){
        if(userEmail == null || movieId == -1)
            return false;
        return true;
    }

    public String getUserEmail() { return this.userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; } 

    public int getMovieId() { return this.movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId;}
}
