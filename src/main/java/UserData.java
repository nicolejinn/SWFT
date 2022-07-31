import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is the UserData class that creates individual account as a user sign up.
 * 
 * @author      Nicole Jin 
 * @version     2021-12-17
 * @serial      to serialize all of the objects (stored data) so they can be
 *              stored into the external file as objects | include
 */
public class UserData implements Serializable {

    // 0 - username ; 1 - pwd ;
    String[] dataArr = new String[2];
    ArrayList<String> toDoList;

    /**
     * The UserData method is the contructor method of the UserData class. 
     * It sets the username and password of the object as inputted, and 
     * initializes the default string array that is used to store the username
     * and password.
     * 
     * @param username          the inputted username of the user.
     * @param pwd               the inputted password of the user.
     * @throws IOException      if there is a failure in I/O operations.
     */
    public UserData(String username, String pwd) throws IOException { 
        setUsername(username);
        setPwd(pwd);
        for (int i = 2; i < dataArr.length; i++) {
            dataArr[i] = null;
        }
    }

    /**
     * The setUsername method sets the username in the string array as inputted.
     * 
     * @param username     the inputted username of the user.
     */
    public void setUsername(String username) {
        dataArr[0] = username;
    }

    /**
     * The getUsername method gets the username from the string array of the user.
     * 
     * @return dataArr[0]  the usernaem of the user.
     */
    public String getUsername() {
        return dataArr[0];
    }

    /**
    * The setPwd method sets the password in the string array as inputted.
    * 
    * @param username     the inputted password of the user.
    */
    public void setPwd(String pwd) {
        dataArr[1] = pwd;
    }

    /**
     * The getPwd method gets the password from the string array of the user.
     * 
     * @return dataArr[1]  the password of the user.
     */
    public String getPwd() {
        return dataArr[1];
    }

    /**
    * The setToDoList method sets the to-do list of the user as inputted.
    * 
    * @param updatedTable     the inputted to-do list of the user.
    */
    public void setToDoList(ArrayList<String> updatedTable){
        toDoList = updatedTable;
    }

    /**
     * The getToDoList method gets the to-do list of the user.
     * 
     * @return toDoList  the to-do list of the user.
     */
    public ArrayList<String> getToDoList(){
        return toDoList;
    }
}
