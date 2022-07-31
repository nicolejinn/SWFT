import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * This is the Timer class that creates the timer. Upon the button pressed, the time is started 
 * and counts down until zero, then an alarm goes off.
 * There are different buttons for the user to select whether they want to
 * customize the timer or to use the preset Pomodoro timer.
 * When the alarm goes off, a button pops up, which allows the user to stop the alarm.
 * 
 * @author      Sashwati Sanjay
 * @version     2022/01/03
 * @url         Source Help: https://asgteach.com/2011/10/javafx-animation-and-binding-simple-countdown-timer-2/
 */
public class Timer {

    // constants and variables
    private static final int POMTime = 25;
    private Timeline timeline;
    private Integer timeSeconds = POMTime * 60;
    private Integer timeMinutes = POMTime;
    private Media alarmAudio = new Media(getClass().getResource("beep.mp3").toExternalForm());
    private MediaPlayer alarmPlayer = new MediaPlayer(alarmAudio);
    Tutorial timeTutorial = new Tutorial();


    /** 
     * The setPOMTimer method contains the Event handler for the pomButton, 
     * which, on action, sets the timer to the pomodoro timer values, ie POMTime.
     * 
     * @param pomButton         the button that sets the timer to the pomodoro time.
     * @param timerButton       the button that starts the timer.
     * @param customButton      the button that set the timer to allow user input.
     * @param breakButton       the button that sets the timer to break timer.
     * @param stopButton        the button that stops the alardm.
     * @param titleLabel        the title of the current timer.
     * @param minutesLabel      the minutes display.
     * @param secondsLabel      the seconds display.
     * @param incorrectInput    the incorrect input display.
     * @param minutesInput      the textfield for minutes.
     * @param secondsInput      the textfield for seconds.
    */
    private void setPOMTimer(Button pomButton, Button timerButton, Button customButton, Button breakButton,
            Button stopButton, Label titleLabel, Label minutesLabel, Label secondsLabel, Label incorrectInput,
            TextField minutesInput, TextField secondsInput) {
        pomButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                titleLabel.setText("Pomodoro Timer");
                pomButton.setText("Reset Timer");
                timerButton.setDisable(false);
                customButton.setText("Customize");
                breakButton.setText("Break");
                stopButton.setVisible(false);
                stopButton.setDisable(true);
                if (timeline != null) {
                    timeline.stop();
                }
                if (secondsInput.isVisible() || minutesInput.isVisible() || incorrectInput.isVisible()) {
                    secondsInput.setVisible(false);
                    minutesInput.setVisible(false);
                    incorrectInput.setVisible(false);
                }
                timeMinutes = POMTime;
                timeSeconds = timeMinutes * 60;
                secondsLabel.setText("" + timeSeconds % 60);
                minutesLabel.setText(timeMinutes.toString());
                alarmPlayer.stop();
            }
        });
    }

    /**
     * The setBreakTime method contains the event handle for the break 
     * button which sets the timer to the break time on action.
     *  
     * @param pomButton         the button that sets the timer to the pomodoro time.
     * @param timerButton       the button that starts the timer.
     * @param customButton      the button that set the timer to allow user input.
     * @param breakButton       the button that sets the timer to break timer.
     * @param stopButton        the button that stops the alarm.
     * @param titleLabel        the title of the current timer.
     * @param minutesLabel      the minutes display.
     * @param secondsLabel      the seconds display.
     * @param incorrectInput    the incorrect input display.
     * @param minutesInput      the textfield for minutes.
     * @param secondsInput      the textfield for seconds.
     */
    private void setBreakTimer(Button pomButton, Button timerButton, Button customButton, Button breakButton,
            Button stopButton, Label titleLabel, Label minutesLabel, Label secondsLabel, Label incorrectInput,
            TextField minutesInput, TextField secondsInput) {
        breakButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                titleLabel.setText("Break Time");
                breakButton.setText("Reset Timer");
                pomButton.setText("Pomodoro");
                timerButton.setDisable(false);
                customButton.setText("Customize");
                stopButton.setVisible(false);
                stopButton.setDisable(true);
                if (timeline != null) {
                    timeline.stop();
                }
                if (secondsInput.isVisible() || minutesInput.isVisible() || incorrectInput.isVisible()) {
                    secondsInput.setVisible(false);
                    minutesInput.setVisible(false);
                    incorrectInput.setVisible(false);
                }
                timeMinutes = 5;
                timeSeconds = timeMinutes * 60;
                secondsLabel.setText("" + timeSeconds % 60);
                minutesLabel.setText(timeMinutes.toString());
                alarmPlayer.stop();
            }
        });
    }

    /**
     * The setCustomTimer method contains the event handler for the customizable timer, 
     * which, on action, set the textfields visible
     * 
     * @param pomButton         the button that sets the timer to the pomodoro time.
     * @param timerButton       the button that starts the timer.
     * @param customButton      the button that set the timer to allow user input.
     * @param breakButton       the button that sets the timer to break timer.
     * @param stopButton        the button that stops the alarm.
     * @param titleLabel        the title of the current timer.
     * @param minutesLabel      the minutes display.
     * @param secondsLabel      the seconds display.
     * @param incorrectInput    the incorrect input display.
     * @param minutesInput      the textfield for minutes.
     * @param secondsInput      the textfield for seconds.
     */
    private void setCustomTimer(Button pomButton, Button timerButton, Button customButton, Button breakButton,
            Button stopButton, Label titleLabel, Label minutesLabel, Label secondsLabel, Label incorrectInput,
            TextField minutesInput, TextField secondsInput) {
        customButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                titleLabel.setText("Custom Timer");
                timerButton.setDisable(false);
                pomButton.setText("Pomodoro");
                breakButton.setText("Break");
                customButton.setText("Reset Timer");
                stopButton.setVisible(false);
                stopButton.setDisable(true);
                if (timeline != null) {
                    timeline.stop();
                }
                if (!secondsInput.isVisible() || !minutesInput.isVisible()) {
                    secondsInput.setVisible(true);
                    minutesInput.setVisible(true);
                    incorrectInput.setVisible(false);
                }
                alarmPlayer.stop();
            }
        });
    }

    /**
     * The stopAlarm method stops the alarm if it's playing.
     * 
     * @param stopButton    the button that stops the alarm.
     */
    private void stopAlarm(Button stopButton) {
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stopButton.setVisible(false);
                stopButton.setDisable(true);
                alarmPlayer.stop();
            }
        });
    }

    /**
     * The startTimerButton method, on action, starts the timer. 
     * If the timer was customized previously, it checks in the input is correct before starting.
     * 
     * @param pomButton         the button that sets the timer to the pomodoro time.
     * @param timerButton       the button that starts the timer.
     * @param customButton      the button that set the timer to allow user input.
     * @param breakButton       the button that sets the timer to break timer.
     * @param stopButton        the button that stops the alarm.
     * @param titleLabel        the title of the current timer.
     * @param minutesLabel      the minutes display.
     * @param secondsLabel      the seconds display.
     * @param incorrectInput    the incorrect input display.
     * @param minutesInput      the textfield for minutes.
     * @param secondsInput      the textfield for seconds.
     */
    private void startTimerButton(Button pomButton, Button timerButton, Button customButton, Button breakButton,
            Button stopButton, Label titleLabel, Label minutesLabel, Label secondsLabel, Label incorrectInput,
            TextField minutesInput, TextField secondsInput) {
        timerButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                timerButton.setDisable(true);
                stopButton.setVisible(false);
                stopButton.setDisable(true);
                if (timeline != null) {
                    timeline.stop();
                }
                if (secondsInput.isVisible() || minutesInput.isVisible()) {
                    try {
                        timeMinutes = Integer.valueOf(minutesInput.getText());
                        timeSeconds = (timeMinutes * 60) + Integer.valueOf(secondsInput.getText());
                    } catch (NumberFormatException e) {
                        timerButton.setDisable(false);
                        incorrectInput.setVisible(true);
                        minutesInput.setText("" + 0);
                        secondsInput.setText("" + 0);
                        secondsInput.setVisible(true);
                        minutesInput.setVisible(true);
                    }
                    if (Integer.valueOf(secondsInput.getText()) < 0 || Integer.valueOf(secondsInput.getText()) >= 60
                            || Integer.valueOf(minutesInput.getText()) < 0
                            || Integer.valueOf(minutesInput.getText()) >= 60) {
                        incorrectInput.setVisible(true);
                        timerButton.setDisable(false);
                        minutesInput.setText("" + 0);
                        secondsInput.setText("" + 0);
                        secondsInput.setVisible(true);
                        minutesInput.setVisible(true);
                    }

                    timeMinutes = Integer.valueOf(minutesInput.getText());
                    timeSeconds = (timeMinutes * 60) + Integer.valueOf(secondsInput.getText());
                    secondsLabel.setText("" + timeSeconds % 60);
                    minutesLabel.setText(timeMinutes.toString());
                    secondsInput.setVisible(false);
                    minutesInput.setVisible(false);
                    incorrectInput.setVisible(false);
                }
                timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                if (timeSeconds > 0) {
                                    timeSeconds--;
                                    // update seconds
                                    secondsLabel.setText("" + timeSeconds % 60);
                                } else if (incorrectInput.isVisible() == false) {
                                    alarmPlayer.play();
                                    alarmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                                    timeline.stop();
                                    stopButton.setVisible(true);
                                    stopButton.setDisable(false);
                                }
                                if (timeSeconds % 60 == 59) {
                                    timeMinutes--;
                                    minutesLabel.setText(timeMinutes.toString());
                                }
                            }
                        }));
                        
                    // makes sure that the user can't input 0 as a timer
                    if (timeMinutes == 0 && timeSeconds == 0) {
                        alarmPlayer.stop();
                        incorrectInput.setVisible(true);
                        timerButton.setDisable(false);
                        minutesInput.setText("" + 0);
                        secondsInput.setText("" + 0);
                        secondsInput.setVisible(true);
                        minutesInput.setVisible(true);
                    }
                        stopAlarm(stopButton);
                        timeline.playFromStart();
            }
        });

    }
    /**
     * This initialize method runs when the scene is initialized. 
     * It creates a new stage for the timer, with all the features related to it, 
     * such as the buttons, the pane, and the labels.
     * 
     * @throws IOException              if there is a failure in I/O operations.
     */

    public void init() throws IOException {

        // Setup the Stage and the Scene
        Stage timerStage = new Stage();
        timerStage.setTitle("Timer");
        Scene scene = new Scene(SWFT.loadFXML("timer"));
        TextField secondsInput = (TextField) scene.lookup("#secondsInput");
        TextField minutesInput = (TextField) scene.lookup("#minutesInput");
        Label secondsLabel = (Label) scene.lookup("#secondsLabel");
        Label minutesLabel = (Label) scene.lookup("#minutesLabel");
        Label titleLabel = (Label) scene.lookup("#titleLabel");
        Label incorrectInput = (Label) scene.lookup("#incorrectInput");
        Button pomButton = (Button) scene.lookup("#pomTimer");
        Button customButton = (Button) scene.lookup("#customTimer");
        Button timerButton = (Button) scene.lookup("#startTime");
        Button stopButton = (Button) scene.lookup("#stopTimer");
        Button breakButton = (Button) scene.lookup("#breakTimer");
        Pane timerHelp = (Pane) scene.lookup("#timerHelp");

        // Configure the Labels
        secondsLabel.setText(Integer.toString(timeSeconds % 60));
        minutesLabel.setText(timeMinutes.toString());
        titleLabel.setText("Pomodoro Timer");
        pomButton.setText("Reset Timer");
        customButton.setText("Customize");
        timerButton.setText("Start Timer");
        stopButton.setText("Stop Timer");
        breakButton.setText("Break");
        secondsInput.setVisible(false);
        minutesInput.setVisible(false);
        incorrectInput.setVisible(false);
        stopButton.setVisible(false);
        stopButton.setDisable(true);
        alarmPlayer.stop();

        // Create and configure the Buttons
        setPOMTimer(pomButton, timerButton, customButton, breakButton, stopButton, titleLabel, minutesLabel,
                secondsLabel, incorrectInput, minutesInput, secondsInput);

        setBreakTimer(pomButton, timerButton, customButton, breakButton, stopButton, titleLabel, minutesLabel,
                secondsLabel, incorrectInput, minutesInput, secondsInput);

        setCustomTimer(pomButton, timerButton, customButton, breakButton, stopButton, titleLabel, minutesLabel,
                secondsLabel, incorrectInput, minutesInput, secondsInput);

        startTimerButton(pomButton, timerButton, customButton, breakButton, stopButton, titleLabel, minutesLabel,
                secondsLabel, incorrectInput, minutesInput, secondsInput);        

        timeTutorial.setPane(
                "Use this timer to pace your studying!" +
                        " The pomodoro timer is set to the standard 25" +
                        " minutes of studying, after which you get a 5 minutes break." +
                        " However, you can customize your own timer based on your needs." +
                        " There will be an alarm after the timer ends.",
                timerHelp, "LEFT", "DOWN");
        timerStage.setScene(scene);
        timerStage.show();
    }
}
