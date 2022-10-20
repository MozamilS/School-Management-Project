package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentObject {
    private SimpleIntegerProperty ID;
    private SimpleStringProperty Username;
    private SimpleStringProperty FirstName;
    private SimpleStringProperty LastName;
    private SimpleStringProperty Level;

    public int getID() {
        return ID.get();
    }

    public void setID(int ID) {
        this.ID = new SimpleIntegerProperty(ID);
    }

    public String getUsername() {
        return Username.get();
    }


    public void setUsername(String username) {
        this.Username = new SimpleStringProperty(username);
    }

    public String getFirstName() {
        return FirstName.get();
    }

    public void setFirstName(String firstName) {
        this.FirstName = new SimpleStringProperty(firstName);
    }

    public String getLastName() {
        return LastName.get();
    }

    public void setLastName(String lastName) {
        this.LastName = new SimpleStringProperty(lastName);
    }

    public String getLevel() {
        return Level.get();
    }

    public void setLevel(String level) {
        this.Level = new SimpleStringProperty(level);
    }

    public StudentObject(int ID, String Username, String FirstName, String LastName, String Level){
        this.ID = new SimpleIntegerProperty(ID);
        this.Username = new SimpleStringProperty(Username);
        this.FirstName = new SimpleStringProperty(FirstName);
        this.LastName = new SimpleStringProperty(LastName);
        this.Level = new SimpleStringProperty(Level);
    }
}
