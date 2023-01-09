package DAL;

import BE.Movie;

import java.util.List;

public interface IMovieDAO {
    public List<Movie> getAllMovies() throws Exception;;

    public Movie createMovie(String title, float imdbRating, float personalRating, String filePath) throws Exception;

    public void deleteMovie(Movie movie) throws Exception;

    void updateMovie(Movie updatedMovie);
}
