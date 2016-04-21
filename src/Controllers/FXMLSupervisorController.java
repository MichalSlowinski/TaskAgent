package Controllers;

import Logic.WindowsOpener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FXMLSupervisorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
//cuś
    @FXML
    private Button Logout;

    @FXML
    void HandleLogoutButtonAction(ActionEvent event) {
        WindowsOpener.logout();
    }
    /**
     * Initializes the controller class.
     */
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
