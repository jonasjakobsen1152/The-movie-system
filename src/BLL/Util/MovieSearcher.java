package BLL.Util;

import BE.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSearcher {

    public List<Movie> search(List<Movie> searchBase, String query){
       List<Movie> searchResult = new ArrayList<>();

       for(Movie movie : searchBase){
           if(compareToMovieTitle(query, movie) || compareToMovieIMDB((query),movie) || compareToMoviePersonalRating((query),movie) )
           {
               searchResult.add(movie);
           }
       }
       return searchResult;
    }

    private boolean compareToMovieTitle(String query,Movie movie){
        return movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToMovieIMDB(String query, Movie movie){
        String iMDBQuery = String.valueOf(movie.getImdbRating());
        return iMDBQuery.toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToMoviePersonalRating(String query, Movie movie){
        String personalQuery = String.valueOf(movie.getPersonalRating());
        return personalQuery.toLowerCase().contains(query.toLowerCase());
    }
}
