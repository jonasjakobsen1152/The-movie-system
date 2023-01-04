package BLL;

import BE.Movie;
import BLL.Util.MovieSearcher;

import java.util.List;

public class MovieManager {
    MovieSearcher movieSearcher;
    private List<Movie> getAllMovies() {
        return null;
    }
    public List<Movie> searchMovie(String query) {
        List<Movie> allMovies = getAllMovies();
        List<Movie> searchResult = movieSearcher.search(allMovies,query);
        return searchResult;
    }

}
