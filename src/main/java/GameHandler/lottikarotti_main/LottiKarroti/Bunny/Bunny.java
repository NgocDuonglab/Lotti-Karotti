package GameHandler.lottikarotti_main.LottiKarroti.Bunny;

import GameHandler.lottikarotti_main.LottiKarroti.Board.Board;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import GameHandler.lottikarotti_main.PlayerController;
import GameHandler.lottikarotti_main.LottiKarroti.Player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bunny {

    private final String color;
    private final ImageView imageView;
    private final Label label;
    private final String imagePath;// New property
    private static final List<Bunny> allBunnies = new ArrayList<>();
    private final Player playerManager;
    private final Board board;

    public Bunny(String color, ImageView imageView, Label label, String imagePath, Player playerManager, Board board) {
        this.color = color;
        this.imageView = imageView;
        this.label = label;
        this.imagePath = imagePath;
        this.playerManager = playerManager;
        this.board = board;
        allBunnies.add(this);
    }


    /**
     * Gets the label associated with the bunny's player
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Enables visibility for all bunnies and their corresponding labels
     */
    public static void enableAllRabbits() {
        for (Bunny bunny : allBunnies) {
            bunny.imageView.setVisible(true);
            bunny.label.setVisible(true);
        }
    }

    /**
     Handles the logic when a bunny is clicked.
     Determines if the rabbit can be added to the board or moved based on the game state.
    */

    public void rabbitOnClicked(MouseEvent mouseEvent, PlayerController controller, int currentPlayer, boolean cardDrawn, int randomIndex,
                                Map<ImageView, Circle> rabbitPositionMap, Circle start) {
        ImageView clickedRabbit = (ImageView) mouseEvent.getSource();

        // Check if it's the current player's turn
        if (!imageView.equals(clickedRabbit)) {
            System.err.println("Invalid rabbit clicked!");
            return;
        }

        String playerColor = playerManager.getPlayerColor(currentPlayer);



        if (!color.equalsIgnoreCase(playerColor)) {
            controller.showAlert("Invalid Turn", "It's not " + color + " player's turn!", Alert.AlertType.WARNING);
            return;
        }

        // Check if the player has drawn a card
        if (!cardDrawn) {
            controller.showAlert(playerColor + " Player's Turn", "Please draw a card first!", Alert.AlertType.WARNING);
            return;
        }

        // When the carrot card is selected, no rabbits can be clicked
        if (randomIndex == 0) {
            controller.showAlert(playerColor + " Player's Turn", playerColor + " player, it's your turn! Click on a carrot to proceed.\n"
                    + "If a field opens, it will create a hole in the ground.", Alert.AlertType.WARNING);
            return;
        }

        // Allow choosing between adding a new rabbit or moving an existing one
        if (!rabbitPositionMap.containsKey(clickedRabbit)) {
            board.addRabbitToPath(imagePath, start, label, color);
        } else {
            board.moveRabbit(clickedRabbit);
        }
    }

    }


