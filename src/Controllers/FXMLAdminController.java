package Controllers;

import Logic.WindowsOpener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FXMLAdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Tasks;

    @FXML
    private Label LogAs;

    @FXML
    private Button Logout;

    @FXML
    private Button Users;
     @FXML
     private Button Back;
    @FXML
    void HandleLogoutButtonAction(ActionEvent event) {
     WindowsOpener.logout();
    }

    @FXML
    void HandleUsersButtonAction(ActionEvent event) {
       WindowsOpener.open("/TaskAgent/FxmlUserEdit.fxml","Edit User",true);
    }

    @FXML
    void HandleTasksButtonAction(ActionEvent event) {
        WindowsOpener.open("/TaskAgent/FXMLTasks.fxml","Edit User",true);
    }
    @FXML
    void HandleBackButtonAction(ActionEvent event) {
     WindowsOpener.open("/TaskAgent/FXMLAdmin.fxml", "Administrator", false);
    }

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
