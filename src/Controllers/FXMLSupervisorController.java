package Controllers;

import Logic.Querys;
import Logic.WindowsOpener;
import static Logic.WindowsOpener.open;
import Models.Task;
import TaskAgent.TaskAgent;
import static TaskAgent.TaskAgent.actual_option;
import static TaskAgent.TaskAgent.db;
import static TaskAgent.TaskAgent.task_state;
import static TaskAgent.TaskAgent.user_id;
import static TaskAgent.TaskAgent.user_state;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLSupervisorController implements Initializable {
    @FXML
    private TableView<Task> task_table = new TableView<>();
    @FXML
    private ObservableList<Task> data1 = FXCollections.observableArrayList();
    @FXML
    public TableColumn colName, colDesc, colSupervisor;
    @FXML
    private ComboBox task_userr, comboGroup, task_status;
    @FXML
    private TextField task_namee, task_descc;
    @FXML
    private DatePicker start, end;
    @FXML
    private Button button_add;
    @FXML
    private Label task_name, task_desc, task_visior, task_start, task_end, task_user, task_comm, task_stat;
    
    @FXML
    void HandleLogoutButtonAction(ActionEvent event) {
        WindowsOpener.logout();
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
    void addTaskButton(ActionEvent event) {
        try {
            String name = task_namee.getText();
            String desc = task_descc.getText();
            int status = 1;
            String[] supervisor = {TaskAgent.user.getFirstname(), TaskAgent.user.getLastname()};
            String[] user = task_userr.getSelectionModel().getSelectedItem().toString().split(" ");
            String date_start = start.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            String date_end = end.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            Calendar c = Calendar.getInstance();
            if(start.getValue().getYear() > end.getValue().getYear() 
                    || ((start.getValue().getDayOfMonth() > end.getValue().getDayOfMonth() && start.getValue().getMonthValue() == end.getValue().getMonthValue()))
                    || start.getValue().getMonthValue() > end.getValue().getMonthValue()) {
                WindowsOpener.alert("Błąd", "Data zakończenia musi być większa od daty startu.");
                return;
            }
            /*
            if(c.get(Calendar.YEAR) < start.getValue().getYear() 
                    || c.get(Calendar.MONTH) < start.getValue().getMonthValue() 
                    || (c.get(Calendar.MONTH) == start.getValue().getMonthValue() && c.get(Calendar.DAY_OF_MONTH) <= start.getValue().getDayOfMonth())) {
                WindowsOpener.alert("Błąd", "Data rozpoczęcia musi być wieksza lub równa dzisiejszej dacie!");
                return;
            }
            */
            if(actual_option == 0) {
                Querys.addTask(name, desc, supervisor, user, date_start, date_end, status);
            } else {
                Querys.editTask(actual_option, name, desc, supervisor, user, date_start, date_end, status);
            }
        } catch(Exception e) {
            WindowsOpener.alert("Błąd", "Wypełnij wszystkie pola! " + e.getMessage());
        }
    }

    @FXML
    void addTaskWindow(ActionEvent evnet) {
        user_state = 15;
        WindowsOpener.open("/TaskAgent/FXMLAddTaskSupervisor.fxml", "Dodaj Zadanie", true);
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
            open("/TaskAgent/FXMLSupervisor.fxml", "Kierownik", false);
        }
    }
    
    @FXML
    void editTask(ActionEvent event) {
        Task task = task_table.getSelectionModel().getSelectedItem();
        if(task.getId() > 0) {
            actual_option = task.getId();
            user_state = 15;
            WindowsOpener.open("/TaskAgent/FXMLAddTaskSupervisor.fxml", "Edytuj Zadanie", true);
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
        } else if(user_state == 15) {
            if(actual_option > 0) {
                button_add.setText("Edytuj");
                ResultSet task = db.Query("SELECT * FROM tasks WHERE id = " + actual_option);
                try {
                    if(task.next()) {
                        task_namee.setText(task.getString("name"));
                        task_descc.setText(task.getString("description"));
                        task_status.getSelectionModel().select(task.getInt("status") - 1);
                        Date date = new SimpleDateFormat("dd.mm.yyyy").parse(task.getString("date_start"));
                        LocalDate local = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        start.setValue(local);
                        date = new SimpleDateFormat("dd.mm.yyyy").parse(task.getString("date_end"));
                        local = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        end.setValue(local);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLAdminController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(FXMLAdminController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ResultSet p = db.Query("SELECT * FROM users");
            try {
                while (p.next()) {
                    task_userr.getItems().add(
                        p.getString("firstname") + " " + p.getString("lastname")
                    );
                }
                task_status.getItems().addAll(task_state);
                task_userr.getSelectionModel().select(0);
                task_status.getSelectionModel().select(0);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }    
    
}
