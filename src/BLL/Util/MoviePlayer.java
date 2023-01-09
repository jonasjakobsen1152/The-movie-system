package BLL.Util;

import BE.Movie;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MoviePlayer {

    public void play(Movie selectedMovie) {
        String filePath = selectedMovie.getFilepath();

        File file = new File(filePath);
        if(file.exists())
        try{
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        else{
            alertUser("Incorrect filepath");
        }
    }

    private void alertUser(String error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error);
        alert.setHeaderText(error + "");
        alert.showAndWait();
    }
}
