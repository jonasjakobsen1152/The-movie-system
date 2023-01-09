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
import java.util.Optional;
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
    public TableColumn<Category, String> clmCategories;
    public Button btnPlayMovie;
    public TableColumn clmTitleInCategory;
    public TableColumn clmIMDBInCategory;
    public TableColumn clmPersonalInCategory;
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
        lstMovies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                selectedMovie = newValue);
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
        lstMovies.setOnMouseClicked(event -> { // Checks for click.
            if (event.getClickCount() == 2 ) //Ved dobbeltklik kan man starte musikken
            {
                if(selectedMovie != null)
                try {
                    movieModel.play(selectedMovie);       //Håndtere afspilning af sange.
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                else{
                    alertUser("Please select a movie");
                }
            }  });
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

        lstCategories.setItems(categoryModel.getAllCategories());
        clmCategories.setCellValueFactory(new PropertyValueFactory<Category, String>("Category"));



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
        dialogWindow.setResizable(false);
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

    private void updateCategoryModel() throws Exception {
        CategoryModel updateCategoryModel = new CategoryModel();
        categoryModel = updateCategoryModel;
        lstCategories.setItems(categoryModel.getAllCategories());
    }

    public Movie getSelectedMovie(){
        Movie movie;
        movie = lstMovies.getSelectionModel().getSelectedItem();
        return movie;
    }

    /*
    A method to delete movies from the database
     */
    public void handleDeleteMovie(ActionEvent actionEvent) throws Exception {
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();
        if(selectedMovie == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a movie");
            alert.setHeaderText("Choose a movie to delete");
            alert.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedMovie.getTitle().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if(action.get()==ButtonType.OK){
                movieModel.deleteMovie(getSelectedMovie());
            }
        }
    }

    /*
    Creates a new category in the database
     */
    public void handleCreateCategory(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/CreateCategoryWindow.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();
        CategoryController categoryController = loader.getController();
        mrsModel.setCategoryModel(super.getModel().getCategoryModel());

        Stage dialogWindow = new Stage();
        dialogWindow.setTitle("Add Category");
        dialogWindow.initModality(Modality.WINDOW_MODAL);
        dialogWindow.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        dialogWindow.setResizable(false);
        Scene scene = new Scene(pane);
        dialogWindow.setScene(scene);

        dialogWindow.showAndWait();
        updateCategoryModel();
    }

    /*
    Deletes a category from the database
     */
    public void handleDeleteCategory(ActionEvent actionEvent) throws Exception {
        Category selectedCategory = lstCategories.getSelectionModel().getSelectedItem();
        if(selectedCategory == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a category");
            alert.setHeaderText("Choose a category to delete");
            alert.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedCategory.getCategory().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if(action.get()==ButtonType.OK){
                categoryModel.deleteCategory(getSelectedCategory());
                updateCategoryModel();
            }
        }
    }

    private Category getSelectedCategory() {
        Category category;
        category = lstCategories.getSelectionModel().getSelectedItem();
        return category;
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

    public void handlePlayMovie(ActionEvent actionEvent) {
        movieModel.play(selectedMovie);
    }

    private void alertUser(String error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void handleEditIMDB(ActionEvent actionEvent) {
    }
}
