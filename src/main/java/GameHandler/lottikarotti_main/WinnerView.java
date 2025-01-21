/**
 * OOP Java Project  WiSe 2024/2025
 * @Author: Sujung Lee (Matriculation Nr. 1365537)
 *
 * @Version: 1.0 (20/12/2024)
 *
 * WinnerView.java - Displays the Winner Screen in Lotti Karotti
 *
 * The WinnerView class manages the logic and user interface for displaying
 * the winner of the Lotti Karotti game. It showcases the winning rabbit
 * with animations and provides options for the user to restart the game or exit.
 *
 * Key Responsibilities:
 * - Preloads and updates the rabbit image corresponding to the winning player.
 * - Plays congratulatory animations (e.g., scaling, rotation, fade) for the winner.
 * - Handles user actions for closing the application or restarting the game.
 *
 * Key Features:
 * - Animation Effects: Adds visual appeal with scaling, rotation, and fade animations
 *   for the winning rabbit image.
 * - Image Preloading: Preloads rabbit images for all player colors for smoother transitions.
 * - User Interaction: Provides buttons to exit the application or return to the main view.
 *
 * Constructor and Initialization:
 * - Preloads rabbit images during initialization.
 * - Plays congratulatory animations automatically when the winner is set.
 *
 * Methods:
 * - preloadImages: Loads images for all possible rabbit colors into a map.
 * - updateRabbitImage: Updates the ImageView with the rabbit image for the winner.
 * - setWinnerId: Sets the winner's ID, updates the rabbit image, and triggers animations.
 * - playCongratulationAnimation: Plays scaling, rotation, and fade animations to
 *   celebrate the winner.
 * - onWinnerButtonClick: Handles returning to the main view.
 * - onExitButtonClick: Handles exiting the application.
 *
 * Dependencies:
 * - JavaFX for UI components and animations (e.g., ImageView, Button, Timeline).
 * - SceneChanger for managing scene transitions.
 *
 * Notes:
 * - Designed for dynamic and visually engaging winner displays.
 * - Ensures smooth performance by preloading images.
 * - Supports up to 4 players (green, yellow, pink, purple).
 */

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

    /**
     * Initialrize the WinnerView
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        preloadImages();
        playCongratulationAnimation();

    }


    /**
     * Handles the action when the "Winner" button is clicked.
     */
    @FXML
    protected void onWinnerButtonClick() throws IOException {
        SceneChanger.changeScene("View/MainView.fxml");
    }

    /**
     * Handles the action when the "Exit" button is clicked.
     */
    @FXML
    public void onExitButtonClick(ActionEvent event) {
        System.exit(0); }

    /**
     * Preloads images of all rabbit colors into a map.
     * This ensures smooth transitions and efficient image access during runtime.
     */
    public void preloadImages() {
        rabbitImages.put("green", new Image(getClass().getResource("/GameHandler/lottikarotti_main/images/green_bunny.png").toExternalForm()));
        rabbitImages.put("yellow", new Image(getClass().getResource("/GameHandler/lottikarotti_main/images/yellow_bunny.png").toExternalForm()));
        rabbitImages.put("pink", new Image(getClass().getResource("/GameHandler/lottikarotti_main/images/pink_bunny.png").toExternalForm()));
        rabbitImages.put("purple", new Image(getClass().getResource("/GameHandler/lottikarotti_main/images/purple_bunny.png").toExternalForm()));
    }

    /**
     * Updates the ImageView to display the rabbit corresponding to the winner.
     */
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

    /**
     * Sets the winner's ID and updates the display.
     */
     public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
        updateRabbitImage();
        playCongratulationAnimation();
    }

    /**
     * Plays a congratulatory animation for the winning rabbit.
     */
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