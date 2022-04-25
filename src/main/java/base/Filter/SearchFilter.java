package base.Filter;

import base.Models.Movie;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SearchFilter implements Filter{
    private String keyword;

    public SearchFilter(String keyword){
        this.keyword = keyword;
    }

    public ArrayList<Movie> filter(ArrayList<Movie> movies){
        if(keyword.isEmpty())
            return movies;
        return (ArrayList<Movie>) movies.stream().filter((m) -> {return m.getName().contains(keyword);})
                        .collect(Collectors.<Movie>toList());
    }
}
