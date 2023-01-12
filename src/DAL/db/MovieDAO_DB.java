package DAL.db;
import BE.Movie;
import DAL.IMovieDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MovieDAO_DB implements IMovieDAO {

    private DAL.db.MyDatabaseConnector databaseConnector;
    public MovieDAO_DB(){
        databaseConnector = new MyDatabaseConnector();
    }

    /**
     * Gets needed information from the Movie table
     * @return List<Movie>
     * @throws Exception
     */
    @Override
    public List<Movie> getAllMovies() throws Exception {
        ArrayList<Movie> allMovies = new ArrayList<>();

        try(Connection conn = databaseConnector.getConnection();
        Statement stmt = conn.createStatement())
        {
            //SQL string that gets all information from the Movie table
            String sql = "Select * From dbo.Movie;";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("MovieID");
                String title = rs.getString("Title");
                float IMDBRating = rs.getFloat("PersonalRating");
                float PersonalRating = rs.getFloat("IMDBRating");
                String filePath = rs.getString("FilePath");

                Movie movie = new Movie(id,title,PersonalRating,IMDBRating,filePath);
                allMovies.add(movie);
            }
            return allMovies;

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not get Songs from database", ex);
        }

    }

    /**
     * Creates a new movie
     * @param title
     * @param imdbRating
     * @param personalRating
     * @param filePath
     * @return Movie
     * @throws Exception
     */
    @Override
    public Movie createMovie(String title, float imdbRating, float personalRating, String filePath) throws Exception {

        //SQL string that creates a new movie in the Movie table
        String sql = "INSERT INTO movie (Title,IMDBRating,PersonalRating,FilePath) VALUES (?,?,?,?);";



        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, title);
            stmt.setFloat(2, imdbRating);
            stmt.setFloat(3, personalRating);
            stmt.setString(4, filePath);

            // Run the specified SQL statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            Movie movie = new Movie(id, title, imdbRating, personalRating, filePath);
            return movie;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create movie", ex);
        }
    }

    /**
     * Updates the IMDBRating in the Movie table
     * @param movie
     */
    public void updateMovie(Movie movie) {
        try (Connection conn = databaseConnector.getConnection()) {
            String sql = "Update movie set Title = ?, IMDBRating = ?, PersonalRating = ?, FilePath = ? WHERE MovieID = ?;";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, movie.getTitle());
            stmt.setFloat(2, movie.getImdbRating());
            stmt.setFloat(3, movie.getPersonalRating());
            stmt.setString(4, movie.getFilepath());
            stmt.setInt(5, movie.getId());

            String filePath = movie.getFilepath();

            stmt.executeUpdate();

        } catch (SQLException e) {

        }
    }


    /**
     * Deletes a movie from the Movie table
     * @param movie
     * @throws Exception
     */
    @Override
    public void deleteMovie(Movie movie) throws Exception {
        try (Connection conn = databaseConnector.getConnection()) {
            //SQL string that deletes a movie based on Title and MovieID in the movie table
            String sql = "DELETE FROM Movie WHERE Title = (?) AND MovieID = (?);";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, movie.getTitle());
            stmt.setInt(2, movie.getId());

            stmt.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception(ex);
        }

    }
}
