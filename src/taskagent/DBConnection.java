package TaskAgent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DBConnection {
	
	private static Connection conn;
    private static String url = "jdbc:mysql://sql7.freesqldatabase.com:3306/";
    private static String dbName = "sql7114809";
    private static String user = "sql7114809";
    private static String pass = "se4Ag7WSZ3";
    private static String parameters = "?useSSL=false";

    private static PreparedStatement preparedStatement = null;

    
    public static Connection connect() throws SQLException {
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
        System.out.println("Connected"); //TODO delete this line
        return conn;
    }

    /**
     * Connection object getter
     * @return current connection or null when connection object is null
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(conn !=null && !conn.isClosed())
            return conn;
        connect();
        return conn;
    }

    /**
     * Method which close connection
     */
    public static void close() {
        try {
            conn.close();
            System.out.println("Disconnected"); //TODO delete this line
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public static String toString(ResultSet resultSet) { //TODO delete this method
        StringBuilder sBuffer = null;
        if(resultSet != null) {
            try {
                sBuffer = new StringBuilder("");
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                while (resultSet.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) sBuffer.append(",  ");
                        String columnValue = resultSet.getString(i);
                        sBuffer.append(rsmd.getColumnName(i)).append(": ").append(columnValue);
                    }
                    sBuffer.append("\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sBuffer == null ? "Missing data - resultSet == null" : sBuffer.toString();
    }
}