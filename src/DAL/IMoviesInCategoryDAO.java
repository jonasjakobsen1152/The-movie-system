package DAL;

import BE.Category;
import BE.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.List;

public interface IMoviesInCategoryDAO {
    public List<Movie> getCategoryMovies(int movieNumber) throws SQLException;
    public void addMovieToCategory(Category selectedCategory, Movie selectedMovie);
    public void deleteMovieFromCategory(Category category, Movie selectedMovie, int selectedCMId);
}
