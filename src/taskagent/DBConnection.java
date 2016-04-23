package TaskAgent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
	
    private static Connection conn;
    private static String url = "jdbc:mysql://sql7.freesqldatabase.com:3306/";
    private static String dbName = "sql7114809";
    private static String user = "sql7114809";
    private static String pass = "se4Ag7WSZ3";
    private static String parameters = "?useSSL=false";

    private static PreparedStatement preparedStatement = null;

    public DBConnection() throws SQLException {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch(ClassNotFoundException cnfe){
            System.err.println("Error: "+cnfe.getMessage());
        }catch(InstantiationException ie){
            System.err.println("Error: "+ie.getMessage());
        }catch(IllegalAccessException iae){
            System.err.println("Error: "+iae.getMessage());
        }
        conn = DriverManager.getConnection(url + dbName + parameters, user, pass);
    }

    public static ResultSet Query(String query) { //TODO close resultSet somewhere
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    public void addTask(String name, String desc){
        Query("insert into tasks(name,task)values('"+name+"','"+desc+"'');");
    }

}