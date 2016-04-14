package taskagent;

import java.net.URL;
import java.util.ResourceBundle;
import TaskAgent.TaskAgent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLLoginController  {
    
    @FXML
   
    public TextField login;
    public PasswordField password;
    String log = login.getText();
    String pas = password.getText();
    @FXML
    
    
    
    private void handleButtonAction(ActionEvent event)  {
        
        if(!log.equals("") && !pas.equals("")) {
            TaskAgent.login(log, pas);
        } else {
           TaskAgent.alert("Error","Podaj pełne dane logowania");
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}