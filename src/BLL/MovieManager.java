package BLL;

import BE.Movie;
import BLL.Util.MovieSearcher;
import DAL.db.MovieDAO_DB;
import DAL.IMovieDAO;

import java.util.List;

public class MovieManager {
    private IMovieDAO movieDAO;
    MovieSearcher movieSearcher;

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



}
