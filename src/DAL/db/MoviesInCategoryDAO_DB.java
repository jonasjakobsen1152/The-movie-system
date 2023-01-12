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

    /**
     * Gets needed information from the CatMovie table
     * @param movieNumber
     * @return List<Movie>
     * @throws SQLException
     */
    @Override
    public List<Movie> getCategoryMovies(int movieNumber) throws SQLException {
        List<Movie> moviesInCategoryList = new ArrayList<>();

        try (Connection connection = dbConnector.getConnection()){

            String sql = "SELECT * FROM Categories cat, CatMovie catm, Movie m \n" +
                    " WHERE m.MovieID = catm.MovieID AND catm.CategoryID =" + movieNumber +
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

    /**
     * Links a movie and a category together so the application knows that the movie is a given category.
     * @param selectedCategory
     * @param selectedMovie
     * @param catMovieId
     */
    @Override
    public void addMovieToCategory(Category selectedCategory, Movie selectedMovie, int catMovieId) {

        String sql = "INSERT INTO CatMovie ( MovieId, CategoryID) VALUES (?,?);";

        try(Connection connection = dbConnector.getConnection()){


            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int selectedCategoryID = selectedCategory.getId();
            int selectedMovieID = selectedMovie.getId();
            int catMovieID = catMovieId;

            //stmt.setInt(1, catMovieID);
            stmt.setInt(1, selectedMovieID);
            stmt.setInt(2, selectedCategoryID);

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Removes a movie from a category in the CatMovieTable
     * @param category
     * @param selectedMovie
     * @param selectedCMId
     */
    @Override
    public void deleteMovieFromCategory(Category category, Movie selectedMovie, int selectedCMId) {

        String sql = "Delete from CatMovie \n" +
                "Where CatMovie.MovieID = ? \n" +
                "And CatMovie.CategoryID = ?";

        try (Connection conn = dbConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1,selectedMovie.getId());
            stmt.setInt(2,category.getId());

            stmt.executeUpdate(); //executeUpdate method is used to execute the SQL statement and returns the number of rows affected.

        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Gets the CatMovieID from the CatMovie table
     * @param movieID
     * @param categoryID
     * @return int
     */
        public int getCatMovieID(int movieID, int categoryID){
        int catmID = 0;

        String sql = "SELECT * FROM CatMovie CATM WHERE CATM.MovieID =" + movieID + " AND " + "CATM.CategoryID=" + categoryID + ";";

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
