package sample;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ClassesController implements Initializable {
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

    public void FindAndUpdateObservableListItems(String name){
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

    public void RemoveData(){
        try{
            int Index = classesTable.getSelectionModel().getSelectedIndex();
            int id = colID.getCellData(Index);
            try{
                connectionTest conn2 = new connectionTest();
                Connection conn = conn2.getConnection();
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM classes WHERE class_id = " + id + ";");
                addObservableListItems();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message box");
                alert.setHeaderText("Update");
                alert.setContentText("Class removed!");
                alert.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message box");
            alert.setHeaderText("Alert");
            alert.setContentText("Attempted removing an unknown class!");
            alert.show();
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
    @FXML private TextField UpdateCode;
    @FXML private TextField UpdateName;
    @FXML private TextField UpdateCapacity;
    @FXML private TextField UpdateSeatsTaken;


    public void AddButtonAction(ActionEvent actionEvent) {
        try{
            String codetf = UpdateCode.getText();
            String nametf = UpdateName.getText();
            int seatsAvailable = Integer.parseInt(UpdateCapacity.getText());
            int seatsTaken = Integer.parseInt(UpdateSeatsTaken.getText());

            if (seatsAvailable >= seatsTaken){
                connectionTest conn2 = new connectionTest();
                Connection conn = conn2.getConnection();
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("INSERT INTO classes(class_code, class_name, class_capacity, class_seats_taken) VALUES ('" + codetf + "', '" + nametf+ "', " + seatsAvailable + ", " + seatsTaken+ ");");
                ResultSet result = stmt.executeQuery("SELECT * FROM classes");
                UpdateCode.setText("");
                UpdateName.setText("");
                UpdateCapacity.setText("");
                UpdateSeatsTaken.setText("");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message box");
                alert.setHeaderText("Update");
                alert.setContentText("Class added!");
                alert.show();
                addObservableListItems();
                while (result.next()){
                    System.out.println(result.getString("class_code") + " , " + result.getString("class_name") + " , " + result.getString("class_capacity"));
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message box");
                alert.setHeaderText("Update");
                alert.setContentText("Class seats taken cannot be greater than class capacity!");
                alert.show();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void UpdateEntry(String code, String name, int capacity, int seatsTaken){
        try{
            int id = 100;
            if (capacity >= seatsTaken){
                connectionTest conn2 = new connectionTest();
                Connection conn = conn2.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery("SELECT * FROM classes WHERE class_code = '" + code + "';");
                if (result.next()){
                    id = Integer.parseInt(result.getString("class_id"));
                }

                stmt.executeUpdate("UPDATE classes SET class_code = '" + code + "', class_name = '" + name + "', class_capacity = " + capacity + ", class_seats_taken = " + seatsTaken + " WHERE class_id = " + id + ";");
                addObservableListItems();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message box");
                alert.setHeaderText("Update");
                alert.setContentText("Class details updated!");
                alert.show();
                addObservableListItems();

            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message box");
                alert.setHeaderText("Alert");
                alert.setContentText("Class seats taken cannot be greater than class capacity!");
                alert.show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colClassCode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        colClassName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("SeatsAvailable"));
        colSeatsLeft.setCellValueFactory(new PropertyValueFactory<>("SeatsTaken"));
        classesTable.setItems(observableList);
    }


    public void RemoveButtonAction(ActionEvent actionEvent) {

        RemoveData();
    }

    public void UpdateButtonAction(ActionEvent actionEvent) {
        UpdateEntry(UpdateCode.getText(), UpdateName.getText(), Integer.parseInt(UpdateCapacity.getText()), Integer.parseInt(UpdateSeatsTaken.getText()));

    }

    public void SearchButtonAction(ActionEvent actionEvent) {
        FindAndUpdateObservableListItems(Search.getText());

    }

    public void BackButtonAction(ActionEvent actionEvent) {
        loadStage("AdminDashboard.fxml", actionEvent);
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


    public void RefreshButtonAction(ActionEvent actionEvent) {
        addObservableListItems();
        UpdateCode.setText("");
        UpdateCapacity.setText("");
        UpdateSeatsTaken.setText("");
        UpdateName.setText("");
    }

    public void TableClickedAction(MouseEvent mouseEvent) {
        int Index = classesTable.getSelectionModel().getSelectedIndex();
        UpdateCode.setText(colClassCode.getCellData(Index));
        UpdateName.setText(colClassName.getCellData(Index));
        UpdateCapacity.setText(colCapacity.getCellData(Index).toString());
        int seatstaken = colCapacity.getCellData(Index) - colSeatsLeft.getCellData(Index);
        UpdateSeatsTaken.setText(String.valueOf(seatstaken));
    }

}
