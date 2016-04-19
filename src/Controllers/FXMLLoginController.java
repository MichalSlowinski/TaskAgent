package Controllers;

import taskagent.*;
import java.net.URL;
import java.util.ResourceBundle;
import TaskAgent.TaskAgent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLLoginController  {
    
    @FXML
   
    public TextField log;
    public PasswordField pswd;
    
    @FXML
    
    
    
    private void handleButtonAction(ActionEvent event)  {
        String usr = log.getText();
        String pas = pswd.getText();
        
        if(!usr.equals("") && !pas.equals("")) {
            TaskAgent.login(usr, pas);
        } else {
           TaskAgent.alert("Error","Podaj pełne dane logowania");
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}