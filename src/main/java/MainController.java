import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javafx.animation.ScaleTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.util.Duration;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

 /**
 * This is the MainController class that controls the main scene.
 * 
 * This class includes all the functions for all background, media, playlists, and 
 * built in audio options, the to-do list feature, the tutorial that gives a general overview 
 * of all the features on the main screen, the timer which opens the timer screen, and 
 * the 3d cube animation code that generates a 3d box that the user can play with.
 * 
 * @author Diya Shah, Elizabeth Caporiccio, Nicole Jin, Shashwati Sanjay
 * @version 2022-01-13
 */
public class MainController {

    // Menu Buttons
    @FXML
    private MenuButton audio;
    @FXML
    private MenuButton backgroundColor;
    @FXML
    private MenuButton media;
    @FXML
    private MenuButton playlist;

    // Buttons
    @FXML
    private Button pauseAudio;
    @FXML
    private Button pauseMedia;
    @FXML
    private Button mode;
    @FXML
    private Button displayToDo;
    @FXML
    private Button addTask;
    @FXML
    private Button deleteTask;
    @FXML
    private Button playSpotify;
    @FXML
    private Button switchToTimer;

    // Media Views
    @FXML
    private MediaView mediaView;

    // Scene
    @FXML
    private Scene scene;

    // Panes
    @FXML
    private Pane background;
    @FXML
    private Pane pane;

    // Strings
    private String selectedBackgroundColor = "";
    private String selectedMedia = "";
    private String selectedAudio = "";
    private String selectedPlaylist = "";
    private boolean darkMode = false;

    // Built in Audios
    Media relaxingAudio = new Media(getClass().getResource("relaxingAudio.mp3").toExternalForm());
    MediaPlayer relaxPlayer = new MediaPlayer(relaxingAudio);

    Media rainAudio = new Media(getClass().getResource("rainAudio.wav").toExternalForm());
    MediaPlayer rainPlayer = new MediaPlayer(rainAudio);

    Media cafeAudio = new Media(getClass().getResource("cafeAudio.mp3").toExternalForm());
    MediaPlayer cafeAudioPlayer = new MediaPlayer(cafeAudio);

    Media oceanAudio = new Media(getClass().getResource("oceanAudio.mp3").toExternalForm());
    MediaPlayer oceanPlayer = new MediaPlayer(oceanAudio);

    // Media Backgrounds
    Media parkMedia = new Media(getClass().getResource("parkBackground.mp4").toExternalForm());
    MediaPlayer parkPlayer = new MediaPlayer(parkMedia);

    Media beachMedia = new Media(getClass().getResource("beachBackground.mp4").toExternalForm());
    MediaPlayer beachPlayer = new MediaPlayer(beachMedia);

    Media rainforestMedia = new Media(getClass().getResource("rainforestBackground.mp4").toExternalForm());
    MediaPlayer rainforestPlayer = new MediaPlayer(rainforestMedia);

    Media cafeMedia = new Media(getClass().getResource("cafeBackground.mp4").toExternalForm());
    MediaPlayer cafeMediaPlayer = new MediaPlayer(cafeMedia);

    // table
    @FXML
    private TableColumn<ToDo, String> ToDoColumn;
    @FXML
    private TableView<ToDo> ToDoTable;

    // text field
    @FXML
    private TextField task;
    @FXML
    private Text welcomeText;

    // box
    @FXML
    private Box box;

    // 3d box variables
    private Rotate r;
    private Transform t = new Rotate();

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    private double mouseXToBox, mouseYToBox;
    private final DoubleProperty posX = new SimpleDoubleProperty(0);
    private final DoubleProperty posY = new SimpleDoubleProperty(0);

    // tutorial
    Tutorial timeTutorial = new Tutorial();
    @FXML
    Pane mainHelp;
    @FXML
    Pane toDoHelp;
    @FXML
    Pane cubeHelp;

    /**
     * The switchToTimer method opens the new window with the timer.
     *
     * @throws IOException      if there is a failure in I/O operations.
     */
    @FXML
    private void switchToTimer() throws IOException {
        welcomeText.setText("");
        Timer timer = new Timer();
        timer.init();
    }

    /**
     * The switchToLogin method switches the scene to login when the 
     * logout button is pressed.
     * 
     * @throws IOException              if there is a failure in I/O operations.
     * @throws ClassNotFoundException   when the program tries to load a class through 
     *                                  its fully-qualified name and can not find its definition 
     *                                  on the classpath.
     */
    @FXML
    private void switchToLogin() throws IOException, ClassNotFoundException {
        stopAllMedia();
        stopAudios();
        SWFT.setCurrentUser(null);
        if (!SWFT.nullAccount){
            SWFT.storeData();
        }
        SWFT.setRoot("login");
    }

    /**
     * The setBackground method is the general setter for all backgrounds.
     */
    @FXML 
    public void setBackground() {
        backgroundColor.setText(selectedBackgroundColor); 
    }

    /**
     * The setRedBackground method sets red background to pane.
     */
    @FXML
    public void setRedBackground() {
        selectedBackgroundColor = "RED";
        background.setStyle("-fx-background-color: #AD5267");
        setBackground(); 
    }

    /**
     * The setOrangeBackground method sets orange background to pane.
     */
    @FXML
    public void setOrangeBackground() {
        selectedBackgroundColor = "ORANGE";
        background.setStyle("-fx-background-color: #F7AB77");
        setBackground(); 
    }

    /**
     * The setRedBackground method sets yellow background to pane.
     */
    @FXML
    public void setYellowBackground() {
        selectedBackgroundColor = "YELLOW";
        background.setStyle("-fx-background-color: #F7DC66");
        setBackground(); 
    }

    /**
     * The setGreenBackground method sets green background to pane.
     */
    @FXML
    public void setGreenBackground() {
        selectedBackgroundColor = "GREEN";
        background.setStyle("-fx-background-color: #94AD52");
        setBackground(); 
    }

    /**
     * The setBlueBackground method sets blue background to pane.
     */
    @FXML
    public void setBlueBackground() {
        selectedBackgroundColor = "BLUE";
        background.setStyle("-fx-background-color: #52AD98 ");
        setBackground(); 
    }

    /**
     * The setPurpleBackground method sets purple background to pane.
     */
    @FXML
    public void setPurpleBackground() {
        selectedBackgroundColor = "PURPLE";
        background.setStyle("-fx-background-color: #6B52AD ");
        setBackground(); 
    }

    /**
     * The setMediaView method is the general setter for all media views.
     */
    private void setMediaView() {
        welcomeText.setText("");
        media.setText(selectedMedia);
    }

    /**
     * The stopAllMedia stops all media, used before another is played.
     */
    private void stopAllMedia() {
        parkPlayer.stop();
        beachPlayer.stop();
        rainforestPlayer.stop();
        cafeMediaPlayer.stop();
    }

    /**
     * The setParkBackground sets park video background.
     */
    @FXML
    public void setParkBackground() {
        stopAllMedia();
        selectedMedia = "PARK";
        mediaView.setMediaPlayer(parkPlayer);
        parkPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
        parkPlayer.play();
        setMediaView();
        setAudioWithMedia();
    }

    /**
     * The setBeachBackground sets beach video background.
     */
    @FXML
    public void setBeachBackground() {
        stopAllMedia();
        selectedMedia = "BEACH";
        mediaView.setMediaPlayer(beachPlayer);
        beachPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
        beachPlayer.play();
        setMediaView();
        setAudioWithMedia();
    }

    /**
     * The setRainforestBackground sets rainforest video background.
     */
    @FXML
    public void setRainforestBackground() {
        stopAllMedia();
        selectedMedia = "RAINFOREST";
        mediaView.setMediaPlayer(rainforestPlayer);
        rainforestPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
        rainforestPlayer.play();
        setMediaView();
        setAudioWithMedia();
    }

    /**
     * The setCafeBackground sets cafe video background.
     */
    @FXML
    public void setCafeBackground() {
        stopAllMedia();
        selectedMedia = "CAFE";
        mediaView.setMediaPlayer(cafeMediaPlayer);
        cafeMediaPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
        cafeMediaPlayer.play();
        setMediaView();
        setAudioWithMedia();
    }

    /**
     * The setModeWithBackground switches between light mode and dark mode for 
     * each background color chosen through a switch statement.
     */
    @FXML
    public void setModeWithBackground() {
        switch (selectedBackgroundColor) {
            case "RED":
                if (!darkMode) {
                    background.setStyle("-fx-background-color: #D09EA9");
                } else {
                    background.setStyle("-fx-background-color: #864050");
                }
                darkMode = !darkMode;
                break;

            case "ORANGE":
                if (!darkMode) {
                    background.setStyle("-fx-background-color: #F8B486");
                } else {
                    background.setStyle("-fx-background-color: #F38032");
                }
                darkMode = !darkMode;
                break;

            case "YELLOW":
                if (!darkMode) {
                    background.setStyle("-fx-background-color: #FCF2C6");
                } else {
                    background.setStyle("-fx-background-color: #F3CE2C");
                }
                darkMode = !darkMode;
                break;

            case "GREEN":
                if (!darkMode) {
                    background.setStyle("-fx-background-color: #C3D09E");
                } else {
                    background.setStyle("-fx-background-color: #738640");
                }
                darkMode = !darkMode;
                break;

            case "BLUE":
                if (!darkMode) {
                    background.setStyle("-fx-background-color: #9ED0C5");
                } else {
                    background.setStyle("-fx-background-color: #408676");
                }
                darkMode = !darkMode;
                break;

            case "PURPLE":
                if (!darkMode) {
                    background.setStyle("-fx-background-color: #AB9ED0");
                } else {
                    background.setStyle("-fx-background-color: #534086");
                }
                darkMode = !darkMode;
                break;

            default:
                if (!darkMode) {
                    background.setStyle("-fx-background-color: rgb(245, 213, 193)");
                } else {
                    background.setStyle("-fx-background-color: rgb(94, 81, 73)");
                }
                darkMode = !darkMode;
                break;
        }
        if (!darkMode) {
            mode.setText("light mode");
        } else {
            mode.setText("dark mode");
        }
    }

    /**
     * The setPlaylist method is the general setter for all playlists.
     */
    private void setPlaylist() {
        playlist.setText(selectedPlaylist);
        // welcomeText.setText("you're now using spotify");
    }

    /**
     * The playJazz method opens jazz playlist in a new tab.
     * 
     * @param event                         checks to see if event has occured.
     * @throws URISyntaxException           if the string doesn't have the correct format. 
     * @throws IOException                  if there is a failure in I/O operations.
     */
    @FXML
    private void playJazz(ActionEvent event) throws URISyntaxException, IOException {
        selectedPlaylist = "JAZZ";
        Desktop.getDesktop().browse(new URI("https://open.spotify.com/playlist/5145sx3JFaxDzJN0QsDX4b"));
        setPlaylist();
    }

    /**
     * The playClassical method opens classical playlist in a new tab.
     * 
     * @param event                         checks to see if event has occured.
     * @throws URISyntaxException           if the string doesn't have the correct format. 
     * @throws IOException                  if there is a failure in I/O operations.
     */
    @FXML
    private void playClassical(ActionEvent event) throws URISyntaxException, IOException {
        selectedPlaylist = "CLASSICAL";
        Desktop.getDesktop()
                .browse(new URI("https://open.spotify.com/playlist/3VYb1yVjJ0L6kK3cIa3hrN?si=1c535fa0359243b8&nd=1"));
        setPlaylist();
    }

    /**
     * The playPunk method opens punk playlist in a new tab.
     * 
     * @param event                         checks to see if event has occured.
     * @throws URISyntaxException           if the string doesn't have the correct format. 
     * @throws IOException                  if there is a failure in I/O operations.
     */
    @FXML
    private void playPunk(ActionEvent event) throws URISyntaxException, IOException {
        selectedPlaylist = "PUNK ROCK";
        Desktop.getDesktop()
                .browse(new URI("https://open.spotify.com/playlist/1GDaTIdj0BD46dDm4p6Bh4?si=4e62271be12546d3&nd=1"));
        setPlaylist();
    }

    /**
     * The playKpop method opens kpop/jpop playlist in a new tab.
     * 
     * @param event                         checks to see if event has occured.
     * @throws URISyntaxException           if the string doesn't have the correct format. 
     * @throws IOException                  if there is a failure in I/O operations.
     */
    @FXML
    private void playKpop(ActionEvent event) throws URISyntaxException, IOException {
        selectedPlaylist = "K POP/J POP";
        Desktop.getDesktop()
                .browse(new URI("https://open.spotify.com/playlist/71bEK9J3KGuYt1ndceepSX?si=567dc75819884db9&nd=1"));
        setPlaylist();
    }

    /**
     * The playHype method opens hype playlist in a new tab.
     * 
     * @param event                         checks to see if event has occured.
     * @throws URISyntaxException           if the string doesn't have the correct format. 
     * @throws IOException                  if there is a failure in I/O operations.
     */
    @FXML
    private void playHype(ActionEvent event) throws URISyntaxException, IOException {
        selectedPlaylist = "HYPE";
        Desktop.getDesktop()
                .browse(new URI("https://open.spotify.com/playlist/6CRwFtRTpqruxXbavRlVXs?si=35c7daefba3b436c"));
        setPlaylist();
    }

    /**
     * The playCasual method opens casual playlist in a new tab.
     * 
     * @param event                         checks to see if event has occured.
     * @throws URISyntaxException           if the string doesn't have the correct format. 
     * @throws IOException                  if there is a failure in I/O operations.
     */
    @FXML
    private void playCasual(ActionEvent event) throws URISyntaxException, IOException {
        selectedPlaylist = "CASUAL";
        Desktop.getDesktop()
                .browse(new URI("https://open.spotify.com/playlist/6u3WImpQ5apCZP5rgUO8EG?si=71ec1b10bf3b49ac"));
        setPlaylist();
    }

    /**
     * The setAudio method is the general setter for all built in audios.
     */
    private void setAudio() {
        welcomeText.setText("");
        audio.setText(selectedAudio);
    }

    /**
     * The stopAudios method stops all audios, used before another is played.
     */
    private void stopAudios() {
        relaxPlayer.stop();
        rainPlayer.stop();
        cafeAudioPlayer.stop();
        cafeAudioPlayer.stop();
        oceanPlayer.stop();
    }

    /**
     * The setRelaxingAudio method sets library audio.
     */
    @FXML
    public void setRelaxingAudio() {
        stopAudios();
        selectedAudio = "RELAXING";
        setAudio();
        relaxPlayer.play();
        relaxPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
    }

    /**
     * The setRainAudio method sets rain audio.
     */
    @FXML
    public void setRainAudio() {
        stopAudios();
        selectedAudio = "RAIN";
        setAudio();
        rainPlayer.play();
        rainPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
    }

    /**
     * The setCafeAudio method sets cafe audio.
     */
    @FXML
    public void setCafeAudio() {
        stopAudios();
        selectedAudio = "CAFE";
        setAudio();
        cafeAudioPlayer.play();
        cafeAudioPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
    }

    /**
     * The setOceanAudio method sets ocean audio.
     */
    @FXML
    public void setOceanAudio() {
        stopAudios();
        selectedAudio = "OCEAN";
        setAudio();
        oceanPlayer.play();
        oceanPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
    }

    /**
     * The pauseAudio method is the pauser method for all audios.
     * It checks if the audio is playing, and if it is and the user clicks pause, 
     * the audio pauses.
     */
    @FXML
    public void pauseAudio() {      
        switch(selectedAudio) {
            case "RELAXING": 
                if (relaxPlayer.getStatus() == Status.PLAYING) {
                    relaxPlayer.pause();
                    pauseAudio.setText("Play Audio");
                } else {
                    relaxPlayer.play();
                    pauseAudio.setText("Pause Audio");
                }
                break; 

            case "RAIN": 
                if (rainPlayer.getStatus() == Status.PLAYING) {
                    rainPlayer.pause();
                    pauseAudio.setText("Play Audio");
                } else {
                    rainPlayer.play();
                    pauseAudio.setText("Pause Audio");
                }
                break; 

            case "CAFE":
                if (cafeAudioPlayer.getStatus() == Status.PLAYING) {
                    cafeAudioPlayer.pause();
                    pauseAudio.setText("Play Audio");
                } else {
                    cafeAudioPlayer.play();
                    pauseAudio.setText("Pause Audio");
                }
                break; 

            case "OCEAN": 
                if (oceanPlayer.getStatus() == Status.PLAYING) {
                    oceanPlayer.pause();
                    pauseAudio.setText("Play Audio");
                } else {
                    oceanPlayer.play();
                    pauseAudio.setText("Pause Audio");
                }
                break; 

            default: 
                stopAudios(); 
        }
    }

    /**
     * The pauseMedia method is pauser method for all medias.
     * It cecks if the video is playing, and if it is and the user clicks pause, 
     * the video pauses.
     */
    @FXML
    public void pauseMedia() {
        switch (selectedMedia) {
            case "PARK": 
                if (parkPlayer.getStatus() == Status.PLAYING) {
                    parkPlayer.pause();
                    pauseMedia.setText("Play Media");
                } else {
                    parkPlayer.play();
                    pauseMedia.setText("Pause Media");
                }
                break; 

            case "BEACH": 
                if (beachPlayer.getStatus() == Status.PLAYING) {
                    beachPlayer.pause();
                    pauseMedia.setText("Play Media");
                } else {
                    beachPlayer.play();
                    pauseMedia.setText("Pause Media");
                }
                break; 

            case "RAINFOREST": 
                if (rainforestPlayer.getStatus() == Status.PLAYING) {
                    rainforestPlayer.pause();
                    pauseMedia.setText("Play Media");
                } else {
                    rainforestPlayer.play();
                    pauseMedia.setText("Pause Media");
                }
                break; 

            case "CAFE": 
                if (cafeMediaPlayer.getStatus() == Status.PLAYING) {
                    cafeMediaPlayer.pause();
                    pauseMedia.setText("Play Media");
                } else {
                    cafeMediaPlayer.play();
                    pauseMedia.setText("Pause Media");
                }
                break; 

            default: 
                stopAllMedia(); 
        }
    }

    /**
     * The setAudioWithMedia method sets the media with the coresponding audio,
     * and the user will stil be able to chose different audios.
     */
    @FXML
    public void setAudioWithMedia() {
        if (selectedMedia == "PARK") {
            setRelaxingAudio();
        } else if (selectedMedia == "RAINFOREST") {
            setRainAudio();
        } else if (selectedMedia == "CAFE") {
            setCafeAudio();
        } else if (selectedMedia == "BEACH") {
            setOceanAudio();
        }
    }

    /**
     * The setBackgroundWithTable method sets the media's position based on whether 
     * the to-do list is shown on the scene.
     */
    @FXML
    public void setBackgroundWithTable() {
        if(displayToDo.isVisible() && ToDoTable.isVisible() && task.isVisible() && addTask.isVisible()
        && deleteTask.isVisible()) {
            mediaView.setTranslateX(104);
        } else {
            mediaView.setTranslateX(0);
        }
    }

    /**
     * The tableSetUp method sets up the table.
     */
    private void tableSetUp() {
        // set up the columns in the table.
        ToDoColumn.setCellValueFactory(new PropertyValueFactory<ToDo, String>("text"));

        ToDoTable.setEditable(true);
        ToDoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ToDoColumn.setOnEditCommit(new EventHandler<CellEditEvent<ToDo, String>>() {

            @Override
            public void handle(CellEditEvent<ToDo, String> changedCell) {
                changeCellEvent(changedCell);
            }
        });

        // select multiple cells
        ToDoTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // to do list and everything associated with it will be not visible by default.
        ToDoTable.setVisible(false);
        task.setVisible(false);
        addTask.setVisible(false);
        deleteTask.setVisible(false);
        toDoHelp.setVisible(false);
        toDoHelp.setDisable(true);

        // putting saved data onto the table.
        if (!SWFT.nullAccount){
            ArrayList<String> userData = SWFT.currentUser.getToDoList();
            if (userData!=null) {
                for (int n = 0; n < userData.size(); n++) {
                    ToDoTable.getItems().add(new ToDo(userData.get(n)));
                }
            }
        }
    }

    /**
     * The changeCellEvent method sets the column to the changed value. 
     * It allows users to double click on a cell and update it.
     * 
     * @param edditedCell       This is the spot on the table that wil be eddited.
     */
    public void changeCellEvent(CellEditEvent edditedCell) {
        ToDo selected = ToDoTable.getSelectionModel().getSelectedItem();
        String columnSelect = (String) edditedCell.getNewValue();
        selected.setText(edditedCell.getNewValue().toString());
    }

    /**
     * The addToDo method adds the to-do text to the table.
     */
    @FXML
    public void addToDo() {
        String savedValue = task.getText();
        ToDo newValue = new ToDo(savedValue);

        // add new values to the table
        ToDoTable.getItems().add(newValue);
        task.clear();

        ObservableList<ToDo> items = ToDoTable.getItems();

        ArrayList<String> updatedTable = new ArrayList<String>();
        for (int n = 0; n < items.size(); n++) {
            updatedTable.add(items.get(n).getText());
        }

        // update user data.
        SWFT.currentUser.setToDoList(updatedTable);

    }

    /**
     * The doneTask method removes the to-do task from the table.
     */
    @FXML
    public void doneTask() {
        ObservableList<ToDo> selectedRows, allStrings;

        // total items
        allStrings = ToDoTable.getItems();

        // gives rows selected
        selectedRows = ToDoTable.getSelectionModel().getSelectedItems();

        // loop through the selected rows and remove the objects from the table.
        if (allStrings.size() == 1) {
            allStrings.clear();
        } else {
            for (ToDo string : selectedRows) {
                allStrings.remove(string);
            }
        }

        ObservableList<ToDo> items = ToDoTable.getItems();

        ArrayList<String> updatedTable = new ArrayList<String>();
        for (int n = 0; n < items.size(); n++) {
            updatedTable.add(items.get(n).getText());
        }

        // update user data.
        SWFT.currentUser.setToDoList(updatedTable);

    }

    /**
     * The toggleButton method toggles the todo display.
     */
    @FXML
    private void toggleButton() {

        if (displayToDo.isVisible() && ToDoTable.isVisible() && task.isVisible() && addTask.isVisible()
                && deleteTask.isVisible()) {

            ToDoTable.setVisible(false);
            task.setVisible(false);
            addTask.setVisible(false);
            deleteTask.setVisible(false);
            toDoHelp.setVisible(false);
            toDoHelp.setDisable(true);

        } else {
            ToDoTable.setVisible(true);
            task.setVisible(true);
            addTask.setVisible(true);
            deleteTask.setVisible(true);
            toDoHelp.setVisible(true);
            toDoHelp.setDisable(false);
        }
        setBackgroundWithTable(); 
    }

    /**
     * The boxSetUp method sets up the box(cube) by setting its dimension, its matierials, 
     * reflection, transformation, and intialize the mouse control on the box.
     */
    private void boxSetUp() {

        // default position of the box
        box.translateXProperty().bind(posX);
        box.translateYProperty().bind(posY);

        // default size
        box.setWidth(30);
        box.setHeight(30);
        box.setDepth(30);

        // default texture
        PhongMaterial material = new PhongMaterial(Color.rgb(224, 194, 177));
        material.setDiffuseMap(new Image(getClass().getResourceAsStream("texture.jpeg")));
        material.setSpecularColor(Color.rgb(100, 100, 100));
        box.setMaterial(material);

        // default transformation
        Transform defaultTransform = new Rotate(65, new Point3D(1, 1, 1));
        box.getTransforms().add(defaultTransform);

        // mouse control set up
        initMouseControl();
    }

    /**
     * The mouseOn method triggers an animation on the cube when the mouse is placed on it.
     */
    @FXML
    private void mouseOn() {
        ScaleTransition transition = new ScaleTransition(Duration.seconds(0.3));
        transition.setNode(box);
        transition.setToX(7.5);
        transition.setToY(7.5);
        transition.setToZ(7.5);
        transition.play();
    }

    /**
    * The mouseOn method triggers an animation on the cube when the mouse is moved off it.
    */
    @FXML
    private void mouseOff() {
        ScaleTransition transition = new ScaleTransition(Duration.seconds(0.3));
        transition.setNode(box);
        transition.setToX(1);
        transition.setToY(1);
        transition.setToZ(1);
        transition.play();
    }

    /**
     * The initMouseController intializes the rotational axes on the cube.
     */
    private void initMouseControl() {
        Rotate xRotate;
        Rotate yRotate;
        box.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS));
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);
    }

    /**
     * The mousePressed method detects if the mouse has been left clicked or right clicked.
     * 
     * If the mouse is left clicked, the angle of the current rotation of the cube will be saved.
     * If the mouse is right clicked, the distance betweeen the position of the mouse and the 
     * distance to teh centre of the cube will be saved.
     * 
     * @param event     the mouse event that has occured.
     */
    @FXML
    private void mousePressed(MouseEvent event) {
        if (event.isPrimaryButtonDown()) { // press left mouse button
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        } else if (event.isSecondaryButtonDown()) { // press right mouse button
            mouseXToBox = box.translateXProperty().get() - event.getScreenX();
            mouseYToBox = box.translateYProperty().get() - event.getScreenY();
        }
    }

    /**
     * The mousePressed method detects if the mouse has been held down on the left 
     * button or the right button.
     * 
     * If the mouse is held down on the left button, the angle of the new rotation
     * will be set based on the movement of the mouse.
     * If the mouse is held down on the right button, the cube will be repositioned 
     * based on the position of the mouse.
     * 
     * @param event     the mouse event that has occured.
     */
    @FXML
    private void mouseDragged(MouseEvent event) {
        if (event.isPrimaryButtonDown()) { // hold left mouse button
            double newAngleX = anchorAngleX - (anchorY - event.getSceneY());
            double newAngleY = anchorAngleY + anchorX - event.getSceneX();
            angleX.set(newAngleX);
            angleY.set(newAngleY);
        } else if (event.isSecondaryButtonDown()) { // hold right mouse button
            posX.set(event.getScreenX() + mouseXToBox);
            posY.set(event.getScreenY() + mouseYToBox);
        }
    }

    /**
     * The commented code below implements animations, transitions, and rotations on buttons 
     * that corresponds to those on the cube.
     * 
     * The scaling animation, transition and rotation codes are fully finished.
     * The repositioning of the button when rotating is partially finished with Maths formula provided, 
     * but not properly implemented onto the button.
     * 
     * This code is commented out due to the time constraint and the complexity of the task.
     */
    // // calculate translational distance / 2d new location based on trig and angle
    // // of rotation
    // private void buttonTransRotate(Button button, double newAngleX, double newAngleY){

        // // distances
        // double xDis = button.getLayoutX() - box.getLayoutX();
        // double yDis = button.getLayoutY() - box.getLayoutY();
        // double zDis = box.getDepth()/2;

        // double distance = Math.pow((Math.pow(xDis, 2) + Math.pow(yDis, 2) +
        // Math.pow(zDis, 2)), 1/2);

        // // given angle of rotation on the x and y axes
        // double transX = distance * Math.tan(Math.toRadians(newAngleX));
        // double transY = distance * Math.tan(Math.toRadians(newAngleY));
        // }

        // private void allButtonTransRotate(double newAngleX, double newAngleY) {
        // buttonTransRotate(switchToTimer, newAngleX, newAngleY);
        // }

        // private void buttonTransRotate(Button button, double newAngleX, double
        // newAngleY) { // calculate translational
        // distance / 2d new location
        // based on trig and angle of
        // rotation

        // double xDis = button.getLayoutX() - box.getLayoutX();
        // double yDis = button.getLayoutY() - box.getLayoutY();
        // double zDis = box.getDepth() / 2;

        // double oldX = button.getLayoutX();
        // double oldY = button.getLayoutY();

        // button.setLayoutX(oldX + transX);
        // button.setLayoutY(oldY + transY);

    // }

    // private int[] node0 = {-50, -50, -50};
    // private int[] node1 = {50, -50, 50};
    // private int[] node2 = {-50, 50, -50};
    // private int[] node3 = {-50, 50, 50};
    // private int[] node4 = { 50, -50, -50};
    // private int[] node5 = { 50, -50, 50};
    // private int[] node6 = { 50, 50, -50};
    // private int[] node7 = { 50, 50, 50};
    // private int[][] nodes = {node0, node1, node2, node3, node4, node5, node6,
    // node7};

    // private void rotateX3D(double angle){
        // double sinTheta = Math.sin(angle);
        // double cosTheta = Math.cos(angle);
        // for (int n = 0; n < nodes.length; n++) {
            // var node = nodes[n];
            // int y = node[1];
            // int z = node[2];
            // node[1] = (int) (y * cosTheta - z * sinTheta);
            // node[2] = (int) (z * cosTheta + y * sinTheta);
        // }
    // }

    // private ScaleTransition buttonAnimation(Button button, int state){
        // ScaleTransition transition = new ScaleTransition(Duration.seconds(0.3));
        // transition.setNode(button);
        // if (state == 0){
            // transition.setToX(1);
            // transition.setToY(1);
            // transition.setToZ(1);
        // } else if (state == 1){
            // transition.setToX(0.1);
            // transition.setToY(0.1);
            // transition.setToZ(0.1);
        // }
        // return transition;
    // }

    // private void buttonRotation(Button button){

        // int switchNum;
        // Transform defaultTransform;
        // Rotate xRotate;
        // Rotate yRotate;

        // if (button.equals(switchToTimer)){
            // switchNum = 1;
        // } else{
            // switchNum = 0;
        // }

        // switch(switchNum){
            // case 0:
                // defaultTransform = new Rotate(65, new Point3D(1, 1, 1)); // need to change
                // parameters
                // switchToTimer.getTransforms().add(defaultTransform);
                // break;
            // case 1:
                // defaultTransform = new Rotate(65, new Point3D(1, 1, 1));
                // switchToTimer.getTransforms().add(defaultTransform);
                // break;
        // }

        // button.getTransforms().addAll(
        // xRotate = new Rotate(0, Rotate.X_AXIS),
        // yRotate = new Rotate(0, Rotate.Y_AXIS));
        // xRotate.angleProperty().bind(angleX);
        // yRotate.angleProperty().bind(angleY);

        // button.translateXProperty().bind(posX);
        // button.translateYProperty().bind(posY);

    // }

    // private void buttonSetUp(){
        // buttonRotation(switchToTimer);

    // }

    /**
     * The initialize method runs at the very start of the program.
     * 
     * It sets up the box and the table, sets the button-pressed style 
     * for the buttons, and sets the tutorial's messages.
     */
    @FXML
    public void initialize() {
        boxSetUp();
        tableSetUp();
        SWFT.buttonPressed(pauseAudio);
        SWFT.buttonPressed(pauseMedia);
        SWFT.buttonPressed(mode);
        timeTutorial.setPane("Welcome to the Main Screen! " +
                "This is the screen that will be the background while you study. " +
                "Here is where you can customize your background screen, " +
                "background colors, and music to your own prefrences. " +
                "Get ready to start studying! ",
                mainHelp, "LEFT", "DOWN");
        timeTutorial.setPane("Add your task into the text field," +
                "Then click add to do and you will see your task!" +
                "when you finish the task click on it and then click done task" +
                "the task will then disappear!" +
                "if you need to edit a task, double click on the task to change it." +
                "click on the TODO button to see the table.", toDoHelp, "RIGHT", "DOWN");
        timeTutorial.setPane("This cube is for aethetic purposes. \nLeft click to rotate the cube. \nRight click to drag the cube to a new position.", cubeHelp, "RIGHT", "DOWN");
    }
}
