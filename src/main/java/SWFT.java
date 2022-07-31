
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This is the SWFT class that creates the main structure of the program.
 * This class is also used to pass information between scene controllers and
 * other classes.
 * 
 * @author Diya Shah, Elizabeth Caporiccio, Nicole Jin, Shashwati Sanjay
 * @version 2021-01-14
 */
public class SWFT extends Application {

    public static Scene scene;
    public static ArrayList<UserData> allDataLst;
    public static UserData currentUser = null;
    public static boolean nullAccount = false;
    Tutorial timeTutorial = new Tutorial();

    /**
     * The start method loads in when you start the app.
     * 
     * @param stage                     is a top-level container that host scenes.
     * @throws IOException              if there is a failure in I/O operations.
     * @throws SpotifyWebApiException   if there is a failure in spotify web api.    
     */
    @Override
    public void start(Stage stage) throws IOException, SpotifyWebApiException {

        scene = new Scene(loadFXML("login"), 1000, 550);
        stage.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The stop method is called at the end of the program.
     * 
     * The user data is stored into the external file and the 
     * current user is set to null when the program is closed.
     * 
     * @throws IOException              if there is a failure in I/O operations.
     * @throws ClassNotFoundException   when the program tries to load a class through 
     *                                  its fully-qualified name and can not find its definition 
     *                                  on the classpath.
     */
    @Override
    public void stop() throws ClassNotFoundException, IOException {
        if (!nullAccount) {
            setCurrentUser(null);
            storeData();
        }
    }

    /**
     * The setRoot method sets the root of the scene that the program is displaying.
     * 
     * @param fxml               is the fxml file that the program sets root to.
     * @exception IOException    if the stream is corrupted or errors occurred during 
     *                           reading the data (connecting to the fxml files).
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * The loadFXML method gets the base parent subclass.
     * 
     * @param fxml                  is the fxml file that the program is reading from.
     * @exception IOException       if the stream is corrupted or errors occurred during 
     *                              reading the data (connecting to the fxml files).
     * @return fmlLoader.load()     is the base class for all nodes that have children.
     */
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SWFT.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * The getScene method gets the instance of the scene.
     * 
     * @return scene    the current scene of the program.
     */
    public static Scene getScene() {
        return scene;
    }

    /**
     * The setAllData method gets all of the data from the 
     * external file and sets it to an arraylist.
     * 
     * @throws IOException              if there is a failure in I/O operations.
     * @throws ClassNotFoundException   when the program tries to load a class through 
     *                                  its fully-qualified name and can not find its definition 
     */
    public static void setAllData() throws IOException, ClassNotFoundException {
        allDataLst = ReadFromFile.getUserData();
    }

    /**
     * The getAllData method gets all of the current user data 
     * that was previously stored in an arraylist.
     * 
     * @return allDataList      all of the current user data.
     */
    public static ArrayList<UserData> getAllData() {
        return allDataLst;
    }

    /**
     * The setCurrentUser method sets the current user of the program.
     * 
     * @param userIn    ths user account that the current user needs to be set to.
     */
    public static void setCurrentUser(UserData userIn) {
        currentUser = userIn;
    }

    /**
     * The getCurrentUser method gets the current user of the proram.
     * 
     * @return currentUser    the current user of the program.
     */
    public static UserData getCurrentUser() {
        return currentUser;
    }

    /**
     * The addCurrentUser adds the current user on to the arraylist that
     * stores all of the current user data.
     * This method is only called when a new user account is created.
     */
    public static void addCurrentUser() {
        allDataLst.add(currentUser);
    }

    /**
     * The getUserIndex method gets the index of the user object in the 
     * arraylist that stores all of the current user data.
     * 
     * @return allDataList.indexOf(currentUser)     the index of the user object
     */
    public static int getUserIndex() {
        return allDataLst.indexOf(currentUser);
    }

    /**
     * The updateAllData updates the updated information from the current user onto 
     * the arraylist that stores all current user data.
     */
    public static void updateAllData() {
        allDataLst.set(getUserIndex(), currentUser);
    }

    /**
     * The storeData stores all of the user data into an external file.
     * 
     * @throws IOException              if there is a failure in I/O operations.
     * @throws ClassNotFoundException   when the program tries to load a class through 
     *                                  its fully-qualified name and can not find its definition 
     */
    public static void storeData() throws ClassNotFoundException, IOException {
        WriteToFile.storeUserData();
    }

    /**
     * The buttonPressed method styles the button when it is pressed.
     *
     * @param button       the button that needs to be styled.
     */
    public static void buttonPressed(Button button) {
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                button.setStyle(
                        "-fx-background-color: rgb(122, 90, 95); -fx-text-fill: rgb(224, 194, 177); -fx-border-color: rgb(224, 194, 177);");
            }
        });
        button.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                button.setStyle(null);
            }
        });
    }

     /**
     * The main method launches the program.
     * 
     * @param args      the command line arguments.
     **/
    public static void main(String[] args) {
        launch();
    }

}