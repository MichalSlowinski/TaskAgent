package Controllers;

import TaskAgent.TaskAgent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class FXMLUserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<?> StartedTask;

    @FXML
    private Button StartTask;

    @FXML
    private Button Logout;

    @FXML
    private Button Finish;

    @FXML
    private ListView<?> WaitingTask;

    @FXML
    void HandleStartTaskButtonAction(ActionEvent event) {

    }

    @FXML
    void HandleFinishTaskButtonAction(ActionEvent event) {

    }

    @FXML
    void HandleLogoutButtonAction(ActionEvent event) {
     TaskAgent.logout();
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
