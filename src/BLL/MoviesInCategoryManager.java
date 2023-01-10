package BLL;

import BE.Category;
import BE.Movie;
import DAL.db.MoviesInCategoryDAO_DB;

import java.sql.SQLException;
import java.util.List;

public class MoviesInCategoryManager {
    MoviesInCategoryDAO_DB moviesInCategoryDAO_DB;

    public MoviesInCategoryManager(){moviesInCategoryDAO_DB = new MoviesInCategoryDAO_DB();}

    public List<Movie> getCategoryMovies(int movieNumber) throws SQLException {
        return moviesInCategoryDAO_DB.getCategoryMovies(movieNumber);
    }

    public int getCatMovieID(int movieID, int categoryID){
        return moviesInCategoryDAO_DB.getCatMovieID(movieID, categoryID);
    }

    public void addMovieToCategory(Category category, Movie movie, int catMovieID){
        moviesInCategoryDAO_DB.addMovieToCategory(category,movie,catMovieID);
    }

}
