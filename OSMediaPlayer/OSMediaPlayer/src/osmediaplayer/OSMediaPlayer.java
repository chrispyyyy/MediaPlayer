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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Cristina
 */
public class OSMediaPlayer extends Application {

    private Media media;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private Slider slider;
    private Duration duration;
    private Label label_2;
    private Label label_4;
    private MediaPlayer.Status status;
    private Label label_3;
    private Slider volume;

    @Override
    public void start(Stage primaryStage) {
        VBox vBox = new VBox();
        HBox hBox3 = new HBox();

        hBox3.setTranslateX(10);
        hBox3.setPrefWidth(100);
        media = new Media(new File("C:\\Users\\Cristina\\Videos\\Otavia - Abstract video art.mp4").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaView = new MediaView(mediaPlayer);
        mediaView.setFitWidth(1000);
        mediaView.setFitHeight(630);
        mediaView.setPreserveRatio(false);

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                duration = mediaPlayer.getMedia().getDuration();
                updateProgress();
            }
        });
        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateProgress();
            }
        });
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);

        Label label_1 = new Label("Time");
        label_2 = new Label();

        slider = new Slider();
        slider.setPrefHeight(20);
        slider.setPrefWidth(900);
        slider.setMaxWidth(Double.MAX_VALUE);

        slider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (slider.isValueChanging()) {
                    mediaPlayer.seek(duration.multiply(slider.getValue() / 100.0));
                }
            }
        });
        hBox.getChildren().addAll(label_1, slider, label_2);

        HBox hBox2 = new HBox();
        hBox2.setTranslateX(60);

        ImageView icon1 = new ImageView(new Image("skip_back.png"));
        hBox2.setMargin(icon1, new Insets(5));
        icon1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(mediaPlayer.getCurrentTime().divide(2));
            }
        });

        ImageView icon2 = new ImageView(new Image("play.png"));
        hBox2.setMargin(icon2, new Insets(5));
        icon2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                status = mediaPlayer.getStatus();
                if (status == MediaPlayer.Status.UNKNOWN) {
                    return;
                }
                if (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.STOPPED) {
                    mediaPlayer.setRate(1);
                    mediaPlayer.play();
                }
            }
        });

        ImageView icon3 = new ImageView(new Image("pause.png"));
        hBox2.setMargin(icon3, new Insets(5));
        icon3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.pause();
            }
        });

        ImageView icon4 = new ImageView(new Image("stop.png"));
        hBox2.setMargin(icon4, new Insets(5));
        icon4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.stop();
            }
        });

        ImageView icon5 = new ImageView(new Image("skip_forward.png"));
        hBox2.setMargin(icon5, new Insets(5));
        icon5.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(mediaPlayer.getCurrentTime().multiply(2));
            }
        });

        ImageView icon6 = new ImageView(new Image("slow.png"));
        hBox2.setMargin(icon6, new Insets(5));
        icon6.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.setRate(.75);
            }
        });
        ImageView icon7 = new ImageView(new Image("fast.png"));
        hBox2.setMargin(icon7, new Insets(5));
        icon6.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.setRate(1.5);
            }
        });

        ImageView icon8 = new ImageView(new Image("replay.png"));
        hBox2.setMargin(icon8, new Insets(5));
        icon8.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        ImageView icon10 = new ImageView(new Image("exit.png"));
        icon10.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });
        
        
        ImageView icon9 = new ImageView(new Image("fullscreen.png"));
        hBox2.setMargin(icon9, new Insets(5));
        icon9.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (primaryStage.isFullScreen()) {
                    primaryStage.setFullScreen(false);
                    mediaView.setFitHeight(630);
                    mediaView.setFitWidth(1000);
                    icon9.setImage(new Image("fullscreen.png"));
                    

                } else {
                    primaryStage.setFullScreen(true);
                    mediaView.setFitHeight(500);
                    mediaView.setFitWidth(1510);
                    icon9.setImage(new Image("minimise.png"));
                    
                }
            }
        });
       
        Slider volume = new Slider();

        volume.setTranslateX(300);
        volume.setTranslateY(15);
        volume.setPrefWidth(100);
        volume.setValue(30);
        mediaPlayer.setVolume(30);

        Label label_3 = new Label("Volume:");
        label_3.setTranslateX(290);
        label_3.setTranslateY(15);
        label_4 = new Label();
        label_4.setTranslateX(310);
        label_4.setTranslateY(15);
        label_4.setText("30");

        volume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (volume.isValueChanging()) {
                    mediaPlayer.setVolume(volume.getValue() / 100.0);
                    label_4.setText((int) volume.getValue() + "");
                }
            }
        });
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
        mediaView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();

                if (db.hasFiles()) {
                    String path = null;
                    for (File file : db.getFiles()) {
                        path = file.getAbsolutePath();

                        if (path.endsWith(".mp4") || path.endsWith(".avi") || path.endsWith(".m4p") || path.endsWith(".wmw")) {

                            //we should to close the current video before play the new video
                            mediaPlayer.stop();

                            Media media = new Media(new File(path).toURI().toString());
                            mediaPlayer = new MediaPlayer(media);
                            mediaPlayer.setAutoPlay(true);
                            mediaView.setMediaPlayer(mediaPlayer);

                            mediaPlayer.setOnReady(new Runnable() {
                                @Override
                                public void run() {
                                    duration = mediaPlayer.getMedia().getDuration();
                                    updateProgress();
                                }
                            });

                            mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
                                @Override
                                public void invalidated(Observable observable) {
                                    updateProgress();
                                }
                            });

                            slider.valueProperty().addListener(new InvalidationListener() {
                                @Override
                                public void invalidated(Observable observable) {
                                    if (slider.isValueChanging()) {
                                        mediaPlayer.seek(duration.multiply(slider.getValue() / 100.0));
                                    }
                                }
                            });

                        }
                    }
                }
            }
        });
        hBox2.getChildren().addAll(icon1, icon2, icon3, icon4, icon5, icon6, icon7, icon8, icon9, label_3, volume, label_4);
        hBox3.getChildren().addAll(icon10);
        vBox.getChildren().addAll(hBox3, mediaView, hBox, hBox2);
        vBox.setStyle("-fx-background-color:#ccc");

        Scene scene = new Scene(vBox, 1000, 740);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    status = mediaPlayer.getStatus();
                    if (status == MediaPlayer.Status.PAUSED) {
                        mediaPlayer.play();
                    } else if (status == MediaPlayer.Status.PLAYING) {
                        mediaPlayer.pause();
                    }
                } else if (event.getCode() == KeyCode.UP) {
                    volume.setValue(volume.getValue() + 5);
                    mediaPlayer.setVolume(volume.getValue() + 5);
                    label_4.setText((int) volume.getValue() + 5 + "");
                    updateProgress();
                } else if (event.getCode() == KeyCode.ESCAPE){
                    primaryStage.setFullScreen(false);
                    mediaView.setFitHeight(630);
                    mediaView.setFitWidth(1000);
                    icon9.setImage(new Image("fullscreen.png"));
                }
            }
        });

        primaryStage.setTitle("Hello World!");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    if (primaryStage.isFullScreen()) {
                        primaryStage.setFullScreen(false);
                        mediaView.setFitHeight(630);
                        mediaView.setFitWidth(1000);
                        icon9.setImage(new Image("fullscreen.png"));

                    } else {
                        primaryStage.setFullScreen(true);
                        mediaView.setFitHeight(930);
                        mediaView.setFitWidth(1510);
                        icon9.setImage(new Image("minimise.png"));;
                    }
                }
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void updateProgress() {
        if (slider != null) {
            Duration currentTime = mediaPlayer.getCurrentTime();
            slider.setValue(currentTime.divide(duration).toMillis() * 100.0);

            label_2.setText(formatTime(currentTime, duration));

        }

    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

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
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }
}
