package parkeasy;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class DbConnect {

    public static Connection connection = null;

    public static Connection dbconnect() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkeasy", "root", "");
            System.out.println("Connection Successful.");

            return connection;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }

}
