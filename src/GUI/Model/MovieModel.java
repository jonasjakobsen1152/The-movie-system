package GUI.Model;

import BE.Movie;
import BLL.MovieManager;

import java.util.List;

public class MovieModel {
    MovieManager movieManager;

    public void searchMovie(String query) {
        List<Movie> searchResults = movieManager.searchMovie(query);
    }
}
