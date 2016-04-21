package Controllers;

import Logic.WindowsOpener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    
    private ListView StartedTask;
    
    @FXML
    
    private Button StartTask;

    @FXML
    
    private Button LoadTasks;
     
    @FXML
    
    private Button Logout;

    @FXML
    
    private Button Finish;

    

    public static final ObservableList WaitingTasks = 
        FXCollections.observableArrayList();

    public static final ObservableList StartedTasks = 
        FXCollections.observableArrayList();
    
    @FXML
    void HandleStartTaskButtonAction(ActionEvent event) {

    }

    @FXML
    void HandleFinishTaskButtonAction(ActionEvent event) {

    }

    @FXML
    void HandleLogoutButtonAction(ActionEvent event) {
     WindowsOpener.logout();
    }
    @FXML
    void HandleLoadTasksButtonAction(ActionEvent event) {
        WaitingTasks.add("coś");
        ListView Waitinglist = new ListView();
    Waitinglist.setItems(WaitingTasks);
    }
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
