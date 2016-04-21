package Controllers;

import static Logic.WindowsOpener.*;
import TaskAgent.DBConnection;
import java.net.URL;
import java.util.ResourceBundle;
import TaskAgent.TaskAgent;
import static TaskAgent.TaskAgent.*;
import Models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Singleton.WhoIm;


public class FXMLLoginController  {
    
    @FXML
   
    public TextField log;
    public PasswordField pswd;
    public static DBConnection dbc;
    public static User user = null;
    @FXML
    
    
     
    private void handleButtonAction(ActionEvent event) throws SQLException  {
        String usr = log.getText();
        String pas = pswd.getText();
        
       
            login(usr, pas);
        
    }
 public static void login(String login, String password) throws SQLException 
    {
        
        try {
            dbc.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(TaskAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TaskAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       ResultSet check = dbc.Query("Select id_groups,id from user where login='"+login+"' and password='"+password+"';");                
        
        while(check.next()){
            id_groups=check.getInt(1);
            user_id=check.getInt(2);
        }
    
                user=new User(user_id);
        WhoIm.getInstance().setUser(user);
        switch (id_groups) {
            case 1:
                open_window("/TaskAgent/FXMLAdmin.fxml","Administrator",true);
                break;
            case 2:
                open_window("/TaskAgent/FXMLSupervisor.fxml","Supervisor",true);
                break;
            case 3:
                open_window("/TaskAgent/FXMLUser.fxml","User",true);
                break;
        }
        dbc.close();
    }
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}