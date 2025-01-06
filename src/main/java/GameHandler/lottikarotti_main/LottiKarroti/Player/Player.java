package GameHandler.lottikarotti_main.LottiKarroti.Player;

import GameHandler.lottikarotti_main.LottiKarroti.Card.Card;
import GameHandler.lottikarotti_main.LottiKarroti.Board.Board;
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
    private final Board board;


    public Player(List<Label> rabbitLabels, Card card, Board board, PlayerController controller, int currentPlayer, int totalPlayers) {
        this.rabbitLabels = rabbitLabels;
        this.card = card;
        this.controller = controller;
        this.board = board;
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

            String playerColor = getPlayerColor(initialPlayer);
            int rabbitCount = controller.chkRabbitCounts(playerColor);
            boolean hasRabbitsToAdd = controller.checkPlayerHasRabbitsToAdd(getRabbitLabelForPlayer(initialPlayer));

            System.out.println("Player " + playerColor + " has " + rabbitCount + " rabbits.");
            System.out.println("Can add rabbits: " + hasRabbitsToAdd);

            if (rabbitCount > 0 || hasRabbitsToAdd) {
                hasValidPlayer = true;
                break;
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
