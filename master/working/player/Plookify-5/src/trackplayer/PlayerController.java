 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackplayer;

import java.io.File;
import static java.lang.Math.floor;
import static java.lang.String.format;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import static javafx.application.Platform.runLater;

/**
 * FXML Controller class
 *
 * @author Zilan Su */
public class PlayerController implements Initializable {
    @FXML
    private Button pauseButton;
    @FXML
    private Button playButton;
    @FXML
    private Button reloadButton;
    @FXML
    private Slider timeSlider;
    @FXML
    private Label time;
    @FXML
    private Label volumeLabel;
    @FXML
    private Slider volumeSlider;
    MediaPlayer mediaPlayer;
    Duration duration;
    Media media;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    media = new Media(new File("C:\\Users\\samie\\Desktop\\Plookify-5\\src\\trackplayer\\Hey there delilah.mp3").toURI().toString());
    //create a mediaplayer
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setAutoPlay(true);
    
    //Add play button
    playButton.setOnAction((ActionEvent e) -> {
    mediaPlayer.play();
    });
    playButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
    //playButton.setStyle("-fx-background-color: Black");
    //playButton.setStyle("-fx-body-color: Black");
    });
    playButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
    //playButton.setStyle("-fx-background-color: Black");
    });
    
    //Add pause button
    pauseButton.setOnAction((ActionEvent e) -> {
        mediaPlayer.pause();
    });

    pauseButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
    //pauseButton.setStyle("-fx-background-color: Black");
    //pauseButton.setStyle("-fx-body-color: Black");
    });
    pauseButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
    //pauseButton.setStyle("-fx-background-color: Black");
    });
    
    //Add reload button
    reloadButton.setOnAction((ActionEvent e) -> {
        mediaPlayer.seek(mediaPlayer.getStartTime());
    });

    reloadButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
    //reloadButton.setStyle("-fx-background-color: Black");
    //reloadButton.setStyle("-fx-body-color: Black");
    });
    reloadButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
    //reloadButton.setStyle("-fx-background-color: Black");
    });
    
    //Add time label
    mediaPlayer.currentTimeProperty().addListener((Observable ov) -> {
    updateValues();
    });
    
    mediaPlayer.setOnReady(() -> {
    duration = mediaPlayer.getMedia().getDuration();
    updateValues();
    });
    
    //Add vol lable
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volumeSlider.isValueChanging()) {
                    mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });
        
        // Add Volume slider
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    mediaPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
            }
        });
    } 
    
    //Let time slider moving and also the time label
    protected void updateValues() {
    if (time != null && timeSlider != null) {
    runLater(() -> {
    Duration currentTime = mediaPlayer.getCurrentTime();
    time.setText(formatTime(currentTime, duration));
    timeSlider.setDisable(duration.isUnknown());
    if(!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()){
    timeSlider.setValue(currentTime.divide(duration).toMillis()*100.0);
    }
    });
    }
    }
    private static String formatTime(Duration elapsed, Duration duration) {
    int intElapsed = (int) floor(elapsed.toSeconds());
    int elapsedHours = intElapsed / (60 * 60);
    if (elapsedHours > 0) {
    intElapsed -= elapsedHours * 60 * 60;
    }
    int elapsedMinutes = intElapsed / 60;
    int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
    - elapsedMinutes * 60;

    if (duration.greaterThan(Duration.ZERO)) {
    int intDuration = (int) floor(duration.toSeconds());
    int durationHours = intDuration / (60 * 60);
    if (durationHours > 0) {
    intDuration -= durationHours * 60 * 60;
    }
    int durationMinutes = intDuration / 60;
    int durationSeconds = intDuration - durationHours * 60 * 60
    - durationMinutes * 60;
    if (durationHours > 0) {
    return format("%d:%02d:%02d/%d:%02d:%02d",
    elapsedHours, elapsedMinutes, elapsedSeconds,
    durationHours, durationMinutes, durationSeconds);
    } else {
    return format("%02d:%02d/%02d:%02d",
    elapsedMinutes, elapsedSeconds, durationMinutes,
    durationSeconds);
    }
    } else {
    if (elapsedHours > 0) {
    return format("%d:%02d:%02d", elapsedHours,
    elapsedMinutes, elapsedSeconds);
    } else {
    return format("%02d:%02d", elapsedMinutes,
    elapsedSeconds);
    }
    }
    }
    
}
