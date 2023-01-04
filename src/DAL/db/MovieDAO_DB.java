package DAL.db;
import BE.Movie;
import DAL.IMovieDAO;

import java.sql.*;
import java.util.List;
public class MovieDAO_DB implements IMovieDAO {

    @Override
    public List<Movie> getAllMovies() throws Exception {
        return null;
    }
    

    @Override
    public Movie createMovie(String title, String year, float imdbRating, float personalRating, String filePath) throws Exception {

        String sql = "INSERT INTO movie (Id,Title,Year,IMDBRating,PersonalRating,FilePath) VALUES (?,?,?,?,?);";

        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, title);
            stmt.setString(2, year);
            stmt.setFloat(3, imdbRating);
            stmt.setFloat(4, personalRating);
            stmt.setString(5, filePath);

            // Run the specified SQL statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            Movie movie = new Movie(id, title, year, imdbRating, personalRating, filePath);
            return movie;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create movie", ex);
        }
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {

    }
}
