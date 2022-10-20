package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.stage.Window;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Controller {
    @FXML TextField UsernameTextField;
    @FXML TextField PasswordTextField;

    public void LoginButtonAction(ActionEvent actionEvent) {
        try{
            String Username = UsernameTextField.getText();
            String Password = PasswordTextField.getText();
            connectionTest connInitiate = new connectionTest();
            Connection conn = connInitiate.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM admin WHERE admin_username = '" + Username + "' AND admin_password = '" +Password + "';");
            if (result.next()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message box");
                alert.setHeaderText("Login");
                alert.setContentText("Logged in as Admin!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        try{
                            stmt.executeUpdate("DELETE FROM currentUser;");
                            stmt.executeUpdate("INSERT INTO currentUser VALUES ('" + Username + "','" + Password + "');");
                            loadStage("AdminDashboard.fxml", actionEvent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });


            }else{
                ResultSet result2 = stmt.executeQuery("SELECT * FROM students WHERE student_username = '" + Username + "' AND student_password = '" + Password + "';");
                if(result2.next()){

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message box");
                    alert.setHeaderText("Login");
                    alert.setContentText("Logged in as a Student!");
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK) {
                            try{
                                stmt.executeUpdate("DELETE FROM currentUser;");
                                stmt.executeUpdate("INSERT INTO currentUser VALUES ('" + Username + "','" + Password + "');");
                                loadStage("StudentDashboard.fxml", actionEvent);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else{
                    System.out.println("Incorrect username or password");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message box");
                    alert.setHeaderText("Alert");
                    alert.setContentText("Incorrect username or password!");
                    alert.show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void RegistrationButtonAction(ActionEvent actionEvent) {
        System.out.println("Registration button activated");
        System.out.println(getClass().getResource("Classes.fxml"));
        loadStage("Registration.fxml", actionEvent);
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
