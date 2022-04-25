package base.Filter;

import base.Models.Movie;

import java.util.ArrayList;
import java.util.Comparator;


public class SortByDateFilter implements Filter{
    @Override
    public ArrayList<Movie> filter(ArrayList<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getReleaseDate));
        return movies;
    }
}
