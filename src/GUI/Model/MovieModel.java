package GUI.Model;

import BE.Movie;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {
    MovieManager movieManager;

    private ObservableList<Movie> moviesToBeViewed;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }

    public void searchMovie(String query) throws Exception {
        List<Movie> searchResults = movieManager.searchMovie(query);
    }

    public ObservableList<Movie> getObservableSong() {
        return moviesToBeViewed;
    }
}
