package GUI.Controller;

import BE.Movie;
import GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MovieDataInputs {

    public TextField txtIMDBChange;
    public Text txt;

    public Button txtChangeIMDB;
    private MovieModel movieModel = new MovieModel();
    public TextField txtTitle;
    public TextField txtIMDBRating;
    public TextField txtPersonalRating;
    public TextField txtYear;
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

    public void handleCreateMovie(ActionEvent actionEvent) {
        String Title = txtTitle.getText();
        Float PersonalRating = Float.valueOf(txtPersonalRating.getText());
        Float IMDBRating = Float.valueOf(txtIMDBRating.getText());
        String FilePath = txtFilePath.getText();
        try {
           movieModel.createMovie(Title, PersonalRating, IMDBRating, FilePath);
           Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
           stage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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


    public void setSelectMovie(Movie m) {
        selectMovie = m;
        txtPersonalRating.setText(String.valueOf(selectMovie.getPersonalRating()));
    }


    public void handleCreationCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
