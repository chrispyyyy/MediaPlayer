/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osmediaplayer;

import java.io.File;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Cristina
 */
public class OSMediaPlayer extends Application {

    private Media media;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private String filePath;
    private Slider slider;
    private Duration duration;
    private Label label_2;
    private Label label_4;
    private MediaPlayer.Status status;
    private Label label_3;
    private Slider volume;
    private Label name;

    @Override
    public void start(Stage primaryStage) {
        //creating the vertical box 
        VBox vBox = new VBox();
        //default video after the app is ran
        media = new Media(new File("C:\\Users\\Cristina\\Desktop\\OSMediaPlayer\\OSMediaPlayer\\src\\vid.mp4").toURI().toString());
        //passing the media object to the mediaPlayer object
        mediaPlayer = new MediaPlayer(media);
        //autoplay the default video
        mediaPlayer.setAutoPlay(true);
        //adding the mediaPlayer object to the mediaView object
        mediaView = new MediaView(mediaPlayer);
        //setting the width
        mediaView.setFitWidth(1000);
        //setting the height
        mediaView.setFitHeight(630);
        //Indicates whether to preserve the aspect ratio when scaling to fit within the fitting bounding box
        mediaView.setPreserveRatio(false);

        //creating the first horizontal box 
        HBox hBox = new HBox();
        //positioning the hBox in the center
        hBox.setAlignment(Pos.CENTER);
        //Label for time
        Label label_1 = new Label("Time  ");
        //In label_2, we will display the formatted current play time
        label_2 = new Label();
        //This slider will show the progress of the media playing
        slider = new Slider();
        //setting the height
        slider.setPrefHeight(20);
        //setting the width
        slider.setPrefWidth(900);
        //setting the cursor for the progress slider
        slider.setCursor(javafx.scene.Cursor.HAND);
        //adding the first two labels and the progress slider to the first horizontal box
        hBox.getChildren().addAll(label_1, slider, label_2);
        //creating the second horizontal box
        HBox hBox2 = new HBox();
        //moves the hBox2 to the right on the x axis
        hBox2.setTranslateX(100);
        //creating the first icon for opening a new file
        ImageView icon0 = new ImageView(new Image("openFile.png"));
        //placing the icon on the left side
        icon0.setTranslateX(-80);
        //aligning the icon in the hBox2
        icon0.setCursor(javafx.scene.Cursor.HAND);
        //mouse click on the icon
        icon0.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
               //creating a FileChooser object
                FileChooser fileChooser = new FileChooser();
                //setting a title to the file chooser
                fileChooser.setTitle("Open File Dialog");
                Stage stage = (Stage) icon0.getScene().getWindow();
                //using the ExtensionFilter class, we will set the supported file extensions
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a video file", "*.mp4", "*.m4p", "*.m4v", "*.avi", "*.wmv", "*.mp3");
                //adding the filters to the file chooser
                fileChooser.getExtensionFilters().add(filter);
                //when the window opens, that's when it's going to choose the file
                File file = fileChooser.showOpenDialog(stage);
                //adding the string variable filePath to the media path
                filePath = file.toURI().toString();
                
                if (filePath != null) {
                    //creating a new media object having the filePath as parameter
                    media = new Media(filePath);
                    //creating a new Media Player object having the media as parameter
                    mediaPlayer = new MediaPlayer(media);
                    //passing the mediaView the mediaPlayer object
                    mediaView.setMediaPlayer(mediaPlayer);
                    //playing the media 
                    mediaPlayer.play();
                    
                    
                    ready();

                    currentTime();

                    slider();

                }
            }
        });
        //creating the icon for skipping back 
        ImageView icon1 = new ImageView(new Image("skip_back.png"));
        //setting the cursor for the icon
        icon1.setCursor(javafx.scene.Cursor.HAND);
        //aligning the icon in the hBox2
        hBox2.setMargin(icon1, new Insets(5));
        //mouse click on icon
        icon1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               //seeking through the current mediaPlayer, getting the current time and dividing it by 1.3 
                mediaPlayer.seek(mediaPlayer.getCurrentTime().divide(1.3));
            }
        });
        //creating the icon for playing
        ImageView icon2 = new ImageView(new Image("play.png"));
        //setting the cursor for the icon
        icon2.setCursor(javafx.scene.Cursor.HAND);
         //aligning the icon in the hBox2
        hBox2.setMargin(icon2, new Insets(5));
       //mouse click on icon
        icon2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //setting a normal speed to the media
                mediaPlayer.setRate(1);
                //getting the status of the mediaPlayer
                status = mediaPlayer.getStatus();
                if (status == MediaPlayer.Status.UNKNOWN) {

                    return;
                }
                //if the media is paused or stop, we play it
                if (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.STOPPED) {
                    
                    mediaPlayer.play();
                }
            }
        });
        //creating the icon for pausing
        ImageView icon3 = new ImageView(new Image("pause.png"));
        //setting the cursor for the icon
        icon3.setCursor(javafx.scene.Cursor.HAND);
         //aligning the icon in the hBox2
        hBox2.setMargin(icon3, new Insets(5));
        //mouse click on icon
        icon3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //pausing the current media
                mediaPlayer.pause();
            }
        });
        //creating the icon for stopping
        ImageView icon4 = new ImageView(new Image("stop.png"));
        //setting the cursor for the icon
        icon4.setCursor(javafx.scene.Cursor.HAND);
        //aligning the icon in the hBox2
        hBox2.setMargin(icon4, new Insets(5));
        //mouse click on icon
        icon4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //stopping the current media
                mediaPlayer.stop();
            }
        });
        //creating the icon for skipping forward
        ImageView icon5 = new ImageView(new Image("skip_forward.png"));
        //setting the cursor for the icon
        icon5.setCursor(javafx.scene.Cursor.HAND);
        //aligning the icon in the hBox2
        hBox2.setMargin(icon5, new Insets(5));
        //mouse click on icon
        icon5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //seeking through the current mediaPlayer, getting the current time and multiplying it by 1.5
                mediaPlayer.seek(mediaPlayer.getCurrentTime().multiply(1.5));
            }
        });
        //creating the icon for slowing down the video
        ImageView icon6 = new ImageView(new Image("slow.png"));
        //setting the cursor for the icon
        icon6.setCursor(javafx.scene.Cursor.HAND);
        //aligning the icon in the hBox2
        hBox2.setMargin(icon6, new Insets(5));
        //mouse click on icon
        icon6.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //setting the rate to a smaller one
                mediaPlayer.setRate(.5);
            }
        });
        //creating the icon for fast forwarding the video
        ImageView icon7 = new ImageView(new Image("fast.png"));
        //setting the cursor for the icon
        icon7.setCursor(javafx.scene.Cursor.HAND); 
        //aligning the icon in the hBox2
        hBox2.setMargin(icon7, new Insets(5));
        //mouse click on icon
        icon7.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //setting tha rate to a higher one
                mediaPlayer.setRate(1.5);
            }
        });
        //creating the icon for replaying the video
        ImageView icon8 = new ImageView(new Image("replay.png"));
        //setting the cursor for the icon
        icon8.setCursor(javafx.scene.Cursor.HAND);
        //aligning the icon in the hBox2
        hBox2.setMargin(icon8, new Insets(5));
        
        icon8.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //seeking to play time zero
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        //creating the icon for fullscreening the video
        ImageView icon9 = new ImageView(new Image("fullscreen.png"));
        //setting the cursor for the icon
        icon9.setCursor(javafx.scene.Cursor.HAND);
        //aligning the icon in the hBox2
        hBox2.setMargin(icon9, new Insets(5));
        //mouse click on icon
        icon9.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (primaryStage.isFullScreen()) {
                    //exiting fullscreen
                    primaryStage.setFullScreen(false);
                    //setting the mediaView height&width to the previous ones
                    mediaView.setFitHeight(630);
                    mediaView.setFitWidth(1000);
                    //setting the icon to the fullscreen one
                    icon9.setImage(new Image("fullscreen.png"));

                } else {
                    //going fullscreen
                    primaryStage.setFullScreen(true);
                    //setting the mediaView height&width to the fullscreen ones
                    mediaView.setFitHeight(930);
                    mediaView.setFitWidth(1510);
                    //setting the icon to the minimise one
                    icon9.setImage(new Image("minimise.png"));

                }
            }
        });
        
        //creating the slider for the volume
        Slider volume = new Slider();
        //aligning the volume slider on the x&y axis
        volume.setTranslateX(230);
        volume.setTranslateY(15);
        //setting the width of the slider
        volume.setPrefWidth(100);
        //setting a default value for the slider
        volume.setValue(30);
        //setting a default value for the mediaPlayer volume
        mediaPlayer.setVolume(30);
        //adding a label for the volume
        Label label_3 = new Label("Volume:");
        //aligning the label on the x&y axis
        label_3.setTranslateX(220);
        label_3.setTranslateY(15);
        //creating a new label 
        label_4 = new Label();
        //aligning the label on the x&y axis
        label_4.setTranslateX(240);
        label_4.setTranslateY(15);
        //setting the default text for the label
        label_4.setText("30");
        //changing the volume of the media and displaying it in label_4 
        volume.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        if (volume.isValueChanging()) {
                            mediaPlayer.setVolume(volume.getValue() / 100.0);
                            label_4.setText((int) volume.getValue() + "");
                        }
                    }
                });
        //this code enables us to drag over files and copy them inside the mediaView
        mediaView.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });
        // drop the file into the mediaView
        mediaView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //getDragboard method gives the Dragboard object which contains the files being transferred
                Dragboard db = event.getDragboard();

                if (db.hasFiles()) {
                    filePath = null;
                    for (File file : db.getFiles()) {
                        filePath = file.getAbsolutePath();

                        if (filePath.endsWith(".mp4") || filePath.endsWith(".avi") || filePath.endsWith(".m4p") || filePath.endsWith(".wmw") || filePath.endsWith(".mp3")) {

                            //we have to stop the current video before playing another one
                            mediaPlayer.stop();
                            //creating a new media with the filePath
                            media = new Media(new File(filePath).toURI().toString());
                            //creading a new mediaPlayer with the media parameter
                            mediaPlayer = new MediaPlayer(media);
                            //autoplaying the media
                            mediaPlayer.setAutoPlay(true);
                            //passing the mediaPlayer to the mediaView
                            mediaView.setMediaPlayer(mediaPlayer);

                            ready();
                            currentTime();
                            slider();

                        }
                    }
                }
            }
        });
        //adding the icons, labels and volume slider
        hBox2.getChildren().addAll(icon0, icon1, icon2, icon3, icon4, icon5, icon6, icon7, icon8, icon9, label_3, volume, label_4);
        //adding the main elements to the vBox
        vBox.getChildren().addAll(mediaView, hBox, hBox2);
        //setting a background color for the vBox
        vBox.setStyle("-fx-background-color:#ccc");
        //creating the container of all content, vBox is a root Node
        Scene scene = new Scene(vBox);
        //adding the key controls
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //if we press on SPACE
                if (event.getCode() == KeyCode.SPACE) {
                    //getting the status of the media
                    status = mediaPlayer.getStatus();
                    if (status == MediaPlayer.Status.PAUSED) {
                        mediaPlayer.play();
                    } else if (status == MediaPlayer.Status.PLAYING) {
                        mediaPlayer.pause();
                    }
                 //if we press UP
                } else if (event.getCode() == KeyCode.UP) {
                    volume.setValue(volume.getValue() + 5);
                    mediaPlayer.setVolume(volume.getValue() + 5);
                    label_4.setText((int) volume.getValue() + 5 + "");
                    updateProgress();
                    //if we press DOWN
                } else if (event.getCode() == KeyCode.DOWN) {
                    //setting the volume slider
                    volume.setValue(volume.getValue() - 5);
                    //setting the volume
                    mediaPlayer.setVolume(volume.getValue() - 5);
                    //setting the text in the label
                    label_4.setText((int) volume.getValue() - 5 + "");
                    
                    updateProgress();
                    //if we press ESCAPE
                } else if (event.getCode() == KeyCode.ESCAPE) {
                    //exiting fullscreen
                    primaryStage.setFullScreen(false);
                    //setting the mediaView height&width to the minimised ones
                    mediaView.setFitHeight(630);
                    mediaView.setFitWidth(1000);
                    icon9.setImage(new Image("fullscreen.png"));
                }
            }
        }
        );
        
        primaryStage.setTitle("OS Media Player");
        //setting the scene for the stage
        primaryStage.setScene(scene);
        //making the stage fullscreen by double clicking on the scene
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    if (primaryStage.isFullScreen()) {
                        //exiting fullscreen
                        primaryStage.setFullScreen(false);
                        //setting the mediaView height&width to the minimised ones
                        mediaView.setFitHeight(630);
                        mediaView.setFitWidth(1000);
                        icon9.setImage(new Image("fullscreen.png"));

                    } else {
                        //entering fullscreen
                        primaryStage.setFullScreen(true);
                        //setting the mediaView height&width to the fullscreen ones
                        mediaView.setFitHeight(930);
                        mediaView.setFitWidth(1510);
                        icon9.setImage(new Image("minimise.png"));;
                    }
                }
            }
        });
        //making the stage visible
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
//Sets the MediaPlayer.Status.READY event handler.State of the player once it is prepared to play. This state is entered only once when the movie is loaded
    private void ready() {
        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                duration = mediaPlayer.getMedia().getDuration();
                updateProgress();
            }
        });
    }
//method for getting current time
    private void currentTime() {
        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateProgress();
            }
        });
    }
//method for seeking in the media 
    private void slider() {
        slider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (slider.isValueChanging()) {
                    mediaPlayer.seek(duration.multiply(slider.getValue() / 100.0));
                }
            }
        });
    }
//method for updating the progress of the media
    public void updateProgress() {
        if (slider != null) {
            //getting the current time of the media
            Duration currentTime = mediaPlayer.getCurrentTime();
            //setting the slider so that it shows the current time of the media
            slider.setValue(currentTime.divide(duration).toMillis()* 100.0);
            //setting label_2 so that it shows the current time and the duration of the media
            label_2.setText(formatTime(currentTime, duration));

        }

    }
    //method for formatting the time
    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60- elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",elapsedHours, elapsedMinutes, elapsedSeconds,durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",elapsedMinutes, elapsedSeconds, durationMinutes, durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,elapsedSeconds);
            }
        }
    }
}
