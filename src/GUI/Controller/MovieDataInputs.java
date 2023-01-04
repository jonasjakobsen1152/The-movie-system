package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MovieDataInputs {

    public TextField txtTitle;
    public TextField txtIMDBRating;
    public TextField txtPersonalRating;
    public TextField txtYear;
    public TextField txtFilePath;
    public Button btnCreateMovie;
    public Button txtChooseFile;
    public Button btnCancel;

    public void handleChooseFile(ActionEvent actionEvent) {
        String fileName;
        Stage stage = new Stage();
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(stage);
        if (file != null) {

            fileName = file.toURI().toString();
            fileName=fileName.substring(6);
            String newFile = fileName;
            fileName = newFile.replace("%20"," ");
            txtFilePath.setText(fileName);
        }
    }

    public void handleCreateMovie(ActionEvent actionEvent) {
    }

    public void handleCreationCancel(ActionEvent actionEvent) {
    }
}
