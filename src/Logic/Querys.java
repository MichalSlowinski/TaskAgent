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
    
      public static void addTask(String name, String desc, String[] supervisor, String[] user) {
        if(!name.equals("") && !desc.equals("")) {
            ResultSet m = Query("SELECT * FROM users WHERE firstname = '" + supervisor[0] + "' AND lastname = '" + supervisor[1] + "';");
            try {
                if(m.next()) {
                    ResultSet u = Query("SELECT * FROM users WHERE firstname = '" + user[0] + "' AND lastname = '" + user[1] + "';");
                    if(u.next()) {
                        Execute("INSERT INTO `tasks` (`name`, `description`, `id_supervisor`, `date_start`, `date_end`, `execution_time`, `status`, `user_id`) "
                              + "VALUES ('"+name+"', '"+desc+"', '"+m.getInt("id")+"', '2016-04-06 00:00:00', '2016-04-06 00:00:00', '2016-04-06 00:00:00', '1', '"+u.getInt("id")+"');");
                        WindowsOpener.open("/TaskAgent/FXMLTasks.fxml", "Tasks", true);
                    }
                } else {
                    WindowsOpener.alert("Błąd", "Wypełnij wszystkie dane!");
                }
            } catch (SQLException ex) {
                
            }
        }
    }
    
    public static void editTask(int id, String name, String desc, String[] supervisor, String[] user) {
        Execute("UPDATE tasks SET name = \""+name+"\", description = \""+desc+"\" WHERE id = "+id);
        WindowsOpener.open("/TaskAgent/FXMLTasks.fxml", "Tasks", true);
    }
    public static void addUser(String firstname,String lastname,String login, String password, String email, String group, String[] supervisor) throws SQLException{
        ResultSet sp = Query("SELECT * FROM users WHERE firstname = '" + supervisor[0] + "' AND lastname = '" + supervisor[1] + "';");
        ResultSet gp = Query("Select id from groups where position='"+group+"';");
        Execute("Insert into users(firstname,lastname,login,password,email,id_groups,id_supervisor) values('"+firstname+"','"+lastname+"','"+login+"','"+password+"','"+email+"','"+gp.getInt("id")+"',"+sp.getInt("id")+");");
  }
    
}