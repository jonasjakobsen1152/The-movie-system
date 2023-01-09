package DAL.db;

import BE.Category;
import BE.Movie;
import DAL.IMoviesInCategoryDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoviesInCategoryDAO_DB implements IMoviesInCategoryDAO {
    private MyDatabaseConnector dbConnector;

    public MoviesInCategoryDAO_DB(){dbConnector = new MyDatabaseConnector();}

    @Override
    public List<Movie> getCategoryMovies(int movieNumber) throws SQLException {
        List<Movie> moviesInCategoryList = new ArrayList<>();

        try (Connection connection = dbConnector.getConnection()){

            String sql = "SELECT * FROM Categories cat, CatMovie catm, Movie m \n" +
                    " WHERE m.MovieID = catm.MovieID and catm.CategoryID =" + movieNumber +
                    " AND cat.CategoryID = catm.CategoryID;";

            Statement statement = connection.createStatement();

            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()){
                    int id = resultSet.getInt("MovieID");
                    String title = resultSet.getString("Title");
                    int pRating = resultSet.getInt("PersonalRating");
                    int imdbRating = resultSet.getInt("IMDBRating");
                    String moviePath = resultSet.getString("FilePath");

                    Movie movie = new Movie(id, title, pRating, imdbRating, moviePath);
                    moviesInCategoryList.add(movie);
                }
            }
        }
        return moviesInCategoryList;
    }

    @Override
    public void addMovieToCategory(Category category, Movie movie, int catMovieId) {

    }

    @Override
    public void deleteMovieFromCategory(Category category, Movie selectedMovie, int selectedCMId) {

    }
}
