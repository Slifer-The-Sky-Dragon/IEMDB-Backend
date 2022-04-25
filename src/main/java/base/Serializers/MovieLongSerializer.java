package base.Serializers;

import base.Models.Actor;
import base.Models.Movie;

import java.util.ArrayList;

public class MovieLongSerializer {
    private Movie movie = null;
    private Double rating = null;
    private ArrayList<Actor> cast = null;
    private ArrayList<CommentSerializer> comments = null;

    public MovieLongSerializer(Movie movie, Double rating, ArrayList<Actor> cast, 
                                    ArrayList<CommentSerializer> comments){
        this.movie = movie;
        this.rating = rating;
        this.cast = cast;
        this.comments = comments;
    }

    public int getMovieId(){
        return movie.getId();
    }

    public String getName(){
        return movie.getName();
    }

    public String getSummary(){
        return movie.getSummary();
    }

    public String getReleaseDate(){
        return movie.getReleaseDate();
    }

    public String getDirector(){
        return movie.getDirector();
    }

    public ArrayList<String> getWriters(){
        return movie.getWriters();
    }

    public ArrayList<String> getGenres(){
        return movie.getGenres();
    }

    public ArrayList<Actor> getCast(){
        return this.cast;
    }

    public Double getRating(){
        return this.rating;
    }

    public int getDuration(){
        return movie.getDuration();
    }

    public int getAgeLimit(){
        return movie.getAgeLimit();
    }

    public ArrayList<CommentSerializer> getComments(){
        return this.comments;
    }
}
