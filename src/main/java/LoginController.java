import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
// import javafx.scene.effect.BoxBlur;
// import javafx.scene.effect.Effect;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * This is the LoginController class that controls the login scene.
 * 
 * @author Nicole Jin
 * @version 2021-12-17
 */
public class LoginController {

    // VBox
    @FXML
    private VBox backgroundVBox;
    @FXML
    private VBox mainVBox;
    @FXML
    private VBox vBox;

    // Label
    @FXML
    private Label displayTxt;

    // Text
    @FXML
    private Text infoTxt;

    // Username Field
    @FXML
    private TextField nameInTextField;

    // Password Fields
    @FXML
    private PasswordField pwdInField;
    @FXML
    private PasswordField confirmPwdInField;

    // Buttons
    @FXML
    private Button signUpButton;
    @FXML
    private Button confirmButton;

    // Strings
    private String username;
    private String pwd;

    // Booleans
    private boolean validName = false;
    private boolean validPwd = false;

    /**
     * Int
     * mode = 0 when the screen is switched to login screen
     * mode = 1 when the screen is switched to sign up screen.
     *  */ 
    private int mode = 0; 

    /**
     * The switchMode method switches mode between login mode and 
     * sign up mode.
     */
    @FXML
    private void switchMode() {
        switch (mode) {
            case 0:
                signUpSetUp();
                break;
            case 1:
                loginSetUp();
                break;
        }
    }

    /**
     * The loginSetUp method sets up and initializes the buttons and 
     * text messages when the stage generate the login scene in 
     * login mode.
     */
    private void loginSetUp() {
        confirmPwdInField.setVisible(false);
        signUpButton.setText("Don't have an account? Sign up here.");
        displayTxt.setText("Login Page");
        infoTxt.setText("");
        nameInTextField.setText("");
        pwdInField.setText("");
        mode = 0;
    }

    /**
     * The signUpSetUp method sets up and initializes the buttons and 
     * text messages when the stage generate the login scene in 
     * sign up mode.
     */
    private void signUpSetUp() {
        confirmPwdInField.setVisible(true);
        signUpButton.setText("Back to login");
        displayTxt.setText("Sign Up Page");
        infoTxt.setText("");
        nameInTextField.setText("");
        pwdInField.setText("");
        confirmPwdInField.setText("");
        mode = 1;
    }

    /**
     * The nameSignUp method gets the username inputted by the user in sign
     * up mode, and check if the username is valid.
     */
    private void nameSignUp() {
        String nameIn = nameInTextField.getText();
        if (nameIn.equals("")) {
            infoTxt.setText("Username invalid.");
        } else if (nameIn.indexOf(" ") != -1) {
            nameInTextField.setText("");
            infoTxt.setText("Your username cannot include space.");
        } else if (nameIn.length() < 6) {
            nameInTextField.setText("");
            infoTxt.setText("Your username needs at least 6 characters.");
        } else {
            username = nameIn;
            validName = true;
        }
    }

    /**
     * The pwdSignUp method gets the password inputted by the user in sign
     * up mode, and check if the password is valid.
     */
    private void pwdSignUp() {
        String pwdIn = pwdInField.getText();
        if (pwdIn.equals("")) {
            infoTxt.setText("Password invalid.");
        } else if (pwdIn.indexOf(" ") != -1) {
            pwdInField.setText("");
            confirmPwdInField.setText("");
            infoTxt.setText("Your password cannot include space.");
        } else if (pwdIn.length() < 6) {
            pwdInField.setText("");
            confirmPwdInField.setText("");
            infoTxt.setText("Your password needs at least 6 characters.");
        } else if (!pwdIn.equals(confirmPwdInField.getText())) {
            pwdInField.setText("");
            confirmPwdInField.setText("");
            infoTxt.setText("Confirm password is not matched up.");
        } else {
            pwd = pwdIn;
            validPwd = true;
        }
    }

    /**
     * The userSignUp method takes the valid username and password that
     * the user has inputted in sign up mode, and checked if there is identical username
     * already created. If the username is new, the user then successfully
     * created an account and is now able to log in to the main scene. 
     * Otherwise, the program will output error messages on the screen.
     * 
     * @throws IOException              if there is a failure in I/O operations.
     * @throws ClassNotFoundException   when the program tries to load a class through 
     *                                  its fully-qualified name and can not find its definition 
     *                                  on the classpath.
     */
    private void userSignUp() throws IOException, ClassNotFoundException {
        nameSignUp();
        pwdSignUp();
        if (validName && validPwd) {
            validName = false;
            validPwd = false;
            // write to external file
            ArrayList<String> usernameLst = new ArrayList<String>();

            for (UserData tempUser : SWFT.allDataLst) {
                usernameLst.add(tempUser.getUsername());
            }

            if (usernameLst.contains(username)) {
                infoTxt.setText("This username already exists.");
            } else {
                UserData userData = new UserData(username, pwd);
                SWFT.setCurrentUser(userData);
                SWFT.addCurrentUser();
                SWFT.storeData();
                SWFT.setRoot("main");
            }
        }
    }

    /**
     * The nameLogIn method gets the username inputted by the user in 
     * login mode.
     */
    private void nameLogIn() {
        username = nameInTextField.getText();
    }

    /**
     * The pwdLogIn method gets the password inputted by the user in 
     * login mode.
     */
    private void pwdLogIn() {
        pwd = pwdInField.getText();
    }

    /**
     * The userLogIn method takes the  username and password that the user has inputted
     * in login mode, and checked if there is a matching username and password in the stored 
     * data. If there is, then the program will switch to the correct account and switch 
     * to main scene. Otherwise, it will output error messages on the screen.
     * 
     * @throws IOException              if there is a failure in I/O operations.
     * @throws ClassNotFoundException   when the program tries to load a class through 
     *                                  its fully-qualified name and can not find its definition 
     *                                  on the classpath.
     */
    private void userLogIn() throws IOException, ClassNotFoundException {
        nameLogIn();
        pwdLogIn();

        if (SWFT.allDataLst.isEmpty()) {
            infoTxt.setText("Username or password is incorrect.");
        } else {

            ArrayList<String> usernameLst = new ArrayList<String>();
            ArrayList<String> pwdLst = new ArrayList<String>();

            for (UserData tempUser : SWFT.allDataLst) {
                usernameLst.add(tempUser.getUsername());
                pwdLst.add(tempUser.getPwd());
            }

            int userIndex = usernameLst.indexOf(username);

            if ((userIndex != -1) && (pwdLst.get(userIndex).equals(pwd))) {
                SWFT.setCurrentUser(SWFT.allDataLst.get(userIndex));
                SWFT.setRoot("main");
            } else {
                infoTxt.setText("Username or password is incorrect.");
            }
        }
    }

    /**
     * The switchToMain method reads all the previously stored user data first, then 
     * changes to different set up method based on different mode selected by the user.
     * 
     * @throws IOException              if there is a failure in I/O operations.
     * @throws ClassNotFoundException   when the program tries to load a class through 
     *                                  its fully-qualified name and can not find its definition 
     *                                  on the classpath.
     */
    @FXML
    private void switchToMain() throws IOException, ClassNotFoundException {
        SWFT.setAllData();
        if (mode == 0) {
            userLogIn();
        } else if (mode == 1) {
            userSignUp();
        }
    }

    /**
     * The skipToMain method allows the user to access the main scene without an account.
     * 
     * @throws IOException    if there is a failure in I/O operations.
     */
    @FXML
    private void skipToMain() throws IOException{
        SWFT.nullAccount = true;
        SWFT.setRoot("main");
    }

    /**
     * The textFieldSetUp method sets up a text field. 
     * It changes the style of the text field, and also 
     * enables the text field to  show the prompt text even when it is focused.
     * 
     * @param textFieldIn    the text field that needs to be set up.                    
     */
    private void textFieldSetUp(TextField textFieldIn) {
        textFieldIn.styleProperty().bind(
                Bindings
                        .when(textFieldIn.focusedProperty())
                        .then("-fx-prompt-text-fill: rgb(60, 48, 40), derive(-fx-control-inner-background, -30%);")
                        .otherwise(
                                "    -fx-prompt-text-fill: rgb(100, 100, 100), derive(-fx-control-inner-background, -30%);"));
    }
    /**
     * The backgroundSetUp method sets up the blurring effect of the background.
     * 
     * It is commented out due to the undersired effect of blurring the whole screen.
     */
    // private void backgroundSetUp(){ 
    //      Effect frostEffect = new BoxBlur(5, 5, 2);
    //      backgroundVBox.setEffect(frostEffect);
    // }

    /**
     * The initialize method is called when the scene is first built.
     * 
     * It sets the current user account to null, sets up all of the text fields, 
     * sets up the button pressed styles, and set up the login scene in default
     * login mode.
     */
    @FXML
    private void initialize() {
        SWFT.setCurrentUser(null);
        textFieldSetUp(nameInTextField);
        textFieldSetUp(pwdInField);
        textFieldSetUp(confirmPwdInField);
        SWFT.buttonPressed(confirmButton);
        SWFT.buttonPressed(signUpButton);
        loginSetUp();
    }
}
