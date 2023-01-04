package GUI.Controller;

import BE.Movie;
import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController {
    public TableView lstMovies;
    public TableView lstMovieByCategory;
    public TableView lstCategories;
    public Button btnAddMovieToCategory;
    public Button btnDeleteMovieFromCategory;
    public Button btnCreateMovie;
    public Button btnDeleteMovie;
    public Button btnCreateCategory;
    public Button btnDeleteCategory;
    public TextField txtFilter;

    public MovieModel movieModel;

    public void initialize (URL url, ResourceBundle resourceBundle){

        txtFilter.textProperty().addListener((((observable, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })));
    }


    public void handleAddMovieToCategory(ActionEvent actionEvent) {
    }

    public void handleDeleteMovieFromCategory(ActionEvent actionEvent) {
    }

    public void handleCreateMovie(ActionEvent actionEvent) {
    }

    public void handleDeleteMovie(ActionEvent actionEvent) {
    }

    public void handleCreateCategory(ActionEvent actionEvent) {
    }

    public void handleDeleteCategory(ActionEvent actionEvent) {
    }
}
