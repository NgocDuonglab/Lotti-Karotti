package GameHandler.lottikarotti_main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameHandler/lottikarotti_main/View/MainView.fxml"));


        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lotti Karotti");

        // Set the icon for the primary stage
        Image icon = new Image(getClass().getResourceAsStream("/GameHandler/lottikarotti_main/images/icon_game.jpg"));
        primaryStage.getIcons().add(icon);

        primaryStage.show();

        SceneChanger.setPrimaryStage(primaryStage);

        primaryStage.setResizable(false);
    }

}
