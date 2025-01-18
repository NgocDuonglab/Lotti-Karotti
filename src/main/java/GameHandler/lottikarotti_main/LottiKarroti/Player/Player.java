/**
 * OOP Java Project  WiSe 2024/2025
 * @Author: Sujung Lee (Matriculation No. 1365537)
 *
 * @Version: 1.0 (12/01/2025)
 *
 * Player Class - Manages Player Logic in Lotti Karotti
 *
 * The Player class handles the game logic and interactions related to the players
 * in the Lotti Karotti game. It keeps track of player turns, validates actions,
 * and integrates with other components like the Board and Card classes to ensure
 * smooth gameplay.
 *
 * Key Responsibilities:
 * - Manages the turn-based gameplay by updating and validating player turns.
 * - Validates player actions, such as drawing a card or moving a rabbit.
 * - Tracks and retrieves player-specific data, including rabbit labels and colors.
 * - Ensures players adhere to game rules, including card-drawing requirements.
 *
 * Key Features:
 * - updatePlayerTurn: Manages turn rotation and ensures valid players can continue.
 * - validateRabbitAction: Validates whether a rabbit action is permissible.
 * - getPlayerColor: Retrieves the color associated with a player index.
 * - getRabbitPlayer: Maps a rabbit ImageView to its associated player.
 * - getCurrentPlayer: Provides the index of the current player.
 *
 * Constructor:
 * - Initializes the Player object with rabbit labels, card logic, and total player count.
 *
 * Methods:
 * - updatePlayerTurn: Handles the turn progression and ensures a valid player is chosen.
 * - validateRabbitAction: Checks if the current action (e.g., moving a rabbit) follows game rules.
 * - getPlayerColor: Maps player indices to their respective colors.
 * - getRabbitPlayer: Identifies the player associated with a specific rabbit ImageView.
 * - getRabbitLabelForPlayer: Retrieves the label for a given player's remaining rabbits.
 *
 * Dependencies:
 * - JavaFX for GUI elements (e.g., Label, ImageView).
 * - PlayerController for managing game state and alerts.
 * - Card for managing card-related logic during turns.
 *
 * Notes:
 * - Player turn logic supports up to 4 players with dynamic validation of rabbit counts.
 * - Designed to ensure seamless integration with other game components.
 * - Throws appropriate exceptions for invalid indices or unexpected states.
 */

package GameHandler.lottikarotti_main.LottiKarroti.Player;

import GameHandler.lottikarotti_main.LottiKarroti.Card.Card;
import GameHandler.lottikarotti_main.PlayerController;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Map;

public class Player {

    private final List<Label> rabbitLabels;
    private final Card card;
    private final int totalPlayers;
    private int currentPlayer;
    private final PlayerController controller;



    public Player(List<Label> rabbitLabels, Card card, PlayerController controller, int currentPlayer, int totalPlayers) {
        this.rabbitLabels = rabbitLabels;
        this.card = card;
        this.controller = controller;
        this.currentPlayer = currentPlayer;
        this.totalPlayers = totalPlayers;


    }

    /**
     * Updates the current player's turn and checks for valid players who can play.
     */
    public void updatePlayerTurn(Label currentPlayerLabel) {
        int initialPlayer = currentPlayer;
        boolean hasValidPlayer = false;

        do {

            currentPlayer = (currentPlayer + 1) % totalPlayers;
            card.resetCardDrawn();

            String playerColor = getPlayerColor(currentPlayer);
            int rabbitCount = controller.chkRabbitCounts(playerColor);
            boolean hasRabbitsToAdd  = false;
            hasRabbitsToAdd = controller.checkPlayerHasRabbitsToAdd(getRabbitLabelForPlayer(currentPlayer));

            System.out.println("Player " + playerColor + " has " + rabbitCount + " rabbits.");
            System.out.println("Can add rabbits: " + hasRabbitsToAdd);
            System.out.println(totalPlayers);

            if (rabbitCount > 0 || hasRabbitsToAdd) {
                hasValidPlayer = true;
                break;
            }

            if(totalPlayers == 2 && !hasRabbitsToAdd) {

                System.out.println("Player " + playerColor + " has no rabbits.!!!!!!!!");
                //controller.triggerWin();
                card.resetCardView();
                return;

            }

            if (currentPlayer == initialPlayer) {
                break;
            }
        } while (true);

        if (!hasValidPlayer) {
            System.out.println("No valid players.");
            return;
        }

        String playerColor = getPlayerColor(currentPlayer);
        currentPlayerLabel.setText("Current Player: " + playerColor);
        card.resetCardView();
    }

    /**
     * Determine the current player based on the clicked rabbit
     */
    public boolean validateRabbitAction(ImageView rabbit) {

        int rabbitPlayer = getRabbitPlayer(rabbit);
        if (rabbitPlayer == -1) {
            // Handle unknown rabbit case
            return false;
        }

        // Check if it's the current player's turn
        if (getCurrentPlayer() != rabbitPlayer) {
            controller.showAlert(getPlayerColor(rabbitPlayer) + " Invalid Turn",
                    "It's not " + getPlayerColor(rabbitPlayer) + " player's turn!",
                    Alert.AlertType.WARNING);
            return false;
        }

        // Check if the player has not drawn a card
        if (!card.isCardDrawn()) {
            controller.showAlert(getPlayerColor(rabbitPlayer) + " Player's Turn",
                    "Please draw a card first!",
                    Alert.AlertType.WARNING);
            return false;
        }

        // Check if the card is a carrot card
        if (card.getRandomIndex() == 0) {
            controller.showAlert(getPlayerColor(rabbitPlayer) + " Player's Turn",
                    getPlayerColor(rabbitPlayer) + " player, it's your turn! Click on a carrot to proceed.\n" +
                            "If a field opens, it will create a hole in the ground.",
                    Alert.AlertType.WARNING);
            return false;
        }

        return true; // All validations passed
    }


    /**
     * Retrieves the rabbit label associated with a specific player.
     */
    private Label getRabbitLabelForPlayer(int player) {
        if (player < 0 || player >= rabbitLabels.size()) {
            throw new IllegalArgumentException("Invalid player index: " + player);
        }
        return rabbitLabels.get(player);
    }

    /**
     * Returns the color associated with a specific player index.
     */
    public String getPlayerColor(int player) {
        switch (player) {
            case 0: return "green";
            case 1: return "yellow";
            case 2: return "pink";
            case 3: return "purple";
            default: return "unknown";
        }
    }

    /**
     * Returns the rabbit's Id associated with a specific rabbit's color.
     */
    public int getRabbitPlayer(ImageView rabbit) {
        Map<String, Integer> rabbitColorToPlayerMap = Map.of(
                "green", 0,
                "yellow", 1,
                "pink", 2,
                "purple", 3
        );

        return rabbitColorToPlayerMap.getOrDefault(rabbit.getId(), -1);
    }


    /**
     * Retrieves the index of the current player whose turn it is.
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

}
