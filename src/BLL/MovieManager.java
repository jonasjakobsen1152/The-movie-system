package BLL;

import BE.Movie;
import BLL.Util.MoviePlayer;
import BLL.Util.MovieSearcher;
import DAL.db.MovieDAO_DB;
import DAL.IMovieDAO;
import javafx.collections.FXCollections;

import java.util.List;

public class MovieManager {
    private IMovieDAO movieDAO;
    private MoviePlayer moviePlayer;
    MovieSearcher movieSearcher;

    public MovieManager() {
        movieDAO = new MovieDAO_DB();
        moviePlayer = new MoviePlayer();
        movieSearcher = new MovieSearcher();
    }

    public List<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }
    public List<Movie> searchMovie(String query) throws Exception {
        List<Movie> allMovies = getAllMovies();
        List<Movie> searchResult = movieSearcher.search(allMovies,query);
        return searchResult;
    }

    public Movie createMovie(String title, float imdbRating, float personalRating, String filePath) throws Exception {
        return movieDAO.createMovie(title, imdbRating, personalRating, filePath);
    }

    public void deleteMovie(Movie movie) throws Exception {
        movieDAO.deleteMovie(movie);
    }

    public void updateMovie(Movie updatedMovie) {
        movieDAO.updateMovie(updatedMovie);
    }


    public void play(Movie selectedMovie) {
        moviePlayer.play(selectedMovie);
    }
}
