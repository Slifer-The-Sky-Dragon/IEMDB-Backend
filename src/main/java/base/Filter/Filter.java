package base.Filter;

import base.Models.Movie;
import java.util.ArrayList;

public interface Filter {
    public ArrayList<Movie> filter(ArrayList<Movie> movies);
}
