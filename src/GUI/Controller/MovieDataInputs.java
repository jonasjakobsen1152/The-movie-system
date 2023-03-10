package GUI.Controller;

import BE.Movie;
import GUI.Model.MovieModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MovieDataInputs {

    public TextField txtIMDBChange;
    public Text txt;
    public Button txtChangeIMDB;
    public TableView lstMoviesToBeReviewed;
    private MainController mainController;
    private ObservableList<Movie> moviesToBeReViewed;
    public TableColumn clmTitles;
    private MovieModel movieModel = new MovieModel();
    public TextField txtTitle;
    public TextField txtIMDBRating;
    public TextField txtPersonalRating;
    public TextField txtFilePath;
    public Button btnCreateMovie;
    public Button txtChooseFile;
    public Button btnCancel;
    private Movie selectMovie;
    private File file;
    private String targetString = "MovieFiles";
    private Path target = Paths.get(targetString);

    public MovieDataInputs() throws Exception {
    }

    /**
     * This is used to choose a file you want to add to the movie list
     * @param actionEvent
     */
    public void handleChooseFile(ActionEvent actionEvent) {
        String fileName;
        Stage stage = new Stage();
        FileChooser fc = new FileChooser();
        file = fc.showOpenDialog(stage);
        if (file != null) {

            fileName = file.toURI().toString();
            fileName=fileName.substring(6);
            String newFile = fileName;
            fileName = newFile.replace("%20"," ");
            txtFilePath.setText(fileName);
        }
    }

    /**
     * This is used to create a new movie based on the given information such as title and filepath
     * @param actionEvent
     */
    public void handleCreateMovie(ActionEvent actionEvent) {
        String Title = txtTitle.getText();
        Float PersonalRating = Float.valueOf(txtPersonalRating.getText());
        Float IMDBRating = Float.valueOf(txtIMDBRating.getText());
        String FilePath = txtFilePath.getText();
        try {
           movieModel.createMovie(Title,IMDBRating,PersonalRating, FilePath);
           Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
           stage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Used to edit the IMDBRating
     * @param actionEvent
     * @throws Exception
     */
    public void handleEditIMDB(ActionEvent actionEvent) throws Exception {
        int id = selectMovie.getId();
        String placeHolderTitle = selectMovie.getTitle();
        Float placeHolderIMDBRating = selectMovie.getImdbRating();
        Float updatedPersonalRating = Float.valueOf(txtPersonalRating.getText());
        String placeHolderFilePath = selectMovie.getFilepath();
        movieModel.updateMovie(new Movie(id, placeHolderTitle, placeHolderIMDBRating, updatedPersonalRating, placeHolderFilePath));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();

    }

    /**
     * Sets the selected movie
     * @param m
     */
    public void setSelectMovie(Movie m) {
        selectMovie = m;
        txtPersonalRating.setText(String.valueOf(selectMovie.getPersonalRating()));
    }

    /**
     * Used to close the window if you don't want to create a movie
     * @param actionEvent
     */
    public void handleCreationCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /*
       A method used to set list of old movies in opened window
     */
    public void setSelectedList(ObservableList<Movie> moviesToBeReViewed) {
        //Sets the tableView, and then what it should show.
        clmTitles.setCellValueFactory(new PropertyValueFactory<Movie, String>("Title"));
        lstMoviesToBeReviewed.setItems(moviesToBeReViewed);
    }
/*
    A method to delete movies from a list of old Movies
 */
public void handleDeleteMovie(ActionEvent actionEvent) throws Exception {
    selectMovie = (Movie) lstMoviesToBeReviewed.getSelectionModel().getSelectedItem();
    movieModel.deleteMovie(selectMovie);
    saveController(mainController,moviesToBeReViewed);
    moviesToBeReViewed.clear();
    moviesToBeReViewed.addAll(mainController.oldMovies());
}
    public void saveController(MainController mainController, ObservableList<Movie> moviesToBeReViewed) {
        this.mainController = mainController;
        this.moviesToBeReViewed = moviesToBeReViewed;
        setSelectedList(moviesToBeReViewed);
    }

}
