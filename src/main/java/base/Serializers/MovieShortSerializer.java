package base.Serializers;

import base.Models.Movie;

import java.util.ArrayList;

public class MovieShortSerializer {
    private Movie movie = null;
    private Double rating = null;

    public MovieShortSerializer(Movie movie, Double rating){
        this.movie = movie;
        this.rating = rating;
    }

    public int getMovieId(){
        return movie.getId();
    }

    public String getName(){
        return movie.getName();
    }

    public String getDirector(){
        return movie.getDirector();
    }

    public ArrayList<String> getGenres(){
        return movie.getGenres();
    }

    public Double getRating(){
        return this.rating;
    }
}
