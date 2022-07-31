
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;

/**
 * This is the Tutorital class, meant to control and set up everything with the tutorials.
 * The tutorial pops up as they hover over the question mark near the different
 * features of the app.
 * 
 * @author Shashwati Sanjay
 * @version 2021-01-14
 * @url Source Help: https://stackoverflow.com/questions/53831807/javafx-show-a-pane-on-mouse-hover
 */

public class Tutorial {

    double x, y;
    String xpos, ypos;

    /**
     * The setPane method contains all of the instructions for the tutorials. 
     * It sets the text and style of the pop-ups, as well as their position on
     * the screen.
     * It uses a listener to check if the mouse is hovering over the pane and
     * shows the pop-up pane.
     * 
     * @param inputText the instructions on how to use the particular feature.
     * @param pane      the variable that holds the fxid for the question mark pane
     *                  from scene builder associated with the particular feature.
     * @param xpos      the x position for the pop-up. Can be either "RIGHT" or
     *                  "LEFT".
     * @param ypos      the y position for the pop-up. Can be either "UP" or "DOWN".
     */
    public void setPane(String inputText, Pane pane, String xpos, String ypos) {
        Label popUpLabel = new Label();
        popUpLabel.setText(inputText);
        popUpLabel.setWrapText(true);
        popUpLabel.setTextAlignment(TextAlignment.CENTER);
        popUpLabel.setFont(new Font("Times New Roman", 12));
        StackPane popUpPane = new StackPane(popUpLabel);
        popUpPane.setPrefSize(160, 160);
        popUpPane.setStyle("-fx-background-color: white");
        popUpPane.setAlignment(Pos.CENTER);
        popUpPane.setPadding(new Insets(5, 5, 5, 5));

        Popup popup = new Popup();
        popup.getContent().add(popUpPane);
        pane.hoverProperty().addListener((obs, oldVal, newValue) -> {
            if (newValue) {
                javafx.geometry.Bounds bnds = pane.localToScreen(pane.getLayoutBounds());
                if (xpos.equals("LEFT")) {
                    x = bnds.getMinX() - popUpPane.getWidth();
                } else if (xpos.equals("RIGHT")) {
                    x = bnds.getMaxX();
                }
                if (ypos.equals("DOWN")) {
                    y = bnds.getMaxY();
                } else if (ypos.equals("UP")) {
                    y = bnds.getMinY() + popUpPane.getHeight() + pane.getHeight();
                }
                popup.show(pane, x, y);
            } else {
                popup.hide();
            }
        });
    }

}