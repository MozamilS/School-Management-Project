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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentsController implements Initializable {
    @FXML private TableColumn colID;
    @FXML private TableColumn colUsername;
    @FXML private TableColumn colFirstName;
    @FXML private TableColumn colLastName;
    @FXML private TableColumn colLevel;
    @FXML private TableView studentsTable;
    @FXML private TextField Search;
    ObservableList<StudentObject> observableList = FXCollections.observableArrayList();

    public void addObservableListItems(){
        try {
            observableList.clear();
            connectionTest conn2 = new connectionTest();
            Connection conn = conn2.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM students");
            while (result.next()){
                int id = Integer.parseInt(result.getString("student_id"));
                String code = result.getString("student_username");
                String class_name = result.getString("student_first_name");
                String class_capacity = result.getString("student_last_name");
                String seatsAvailable = result.getString("student_level");
                observableList.add(new StudentObject(id, code,class_name,class_capacity,seatsAvailable));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void FindAndUpdateObservableListItems(String name){
        try {
            observableList.clear();
            connectionTest conn2 = new connectionTest();
            Connection conn = conn2.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM students WHERE student_username = '"+ name + "';");
            while (result.next()){
                int id = Integer.parseInt(result.getString("student_id"));
                String code = result.getString("student_username");
                String class_name = result.getString("student_first_name");
                String class_capacity = result.getString("student_last_name");
                String seatsAvailable = result.getString("student_level");
                observableList.add(new StudentObject(id, code,class_name,class_capacity,seatsAvailable));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("Level"));
        studentsTable.setItems(observableList);
    }
    public void SearchButtonAction(ActionEvent actionEvent) {
        FindAndUpdateObservableListItems(Search.getText());
    }

    public void BackButtonAction(ActionEvent actionEvent) {
        loadStage("AdminDashboard.fxml", actionEvent);
    }

    public void RefreshListButtonAction(ActionEvent actionEvent) {
        addObservableListItems();
    }
    private void loadStage(String fxml, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
