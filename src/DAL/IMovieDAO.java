package DAL;

import BE.Movie;

import java.util.List;

public interface IMovieDAO {
    public List<Movie> getAllSongs() throws Exception;;

    public Movie createMovie(int id, String title, int year, float imdbRating, float personalRating, String filepath) throws Exception;;

    public void deleteMovie(Movie movie) throws Exception;
}
