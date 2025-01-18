/**
 * OOP Java Project  WiSe 2024/2025
 * @Author: Sujung Lee (Matriculation No. 1365537)
 *
 * @Version: 3.0 (18/01/2025)
 *
 * PlayerController Class - Handles Player Interactions and Game Logic
 *
 * This class serves as the controller for the Lotti Karotti game. It manages
 * player interactions, game state transitions, and various game functionalities
 * such as drawing cards, moving rabbits, and displaying game rules.
 *
 * Key Features:
 * - Manages rabbit movements and positions on the board.
 * - Handles card interactions and random events (e.g., carrot card).
 * - Tracks player turn and updates the current player.
 * - Displays game rules and winner view.
 * - Provides user alerts for important game events and errors.
 *
 * Dependencies:
 * - JavaFX for GUI interactions (e.g., ImageView, Label, Alert, etc.).
 * - Board, Bunny, Player, and Card classes for game state management.
 *
 * Initialization:
 * - Initializes board, players, cards, rabbits, and paths.
 * - Configures the game dynamically based on the number of players.
 *
 * Methods:
 * - initialize: Sets up the initial state of the game when the FXML is loaded.
 * - cardMixOnClicked: Handles card mix button action.
 * - rabbitOnClicked: Handles rabbit click events.
 * - buttonRulesOnClicked: Displays the game rules in a new window.
 * - buttonQuitOnClicked: Handles the quit button action with confirmation.
 * - carrotOnClicked: Handles the carrot click action when the carrot card is drawn.
 * - triggerWin: Handles the winning scenario and switches to the winner view.
 * - showAlert: Displays alerts for information, warnings, or errors.
 * - chkRabbitCounts: Checks how many rabbits of a specific color remain on paths.
 */

package GameHandler.lottikarotti_main;

import GameHandler.lottikarotti_main.LottiKarroti.Board.Board;
import GameHandler.lottikarotti_main.LottiKarroti.Bunny.Bunny;
import GameHandler.lottikarotti_main.LottiKarroti.Player.Player;
import GameHandler.lottikarotti_main.LottiKarroti.Card.Card;

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
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerController implements Initializable {

    public ImageView cardView;
    public Button cardMix;
    public Circle start, path1, path2, path3, path4, path5, path6, path7, path8, path9, path10, path11, path12, path13, path14, path15, path16, path17, path18, path19,
            path20, path21, path22, path23, path24, path25, path26, path27;
    public Button buttonQuit;
    public ImageView carrot;
    public Button buttonRules;

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
    private ImageView rules;

    @FXML
    private Label greenRabbit_label;
    @FXML
    private Label purpleRabbit_label;
    @FXML
    private Label yellowRabbit_label;
    @FXML
    private Label pinkRabbit_label;

    private int currentPlayer = 0; // Tracks the current player (0: Green, 1: Yellow, 2: Pink, 3: Purple)
    private final int totalPlayers = GameController.player; // Total number of players

    private static final String RULES_IMAGE_PATH = "/GameHandler/lottikarotti_main/images/rules.jpg";


    @FXML
    private Label currentPlayerLabel; // Label to display the current player

    // Map to track rabbit positions
    private final Map<ImageView, Circle> rabbitPositionMap = new HashMap<>();
    private final List<Circle> paths = new ArrayList<>();
    public final Map<String, Integer> rabbitCounts = new ConcurrentHashMap<>();
    private Player playerManager;
    private Card card;
    private Board board;

    private final Image[] cards = {
            new Image(Objects.requireNonNull(getClass().getResource("/GameHandler/lottikarotti_main/images/card_carrot.jpg")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/GameHandler/lottikarotti_main/images/card_1.jpg")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/GameHandler/lottikarotti_main/images/card_2.jpg")).toExternalForm()),
            new Image(Objects.requireNonNull(getClass().getResource("/GameHandler/lottikarotti_main/images/card_3.jpg")).toExternalForm())

    };

    private Bunny greenBunny;
    private Bunny yellowBunny;
    private Bunny pinkBunny;
    private Bunny purpleBunny;



    /**
     * Initializes the controller when the FXML file is loaded.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeRabbitCounts();
        initializeCard(); //Board.java
        initializePlayer(); //Player.java
        initializeBoard(); //Board.java
        initializeAllRabbits(); //Bunny.java
        initializeExistingRabbits();
        Bunny.enableAllRabbits(); //  /Enable all rabbits initially
        initializeNumberOfPlayer(GameController.player); // Adjust visibility based on the number of players
        initializePaths();
        currentPlayerLabel.setText("Current Player: Green"); // Set the initial player's turn
        card.setRandomIndex(4); //set initial card

    }

    private void initializeBoard() {

        board = new Board( anchorPane, rabbitPositionMap, rabbitCounts, this, playerManager ,card, currentPlayerLabel,paths, carrot);
    }

    private void initializePlayer() {
        playerManager = new Player(
                List.of(greenRabbit_label, yellowRabbit_label, pinkRabbit_label, purpleRabbit_label),
                card,this, currentPlayer,
                GameController.player// Total players
        );
    }

    private void initializeCard() {
        card = new Card(cardView, carrot, cards, this);
    }

    private void initializeRabbitCounts() {
        rabbitCounts.put("green", 0);
        rabbitCounts.put("yellow", 0);
        rabbitCounts.put("pink", 0);
        rabbitCounts.put("purple", 0);

    }

    private void initializePaths() {

        for (int i = 1; i <= 27; i++) {
            Circle path = (Circle) anchorPane.lookup("#path" + i);
            if (path != null) {
                paths.add(path);  //paths.add(1)...paths.add(27);
            }
        }

    }

    private void initializeAllRabbits() {
        greenBunny = new Bunny("green", greenRabbit, greenRabbit_label, "/GameHandler/lottikarotti_main/images/green_bunny.png", playerManager, board);
        yellowBunny = new Bunny("yellow", yellowRabbit, yellowRabbit_label, "/GameHandler/lottikarotti_main/images/yellow_bunny.png", playerManager, board);
        pinkBunny = new Bunny("pink", pinkRabbit, pinkRabbit_label, "/GameHandler/lottikarotti_main/images/pink_bunny.png", playerManager, board);
        purpleBunny = new Bunny("purple", purpleRabbit, purpleRabbit_label, "/GameHandler/lottikarotti_main/images/purple_bunny.png", playerManager, board);
    }

    private void initializeExistingRabbits() {
        for (ImageView rabbit : rabbitPositionMap.keySet()) {
            board.moveExistingRabbit(rabbit);
        }
    }

    private void initializeNumberOfPlayer(int selection) {
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


    /**
     * Handles the action when the "Drawn Card" button is clicked.
     */
    @FXML
    private void cardMixOnClicked() {
        card.cardMixOnClicked();
    }


    /**
     * Handles the action when the "Rabbit" is clicked.
     */
    @FXML
    private void rabbitOnClicked(MouseEvent mouseEvent) {
        ImageView clickedRabbit = (ImageView) mouseEvent.getSource();
        System.out.println("Rabbit clicked: " + clickedRabbit.getId()); // Debugging

        Bunny clickedBunny = determineBunnyByImageView(clickedRabbit);
        if (clickedBunny != null) {
            clickedBunny.rabbitOnClicked(mouseEvent, this, playerManager.getCurrentPlayer(), card.isCardDrawn(), card.getRandomIndex(), rabbitPositionMap, start);
        } else {
            System.err.println("No matching bunny found for the clicked rabbit!");
        }
    }

    /**
     * Handles the action when the "Rules" is clicked.
     */

    @FXML
    public void buttonRulesOnClicked() {
        try {
            Image rulesImage = new Image(Objects.requireNonNull(getClass().getResource(RULES_IMAGE_PATH)).toExternalForm());
            ImageView imageView = new ImageView(rulesImage);
            imageView.setFitWidth(1000);
            imageView.setPreserveRatio(true);

            StackPane layout = new StackPane(imageView);
            Scene scene = new Scene(layout, 1000, 563);

            Stage stage = new Stage();
            stage.setTitle("Game Rules");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(anchorPane.getScene().getWindow());
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Unable to load game rules.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Determines the Bunny object corresponding to a clicked rabbit ImageView.
     */
    private Bunny determineBunnyByImageView(ImageView imageView) {
        if (imageView == greenRabbit) {
            return greenBunny;
        } else if (imageView == yellowRabbit) {
            return yellowBunny;
        } else if (imageView == pinkRabbit) {
            return pinkBunny;
        } else if (imageView == purpleRabbit) {
            return purpleBunny;
        } else {
            System.err.println("Unknown rabbit clicked: " + imageView.getId());
            return null;
        }
    }

    /**
     * Handles the action when the "Quit"Button is clicked.
     */

    @FXML
    public void buttonQuitOnClicked(MouseEvent mouseEvent) {
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        Optional<ButtonType> result = showAlert( "Exit Game", "Are you sure you want to quit the game?", Alert.AlertType.CONFIRMATION, yesButton,noButton );// Show a pop-up alert

        if (result.isPresent() && result.get() == yesButton) {
            Platform.exit();
        }
    }

    /**
     * Handles the action when the "Carrot" is clicked.
     */
    @FXML
    public void carrotOnClicked(MouseEvent mouseEvent) {

        if (card.getRandomIndex() == 0) {
            board.makeRandomHoles(); // Call the method to make random holes
            card.setRandomIndex(4);
        } else {
            showAlert( "Invalid Action", "You can only click the carrot when the carrot card is drawn!", Alert.AlertType.WARNING);// Show a pop-up alert
        }
    }

    /**
     * Checks if a player has any remaining rabbits to add to the board.
     */
    public boolean checkPlayerHasRabbitsToAdd(Label rabbitLabel) {
        //Checks if the player has any remaining rabbits to add to the board
        int remainingRabbits = Integer.parseInt(rabbitLabel.getText());
        return remainingRabbits > 0;
    }

    /**
     * Handles the winning scenario when a rabbit reaches the goal.
     */
    public void triggerWin(ImageView winningRabbit) {
        //Handles the win scenario by displaying a congratulatory message and transitioning to the winner view.
        // Show congratulatory message
        showAlert( "Congratulations", "Congratulations! The "+winningRabbit.getId()+" Rabiit is the winner!", Alert.AlertType.INFORMATION);// Show a pop-up alert


        // Switch to the winner view
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameHandler/lottikarotti_main/View/WinnerView.fxml"));
            Parent root = loader.load();

            // Get the controller for the WinnerView
            WinnerView winnerController = loader.getController();

            // Pass the winner ID to the controller
            winnerController.setWinnerId(winningRabbit.getId());

            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            SceneChanger.setPrimaryStage(stage);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load the winner view.", Alert.AlertType.ERROR);

        }
    }


    /**
     * Displays an alert dialog with the specified title and message
     */
   public void showAlert(String title, String message, Alert.AlertType alertType) {
        // Displays a popup alert with the specified title, message, and alert type
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * displays an customizable popup alert dialog with the specified title and message
     */
    public Optional<ButtonType> showAlert(String title, String message, Alert.AlertType alertType, ButtonType yesButton, ButtonType noButton) {
       // Displays a customizable popup alert with specified title, message, alert type, and button options
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(yesButton, noButton);
        return alert.showAndWait();
    }

    /**
     * Checks how many rabbits of a specified color remain on the paths.
     */
    public int chkRabbitCounts(String rabbitColor) {
        // Checks how many rabbits of a specified color remain on the paths.
        int rabbitCountOnPath = 0;
        for (Map.Entry<String, Integer> entry : rabbitCounts.entrySet()) {
          //  System.out.println("Key: '" + entry.getKey() + "', Value: " + entry.getValue());

            if (entry.getKey().equals(rabbitColor.trim().toLowerCase())){
                rabbitCountOnPath = entry.getValue();
               System.out.println("SELECTED Key: '" + entry.getKey() + "', SELECTED Value: " + entry.getValue());

            }
        }
        return rabbitCountOnPath;

    }


}

