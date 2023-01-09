package GUI.Model;

import BE.Movie;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
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
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }

    public ObservableList<Movie> getObservableMovie() {
        return moviesToBeViewed;
    }

    public void createMovie(String title, float imdbRating, float personalRating, String filePath) throws Exception {
        movieManager.createMovie(title, imdbRating, personalRating, filePath);

        showList();
    }
    public void deleteMovie(Movie movie) throws Exception {
        movieManager.deleteMovie(movie);
        moviesToBeViewed.remove(movie);
    }

    public void showList() throws Exception {
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }

    public void play(Movie selectedMovie) {
        movieManager.play(selectedMovie);
    }

    public ArrayList<Movie> getAllMovies() throws Exception {
        return (ArrayList<Movie>) movieManager.getAllMovies();
    }
}
