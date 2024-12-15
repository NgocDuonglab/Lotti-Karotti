package GameHandler.lottikarotti_main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Label myLabel;

    @FXML
    private ChoiceBox<String> myChoiceBox;

    @FXML
    private ImageView myImageView;

    @FXML
    private Rectangle myRectangle;

    private String[] number = {"1 Player", "2 Players", "3 Players", "4 Players"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        myChoiceBox.getItems().addAll(number);
        myChoiceBox.setOnAction(this::getChoice);
        setBackgroundImage();
        setRectangleBackground();
    }

    public void getChoice(ActionEvent event) {
        String myChoice = myChoiceBox.getValue();
        myLabel.setText(myChoice);
    }

    private void setBackgroundImage() {
        Image backgroundImage = new Image(getClass().getResourceAsStream("/GameHandler/lottikarotti_main/images/main-background.png"));
        myImageView.setImage(backgroundImage);
    }

    private void setRectangleBackground() {
        myRectangle.setFill(Color.WHITE);
        myRectangle.setOpacity(0.7);
    }
}