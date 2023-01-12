package BLL.Util;

import BE.Movie;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MoviePlayer {


    /*The method takes in a "Movie" object, which has a filepath property, as an argument.
    The code first gets the filepath of the movie by calling the "getFilepath()" method on the "selectedMovie" object.
    Then it creates a "File" object using the filepath. It then checks if the file exists, if it does, it uses the "Desktop" class to open the file.
    If the file does not exist, the code calls an "alertUser" method and passes in the string "Incorrect filepath" as an argument.
    */

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
