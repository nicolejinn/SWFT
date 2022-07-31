import javafx.beans.property.SimpleStringProperty;
import java.io.Serializable;

/**
 * This is the ToDo class, which gets and set the values from the table.
 * 
 * @author Elizabeth Caporiccio
 * @version 2021-11-26
 */

public class ToDo implements Serializable {

    private SimpleStringProperty text;

    /**
     * The ToDo method is the constructor of the ToDo class.
     * 
     * @param text     the text that will be passed through the table.
     */
    public ToDo(String text) {
        this.text = new SimpleStringProperty(text);
    }

    /**
     * The getText method gets the text from the table.
     * 
     * @return text     the text in the table.
     */
    public String getText() {
        return text.get();
    }

    /**
     * The setText sets the text in the table.
     * 
     * @param text      the text(task) from the user.
     */
    public void setText(String text) {
        this.text = new SimpleStringProperty(text);
    }
}
