package Controllers;

import Logic.WindowsOpener;
import Models.Task;
import static TaskAgent.TaskAgent.actual_option;
import static TaskAgent.TaskAgent.user_state;
import static TaskAgent.TaskAgent.db;
import static TaskAgent.TaskAgent.user_id;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLUserController implements Initializable {
    @FXML
    private TableView<Task> task_table = new TableView<>();
    @FXML
    private ObservableList<Task> data1 = FXCollections.observableArrayList();
    @FXML
    public TableColumn colName, colDesc, colSupervisor;
    @FXML
    public ComboBox task_status;
    @FXML
    public Label task_name, task_start, task_end, task_visior, task_comm, task_stat, task_desc, task_user;
    @FXML
    public TextArea task_comment;

    @FXML
    void HandleStartTaskButtonAction(ActionEvent event) {

    }
    
    @FXML
    void showTask(ActionEvent event) {
        if(task_table.getSelectionModel().getSelectedIndex() >= 0) {
            actual_option = task_table.getSelectionModel().getSelectedItem().getId();
            user_state = 2;
            WindowsOpener.open("/TaskAgent/FXMLShowTask1.fxml", "Zadanie", true);
        }
    }
    
    @FXML
    void saveTaskChanges(ActionEvent event) {
        int status = task_status.getSelectionModel().getSelectedIndex() + 1;
        String comment = task_comment.getText();
        db.Execute("UPDATE tasks SET status = "+status+", comment = '"+comment+"' WHERE id = "+actual_option);
        actual_option = 0;
        user_state = 0;
        WindowsOpener.open("/TaskAgent/FXMLUser.fxml", "Panel Użytkownika", true);
    }
    
    @FXML
    void userTasks(ActionEvent event) {
        actual_option = 0;
        user_state = 0;
        WindowsOpener.open("/TaskAgent/FXMLUser.fxml", "Panel Użytkownika", true);
    }
    
    @FXML
    void editTask(ActionEvent event) {
        if(task_table.getSelectionModel().getSelectedIndex() >= 0) {
            actual_option = task_table.getSelectionModel().getSelectedItem().getId();
            user_state = 1;
            WindowsOpener.open("/TaskAgent/FXMLUserTask.fxml", "Zadanie", true);
        }
    }

    @FXML
    void HandleLogoutButtonAction(ActionEvent event) {
        WindowsOpener.logout();
    }
    
    void fillTaskTable() {
        ResultSet p = db.Query("SELECT t.*, u.firstname AS super_name, u.lastname AS super_last, u2.firstname AS first_2, u2.lastname AS last_2 FROM tasks t JOIN users u ON u.id = t.id_supervisor LEFT JOIN users u2 ON u2.id = t.user_id WHERE t.user_id = "+user_id);
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
        colSupervisor.setCellValueFactory(new PropertyValueFactory<>("supervisor"));
        task_table.setItems(data1);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(user_state == 0) {
            fillTaskTable();
        } else if(user_state == 1) {
            task_status.getItems().addAll(
                "Aktywne",
                "Zakończone",
                "Zamknięte"
            );
            ResultSet task = db.Query("SELECT * FROM tasks WHERE id = " + actual_option);
            try {
                if(task.next()) {
                    task_name.setText(task.getString("name"));
                    task_comment.setText(task.getString("comment"));
                    task_status.getSelectionModel().select(task.getInt("status") - 1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(user_state == 2) {
            ResultSet task = db.Query("SELECT * FROM tasks WHERE id = " + actual_option);
            try {
                if(task.next()) {
                    task_name.setText(task.getString("name"));
                    task_comm.setText(task.getString("comment"));
                    task_desc.setText(task.getString("description"));
                    task_visior.setText(task.getString("id_supervisor"));
                    task_start.setText(task.getString("date_start"));
                    task_end.setText(task.getString("date_end"));
                    task_user.setText(task.getString("user_id"));
                    task_stat.setText(task.getString("status"));
                }
            } catch(Exception e) {
                
            }
        }
    }
}
