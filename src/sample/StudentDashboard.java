package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class StudentDashboard {
    public void LogoutButtonAction(ActionEvent actionEvent) {
        try {
            connectionTest connInitiate = new connectionTest();
            Connection conn = connInitiate.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM currentUser");
        }catch(Exception e){
            e.printStackTrace();
        }
        loadStage("sample.fxml", actionEvent);
    }

    public void RegistrationButtonAction(ActionEvent actionEvent) {
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
