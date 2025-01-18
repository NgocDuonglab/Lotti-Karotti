/**
 * OOP Java Project  WiSe 2024/2025
 * @Author: Sujung Lee (Matriculation No. 1365537)
 *
 * @Version: 1.0 (12/01/2025)
 *
 * Board.java- Manages the Game Board and Rabbit Movement
 *
 * The Board class encapsulates the core logic for managing the game board
 * in the Lotti Karotti game. It handles rabbit placement, movement,
 * path interactions, and special events such as holes and winning scenarios.
 *
 * Key Responsibilities:
 * - Tracks rabbit positions on the board using a mapping of ImageView to Circle.
 * - Manages interactions with paths, including random holes and rabbit removal.
 * - Animates rabbit movement, enlargements, and transitions during gameplay.
 * - Updates player turns and enforces game rules such as skipping occupied paths.
 * - Notifies the PlayerController for game status updates and win conditions.
 *
 * Key Features:
 * - Rabbit movement logic, including skipping occupied paths and handling carrot cards.
 * - Random hole creation and animation with rabbit removal if affected.
 * - Animation of rabbit movements and special effects (e.g., fade-out, scaling).
 * - Dynamic adjustment of rabbit counts and player turns based on game state.
 *
 * Dependencies:
 * - JavaFX for GUI elements (e.g., ImageView, Circle, Label, animations).
 * - PlayerController for managing game state updates.
 * - Player and Card classes for player-specific and card-specific logic.
 *
 * Constructor:
 * - Initializes the board with references to the game components such as paths,
 *   players, rabbits, and cards, as well as GUI elements for animation and display.
 *
 * Methods:
 * - addRabbitToPath: Places a rabbit on the starting path and tracks its position.
 * - moveRabbit: Handles rabbit movement along the paths based on drawn cards.
 * - getNextPath: Determines the next valid path for a rabbit, skipping occupied paths.
 * - makeRandomHoles: Creates random holes on the board and removes rabbits on those holes.
 * - fadeOutAndRemoveRabbit: Animates and removes a rabbit that falls into a hole.
 * - animateRabbitMovement: Animates rabbit movement between two points on the board.
 * - animateRabbitToCarrot: Animates a rabbit moving directly to the carrot.
 *
 * Notes:
 * - The class supports dynamic path management and concurrency-safe rabbit counts
 *   using ConcurrentHashMap.
 * - Path transitions and animations use JavaFX's Transition API.
 * - The Board class integrates with other components to ensure seamless gameplay.
 */


package GameHandler.lottikarotti_main.LottiKarroti.Board;

import GameHandler.lottikarotti_main.PlayerController;
import GameHandler.lottikarotti_main.LottiKarroti.Player.Player;
import GameHandler.lottikarotti_main.LottiKarroti.Card.Card;

import javafx.animation.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.*;

public class Board {

    private final AnchorPane anchorPane;
    private final Map<ImageView, Circle> rabbitPositionMap;
    private final Map<String, Integer> rabbitCounts;
    private final PlayerController controller;
    private final Player playerManager;
    private final Card card;
    private final Label currentPlayerLabel;
    private final List<Circle> paths;
    private final ImageView carrot;

    public Board(AnchorPane anchorPane, Map<ImageView, Circle> rabbitPositionMap, Map<String, Integer> rabbitCounts, PlayerController controller, Player playerManager, Card card, Label currentPlayerLabel, List<Circle> paths, ImageView carrot) {
        this.anchorPane = anchorPane;
        this.rabbitPositionMap = rabbitPositionMap;
        this.rabbitCounts = rabbitCounts;
        this.controller = controller;
        this.playerManager = playerManager;
        this.card = card;
        this.currentPlayerLabel = currentPlayerLabel;
        this.paths = paths;
        this.carrot = carrot;
    }


    /**
     * Adds a rabbit to the starting path and initializes.
     */
    public void addRabbitToPath(String rabbitImagePath, Circle startingPath, Label rabbitLabel, String rabbitColor) {
        // Parse the current rabbit count from the label
        int currentCount = Integer.parseInt(rabbitLabel.getText());

        // Check if the count is greater than zero
        if (currentCount > 0) {
            // Decrement the rabbit count
            rabbitLabel.setText(String.valueOf(currentCount - 1));

            // Create a new ImageView for the rabbit
            ImageView newRabbit = new ImageView(new Image(getClass().getResource(rabbitImagePath).toExternalForm()));
            newRabbit.setFitWidth(85);
            newRabbit.setFitHeight(93);
            newRabbit.setPreserveRatio(true);
            newRabbit.setId(rabbitColor);

            // Position the new rabbit at the starting path
            newRabbit.setLayoutX(startingPath.getLayoutX() - (newRabbit.getFitWidth() / 2));
            newRabbit.setLayoutY(startingPath.getLayoutY() - (newRabbit.getFitHeight() / 2));

            // Add the new rabbit to the AnchorPane
            anchorPane.getChildren().add(newRabbit);

            // Track the rabbit's position
            rabbitPositionMap.put(newRabbit, startingPath);
            //Adjust rabbitCounts
            rabbitCounts.compute(rabbitColor.trim().toLowerCase(), (key, count) -> (count == null ? 1 : count + 1));

            // Add click listener for moving the rabbit
            moveRabbit(newRabbit);
            moveExistingRabbit(newRabbit);
        } else {
            controller.showAlert(" No Rabbits Left", "No rabbits left to add for this player!", Alert.AlertType.INFORMATION);
        }
    }

    /**
     * existing rabbit for movement.
     */
    public void moveExistingRabbit(ImageView rabbit) {
        rabbit.setOnMouseClicked(event -> {

            if(playerManager.validateRabbitAction(rabbit)){
                moveRabbit(rabbit);
            }

        });

    }

   /**
    * Moves a rabbit along the board based on the card's steps.
    */
    public void moveRabbit(ImageView rabbit) {

        if (!playerManager.validateRabbitAction(rabbit)) {
            return; // Abort move if validation fails
        }

        Circle currentPath = rabbitPositionMap.get(rabbit);


        // Validate that the rabbit has a current path
        if (currentPath == null) {
            System.err.println("Rabbit has no current path!");
            return;
        }

        // Determine the next path based on card logic, skipping occupied paths
        Circle nextPath = getNextPath(currentPath, card.getRandomIndex());


        if (card.getRandomIndex() > 0) { // Ensure movement only happens if steps > 0
            if (nextPath == null) {
             //   animateRabbitToCarrot(rabbit);
                controller.triggerWin(rabbit);

            } else {
                // Normal movement
                animateRabbitMovement(rabbit, currentPath, nextPath);

                // Check if the next path is black
                if (nextPath.getFill().equals(javafx.scene.paint.Color.BLACK)) {
                    // Remove the rabbit if the path is black
                    fadeOutAndRemoveRabbit(rabbit);

                } else {
                    // Update the rabbit's position if the path is not black
                    rabbitPositionMap.put(rabbit, nextPath);

                }
            }
        } else {
            System.out.println("Random index is 0, no movement.");
        }

        // Update the turn after the move
        if (card.isCardDrawn()) {
            playerManager.updatePlayerTurn(currentPlayerLabel);
            card.setRandomIndex(4); //initial randomIndex
        }

    }

    /**
     * Gets the next valid path for a rabbit, skipping occupied paths
     */
    public Circle getNextPath(Circle currentPath, int steps) {
        // Find the index of the current path
        int currentIndex = paths.indexOf(currentPath);

        System.out.println(currentIndex);
        if ( steps > 0) { //if selected card is carrot, rabbit doesn't move

            // Traverse the path while skipping occupied paths
            int nextIndex = currentIndex;
            int remainingSteps = steps;

            while (remainingSteps > 0 && nextIndex < paths.size() - 1) {
                nextIndex++; // Move to the next path
                Circle potentialPath = paths.get(nextIndex);

                // Check if the path is occupied
                boolean isOccupied = rabbitPositionMap.values().contains(potentialPath);

                if (!isOccupied) {
                    // Only count this step if the path is not occupied
                    remainingSteps--;
                }
            }

            if (remainingSteps == 0 && nextIndex < paths.size()) {
                return paths.get(nextIndex);
            } else {
                return null; // No valid next path
            }
        }else{
            return null;
        }
    }



/**
 * Creates random holes on the board and removes any rabbits on those holes.
 */
    public void makeRandomHoles() {

        // Reset all paths to blue
        for (Circle path : paths) {
            path.setFill(Color.DODGERBLUE);
        }

        Random random = new Random();
        // Create a set to keep track of selected random indices
        Set<Integer> selectedIndices = new HashSet<>();

        while (selectedIndices.size() < 1) {
            int randomPathIndex = random.nextInt(paths.size());
            selectedIndices.add(randomPathIndex);
        }

        // Change the color of the selected paths to black and remove rabbits if present
        for (int index : selectedIndices) {
            Circle path = paths.get(index);

            // Create a color transition animation
            Timeline colorTransition = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(path.fillProperty(), Color.DODGERBLUE)),
                    new KeyFrame(Duration.millis(2000), new KeyValue(path.fillProperty(), Color.BLACK))
            );

            colorTransition.play(); // Play the animation

            // Check if any rabbit is on this path
            rabbitPositionMap.entrySet().removeIf(entry -> {
                if (entry.getValue() == path) {
                    // Remove the rabbit with a fade-out animation
                    fadeOutAndRemoveRabbit(entry.getKey());
                    return true; // Remove this rabbit from the map
                }
                return false;
            });
        }

        card.trueCardDrawn(); //true;
        playerManager.updatePlayerTurn(currentPlayerLabel);

    }


    /**
     * Animates and removes a rabbit from the board after falling into a hole.
     */
    private void fadeOutAndRemoveRabbit(ImageView rabbit) {
        // Create a ScaleTransition to enlarge the rabbit
        ParallelTransition parallelTransition = getParallelTransition(rabbit);
        parallelTransition.setOnFinished(event -> {
            // Remove the rabbit after the animation
            anchorPane.getChildren().remove(rabbit); // Remove the rabbit from the scene
            rabbitPositionMap.remove(rabbit); // Remove the rabbit from the map
            String rabbitColor = rabbit.getId(); // Get rabbit Color
            //Adjust rabbitCounts
            rabbitCounts.compute(rabbitColor.trim().toLowerCase(), (key, count) -> (count == null ? 1 : count - 1));
        });

        // Play the parallel transition
        parallelTransition.play();
    }


    /**
     * Creates a combined fade and scale animation for a rabbit
     */
    private static ParallelTransition getParallelTransition(ImageView rabbit) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(5), rabbit);
        scaleTransition.setFromX(1.0); // Original size
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.5);   // Enlarge by 1.5 times
        scaleTransition.setToY(1.5);


        // Create a FadeTransition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), rabbit);
        fadeTransition.setFromValue(1.0); // Fully visible
        fadeTransition.setToValue(0.0);   // Fully transparent


        // Combine the transitions into a ParallelTransition
        return new ParallelTransition(scaleTransition, fadeTransition );
    }


    /**
     * Animates a rabbit's movement between paths
     */
    private void animateRabbitMovement(ImageView rabbit, double toX, double toY, double fromX, double fromY,double durationInSeconds) {
        // Calculate translation offsets
        double deltaX = toX - fromX;
        double deltaY = toY - fromY;

        // Create a TranslateTransition
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), rabbit);
        transition.setByX(deltaX);
        transition.setByY(deltaY);

        // Play the animation
        transition.play();
    }

    /**
     * Updated animateRabbitMovement for moving between paths
     */
    public void animateRabbitMovement(ImageView rabbit, Circle fromPath, Circle toPath) {
        animateRabbitMovement(rabbit, toPath.getLayoutX(), toPath.getLayoutY(), fromPath.getLayoutX(), fromPath.getLayoutY(),1);
    }

    /**
     * Updated animateRabbitToCarrot for moving to the carrot
     */
    public void animateRabbitToCarrot(ImageView rabbit) {
        animateRabbitMovement(rabbit, carrot.getLayoutX(), carrot.getLayoutY(), rabbit.getLayoutX(), rabbit.getLayoutY(),1);
    }





}
