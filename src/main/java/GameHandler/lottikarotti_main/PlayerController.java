package GameHandler.lottikarotti_main;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;



public class PlayerController implements Initializable {

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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("pinkRabbit: " + pinkRabbit);
        System.out.println("FXML Injection Successful: " + (pinkRabbit != null));
        // Enable all rabbits initially
        enableAllRabbits();
        handlePlayerSelection(GameController.player);

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
}
