package Controllers;

import Logic.WindowsOpener;
import Models.Task;
import static TaskAgent.TaskAgent.db;
import static TaskAgent.TaskAgent.user_id;
import static TaskAgent.TaskAgent.user_state;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLSupervisorController implements Initializable {
    @FXML
    private TableView<Task> task_table = new TableView<>();
    @FXML
    private ObservableList<Task> data1 = FXCollections.observableArrayList();
    @FXML
    public TableColumn colName, colDesc, colSupervisor;
    
    @FXML
    void HandleLogoutButtonAction(ActionEvent event) {
        WindowsOpener.logout();
    }
    
    @FXML
    void editTask(ActionEvent event) {
        
    }
    
    @FXML
    void showTask(ActionEvent event) {
        
    }
    
    void fillTaskTable() {
        ResultSet p = db.Query("SELECT t.*, u.firstname AS super_name, u.lastname AS super_last, u2.firstname AS first_2, u2.lastname AS last_2 FROM tasks t JOIN users u ON u.id = t.id_supervisor LEFT JOIN users u2 ON u2.id = t.user_id WHERE t.id_supervisor = "+user_id);
        data1.clear();
        task_table.setEditable(true);
        try {
            while(p.next()) {
                data1.add(new Task(p.getInt("id"),p.getString("name"),p.getString("description"), p.getString("super_name") + " " + p.getString("super_last"), p.getString("first_2") + " " + p.getString("last_2"), p.getInt("status"), p.getString("comment")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSupervisor.setCellValueFactory(new PropertyValueFactory<>("user"));
        task_table.setItems(data1);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(user_state == 0) {
            fillTaskTable();
        }
    }    
    
}
