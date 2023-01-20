package GUI.Controller;

import BE.Category;
import BE.Movie;
import GUI.Model.CategoryModel;
import GUI.Model.MRSModel;
import GUI.Model.MovieModel;
import GUI.Model.MoviesInCategoryModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.time.Instant.now;

public class MainController extends BaseController implements Initializable {
    public TableView<Movie> lstMovies;
    public TableView<Category> lstCategories;
    public TableView <Movie> lstMovieByCategory;
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
    public MoviesInCategoryModel moviesInCategoryModel;

    public Movie movie;

    public Movie selectedMovie;
    private ObservableList<Movie> moviesToBeReViewed;
    private static boolean oldMoviesChecked = false;
    MovieDataInputs movieDataInputs;
    private Stage dialogWindow;
    private int categoryNumber;
    public Category selectedCategory;
    public TableColumn<Category, String> clmCategories;
    public Button btnPlayMovie;
    public TableColumn clmTitleInCategory;
    public TableColumn clmIMDBInCategory;
    public TableColumn clmPersonalInCategory;
    public Button btnEditIMDB;
    @FXML
    private TableColumn<Movie, String> clmTitle;
    @FXML
    private TableColumn<Movie, Float> clmIMDB;
    @FXML
    private TableColumn<Movie, Float> clmPersonal;

    public MainController() throws Exception {
        movieModel = new MovieModel();
        categoryModel = new CategoryModel();
        mrsModel = new MRSModel();
        moviesInCategoryModel = new MoviesInCategoryModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            checkOldMovies();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
            if (event.getClickCount() == 2) //Ved dobbeltklik kan man starte musikken
            {
                if (selectedMovie != null)
                    try {
                        movieModel.play(selectedMovie);       //Håndtere afspilning af sange.
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                else {
                    alertUser("Please select a movie");
                }
            }  });
        lstMovieByCategory.setOnMouseClicked(event -> { // Checks for click.
            if (event.getClickCount() == 2) //Ved dobbeltklik kan man starte musikken
            {
                if (selectedMovie != null)
                    try {
                        movieModel.play(selectedMovie);       //Håndtere afspilning af sange.
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                else {
                    alertUser("Please select a movie");
                }
            }  });
        lstCategories.setOnMouseClicked(event -> {
            Category selectedCategory = lstCategories.getSelectionModel().getSelectedItem();
            if (selectedCategory == null){ //Fortæller user at personen skal lave en category
              alertUser("Please make a category");
            }
            else {
                categoryNumber = selectedCategory.getId();
            }
            try{
                moviesInCategoryModel.showList(categoryNumber);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        lstMovieByCategory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                selectedMovie = newValue); // This listener will be triggered when the selected item in the lstMovieByCategory object changes,
                                           // and will update the value of the selectedMovie variable with the new selected item.
        try {
            moviesInCategoryModel = new MoviesInCategoryModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        showAllMoviesCategories();
    }

    @Override
    public void setup() { //Henter movieModel op, sådan at vi kan bruge den i vores showAllMoviesVategories methode
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

    public void showAllMoviesCategories() {

        //Sets the tableView, and then show all the categories that has been created.
        clmTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("Title"));
        clmIMDB.setCellValueFactory(new PropertyValueFactory<Movie, Float>("ImdbRating"));
        clmPersonal.setCellValueFactory(new PropertyValueFactory<Movie, Float>("PersonalRating"));

        lstMovies.setItems(movieModel.getObservableMovie());

        lstCategories.setItems(categoryModel.getAllCategories());
        clmCategories.setCellValueFactory(new PropertyValueFactory<Category, String>("Category"));

        lstMovieByCategory.setItems(moviesInCategoryModel.getMoviesToBeViewed());
        clmTitleInCategory.setCellValueFactory(new PropertyValueFactory<Movie, String>("Title"));
        clmPersonalInCategory.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("ImdbRating"));
        clmIMDBInCategory.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("PersonalRating"));


    }


    /*
    A method to Add new movies to the database.
    This is the code that let you create a movie, if you fulfill the requirements.
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
        dialogWindow.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        dialogWindow.setResizable(false);
        Scene scene = new Scene(pane);
        dialogWindow.setScene(scene);

        dialogWindow.showAndWait();
        updateMovieModel();
    }

    private void updateMovieModel() throws Exception { //Allows you to update the movieModel.
        MovieModel updateMovieModel = new MovieModel();
        movieModel = updateMovieModel;
        lstMovies.setItems(movieModel.getObservableMovie());

    }

    private void updateCategoryModel() throws Exception { //Allows you to update the categoryModel.
        CategoryModel updateCategoryModel = new CategoryModel();
        categoryModel = updateCategoryModel;
        lstCategories.setItems(categoryModel.getAllCategories());
    }

    public Movie getSelectedMovie() { //Tells the program that which movie has been clicked on.
        Movie movie;
        movie = lstMovies.getSelectionModel().getSelectedItem();
        return movie;
    }

    /*
    A method to delete movies from the database.
    This method allows you to delete the movie that you have clicked on.
     */
    public void handleDeleteMovie(ActionEvent actionEvent) throws Exception {
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a movie");
            alert.setHeaderText("Choose a movie to delete");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //alertUser method that asks the person, to make sure they want to delete the movie.
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedMovie.getTitle().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) { //If the button was pressed to delete, then the program will update, so that it is no longer there.
                movieModel.deleteMovie(getSelectedMovie());
                updateMovieModel();
                updateMovieToCategoryModel();
            }
        }
    }

    /*
    Creates a new category in the database.
    This method allows you to create a category of any type you would like.
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
        dialogWindow.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        dialogWindow.setResizable(false);
        Scene scene = new Scene(pane);
        dialogWindow.setScene(scene);

        dialogWindow.showAndWait();
        updateCategoryModel();
    }

    /*
    Deletes a category from the database.
    This method allows you to delete the categories that has been made.
     */
    public void handleDeleteCategory(ActionEvent actionEvent) throws Exception {
        Category selectedCategory = lstCategories.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a category");
            alert.setHeaderText("Choose a category to delete");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedCategory.getCategory().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                categoryModel.deleteCategory(getSelectedCategory());
                updateCategoryModel();
            }
        }
    }

    private Category getSelectedCategory() { //Tells the program which category that has been selected.
        Category category;
        category = lstCategories.getSelectionModel().getSelectedItem();
        return category;
    }

    /*
    Adds a movie from the database to the chosen category.
    This method allows you to add a movie to the specific category.
     */
    public void handleAddMovieToCategory(ActionEvent actionEvent) throws SQLException {
        selectedCategory = lstCategories.getSelectionModel().getSelectedItem();
        selectedMovie = lstMovies.getSelectionModel().getSelectedItem();
        if(selectedCategory == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a category");
            alert.setHeaderText("Choose a category to add to");
            alert.show();
        }
        else if (selectedMovie == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a movie");
            alert.setHeaderText("Choose a movie to add");
            alert.show();
        }
        else {
                moviesInCategoryModel.addMovieToCategory(selectedCategory, selectedMovie);
            }
        }

    private void updateMovieToCategoryModel() throws SQLException { //If a movie has been added to the category, it tells the program to update the category, so you can see the movie.
        MoviesInCategoryModel updateMovieToCategoryModel = new MoviesInCategoryModel();
        moviesInCategoryModel = updateMovieToCategoryModel;
        lstMovieByCategory.setItems(moviesInCategoryModel.getMoviesToBeViewed());
        moviesInCategoryModel.showList(categoryNumber);
    }

    public void handleDeleteMovieFromCategory(ActionEvent actionEvent) throws Exception { //Allows you to delete a movie from a specific category
        selectedMovie = lstMovieByCategory.getSelectionModel().getSelectedItem();
        selectedCategory = lstCategories.getSelectionModel().getSelectedItem();
        if (selectedMovie == null || selectedCategory == null) { //Tells the user that the person has to click on a movie, in order to delete it.
            alertUser("Please select the movie you wish to delete");
        }
        else {
            int movieID = selectedMovie.getId();
            int categoryID = selectedCategory.getId();
            int movieToBeDeleted = moviesInCategoryModel.getCatMovieID(movieID,categoryID);
            moviesInCategoryModel.deleteMovieFromCategory(selectedMovie, selectedCategory, movieToBeDeleted);
            updateMovieToCategoryModel();

        }
    }


    public void handlePlayMovie(ActionEvent actionEvent) { //Allows the person to play the chosen movie.
        // Check if a movie has been selected by the user
        if (selectedMovie == null) {
            // If no movie has been selected, alert the user to choose a movie
            alertUser("Incorrect filepath, try choosing a movie");
        } else {
            // If a movie has been selected, call the play method on the movieModel object
            movieModel.play(selectedMovie);
        }
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }

    public void handleEditIMDB(ActionEvent actionEvent) { //If the user decides that a movie should get a new personRating, then this method allows the user to change it.
        try {
            Movie seletedMovie = lstMovies.getSelectionModel().getSelectedItem();

            if (seletedMovie != null) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/GUI/View/editIMDB.fxml"));

                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage stage = new Stage();
                stage.setTitle("Edit the movie");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow()); // Does so the window has to be closed before the programm can be accessed again
                stage.setScene(scene);
                MovieDataInputs movieDataInputs = fxmlLoader.getController();
                movieDataInputs.setSelectMovie(seletedMovie);
                stage.showAndWait();
                updateMovieModel(); // Updates the lstMovies

            } else {
                alertUser("Please select a movie to edit");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
/*
    This method checks for movies considered old. If there is any a window will be opened and the user will get the ability to delete movies from a list.
    When done the user can close
 */
    public void checkOldMovies() throws Exception {
        // Create an ArrayList to store movies that are considered "old"
        ArrayList<Movie> outDatedMovies = oldMovies();

        // Check if there are any old movies in the list
        if (outDatedMovies.size() != 0) {
            // Create an observable list of the old movies
            moviesToBeReViewed = FXCollections.observableArrayList();
            moviesToBeReViewed.addAll(oldMovies());

            // Check if a check for old movies has been performed previously
            if(oldMoviesChecked == false) {
                // Load the FXML file for displaying old movies
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/GUI/View/OldMovies.fxml"));
                AnchorPane pane = (AnchorPane) loader.load();

                // Get the controller for the FXML file and save the current controller object
                movieDataInputs = loader.getController();
                movieDataInputs.saveController(this, moviesToBeReViewed);

                // Create a new stage for displaying old movies
                dialogWindow = new Stage();
                dialogWindow.setTitle("Old Movies");
                dialogWindow.setResizable(false);

                // Set the scene to the loaded FXML file
                Scene scene = new Scene(pane);
                dialogWindow.setScene(scene);

                // Show the stage and set oldMoviesChecked to true
                dialogWindow.showAndWait();
                oldMoviesChecked = true;
            }
            else {
                // Set the selected list in the movieDataInputs object to moviesToBeReViewed
                // and show the dialogWindow stage
                movieDataInputs.setSelectedList(moviesToBeReViewed);
                dialogWindow.show();
            }
        }
    }

        public ArrayList<Movie> oldMovies () throws Exception {
            movieModel = new MovieModel();
            ArrayList<Movie> allMovies;
            allMovies = movieModel.getAllMovies(); // Creates an Arraylist of all movies.

            ArrayList<Movie> outDatedMovies = new ArrayList<>(); // Creates a new list for outDatedMovies
            for (Movie movieToBeChecked : allMovies) {
                String filePath = movieToBeChecked.getFilepath();
                File file = new File(filePath); // So we can call the methods from the class File.
                if (file.exists() && movieToBeChecked.getPersonalRating() <= 6) { // Check if the file Exist, and if it has a personal rating at 6 or higher.
                    BasicFileAttributes bfr = Files.readAttributes(Path.of(filePath), BasicFileAttributes.class);

                    FileTime lastAccessed = bfr.lastAccessTime(); // Checks when the Movie was last accessed(Seen/opened).

                    LocalDate dateToCheck = lastAccessed.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Gets the day the file was last accessed
                    LocalDate localdate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate(); // Gets the current day from the system that runs the program

                    Period yearsBetween = Period.between(dateToCheck, localdate); // Calculates the years between the two diffrent days.

                    if (yearsBetween.getYears() >= 2) { // Checks whether the time since the movie was last watched is two years ago
                        outDatedMovies.add(movieToBeChecked);
                    }
                }
            }
            return outDatedMovies; // Returns the list of the outdated Movies to the method checkOldMovies.
        }
    }


