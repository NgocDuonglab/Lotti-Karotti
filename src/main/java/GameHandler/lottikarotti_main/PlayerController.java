package GameHandler.lottikarotti_main;


import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.security.cert.PolicyNode;
import java.util.*;


public class PlayerController implements Initializable {

    public ImageView cardView;
    public Button cardMix;
    public Circle start, path1, path2, path3, path4, path5, path6, path7, path8, path9, path10, path11, path12, path13, path14, path15, path16, path17, path18, path19,
            path20, path21, path22, path23, path24, path25, path26, path27;

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

        // Add more paths if needed

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
    }

    @FXML
    private void greenRabbitOnClicked(MouseEvent mouseEvent) {
        addRabbitToPath("/GameHandler/lottikarotti_main/images/green_bunny.png", start, greenRabbit_label);
    }

    @FXML
    private void yellowRabbitOnClicked(MouseEvent mouseEvent) {
        addRabbitToPath("/GameHandler/lottikarotti_main/images/yellow_bunny.png", start, yellowRabbit_label);
    }

    @FXML
    private void pinkRabbitOnClicked(MouseEvent mouseEvent) {
        addRabbitToPath("/GameHandler/lottikarotti_main/images/pink_bunny.png", start, pinkRabbit_label);
    }

    @FXML
    private void purpleRabbitOnClicked(MouseEvent mouseEvent) {
        addRabbitToPath("/GameHandler/lottikarotti_main/images/purple_bunny.png", start, purpleRabbit_label);
    }


    private void addRabbitToPath(String rabbitImagePath, Circle startingPath, Label rabbitLabel) {
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

            // Position the new rabbit at the starting path
            newRabbit.setLayoutX(startingPath.getLayoutX() - (newRabbit.getFitWidth() / 2));
            newRabbit.setLayoutY(startingPath.getLayoutY() - (newRabbit.getFitHeight() / 2));

            // Add the new rabbit to the AnchorPane
            anchorPane.getChildren().add(newRabbit);

            // Track the rabbit's position
            rabbitPositionMap.put(newRabbit, startingPath);

            // Add click listener for moving the rabbit
            newRabbit.setOnMouseClicked(event -> moveRabbit(newRabbit));
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


    private void moveRabbit(ImageView rabbit) {
        // Determine the current position of the rabbit
        Circle currentPath = rabbitPositionMap.get(rabbit);

        // Determine the next path based on card logic, skipping occupied paths
        Circle nextPath = getNextPath(currentPath, randomIndex);

        if (nextPath != null && randomIndex > 0) {
            // Animate the rabbit to the next path
            animateRabbitMovement(rabbit, currentPath, nextPath);

            // Check if the next path is black
            if (nextPath.getFill().equals(javafx.scene.paint.Color.BLACK)) {
                // Remove the rabbit if the path is black

                anchorPane.getChildren().remove(rabbit);
                rabbitPositionMap.remove(rabbit);
            } else {
                // Update the rabbit's position if the path is not black
                rabbitPositionMap.put(rabbit, nextPath);
            }
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
                    anchorPane.getChildren().remove(entry.getKey());
                    return true; // Remove this rabbit from the map
                }
                return false;
            });
        }
    }



}
