package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegistrationController {
    @FXML
    TextField UsernameTF;
    @FXML
    TextField PasswordTF;
    @FXML
    TextField FirstNameTF;
    @FXML
    TextField LastNameTF;
    @FXML
    TextField LevelTF;
    public void SubmitButtonAction(ActionEvent actionEvent) {
        try{
            String username = UsernameTF.getText();
            String password = PasswordTF.getText();
            String firstName = FirstNameTF.getText();
            String lastName = LastNameTF.getText();
            String level = LevelTF.getText();

            connectionTest conn2 = new connectionTest();
            Connection conn = conn2.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO students(student_username, student_password, student_first_name, student_last_name, student_level) VALUES ('" + username + "','" + password + "','" + firstName + "','" + lastName + "','" + level+"');");
            stmt.executeUpdate("DELETE FROM currentUser;");
            stmt.executeUpdate("INSERT INTO currentUser VALUES ('" + username + "','" + password + "');");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message box");
            alert.setHeaderText("Update");
            alert.setContentText("Account created successfully");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    loadStage("StudentDashboard.fxml", actionEvent);
                }
            });
            ResultSet result = stmt.executeQuery("SELECT * FROM students");
            UsernameTF.setText("");
            PasswordTF.setText("");
            FirstNameTF.setText("");
            LastNameTF.setText("");
            LevelTF.setText("");

            while (result.next()){
                System.out.println(result.getString("student_username") + " , " + result.getString("student_first_name") + " , " + result.getString("student_level"));
            }
            loadStage("StudentDashboard.fxml", actionEvent);
        }catch (Exception e) {
            e.printStackTrace();
        }
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

    public void BackButtonAction(ActionEvent actionEvent) {
        loadStage("sample.fxml", actionEvent);
    }
}
