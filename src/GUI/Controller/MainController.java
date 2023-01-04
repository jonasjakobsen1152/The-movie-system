package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

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
    @FXML
    private Label lbl;

    public void clickBtn(ActionEvent actionEvent) {
        lbl.setText("Huh?");
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
