package Controllers;

import Logic.Querys;
import Logic.WindowsOpener;
import static Logic.WindowsOpener.open;
import Models.Task;
import static TaskAgent.TaskAgent.actual_option;
import static TaskAgent.TaskAgent.db;
import static TaskAgent.TaskAgent.user_id;
import static TaskAgent.TaskAgent.user_state;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    private Label task_name, task_desc, task_visior, task_start, task_end, task_user, task_comm, task_stat;
    
    @FXML
    void HandleLogoutButtonAction(ActionEvent event) {
        WindowsOpener.logout();
    }
    
    @FXML
    void editTask(ActionEvent event) {
        int index = task_table.getSelectionModel().getSelectedIndex();
        if(index >= 0) {
            user_state = 2;
            actual_option = task_table.getSelectionModel().getSelectedItem().getId();
            open("/TaskAgent/FXMLEditTask.fxml", "Pokaż Zadanie", false);
        }
    }
    
    @FXML
    void showTask(ActionEvent event) {
        int index = task_table.getSelectionModel().getSelectedIndex();
        if(index >= 0) {
            user_state = 1;
            actual_option = task_table.getSelectionModel().getSelectedItem().getId();
            open("/TaskAgent/FXMLShowTask2.fxml", "Pokaż Zadanie", false);
        }
    }
    
    @FXML
    void showTasks(ActionEvent event) {
        user_state = 0;
        open("/TaskAgent/FXMLSupervisor.fxml", "Pokaż Zadanie", false);
    }
    
    @FXML
    void deleteTask(ActionEvent event) {
        int index = task_table.getSelectionModel().getSelectedIndex();
        if(index >= 0) {
            db.Execute("DELETE FROM tasks WHERE id = " + task_table.getSelectionModel().getSelectedItem().getId());
            open("/TaskAgent/FXMLSupervisor.fxml", "Supervisor", false);
        }
    }
    
    @FXML
    void generateRaport(ActionEvent event) {
        Creator c = new Creator(user_id);
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
        } else if(user_state == 1) {
            try {
                ResultSet task = db.Query("SELECT * FROM tasks WHERE id = " + actual_option);
                if(task.next()) {
                    task_name.setText(task.getString("name"));
                    task_desc.setText(task.getString("description"));
                    task_stat.setText(task.getString("status"));
                    task_visior.setText(Querys.getUserNameById(task.getInt("id_supervisor")));
                    task_user.setText(Querys.getUserNameById(task.getInt("user_id")));
                    task_comm.setText(task.getString("comment"));
                    task_start.setText(task.getString("date_start"));
                    task_end.setText(task.getString("date_end"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLSupervisorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
    
}
