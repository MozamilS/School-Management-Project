package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ClassObject {
    private SimpleIntegerProperty ID;
    private SimpleStringProperty Code;
    private SimpleStringProperty Name;
    private SimpleIntegerProperty SeatsAvailable;
    private SimpleIntegerProperty SeatsTaken;

    public ClassObject(int ID, String Code, String Name, int SeatsAvailable, int SeatsTaken){
        this.ID = new SimpleIntegerProperty(ID);
        this.Code = new SimpleStringProperty(Code);
        this.Name = new SimpleStringProperty(Name);
        this.SeatsAvailable = new SimpleIntegerProperty(SeatsAvailable);
        this.SeatsTaken = new SimpleIntegerProperty(SeatsTaken);
    }

    public int getID() {
        return ID.get();
    }

    public void setID(int ID) {
        this.ID = new SimpleIntegerProperty(ID);
    }

    public String getCode() {
        return Code.get();
    }


    public void setCode(String code) {
        this.Code = new SimpleStringProperty(code);
    }

    public String getName() {
        return Name.get();
    }

    public void setName(String name) {
        this.Name = new SimpleStringProperty(name);
    }

    public int getSeatsAvailable() {
        return SeatsAvailable.get();
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.SeatsAvailable = new SimpleIntegerProperty(seatsAvailable);
    }

    public int getSeatsTaken() {
        return SeatsTaken.get();
    }

    public void setSeatsTaken(int seatsTaken) {
        this.SeatsTaken = new SimpleIntegerProperty(seatsTaken);
    }
}
