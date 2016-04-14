package TaskAgent;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    Properties props = new Properties();
    public  DBConnection(String Host,String User ,String Password ) {
        props.setProperty("host",Host);
        
        props.setProperty("user",User);
        props.setProperty("password",Password);
     
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(Host,User,Password);
        } catch(Exception e) {
            TaskAgent.alert("Error", "Połączenie nie udane");
        }
    }
    
    public static ResultSet select(String query) {
        resultSet = null;
        try {
            Statement statement = connect.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
}