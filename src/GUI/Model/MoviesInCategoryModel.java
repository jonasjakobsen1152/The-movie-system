package GUI.Model;

import BE.Category;
import BE.Movie;
import BLL.MoviesInCategoryManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class MoviesInCategoryModel {
    private ObservableList<Movie> moviesToBeViewed;
    private MoviesInCategoryManager moviesInCategoryManager;
    private Category selectedCategory;

    public MoviesInCategoryModel() throws SQLException {
        moviesInCategoryManager = new MoviesInCategoryManager();
        moviesToBeViewed = FXCollections.observableArrayList();

        moviesToBeViewed.addAll(moviesInCategoryManager.getCategoryMovies(0));
    }

    public ObservableList<Movie> getMoviesToBeViewed(){return moviesToBeViewed;}



    public void showList(int movieNumber) throws SQLException {
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(moviesInCategoryManager.getCategoryMovies(movieNumber));
    }

    public int getCatMovieID(int movieID, int categoryID){
        return moviesInCategoryManager.getCatMovieID(movieID, categoryID);
    }

    public void addMovieToCategory(Category category, Movie movie, int catMovieID){
        moviesInCategoryManager.addMovieToCategory(category,movie,catMovieID);
        moviesToBeViewed.add(movie);
    }

}
