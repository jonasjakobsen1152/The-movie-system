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
            categoryNumber = selectedCategory.getId();

            try{
                moviesInCategoryModel.showList(categoryNumber);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        lstMovieByCategory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                selectedMovie = newValue);

        try {
            moviesInCategoryModel = new MoviesInCategoryModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        showAllMoviesCategories();



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

    public void showAllMoviesCategories() {

        //Sets the tableView, and then what it should show.
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
        dialogWindow.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
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

    public Movie getSelectedMovie() {
        Movie movie;
        movie = lstMovies.getSelectionModel().getSelectedItem();
        return movie;
    }

    /*
    A method to delete movies from the database
     */
    public void handleDeleteMovie(ActionEvent actionEvent) throws Exception {
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a movie");
            alert.setHeaderText("Choose a movie to delete");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete: " + selectedMovie.getTitle().concat("?"));
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                movieModel.deleteMovie(getSelectedMovie());
                updateMovieModel();
                updateMovieToCategoryModel();
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
        dialogWindow.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
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

    private Category getSelectedCategory() {
        Category category;
        category = lstCategories.getSelectionModel().getSelectedItem();
        return category;
    }

    /*
    Adds a movie from the database to the chosen category
     */
    public void handleAddMovieToCategory(ActionEvent actionEvent) {
        selectedCategory = lstCategories.getSelectionModel().getSelectedItem();
        int sizeOfCategory = moviesInCategoryModel.getMoviesToBeViewed().size();
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();
        Category selectedCategory = lstCategories.getSelectionModel().getSelectedItem();
        if(sizeOfCategory != 0){
            Movie newMovie = lstMovieByCategory.getItems().get(sizeOfCategory-1);
            int lastMovieID = newMovie.getId();
            int selectedCategoryID = selectedCategory.getId();

            int highestCatMovieID = moviesInCategoryModel.getCatMovieID(lastMovieID, selectedCategoryID);
            moviesInCategoryModel.addMovieToCategory(selectedCategory,selectedMovie, highestCatMovieID + 1);
        }
        else {
            moviesInCategoryModel.addMovieToCategory(selectedCategory, selectedMovie, 1);
        }
    }

    private void updateMovieToCategoryModel() throws SQLException {
        MoviesInCategoryModel updateMovieToCategoryModel = new MoviesInCategoryModel();
        moviesInCategoryModel = updateMovieToCategoryModel;
        lstMovieByCategory.setItems(moviesInCategoryModel.getMoviesToBeViewed());
        moviesInCategoryModel.showList(categoryNumber);
    }

    public void handleDeleteMovieFromCategory(ActionEvent actionEvent) throws Exception {
        selectedMovie = lstMovieByCategory.getSelectionModel().getSelectedItem();
        selectedCategory = lstCategories.getSelectionModel().getSelectedItem();
        if (selectedMovie == null || selectedCategory == null) {
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


    public void handlePlayMovie(ActionEvent actionEvent) {

        if (selectedMovie == null) {
            alertUser("Incorrect filepath, try choosing a movie");
        } else {
            movieModel.play(selectedMovie);
        }
    }

    private void alertUser(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }
    private void informationUser(String information){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Regarding oldMovies");
        info.setHeaderText(information + "");
        info.showAndWait();
    }
    public void handleEditIMDB(ActionEvent actionEvent) {
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

    public void checkOldMovies() throws Exception {
        ArrayList<Movie> outDatedMovies = oldMovies();
        if (outDatedMovies.size() != 0) {
            moviesToBeReViewed = FXCollections.observableArrayList();
            moviesToBeReViewed.addAll(oldMovies());

            if(oldMoviesChecked == false) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/GUI/View/OldMovies.fxml"));
                AnchorPane pane = (AnchorPane) loader.load();
                movieDataInputs = loader.getController();
                movieDataInputs.saveController(this, moviesToBeReViewed); //Saves the controller object so MovieDataInput can update the TableView when a movie is deleted.

                dialogWindow = new Stage();
                dialogWindow.setTitle("Old Movies");
                dialogWindow.setResizable(false);
                Scene scene = new Scene(pane);
                dialogWindow.setScene(scene);

                dialogWindow.showAndWait();
                oldMoviesChecked = true;
            }
            else {
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


