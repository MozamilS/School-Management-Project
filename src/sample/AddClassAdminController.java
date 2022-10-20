package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddClassAdminController implements Initializable {
    ObservableList<ClassObject> observableList = FXCollections.observableArrayList();

    public void addObservableListItems(){
        try {
            observableList.clear();
            connectionTest conn2 = new connectionTest();
            Connection conn = conn2.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM classes");
            while (result.next()){
                int id = Integer.parseInt(result.getString("class_id"));
                String code = result.getString("class_code");
                String class_name = result.getString("class_name");
                int class_capacity = Integer.parseInt(result.getString("class_capacity"));
                int seatsAvailable = class_capacity - Integer.parseInt(result.getString("class_seats_taken"));
                observableList.add(new ClassObject(id, code,class_name,class_capacity,seatsAvailable));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void FindAndUpdateObservableListItemsAddClass(String name){
        try {
            observableList.clear();
            connectionTest conn2 = new connectionTest();
            Connection conn = conn2.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM classes WHERE class_code = '"+ name + "';");
            while (result.next()){
                int id = Integer.parseInt(result.getString("class_id"));
                String code = result.getString("class_code");
                String class_name = result.getString("class_name");
                int class_capacity = Integer.parseInt(result.getString("class_capacity"));
                int seatsAvailable = class_capacity - Integer.parseInt(result.getString("class_seats_taken"));
                observableList.add(new ClassObject(id, code,class_name,class_capacity,seatsAvailable));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private TableView<ClassObject> classesTable;
    @FXML
    private TableColumn<ClassObject, Integer> colID;
    @FXML
    private TableColumn<ClassObject, String> colClassCode;
    @FXML
    private TableColumn<ClassObject, String> colClassName;
    @FXML
    private TableColumn<ClassObject, Integer> colCapacity;
    @FXML
    private TableColumn<ClassObject, Integer> colSeatsLeft;
    @FXML
    private TextField Search;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colClassCode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        colClassName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("SeatsAvailable"));
        colSeatsLeft.setCellValueFactory(new PropertyValueFactory<>("SeatsTaken"));
        classesTable.setItems(observableList);
    }

    public void AddButtonAction(ActionEvent actionEvent) {
        try{
            int index = classesTable.getSelectionModel().getSelectedIndex();
            String class_id = colID.getCellData(index).toString();
            int class_availableSeats = colSeatsLeft.getCellData(index);

            if (class_availableSeats != 0){
                connectionTest conn2 = new connectionTest();
                Connection conn = conn2.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery("SELECT * FROM currentUser");
                String student_username = null;
                String student_id = null;
                if (result.next()){
                    student_username = result.getString("username");
                }
                ResultSet result2 = stmt.executeQuery("SELECT * FROM students WHERE student_username = '" + student_username + "';");
                if (result2.next()) {
                    student_id = result2.getString("student_id");
                }
                ResultSet result3 = stmt.executeQuery("SELECT * FROM registration WHERE class_id = '" + class_id + "' AND student_id = '" + student_id + "';");
                if (result3.next()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message box");
                    alert.setHeaderText("Alert");
                    alert.setContentText("This class has already been registered!");
                    alert.show();
                }
                else {
                    stmt.executeUpdate("INSERT INTO registration VALUES('" + class_id + "', '" + student_id + "');");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message box");
                    alert.setHeaderText("Update");
                    alert.setContentText("Class Registered successfully!");
                    alert.show();
                }
                System.out.println("student id is: " + student_id + "\n" + "class ID is: " + class_id);
            }
            else{

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message box");
                alert.setHeaderText("Alert");
                alert.setContentText("The class has no seats available!");
                alert.show();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void SearchButtonAction(ActionEvent actionEvent) {
        FindAndUpdateObservableListItemsAddClass(Search.getText());

    }

    public void RefreshButtonAction(ActionEvent actionEvent) {
        addObservableListItems();
    }

    public void BackButtonAction(ActionEvent actionEvent) {
        loadStage("RegisteredClasses.fxml", actionEvent);
    }

    private void loadStage(String fxml, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
