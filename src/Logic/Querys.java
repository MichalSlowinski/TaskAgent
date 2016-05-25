/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import Models.User;
import static TaskAgent.DBConnection.Execute;
import static TaskAgent.DBConnection.Query;
import TaskAgent.TaskAgent;
import static TaskAgent.TaskAgent.id_groups;
import static TaskAgent.TaskAgent.user_state;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author slowi
 */
public class Querys {

    public static void addTask(String name, String desc, String[] supervisor, String[] user, String start, String end, int status) {
        if (!name.equals("") && !desc.equals("")) {
            ResultSet m = Query("SELECT * FROM users WHERE firstname = '" + supervisor[0] + "' AND lastname = '" + supervisor[1] + "';");
            try {
                if (m.next()) {
                    ResultSet u = Query("SELECT * FROM users WHERE firstname = '" + user[0] + "' AND lastname = '" + user[1] + "';");
                    if (u.next()) {
                        Execute("INSERT INTO `tasks` (`name`, `description`, `id_supervisor`, `date_start`, `date_end`, `execution_time`, `status`, `user_id`, `comment`) "
                                + "VALUES ('" + name + "', '" + desc + "', '" + m.getInt("id") + "', '"+start+"', '"+end+"', '2016-04-06 00:00:00', '"+status+"', '" + u.getInt("id") + "', '');");
                        user_state = 1;
                        if(id_groups == 2) {
                            user_state = 0;
                            WindowsOpener.open("/TaskAgent/FXMLSupervisor.fxml", "Tasks", true);
                        } else
                            WindowsOpener.open("/TaskAgent/FXMLTasks.fxml", "Tasks", true);
                    }
                } else {
                    WindowsOpener.alert("Błąd", "Wypełnij wszystkie dane!");
                }
            } catch (SQLException ex) {

            }
        }
    }

    public static void editTask(int id, String name, String desc, String[] supervisor, String[] user, String start, String end, int status) {
        try {
            ResultSet m = null, u = null;
            m = Query("SELECT id FROM users WHERE firstname = '" + supervisor[0] + "' AND lastname = '" + supervisor[1] + "';");
            if (m.next()) {
                u = Query("SELECT id FROM users WHERE firstname = '" + user[0] + "' AND lastname = '" + user[1] + "';");
                if (u.next()) {
                    Execute("UPDATE tasks SET status = "+status+", user_id = "+u.getInt(1)+", date_start = '"+start+"', date_end = '"+end+"', id_supervisor = "+m.getInt(1)+", name = \"" + name + "\", description = \"" + desc + "\" WHERE id = " + id);
                    user_state = 1;
                    if(id_groups == 2) {
                        user_state = 0;
                        WindowsOpener.open("/TaskAgent/FXMLSupervisor.fxml", "Tasks", true);
                    } else
                        WindowsOpener.open("/TaskAgent/FXMLTasks.fxml", "Tasks", true);
                } else {
                    WindowsOpener.alert("Błąd", "Wypełnij wszystkie dane!");
                }
            } else {
                WindowsOpener.alert("Błąd", "Wypełnij wszystkie dane!");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void AddUser(String firstname, String lastname, String login, String password, String email, int group) {
        if (!firstname.equals("") && !lastname.equals("") && !login.equals("") && !password.equals("") && !email.equals("") && group > 0) {
            Execute("INSERT INTO `users` (`firstname`, `lastname`, `login`, `password`, `email`, `id_groups`, `id_supervisor`) "
                    + " VALUES ('" + firstname + "', '" + lastname + "', '" + login + "', '" + password + "', '" + email + "', '" + group + "', '0');");
            TaskAgent.user_state = 3;
            WindowsOpener.open("/TaskAgent/FXMLUsers.fxml", "Tasks", true);
        } else {
            WindowsOpener.alert("Błąd", "Wypełnij wszystkie dane!");
        }
    }

    public static void editUser(int id, String firstname, String lastname, String login, String password, String email, int group) {
        if (!firstname.equals("") && !lastname.equals("") && !login.equals("") && !password.equals("") && !email.equals("") && group > 0) {
            Execute("UPDATE users SET password = '" + password + "', email = '" + email + "', id_groups = '" + group + "', login = '" + login + "', firstname = '" + firstname + "', lastname = '" + lastname + "' WHERE id = " + id);
            TaskAgent.user_state = 3;
            TaskAgent.actual_option = 0;
            WindowsOpener.open("/TaskAgent/FXMLUsers.fxml", "Tasks", true);
        } else {
            WindowsOpener.alert("Błąd", "Wypełnij wszystkie dane!");
        }
    }
    
    public static String getUserNameById(int id) throws SQLException {
        String name = "";
        ResultSet user = Query("SELECT * FROM users WHERE id = " + id);
        if(user.next()) {
            name = user.getString("firstname") + " " + user.getString("lastname");
        }
        return name;
    }
    
    public static User getUserById(int id) throws SQLException {
        User u = null;
        ResultSet p = Query("SELECT * FROM users WHERE id = " + id);
        if(p.next()) {
            u = new User(p.getInt("id"), p.getString("firstname"), p.getString("lastname"), p.getInt("id_groups"));
        }        
        return u;
    }
}
