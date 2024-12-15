package LottiKarottiGameHandler.Start.lottikarotti_main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private ImageView myImageView;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setBackgroundImage();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();
        System.out.println("Button clicked: " + buttonText);

        // Load the next scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LottiKarottiGameHandler/Start/lottikarotti_main/View/NextScene.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) clickedButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setBackgroundImage() {
        Image backgroundImage = new Image(getClass().getResourceAsStream("/LottiKarottiGameHandler/Start/lottikarotti_main/images/main-background.png"));
        myImageView.setImage(backgroundImage);
    }
}