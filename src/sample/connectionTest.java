package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class connectionTest {
    public Connection getConnection(){
        try {
            String url = "jdbc:mysql://localhost:8080/FP_SayedSubhanShahSadat";
            String user = "root";
            String pass = "";
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pass);
            return conn;

            /*Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM admin");
            while (result.next()){
                System.out.println(result.getString("admin_id") + " , " + result.getString("admin_username") + " , " + result.getString("admin_password") );
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
