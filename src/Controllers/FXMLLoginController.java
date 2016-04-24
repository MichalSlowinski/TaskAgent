package Controllers;

import static Logic.WindowsOpener.*;
import java.net.URL;
import java.util.ResourceBundle;
import static TaskAgent.TaskAgent.*;
import Models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Singleton.WhoIm;

public class FXMLLoginController {

    @FXML
    public TextField log;
    public PasswordField pswd;
    public static User user = null;

    @FXML

    private void handleButtonAction(ActionEvent event) throws SQLException {
        String usr = log.getText();
        String pas = pswd.getText();
        login(usr, pas);
    }

    public static void login(String login, String password) throws SQLException {
        ResultSet check = db.Query("Select id_groups,id from users where login='" + login + "' and password='" + password + "';");

        if(check.next()) {
            id_groups = check.getInt(1);
            user_id = check.getInt(2);
            user = new User(user_id);
            user_state = 0;
            WhoIm.getInstance().setUser(user);
            
            switch (id_groups) {
                case 1:
                    open("/TaskAgent/FXMLAdmin.fxml", "Administrator", false);
                    break;
                case 2:
                    open("/TaskAgent/FXMLSupervisor.fxml", "Supervisor", true);
                    break;
                case 3:
                    open("/TaskAgent/FXMLUser.fxml", "User", true);
                    break;
            }
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}