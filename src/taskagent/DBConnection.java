package TaskAgent;

import Logic.WindowsOpener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static ResultSet Query(String query) {
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    
    public static void Execute(String query) {
        try {
            System.out.println(query);
            conn.createStatement().execute(query);
        } catch (SQLException ex) {
            WindowsOpener.alert("Błąd", "Nie udało się wykonać zapytania!" + ex.getMessage());
        }
    }

    public static void addTask(String name, String desc, String[] supervisor) {
        if(!name.equals("") && !desc.equals("")) {
            ResultSet m = Query("SELECT * FROM users WHERE firstname = '" + supervisor[0] + "' AND lastname = '" + supervisor[1] + "';");
            try {
                if (m.next()) {
                    Execute("INSERT INTO `tasks` (`name`, `description`, `id_supervisor`, `date_start`, `date_end`, `execution_time`, `status`) "
                          + "VALUES ('"+name+"', '"+desc+"', '"+m.getInt("id")+"', '2016-04-06 00:00:00', '2016-04-06 00:00:00', '2016-04-06 00:00:00', '1');");
                    WindowsOpener.open("/TaskAgent/FXMLTasks.fxml", "Tasks", true);
                } else {
                    WindowsOpener.alert("Błąd", "Wypełnij wszystkie dane!");
                }
            } catch (SQLException ex) {
                
            }
        }
    }
    
    public static void editTask(int id, String name, String desc) {
        Execute("UPDATE tasks SET name = \""+name+"\", description = \""+desc+"\" WHERE id = "+id);
        WindowsOpener.open("/TaskAgent/FXMLTasks.fxml", "Tasks", true);
    }
}