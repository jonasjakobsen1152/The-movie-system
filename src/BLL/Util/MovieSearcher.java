package BLL.Util;

import BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSearcher {

    public List<Movie> search(List<Movie> searchBase, String query){
       List<Movie> searchResult = new ArrayList<>();

       for(Movie movie : searchBase){
           if(compareToMovieTitle(query, movie) || compareToMovieIMDB(Float.parseFloat(query),movie) || compareToMoviePersonalRating(Float.parseFloat(query),movie) )
           {
               searchResult.add(movie);
           }
       }
       return searchResult;
    }

    private boolean compareToMovieTitle(String query,Movie movie){
        return movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToMovieIMDB(float query, Movie movie){
        return movie.getImdbRating() == query;
    }

    private boolean compareToMoviePersonalRating(float query, Movie movie){
        return movie.getPersonalRating() == query;
    }
}
