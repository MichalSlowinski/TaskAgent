package taskagent;

import java.net.URL;
import java.util.ResourceBundle;
import TaskAgent.TaskAgent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLLoginController implements Initializable {
    
    @FXML
    private Label label;
    public TextField login;
    public PasswordField password;
    
    @FXML
    private void handleButtonAction(ActionEvent event)  {
        String l = login.getText();
        String p = password.getText();
        if(!l.equals("") && !p.equals("")) {
            TaskAgent.login(l, p);
        } else {
           TaskAgent.alert("Error","Podaj pełne dane logowania");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}