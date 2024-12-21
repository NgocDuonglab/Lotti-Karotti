package GameHandler.lottikarotti_main;


import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.security.cert.PolicyNode;
import java.util.*;


public class PlayerController implements Initializable {

    public ImageView cardView;
    public Button cardMix;
    public Circle start, path1, path2, path3, path4, path5, path6, path7, path8, path9, path10, path11, path12, path13, path14, path15, path16, path17, path18, path19,
            path20, path21, path22, path23, path24, path25, path26, path27;
    public Button buttonQuit;
    public ImageView carrot;
    private boolean cardDrawn = false;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView pinkRabbit;
    @FXML
    private ImageView purpleRabbit;
    @FXML
    private ImageView greenRabbit;
    @FXML
    private ImageView yellowRabbit;

    @FXML
    private Label greenRabbit_label;
    @FXML
    private Label purpleRabbit_label;
    @FXML
    private Label yellowRabbit_label;
    @FXML
    private Label pinkRabbit_label;

    private int currentPlayer = 0; // Tracks the current player (0: Green, 1: Yellow, 2: Pink, 3: Purple)
    private final int totalPlayers = GameController.player ; // Total number of players

    @FXML
    private Label currentPlayerLabel; // Label to display the current player

    // Map to track rabbit positions
    private final Map<ImageView, Circle> rabbitPositionMap = new HashMap<>();
    private final List<Circle> paths = new ArrayList<>();

    private int randomIndex;


    private final Image[] cards = {
            new Image(getClass().getResource("/GameHandler/lottikarotti_main/images/card_carrot.jpg").toExternalForm()),
            new Image(getClass().getResource("/GameHandler/lottikarotti_main/images/card_1.jpg").toExternalForm()),
            new Image(getClass().getResource("/GameHandler/lottikarotti_main/images/card_2.jpg").toExternalForm()),
            new Image(getClass().getResource("/GameHandler/lottikarotti_main/images/card_3.jpg").toExternalForm())

    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // System.out.println("pinkRabbit: " + pinkRabbit);
        //System.out.println("FXML Injection Successful: " + (pinkRabbit != null));
        // Enable all rabbits initially
        enableAllRabbits();
        handlePlayerSelection(GameController.player);

        // Add paths to the list in sequence
        //paths.add(start);
        paths.add(path1);
        paths.add(path2);
        paths.add(path3);
        paths.add(path4);
        paths.add(path5);
        paths.add(path6);
        paths.add(path7);
        paths.add(path8);
        paths.add(path9);
        paths.add(path10);
        paths.add(path11);
        paths.add(path12);
        paths.add(path13);
        paths.add(path14);
        paths.add(path15);
        paths.add(path16);
        paths.add(path17);
        paths.add(path18);
        paths.add(path19);
        paths.add(path20);
        paths.add(path21);
        paths.add(path22);
        paths.add(path23);
        paths.add(path24);
        paths.add(path25);
        paths.add(path26);
        paths.add(path27);


        // Set the initial player's turn
        currentPlayerLabel.setText("Current Player: Green");
        System.out.println("Game started. Player Turn: Green");

        initializeExistingRabbits();

    }

    private void enableAllRabbits() {
        pinkRabbit.setVisible(true);
        pinkRabbit_label.setVisible(true);
        purpleRabbit.setVisible(true);
        purpleRabbit_label.setVisible(true);
        greenRabbit.setVisible(true);
        greenRabbit_label.setVisible(true);
        yellowRabbit.setVisible(true);
        yellowRabbit_label.setVisible(true);
    }


    private void handlePlayerSelection(int selection) {

        // Disable rabbits based on the selection
        switch (selection) {
            case 2:
                pinkRabbit.setVisible(false);
                pinkRabbit_label.setVisible(false);
                purpleRabbit.setVisible(false);
                purpleRabbit_label.setVisible(false);
                break;
            case 3:
                purpleRabbit.setVisible(false);
                purpleRabbit_label.setVisible(false);
                break;
            case 4:
                // No rabbits are disabled for 4 players
                break;
        }
    }

    public void cardMixOnClicked(MouseEvent mouseEvent) {

       /* // Generate a random index
        Random random = new Random();
        int randomIndex = random.nextInt(cards.length);

        // Set the selected random card in the ImageView
        cardView.setImage(cards[randomIndex]);*/
        // Generate a random index

        if (cardDrawn) {
            // If the card has already been drawn this turn, show an alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Card Already Drawn");
            alert.setHeaderText(null);
            alert.setContentText("You can only draw a card once per turn!");
            alert.showAndWait();
            return;
        }
        Random random = new Random();
        randomIndex = random.nextInt(cards.length);

        System.out.println(randomIndex);

        // Create an animation
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

                // If card index is 0, change the color of three random holes
                if (randomIndex == 0) {
                    makeRandomHoles();

                }
            });
            rotate.play();
        });
        translate.play();

        cardDrawn = true;
    }


    /*@FXML
    private void rabbitOnClicked(MouseEvent mouseEvent) {
        // Determine which rabbit was clicked
        ImageView clickedRabbit = (ImageView) mouseEvent.getSource();

        // Map the rabbit to the corresponding player and image path
        int rabbitPlayer;
        String rabbitImagePath;
        String rabbitcolor;
        Label rabbitLabel;

        if (clickedRabbit == greenRabbit) {
            rabbitPlayer = 0;
            rabbitImagePath = "/GameHandler/lottikarotti_main/images/green_bunny.png";
            rabbitLabel = greenRabbit_label;
            rabbitcolor = "green";
        } else if (clickedRabbit == yellowRabbit) {
            rabbitPlayer = 1;
            rabbitImagePath = "/GameHandler/lottikarotti_main/images/yellow_bunny.png";
            rabbitLabel = yellowRabbit_label;
            rabbitcolor = "yellow";
        } else if (clickedRabbit == pinkRabbit) {
            rabbitPlayer = 2;
            rabbitImagePath = "/GameHandler/lottikarotti_main/images/pink_bunny.png";
            rabbitLabel = pinkRabbit_label;
            rabbitcolor = "pink";
        } else if (clickedRabbit == purpleRabbit) {
            rabbitPlayer = 3;
            rabbitImagePath = "/GameHandler/lottikarotti_main/images/purple_bunny.png";
            rabbitLabel = purpleRabbit_label;
            rabbitcolor = "purple";
        } else {
            System.err.println("Unknown rabbit clicked!");
            return;
        }

        // Check if it's the current player's turn
        if (currentPlayer != rabbitPlayer) {
            showTurnErrorAlert(getPlayerColor(rabbitPlayer)); // Display alert
            return;
        }
        if (!cardDrawn) {

            showCardDrawnAlert(getPlayerColor(rabbitPlayer));

        }else {

            // Add the rabbit to the path
            addRabbitToPath(rabbitImagePath, start, rabbitLabel, rabbitcolor);
        }
    }*/
    @FXML
    private void rabbitOnClicked(MouseEvent mouseEvent) {
        ImageView clickedRabbit = (ImageView) mouseEvent.getSource();

        // Map rabbit to player and attributes
        int rabbitPlayer;
        String rabbitImagePath;
        String rabbitColor;
        Label rabbitLabel;

        if (clickedRabbit == greenRabbit) {
            rabbitPlayer = 0;
            rabbitImagePath = "/GameHandler/lottikarotti_main/images/green_bunny.png";
            rabbitLabel = greenRabbit_label;
            rabbitColor = "green";
        } else if (clickedRabbit == yellowRabbit) {
            rabbitPlayer = 1;
            rabbitImagePath = "/GameHandler/lottikarotti_main/images/yellow_bunny.png";
            rabbitLabel = yellowRabbit_label;
            rabbitColor = "yellow";
        } else if (clickedRabbit == pinkRabbit) {
            rabbitPlayer = 2;
            rabbitImagePath = "/GameHandler/lottikarotti_main/images/pink_bunny.png";
            rabbitLabel = pinkRabbit_label;
            rabbitColor = "pink";
        } else if (clickedRabbit == purpleRabbit) {
            rabbitPlayer = 3;
            rabbitImagePath = "/GameHandler/lottikarotti_main/images/purple_bunny.png";
            rabbitLabel = purpleRabbit_label;
            rabbitColor = "purple";
        } else {
            System.err.println("Unknown rabbit clicked!");
            return;
        }

        // Check if it's the current player's turn
        if (currentPlayer != rabbitPlayer) {
            showTurnErrorAlert(getPlayerColor(rabbitPlayer));
            return;
        }

        // Check if the player has drawn a card
        if (!cardDrawn) {
            showCardDrawnAlert(getPlayerColor(rabbitPlayer));
            return;
        }

        // Allow choosing between adding a new rabbit or moving an existing one
        if (!rabbitPositionMap.containsKey(clickedRabbit)) {
            // Add and move a new rabbit
            addRabbitToPath(rabbitImagePath, start, rabbitLabel, rabbitColor);
        } else {
            // Move an existing rabbit
            moveRabbit(clickedRabbit);
        }
    }


    private String getPlayerColor(int player) {
        switch (player) {
            case 0: return "Green";
            case 1: return "Yellow";
            case 2: return "Pink";
            case 3: return "Purple";
            default: return "Unknown";
        }
    }

    private void showTurnErrorAlert(String playerColor) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Turn");
        alert.setHeaderText(null);
        alert.setContentText("It's not " + playerColor + " player's turn!");

        alert.showAndWait(); // Wait for the user to dismiss the alert
    }

    private void showCardDrawnAlert(String playerColor) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(playerColor + " Player's Turn");
        alert.setHeaderText(null);
        alert.setContentText("Please draw a card first!");

        alert.showAndWait(); // Wait for the user to dismiss the alert
    }



    private void addRabbitToPath(String rabbitImagePath, Circle startingPath, Label rabbitLabel, String rabbitColor) {
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

            // Add click listener for moving the rabbit
            //newRabbit.setOnMouseClicked(event -> moveRabbit(newRabbit));
            moveRabbit(newRabbit);

            moveExistingRabbit(newRabbit);
        } else {
            // Create an alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Rabbits Left");
            alert.setHeaderText(null);
            alert.setContentText("No rabbits left to add for this player!");

            // Display the alert
            alert.showAndWait();
        }
    }

    private void moveExistingRabbit(ImageView rabbit) {
        rabbit.setOnMouseClicked(event -> {
            int rabbitPlayer;
            // Determine the current player based on the rabbit's color
            if (rabbit.getId().equals("green")) {
                rabbitPlayer = 0;
            } else if (rabbit.getId().equals("yellow") ) {
                rabbitPlayer = 1;
            } else if (rabbit.getId().equals("pink")) {
                rabbitPlayer = 2;
            } else if (rabbit.getId().equals("purple")) {
                rabbitPlayer = 3;
            } else {
                System.err.println("Unknown rabbit!");
                System.out.println(rabbit.getId());
                return;
            }


            // Check if it's the current player's turn
            if (currentPlayer != rabbitPlayer) {
                showTurnErrorAlert(getPlayerColor(rabbitPlayer));
                return;
            }

            // Check if the player has drawn a card
            if (!cardDrawn) {
                showCardDrawnAlert(getPlayerColor(rabbitPlayer));
                return;
            }

            // Move the rabbit
            moveRabbit(rabbit);
        });
    }

    private void initializeExistingRabbits() {
        for (ImageView rabbit : rabbitPositionMap.keySet()) {
            moveExistingRabbit(rabbit);
        }
    }


    private void moveRabbit(ImageView rabbit) {

        // Determine the current player based on the clicked rabbit
        int rabbitPlayer;
        if (rabbit.getId().equals("green")) {
            rabbitPlayer = 0;
        } else if (rabbit.getId().equals("yellow") ) {
            rabbitPlayer = 1;
        } else if (rabbit.getId().equals("pink")) {
            rabbitPlayer = 2;
        } else if (rabbit.getId().equals("purple")) {
            rabbitPlayer = 3;
        } else {
            System.err.println("Unknown rabbit!");
            System.out.println(rabbit.getId());
            return;
        }

        // Check if it's the current player's turn
        if (currentPlayer != rabbitPlayer) {
            showTurnErrorAlert(getPlayerColor(rabbitPlayer)); // Show a pop-up alert
            return;
        }
        // Determine the current position of the rabbit
        Circle currentPath = rabbitPositionMap.get(rabbit);

        // Validate that the rabbit has a current path
        if (currentPath == null) {
            System.err.println("Rabbit has no current path!");
            return;
        }

        // Determine the next path based on card logic, skipping occupied paths
        Circle nextPath = getNextPath(currentPath, randomIndex);

        // Debugging information
        System.out.println("Next Path: " + nextPath);
        System.out.println("Random Index (Steps): " + randomIndex);

        if (randomIndex > 0) { // Ensure movement only happens if steps > 0
            if (nextPath == null) {
                animateRabbitToCarrot(rabbit);
                triggerWin(rabbit);
                //  System.out.println("No valid next path found!");
               // return;
            }else {
                // Normal movement
                animateRabbitMovement(rabbit, currentPath, nextPath);

                // Check if the next path is black
                if (nextPath.getFill().equals(javafx.scene.paint.Color.BLACK)) {
                    // Remove the rabbit if the path is black
                    System.out.println("Path is black! Removing rabbit...");
                    fadeOutAndRemoveRabbit(rabbit);
                   // anchorPane.getChildren().remove(rabbit);
                    //rabbitPositionMap.remove(rabbit);
                } else {
                    // Update the rabbit's position if the path is not black
                    System.out.println("Updating rabbit position...");
                    rabbitPositionMap.put(rabbit, nextPath);
                }
            }
        } else {


            System.out.println("Random index is 0, no movement.");
        }

        // Update the turn after the move

        if (cardDrawn == true) {
            updatePlayerTurn();
            randomIndex = 0; //initial randomIndex
        }



    }

/*
    private void updatePlayerTurn() {
        // Move to the next player
        currentPlayer = (currentPlayer + 1) % totalPlayers;
        // Reset the cardDrawn flag for the new turn

        cardDrawn = false;

        // Update the UI to reflect the current player
        String playerColor;
        switch (currentPlayer) {
            case 0: playerColor = "Green"; break;
            case 1: playerColor = "Yellow"; break;
            case 2: playerColor = "Pink"; break;
            case 3: playerColor = "Purple"; break;
            default: playerColor = "Unknown"; break;
        }
        // Enable only the current player's rabbits
        greenRabbit.setDisable(currentPlayer != 0);
        yellowRabbit.setDisable(currentPlayer != 1);
        pinkRabbit.setDisable(currentPlayer != 2);
        purpleRabbit.setDisable(currentPlayer != 3);

        // Update the currentPlayerLabel
      //  String playerColor = getPlayerColor(currentPlayer);
        currentPlayerLabel.setText("Current Player: " + playerColor);
    }
*/

    private void updatePlayerTurn() {
        do {
            // Move to the next player
            currentPlayer = (currentPlayer + 1) % totalPlayers;

            // Reset the cardDrawn flag for the new turn
            cardDrawn = false;

            // Check if the current player has any valid rabbits to move
            boolean hasRabbits = false;

            switch (currentPlayer) {
                case 0: // Green Player
                    hasRabbits = checkPlayerHasRabbits(greenRabbit_label, "green");
                    break;
                case 1: // Yellow Player
                    hasRabbits = checkPlayerHasRabbits(yellowRabbit_label, "yellow");
                    break;
                case 2: // Pink Player
                    hasRabbits = checkPlayerHasRabbits(pinkRabbit_label, "pink");
                    break;
                case 3: // Purple Player
                    hasRabbits = checkPlayerHasRabbits(purpleRabbit_label, "purple");
                    break;
            }

            // If the player has no rabbits, skip their turn
            if (!hasRabbits) {
                System.out.println("Skipping " + getPlayerColor(currentPlayer) + " player's turn: No rabbits available.");
            } else {
                break; // Stop skipping if the player has rabbits
            }
        } while (true);


        // Update the UI to reflect the current player
        String playerColor = getPlayerColor(currentPlayer);
        currentPlayerLabel.setText("Current Player: " + playerColor);
        System.out.println("Player Turn: " + playerColor);

        // Enable only the current player's rabbits
        greenRabbit.setDisable(currentPlayer != 0);
        yellowRabbit.setDisable(currentPlayer != 1);
        pinkRabbit.setDisable(currentPlayer != 2);
        purpleRabbit.setDisable(currentPlayer != 3);

        resetCardView();
    }



        private void resetCardView() {
            try {
                // Create a FadeTransition for the cardView
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), cardView);

                // Set the fade-out properties
                fadeTransition.setFromValue(1.0); // Fully visible
                fadeTransition.setToValue(0.0);   // Fully transparent

                // On fade-out completion, change the image to "card_background.jpg"
                fadeTransition.setOnFinished(event -> {
                    cardView.setImage(new Image(getClass().getResource("/GameHandler/lottikarotti_main/images/card_background.jpg").toExternalForm()));

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





    private boolean checkPlayerHasRabbits(Label rabbitLabel, String rabbitColor) {
        // Check if any rabbit of the player's color is on the path
        for (ImageView rabbit : rabbitPositionMap.keySet()) {
            if (rabbit.getId().equals(rabbitColor)) {
                return true; // A rabbit is on the path
            }
        }

        // Check if the player has any rabbits left in reserve (label count > 0)
        int remainingRabbits = Integer.parseInt(rabbitLabel.getText());
        return remainingRabbits > 0;
    }



    private void animateRabbitToCarrot(ImageView rabbit) {
        // Calculate translation offsets to the carrot
        double deltaX = carrot.getLayoutX() - rabbit.getLayoutX();
        double deltaY = carrot.getLayoutY() - rabbit.getLayoutY();

        // Create a TranslateTransition
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), rabbit);
        transition.setByX(deltaX);
        transition.setByY(deltaY);

        // Play the animation
       // transition.setOnFinished(event -> triggerWin(rabbit));
        transition.play();

    }

    private void triggerWin(ImageView winningRabbit) {
        // Show congratulatory message

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations!");
        alert.setHeaderText(null);
        alert.setContentText("Congratulations! The "+winningRabbit.getId()+" Rabiit is the winner!");

        alert.showAndWait();

        // Switch to the winner view
             try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameHandler/lottikarotti_main/View/WinnerView.fxml"));
            Parent root = loader.load();

            // Get the controller for the WinnerView
          //  WinnerView winnerController = loader.getController();

            // Pass the winner ID to the controller
          //  winnerController.setWinnerId(winningRabbit.getId());

            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            SceneChanger.setPrimaryStage(stage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private Circle getNextPath(Circle currentPath, int steps) {
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


    private void animateRabbitMovement(ImageView rabbit, Circle fromPath, Circle toPath) {
        // Calculate translation offsets
        double deltaX = toPath.getLayoutX() - fromPath.getLayoutX();
        double deltaY = toPath.getLayoutY() - fromPath.getLayoutY();

        // Create a TranslateTransition
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), rabbit);
        transition.setByX(deltaX);
        transition.setByY(deltaY);

        // Play the animation
        transition.play();
    }



   private void makeRandomHoles() {

        // Reset all paths to blue
        for (Circle path : paths) {
            path.setFill(javafx.scene.paint.Color.DODGERBLUE);
        }

        Random random = new Random();
        // Create a set to keep track of selected random indices
        Set<Integer> selectedIndices = new HashSet<>();

        while (selectedIndices.size() < 3) {
            int randomPathIndex = random.nextInt(paths.size());
            selectedIndices.add(randomPathIndex);
        }

        // Change the color of the selected paths to black and remove rabbits if present
        for (int index : selectedIndices) {
            Circle path = paths.get(index);
            path.setFill(javafx.scene.paint.Color.BLACK);

            // Check if any rabbit is on this path
            rabbitPositionMap.entrySet().removeIf(entry -> {
                if (entry.getValue() == path) {

                    // Remove the rabbit from the AnchorPane
                    fadeOutAndRemoveRabbit(entry.getKey());
                  //  anchorPane.getChildren().remove(entry.getKey());
                    return true; // Remove this rabbit from the map
                }
                return false;
            });
        }

        updatePlayerTurn();

    }


    public void buttonQuitOnClicked(MouseEvent mouseEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit the game?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            Platform.exit();
        }

    }

    private void fadeOutAndRemoveRabbit(ImageView rabbit) {
        // Create a ScaleTransition to enlarge the rabbit
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
        ParallelTransition parallelTransition = new ParallelTransition(scaleTransition, fadeTransition );
        parallelTransition.setOnFinished(event -> {
            // Remove the rabbit after the animation
            anchorPane.getChildren().remove(rabbit); // Remove the rabbit from the scene
            rabbitPositionMap.remove(rabbit);        // Remove the rabbit from the map
        });

        // Play the parallel transition
        parallelTransition.play();
    }


}
