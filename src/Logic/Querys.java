/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import static TaskAgent.DBConnection.Execute;
import static TaskAgent.DBConnection.Query;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author slowi
 */
public class Querys {
    
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