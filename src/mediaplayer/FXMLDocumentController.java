/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import java.io.File;
import java.net.URL;

import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;



/**
 *
 * @author Cristina
 */
public class FXMLDocumentController implements Initializable {

    private MediaPlayer mediaPlayer;
    
 
    @FXML
    private MediaView mediaView;

    private String filePath;

    @FXML
    private Slider slider;
    
    @FXML
    private Slider seekSlider;
    
    private Duration duration;
    
    @FXML
    private Label playTime;
    
    @FXML
    //opening a file and playing that file into media view 
    private void openFileButtonAction(ActionEvent event) {
        //using 2 classes 
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter
        ("Select a video file", "*.mp4","*.m4p", "*.m4v","*.avi","*.wmv");
       
        //choose the fileChooser object and add the desired filters
        fileChooser.getExtensionFilters().add(filter);
        //when the window opens, that's when it's going to choose the file
        File file = fileChooser.showOpenDialog(null);
        //adding the string variable filePath to the media path
        filePath = file.toURI().toString();
        //if there is a path, we create a Media object and we pass this object to the mediaPlayer
        if (filePath != null) {
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            
            
            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();
            
            
            width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
            
            
            slider.setValue(mediaPlayer.getVolume() * 100);
            slider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(slider.getValue()/100);
                }
            });
                mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    seekSlider.setValue(newValue.toSeconds());
                }
            });
            
        }
               seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    
                     mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));
                }
                 
                
               });
            
            mediaPlayer.play();
   

               
        }


    @FXML
    private void pauseVideo(ActionEvent event) {
        mediaPlayer.pause();
    }

    @FXML
    private void playVideo(ActionEvent event) {
        mediaPlayer.setRate(1);
        mediaPlayer.play();
       
    }

    @FXML
    private void stopVideo(ActionEvent event) {
        mediaPlayer.stop();
    }

    @FXML
    private void fastVideo(ActionEvent event) {
        mediaPlayer.setRate(1.5);
    }
 @FXML
    private void replayVideo(ActionEvent event) {
        mediaPlayer.stop();
        mediaPlayer.play();
    }

    @FXML
    private void slowVideo(ActionEvent event) {
        mediaPlayer.setRate(.75);
    }

    @FXML
    private void muteVideo(ActionEvent event) {
       mediaPlayer.setVolume(0);
       
       
    }
     @FXML
    private void exitVideo(ActionEvent event) {
        System.exit(0);
    }
      
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
    
//    protected void updateValues() {
//        if (playTime != null && seekSlider != null && slider != null) {
//            Platform.runLater(new Runnable() {
//                public void run() {
//                    Duration currentTime = mediaPlayer.getCurrentTime();
//                    playTime.setText(formatTime(currentTime, duration));
//                    seekSlider.setDisable(duration.isUnknown());
//                    if (!seekSlider.isDisabled()
//                            && duration.greaterThan(Duration.ZERO)
//                            && !seekSlider.isValueChanging()) {
//                        seekSlider.setValue(currentTime.divide(duration).toMillis()
//                                * 100.0);
//                    }
//                    if (!slider.isValueChanging()) {
//                        slider.setValue((int) Math.round(mediaPlayer.getVolume()
//                                * 100));
//                    }
//                }
//
//                private String formatTime(Duration currentTime, Duration duration) {
//                     int intElapsed = (int) Math.floor(elapsed.toSeconds());
//        int elapsedHours = intElapsed / (60 * 60);
//        if (elapsedHours > 0) {
//            intElapsed -= elapsedHours * 60 * 60;
//        }
//        int elapsedMinutes = intElapsed / 60;
//        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
//                - elapsedMinutes * 60;
//
//        if (duration.greaterThan(Duration.ZERO)) {
//            int intDuration = (int) Math.floor(duration.toSeconds());
//            int durationHours = intDuration / (60 * 60);
//            if (durationHours > 0) {
//                intDuration -= durationHours * 60 * 60;
//            }
//            int durationMinutes = intDuration / 60;
//            int durationSeconds = intDuration - durationHours * 60 * 60
//                    - durationMinutes * 60;
//            if (durationHours > 0) {
//                return String.format("%d:%02d:%02d/%d:%02d:%02d",
//                        elapsedHours, elapsedMinutes, elapsedSeconds,
//                        durationHours, durationMinutes, durationSeconds);
//            } else {
//                return String.format("%02d:%02d/%02d:%02d",
//                        elapsedMinutes, elapsedSeconds, durationMinutes,
//                        durationSeconds);
//            }
//        } else {
//            if (elapsedHours > 0) {
//                return String.format("%d:%02d:%02d", elapsedHours,
//                        elapsedMinutes, elapsedSeconds);
//            } else {
//                return String.format("%02d:%02d", elapsedMinutes,
//                        elapsedSeconds);
//            }
//        }
//                }
//            });
//        }
//    }


   

