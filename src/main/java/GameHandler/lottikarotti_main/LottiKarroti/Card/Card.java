package GameHandler.lottikarotti_main.LottiKarroti.Card;

import GameHandler.lottikarotti_main.PlayerController;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

public class Card {

    private final ImageView cardView;
    private final ImageView carrot;
    private final Image[] cards;
    private boolean cardDrawn;
    private int randomIndex;
    private final String cardBackgroundImagePath;
    private final PlayerController controller;

    public Card(ImageView cardView, ImageView carrot, Image[] cards, PlayerController controller) {
        this.cardView = cardView;
        this.carrot = carrot;
        this.cards = cards;
        this.controller = controller;
        this.cardDrawn = false;
        this.randomIndex = -1;
        this.cardBackgroundImagePath = "/GameHandler/lottikarotti_main/images/card_background.jpg";
    }

    /**
     * Handles the logic for clicking the card to draw a random card and apply animations.
     */
    public void cardMixOnClicked() {
        // If the card has already been drawn this turn, show an alert
        if (cardDrawn) {
            controller.showAlert("Card Already Drawn", "You can only draw a card once per turn!",Alert.AlertType.WARNING);
            return;
        }

        // Generate a random index
        Random random = new Random();
        randomIndex = random.nextInt(cards.length);

        System.out.println("Random card index: " + randomIndex);

        // Create animations
        TranslateTransition translate = new TranslateTransition(Duration.millis(300), cardView);
        RotateTransition rotate = new RotateTransition(Duration.millis(300), cardView);

        // Set the translate animation properties
        translate.setByX(20);
        translate.setByY(10);
        translate.setCycleCount(4);
        translate.setAutoReverse(true);

        // Set the rotate animation properties
        rotate.setByAngle(360);
        rotate.setCycleCount(1);

        // Play the animations sequentially
        translate.setOnFinished(e -> {
            rotate.setOnFinished(event -> {
                // Set the selected random card image after animation
                cardView.setImage(cards[randomIndex]);

                // If card index is 0, shake the carrot
                if (randomIndex == 0) {
                    shakeCarrot();
                }
            });
            rotate.play();
        });
        translate.play();

        cardDrawn = true;
    }

    /**
     * Plays a shaking animation on the carrot to indicate a special event.
     */
    private void shakeCarrot() {
        TranslateTransition carrotShake = new TranslateTransition(Duration.millis(200), carrot);
        carrotShake.setByX(10); // Adjust the shake intensity
        carrotShake.setCycleCount(6); // Total shakes (back and forth counts as one)
        carrotShake.setAutoReverse(true); // Return to the original position
        carrotShake.play(); // Play the shake animation
    }



    /**
     * Returns the current index of the selected random card.
    */
    public int getRandomIndex() {
        return randomIndex;
    }

    /**
     * Checks if a card has already been drawn during the current turn.
     */
    public boolean isCardDrawn() {
        return cardDrawn;
    }

    /**
     * reset card status
     */
    public void resetCardDrawn() {
        this.cardDrawn = false;
    }

    /**
     * Marks the card as drawn by setting the cardDrawn flag to true.
     */
    public void trueCardDrawn() {
        this.cardDrawn = true;
    }

    /**
     * Sets the random index for the card to the specified value.
     */
    public void setRandomIndex(int randomIndex) {
        this.randomIndex = randomIndex;
    }

    /**
     * Resets the card view to display the card background with a fade-out and fade-in effect.
     */
    public void resetCardView() {
        try {
            // Create a FadeTransition for the cardView
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), cardView);

            // Set the fade-out properties
            fadeTransition.setFromValue(1.0); // Fully visible
            fadeTransition.setToValue(0.0);   // Fully transparent

            // On fade-out completion, change the image to the background
            fadeTransition.setOnFinished(event -> {
                cardView.setImage(new Image(getClass().getResource(cardBackgroundImagePath).toExternalForm()));

                // Create a fade-in transition
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), cardView);
                fadeIn.setFromValue(0.0); // Fully transparent
                fadeIn.setToValue(1.0);   // Fully visible
                fadeIn.play(); // Start fade-in
            });

            // Start fade-out
            fadeTransition.play();
        } catch (Exception e) {
            System.err.println("Error resetting card view: " + e.getMessage());
        }
    }
}
