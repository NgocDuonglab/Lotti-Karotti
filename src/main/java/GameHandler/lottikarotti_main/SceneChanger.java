/**
 * OOP Java Project  WiSe 2024/2025
 * @Author: Huynh Thi Kim Quynh (Matriculation Nr. 1584541)
 *
 * @Version: 1.0 (20/12/2024)
 *
 * MainView.css - Styling for all components of the start scene.
 */

/* Style for the game name label */

package GameHandler.lottikarotti_main;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneChanger {
    public static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void changeScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneChanger.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
    }
}