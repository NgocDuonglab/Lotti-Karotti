package com.example.lottikarotti_main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import javafx.stage.Window;

public class HelloApplication extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/hello-view.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("winner-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);

        primaryStage = stage;

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}