package GameHandler.lottikarotti_main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class WinnerView {

    @FXML
    public Button closeButton;

    @FXML
    protected void onWinnerButtonClick() throws IOException {
        HelloApplication.changeScene("View/hello-view.fxml");
    }

    @FXML
    public void onExitButtonClick(ActionEvent event) {
        System.exit(0); }
}