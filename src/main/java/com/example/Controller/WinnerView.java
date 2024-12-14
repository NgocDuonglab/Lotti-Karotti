package com.example.Controller;

import com.example.lottikarotti_main.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class WinnerView {

    @FXML
    public Button closeButton;

    @FXML
    protected void onWinnerButtonClick() throws IOException {
        HelloApplication.changeScene("views/hello-view.fxml");
    }

    @FXML
    public void onExitButtonClick(ActionEvent event) {
        System.exit(0); }
}