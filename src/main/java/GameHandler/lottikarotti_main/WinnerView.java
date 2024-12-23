package GameHandler.lottikarotti_main;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class WinnerView implements Initializable {



    @FXML
    public Button closeButton;

    @FXML
    private ImageView rabbitImageView;

    private String winnerId;
    private final Map<String, Image> rabbitImages = new HashMap<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        preloadImages();
        playCongratulationAnimation();

    }


    @FXML
    protected void onWinnerButtonClick() throws IOException {
        SceneChanger.changeScene("View/MainView.fxml");
    }

    @FXML
    public void onExitButtonClick(ActionEvent event) {
        System.exit(0); }


    public void preloadImages() {
        rabbitImages.put("green", new Image(getClass().getResource("/GameHandler/lottikarotti_main/images/green_bunny.png").toExternalForm()));
        rabbitImages.put("yellow", new Image(getClass().getResource("/GameHandler/lottikarotti_main/images/yellow_bunny.png").toExternalForm()));
        rabbitImages.put("pink", new Image(getClass().getResource("/GameHandler/lottikarotti_main/images/pink_bunny.png").toExternalForm()));
        rabbitImages.put("purple", new Image(getClass().getResource("/GameHandler/lottikarotti_main/images/purple_bunny.png").toExternalForm()));
    }

    private void updateRabbitImage() {
        if (rabbitImageView != null && winnerId != null) {
            Image rabbitImage = rabbitImages.get(winnerId);
            if (rabbitImage != null) {
                rabbitImageView.setImage(rabbitImage);
            } else {
                System.err.println("No image found for winnerId: " + winnerId);
            }
        }
    }

     public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
        updateRabbitImage();
        playCongratulationAnimation();
    }

    private void playCongratulationAnimation() {
        if (rabbitImageView == null) return;

        // Create a drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GOLD);
        dropShadow.setRadius(10);
        rabbitImageView.setEffect(dropShadow);

        // Create a Timeline for scale animation
        Timeline scaleAnimation = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(rabbitImageView.scaleXProperty(), 1),
                        new KeyValue(rabbitImageView.scaleYProperty(), 1)
                ),
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(rabbitImageView.scaleXProperty(), 1.2),
                        new KeyValue(rabbitImageView.scaleYProperty(), 1.2)
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(rabbitImageView.scaleXProperty(), 1),
                        new KeyValue(rabbitImageView.scaleYProperty(), 1)
                )
        );

        // Create a Timeline for rotation animation
        Timeline rotationAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rabbitImageView.rotateProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(rabbitImageView.rotateProperty(), 10)),
                new KeyFrame(Duration.seconds(1), new KeyValue(rabbitImageView.rotateProperty(), -10)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(rabbitImageView.rotateProperty(), 0))
        );

        // Create a Timeline for fade animation
        Timeline fadeAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rabbitImageView.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(rabbitImageView.opacityProperty(), 0.5)),
                new KeyFrame(Duration.seconds(1), new KeyValue(rabbitImageView.opacityProperty(), 1))
        );

        // Play all animations together
        scaleAnimation.setCycleCount(4); // Repeat 4 times
        rotationAnimation.setCycleCount(4);
        fadeAnimation.setCycleCount(4);

        scaleAnimation.play();
        rotationAnimation.play();
        fadeAnimation.play();
    }



}