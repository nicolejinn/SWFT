import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * This is the ReadFromFile class that reads the stored user data from the external file.
 * 
 * @author Nicole Jin 
 * @version 2021-12-17
 */
public class ReadFromFile {

    /**
     * The getUserData method gets all of the stored user data from the external file 
     * by calling the getObject method. This method accounts for several possible exceptions that can happen.
     * 
     * If EOFException or StreamCorruptedExeption exception is caught, 
     * the external file will clear itself and erase all of the data.
     * (These exceptions should only happen when there is no real user data,
     * but the file still contaisn a few trash data.)
     * 
     * @return userDataLst              a list of user data objects that was previously stored 
     *                                  in the external file. 
     * @throws IOException              if there is a failure in I/O operations.
     * @throws ClassNotFoundException   when the program tries to load a class through 
     *                                  its fully-qualified name and can not find its definition 
     *                                  on the classpath.
     */
    public static ArrayList<UserData> getUserData() throws IOException, ClassNotFoundException {

        String inputFileName = "UserData.txt";

        File file = new File(inputFileName);
        FileInputStream fileIn = new FileInputStream(file);

        ArrayList<UserData> userDataLst = new ArrayList<UserData>();

        if (file.length() != 0) {
            try{
                userDataLst = getObject(file, fileIn);
                fileIn.close();
            } catch(StreamCorruptedException | EOFException e){

                FileOutputStream fileOut = new FileOutputStream(file);
                fileOut.write(("").getBytes());
                fileOut.close();

                userDataLst = getObject(file, fileIn);
                fileIn.close();
            }
        }

        return userDataLst;
    }

     /**
     * The getObject method gets all of the stored data from the external file.
     * 
     * @param file                      the external file.
     * @param fileIn                    the FileInputStream that is used to access information from 
     *                                  the external file.
     * @return userDataLst              a list of user data objects that was previously stored 
     *                                  in the external file. 
     * @throws IOException              if there is a failure in I/O operations.
     * @throws ClassNotFoundException   when the program tries to load a class through 
     *                                  its fully-qualified name and can not find its definition 
     *                                  on the classpath.
     */
    private static ArrayList<UserData> getObject(File file, FileInputStream fileIn) throws IOException, ClassNotFoundException{
        
        ArrayList<UserData> userDataLst = new ArrayList<UserData>();

        if (file.length() != 0) {

            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            UserData tempObject = null;

            while (fileIn.available() != 0) { 
                tempObject = (UserData) objectIn.readObject();
                userDataLst.add(tempObject);
            }

            objectIn.close();
        }

        return userDataLst;
    }
}
