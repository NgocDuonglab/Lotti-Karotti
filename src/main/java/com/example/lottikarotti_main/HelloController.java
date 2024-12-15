package com.example.lottikarotti_main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        HelloApplication.   changeScene("views/WinnerView.fxml");
    }
}