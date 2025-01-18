package GameHandler.lottikarotti_main;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class GameController implements Initializable {

    @FXML
    private ImageView myImageView;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Button startButton;

    @FXML
    private Label label;

    @FXML
    private Rectangle rectangle;

 public static int player = 0;



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        setBackgroundImage();
        startButton.setVisible(false); // Hide the start button initially


        // Add items to the choice box
        choiceBox.getItems().addAll("2 Players", "3 Players", "4 Players");




        // Add listener to show the start button and update the label when a selection is made
        choiceBox.setOnAction(event -> {
            if (choiceBox.getValue() != null) {
                label.setText(choiceBox.getValue()); // Update the label with the selected number of players

                System.out.println(choiceBox.getValue());

                if (choiceBox.getValue().equals("2 Players")){
                    player = 2;

                }else if (choiceBox.getValue().equals("3 Players")){
                    player = 3;
                }else if (choiceBox.getValue().equals("4 Players")){
                    player = 4;
                }

                choiceBox.setVisible(false); // Hide the choice box

                // Pause for a few seconds before hiding the rectangle and showing the start button
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> {
                    rectangle.setVisible(false); // Hide the rectangle
                    startButton.setVisible(true); // Show the start button
                });
                pause.play();
            }
        });
    }



    @FXML
    private void handleButtonAction(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();
        System.out.println("Button clicked: " + buttonText);

        // Load the board view scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameHandler/lottikarotti_main/View/BoardView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) clickedButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            SceneChanger.setPrimaryStage(stage);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setBackgroundImage() {
        Image backgroundImage = new Image(getClass().getResourceAsStream("/GameHandler/lottikarotti_main/images/main-background.png"));
        myImageView.setImage(backgroundImage);
    }
}