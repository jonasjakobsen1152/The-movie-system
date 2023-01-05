package GUI.Controller;

import BE.Categories;
import BE.Movie;
import GUI.Model.CategoryModel;
import GUI.Model.MRSModel;
import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends BaseController implements Initializable {
    public TableView<Movie> lstMovies;
    public TableView<Categories> lstCategories;
    public TableView lstMovieByCategory;
    public Button btnAddMovieToCategory;
    public Button btnDeleteMovieFromCategory;
    public Button btnCreateMovie;
    public Button btnDeleteMovie;
    public Button btnCreateCategory;
    public Button btnDeleteCategory;
    public TextField txtFilter;
    MRSModel mrsModel = new MRSModel();
    public MovieModel movieModel;

    public CategoryModel categoryModel;

    public Movie movie;
    public TableColumn clmTitle;
    public TableColumn clmIMDB;
    public TableColumn clmPersonal;

    public MainController() throws Exception {
        movieModel = new MovieModel();
        categoryModel = new CategoryModel();
        mrsModel = new MRSModel();
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        lstMovies.setItems(movieModel.getObservableMovie());
        //lstCategories.setItems(); TODO Implement this when implementing category
        //TODO implement MODEL-songs on category
    try {
        movieModel = new MovieModel();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    showAllMoviesCategories();

        txtFilter.textProperty().addListener((((observable, oldValue, newValue) -> { //If value changes it runs the following code
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })));
    }
    @Override
    public void setup() {
    movieModel = getModel().getMovieModel();

    txtFilter.textProperty().addListener(((observable, oldValue, newValue) -> {
        try {
            movieModel.searchMovie(newValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }));
        showAllMoviesCategories();
    }
    public void showAllMoviesCategories(){
        //Sets the tableView, and then what it should show.
        lstMovies.setItems(movieModel.getObservableMovie());
        //System.out.println(movie.getTitle());
        //clmTitle.setCellFactory(new PropertyValueFactory<Movie,String>("title"));
       // clmIMDB.setCellFactory(new PropertyValueFactory<Movie, Float>("imdbRating"));
       // clmIMDB.setCellFactory(new PropertyValueFactory<Movie, Float>("personalRating"));
    }


    /*
    A method to Add new movies to the database
     */
    public void handleCreateMovie(ActionEvent actionEvent) {
    }

    /*
    A method to delete movies from the database
     */
    public void handleDeleteMovie(ActionEvent actionEvent) {
    }

    /*
    Creates a new category in the database
     */
    public void handleCreateCategory(ActionEvent actionEvent) {
    }

    /*
    Deletes a category from the database
     */
    public void handleDeleteCategory(ActionEvent actionEvent) {
    }

    /*
    Adds a movie from the database to the chosen category
     */
    public void handleAddMovieToCategory(ActionEvent actionEvent) {
    }

    /*
    Deletes a movie from the category
     */
    public void handleDeleteMovieFromCategory(ActionEvent actionEvent) {
    }
}
