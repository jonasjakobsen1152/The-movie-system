package DAL.db;

import BE.Category;
import BE.Movie;
import DAL.IMoviesInCategoryDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
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
    public void addMovieToCategory(Category selectedCategory, Movie selectedMovie, int catMovieId) {

        String sql = "INSERT INTO CatMovieID VALUES (?,?,?);";

        try(Connection connection = dbConnector.getConnection()){

            PreparedStatement stmt = connection.prepareStatement(sql);

            int selectedCategoryID = selectedCategory.getId();
            int selectedMovieID = selectedMovie.getId();
            int catMovieID = catMovieId + 1;

            stmt.setInt(1, selectedCategoryID);
            stmt.setInt(2, selectedMovieID);
            stmt.setInt(3, catMovieID);

            stmt.executeUpdate();
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteMovieFromCategory(Category category, Movie selectedMovie, int selectedCMId) {

    }

    public int getCatMovieID(int movieID, int categoryID){
        int catmID = 0;

        String sql = "SELECT * FROM CatMovie CATM WHERE CATM.MovieID =" + movieID + "AND" + "CATM.CategoryID=" + categoryID + ";";

        try(Connection connection = dbConnector.getConnection()) {

            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                catmID = rs.getInt("CatMovieID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return catmID;
    }

}
