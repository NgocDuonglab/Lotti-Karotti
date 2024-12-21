package GameHandler.lottikarotti_main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.io.IOException;

public class WinnerView {

    @FXML
    public Button closeButton;

    @FXML
    private ImageView rabbitImageView;

    private String winnerId;

    @FXML
    protected void onWinnerButtonClick() throws IOException {
        SceneChanger.changeScene("View/MainView.fxml");
    }

    @FXML
    public void onExitButtonClick(ActionEvent event) {
        System.exit(0); }


 /*   public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
        updateRabbitImage();
    }

    private void updateRabbitImage() {
        if (rabbitImageView != null && winnerId != null) {
            String imagePath = getImagePathForWinner(winnerId);
            if (imagePath != null) {
                try {
                    rabbitImageView.setImage(new Image(imagePath));
                } catch (Exception e) {
                    System.err.println("Failed to load image: " + imagePath);
                    e.printStackTrace();
                }
            } else {
                System.err.println("Invalid winnerId: " + winnerId);
            }
        }
    }


private String getImagePathForWinner(String winnerId) {
    switch (winnerId) {
        case "green":
            return "@../images/green_bunny.png";
        case "yellow":
            return "@../images/yellow_bunny.png";
        case "pink":
            return "@../images/pink_bunny.png";
        case "purple":
            return "@../images/purple_bunny.png";
        default:
            return null;
    }
}
*/



}