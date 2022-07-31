import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This is the WriteToFile class that writes the user data to the external file
 * where it is stored.
 * 
 * @author Nicole Jin 
 * @version 2021-12-17
 */
public class WriteToFile {

    /**
     * The storeUserData file gets the updated list of data(objects) that needs to be stored, 
     * and updates the external file by erasing all previous data, and writes the new updated data.
     * 
     * @throws IOException              if there is a failure in I/O operations.
     * @throws ClassNotFoundException   when the program tries to load a class through 
     *                                  its fully-qualified name and can not find its definition 
     *                                  on the classpath.
     */
    public static void storeUserData() throws IOException, ClassNotFoundException {

        String outputFileName = "UserData.txt";

        FileOutputStream fileOut = new FileOutputStream(new File(outputFileName));
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

        // get the updated list of data(objects)
        ArrayList<UserData> dataLst = SWFT.getAllData();
        objectOut.reset();
        for (int i = 0; i < dataLst.size(); i++) {
            objectOut.writeObject(dataLst.get(i));
        }

        fileOut.close();
        objectOut.close();
    }
}
