package GUI.Controller;

import BE.Category;
import BE.Movie;
import GUI.Model.CategoryModel;
import GUI.Model.MRSModel;
import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends BaseController implements Initializable {
    public TableView<Movie> lstMovies;
    public TableView<Category> lstCategories;
    public TableView lstMovieByCategory;
    public Button btnAddMovieToCategory;
    public Button btnDeleteMovieFromCategory;
    public Button btnCreateMovie;
    public Button btnDeleteMovie;
    public Button btnCreateCategory;
    public Button btnDeleteCategory;
    public TextField txtFilter;
    public MRSModel mrsModel;
    public MovieModel movieModel;

    public CategoryModel categoryModel;

    public Movie movie;

    public Movie selectedMovie;
    @FXML
    private TableColumn<Movie,String> clmTitle;
    @FXML
    private TableColumn<Movie,Float> clmIMDB;
    @FXML
    private TableColumn<Movie,Float> clmPersonal;

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
        clmTitle.setCellValueFactory(new PropertyValueFactory<Movie,String>("Title"));
        clmIMDB.setCellValueFactory(new PropertyValueFactory<Movie, Float>("ImdbRating"));
        clmPersonal.setCellValueFactory(new PropertyValueFactory<Movie, Float>("PersonalRating"));

        lstMovies.setItems(movieModel.getObservableMovie());
        //System.out.println(movie.getTitle());

    }


    /*
    A method to Add new movies to the database
     */
    public void handleCreateMovie(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/CreateMovieWindow.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();
        MovieDataInputs movieDataInputs = loader.getController();
        mrsModel.setMovieModel(super.getModel().getMovieModel());

        Stage dialogWindow = new Stage();
        dialogWindow.setTitle("Add Movie");
        dialogWindow.initModality(Modality.WINDOW_MODAL);
        dialogWindow.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        Scene scene = new Scene(pane);
        dialogWindow.setScene(scene);

        dialogWindow.showAndWait();
        updateMovieModel();
    }

    private void updateMovieModel() throws Exception {
        MovieModel updateMovieModel = new MovieModel();
        movieModel = updateMovieModel;
        lstMovies.setItems(movieModel.getObservableMovie());

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
