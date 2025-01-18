/**
 * OOP Java Project  WiSe 2024/2025
 * @Author: Sujung Lee (Matriculation No. 1365537)
 *
 * @Version: 1.0 (12/01/2025)
 *
 * Bunny Class - Represents a Rabbit in the Lotti Karotti Game
 *
 * The Bunny class encapsulates the properties and behavior of a rabbit (bunny)
 * in the Lotti Karotti game. It interacts with the PlayerController and Board
 * classes to manage player actions, including adding new rabbits to the board
 * and moving them during gameplay.
 *
 * Key Responsibilities:
 * - Tracks the properties of individual bunnies (e.g., color, image, label).
 * - Handles click events for bunnies to enable interaction during the game.
 * - Validates player actions based on game rules (e.g., turn validation, card drawing).
 * - Manages visibility and initialization for all bunnies.
 *
 * Key Features:
 * - Supports adding new rabbits to the board if the player has any remaining.
 * - Handles moving existing rabbits along the paths based on drawn cards.
 * - Validates turn rules, ensuring players can only interact with their bunnies.
 * - Provides a centralized list of all bunnies for game-wide visibility management.
 *
 * Constructor:
 * - Initializes a Bunny object with its color, image, label, image path,
 *   and references to Player and Board managers for game interaction.
 *
 * Methods:
 * - getLabel: Returns the label associated with the bunny's player.
 * - enableAllRabbits: Enables visibility for all bunnies and their labels.
 * - rabbitOnClicked: Handles the logic for bunny clicks, determining whether
 *   to add a new rabbit to the board or move an existing one.
 *
 * Dependencies:
 * - JavaFX for GUI interactions (e.g., ImageView, Label, MouseEvent).
 * - PlayerController for managing game interactions and alerts.
 * - Board for handling path-based movements and game state updates.
 * - Player for validating player-specific rules such as turns.
 *
 * Notes:
 * - The Bunny class maintains a static list of all bunnies for centralized management.
 * - Integration with the Board and PlayerController ensures seamless gameplay.
 * - Designed to provide intuitive and responsive interactions for players.
 */


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


