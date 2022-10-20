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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisteredClassesController implements Initializable {

    @FXML
    private TableColumn colID;
    @FXML private TableColumn colClassName;
    @FXML private TableColumn colClassCode;
    @FXML private TableColumn colCapacity;
    @FXML private TableColumn colSeatsLeft;
    @FXML private TableView classesTable;

    ObservableList<ClassObject> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colClassCode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        colClassName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("SeatsAvailable"));
        colSeatsLeft.setCellValueFactory(new PropertyValueFactory<>("SeatsTaken"));
        classesTable.setItems(observableList);
    }

    public void addObservableListItems(){
        try {
            String username = null; 
            observableList.clear();
            connectionTest conn2 = new connectionTest();
            Connection conn = conn2.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet result2 = stmt.executeQuery("SELECT * FROM currentUser;");
            if(result2.next()){
                username = result2.getString("username");
            }
            ResultSet result = stmt.executeQuery("SELECT classes.class_id, class_code, class_name, class_capacity, class_seats_taken FROM classes RIGHT JOIN registration ON classes.class_id = registration.class_id LEFT JOIN students ON students.student_id = registration.student_id WHERE students.student_username = '" + username + "';");
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

    public void RemoveRegisteredClass(){
        try {
            int index = classesTable.getSelectionModel().getSelectedIndex();
            String class_id = colID.getCellData(index).toString();

            try{
                connectionTest conn2 = new connectionTest();
                String student_username = null;
                String student_id = null;
                Connection conn = conn2.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery("SELECT * FROM currentUser");
                if (result.next()){
                    student_username = result.getString("username");
                }
                ResultSet result2 = stmt.executeQuery("SELECT * FROM students WHERE student_username = '" + student_username + "';");
                if (result2.next()){
                    student_id = result2.getString("student_id");
                }
                stmt.executeUpdate("DELETE FROM registration WHERE class_id ='" + class_id + "' AND student_id = '"+ student_id +"';");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message box");
                alert.setHeaderText("Update");
                alert.setContentText("Class registration removed!");
                alert.show();
                addObservableListItems();
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message box");
            alert.setHeaderText("Alert");
            alert.setContentText("No item has been selected!");
            alert.show();
        }


    }

    public void BackButtonAction(ActionEvent actionEvent) {
        loadStage("StudentDashboard.fxml", actionEvent);
    }

    public void AddClassButtonAction(ActionEvent actionEvent) {
        loadStage("AddClassAdmin.fxml", actionEvent);
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

    public void RefreshListButtonAction(ActionEvent actionEvent) {
        addObservableListItems();
    }

    public void RemoveClassButtonAction(ActionEvent actionEvent) {
        RemoveRegisteredClass();

    }
}
