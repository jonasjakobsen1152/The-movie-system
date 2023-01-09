package BLL.Util;

import BE.Movie;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MoviePlayer {

    public void play(Movie selectedMovie) {
        String filePath = selectedMovie.getFilepath();

        File file = new File(filePath);

        try{
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
