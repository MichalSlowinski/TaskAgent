package Controllers;

import Logic.Querys;
import Logic.WindowsOpener;
import Models.Task;
import Models.User;
import static TaskAgent.DBConnection.Execute;
import static TaskAgent.TaskAgent.db;
import static TaskAgent.TaskAgent.user_state;
import static TaskAgent.TaskAgent.actual_option;
import static TaskAgent.TaskAgent.task_state;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLAdminController implements Initializable {
    @FXML
    private TextField task_name, task_desc, LastName, Login, Email, FirstName, Password;
    @FXML
    private ComboBox task_supervisior, task_user, comboGroup, task_status;
    @FXML
    private Button button_add;
    @FXML
    private TableView<Task> task_table = new TableView<>();
    @FXML
    private ObservableList<Task> data1 = FXCollections.observableArrayList();
    @FXML
    private TableView<User> table_users = new TableView<>();
    @FXML
    private ObservableList<User> data2 = FXCollections.observableArrayList();
    @FXML
    public TableColumn colName, colDesc, colSupervisor, colUser, colFirstName, colGroup, colLastName;
    @FXML
    private DatePicker start, end;

    @FXML
    void HandleLogoutButtonAction(ActionEvent event) {
        WindowsOpener.logout();
    }

    @FXML
    void addTaskButton(ActionEvent event) {
        try {
            String name = task_name.getText();
            String desc = task_desc.getText();
            int status = 1;
            String[] supervisor = task_supervisior.getSelectionModel().getSelectedItem().toString().split(" ");
            String[] user = task_user.getSelectionModel().getSelectedItem().toString().split(" ");
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
            WindowsOpener.alert("Błąd", "Wypełnij wszystkie pola!");
        }
    }
    
    @FXML
    void showAddTask(ActionEvent event) {
        user_state = 15;
        WindowsOpener.open("/TaskAgent/FXMLAddTask.fxml", "Dodaj Zadanie", true);
    }
    
    @FXML
    void handleDeleteUser(ActionEvent event) {
        int id = table_users.getSelectionModel().getSelectedItem().getId();
        if(id > 0) {
            Execute("DELETE FROM users WHERE id = "+id);
            WindowsOpener.open("/TaskAgent/FXMLUsers.fxml", "Zadania", true);
        }
    }
    
    @FXML
    void userRaport(ActionEvent event) {
        if(table_users.getSelectionModel().getSelectedIndex() >= 0) {
            int id = table_users.getSelectionModel().getSelectedItem().getId();
            actual_option = id;
            user_state = 21;
            WindowsOpener.open("/TaskAgent/FXMLRaport.fxml", "Zadania", true);
        }
    }
    
    @FXML
    void generateRaport(ActionEvent event) {
        int id = task_table.getSelectionModel().getSelectedItem().getId();
        if(id >= 0) {
            Creator c = new Creator(task_table.getSelectionModel().getSelectedItem());
        }
    }
    
    @FXML
    private void generateMainRaport(ActionEvent event) {
        Creator c = new Creator();
    }

    @FXML
    void deleteTaskButton(ActionEvent event) {
        int id = task_table.getSelectionModel().getSelectedItem().getId();
        if(id > 0) {
            Execute("DELETE FROM tasks WHERE id = "+id);
            WindowsOpener.open("/TaskAgent/FXMLTasks.fxml", "Tasks", true);
        }
    }

    void fillTaskTable() {
        ResultSet p = db.Query("SELECT t.*, u.firstname AS super_name, u.lastname AS super_last, u2.firstname AS first_2, u2.lastname AS last_2 FROM tasks t JOIN users u ON u.id = t.id_supervisor LEFT JOIN users u2 ON u2.id = t.user_id");
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
        colUser.setCellValueFactory(new PropertyValueFactory<>("user"));
        task_table.setItems(data1);
    }
    
    void fillUserTable() {
        ResultSet p = db.Query("SELECT * FROM users");
        data2.clear();
        table_users.setEditable(true);
        try {
            while(p.next()) {
                data2.add(new User(p.getInt("id"), p.getString("firstname"), p.getString("lastname"), p.getInt("id_groups")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        colGroup.setCellValueFactory(new PropertyValueFactory<>("groupname"));
        table_users.setItems(data2);
    }
    
    @FXML
    void editTask(ActionEvent event) {
        Task task = task_table.getSelectionModel().getSelectedItem();
        if(task.getId() > 0) {
            actual_option = task.getId();
            user_state = 15;
            WindowsOpener.open("/TaskAgent/FXMLAddTask.fxml", "Edytuj Zadanie", true);
        }
    }

    @FXML
    void HandleUsersButtonAction(ActionEvent event) {
        user_state = 3;
        WindowsOpener.open("/TaskAgent/FXMLUsers.fxml", "Users", true);
    }

    @FXML
    void HandleTasksButtonAction(ActionEvent event) {
        user_state = 1;
        WindowsOpener.open("/TaskAgent/FXMLTasks.fxml", "Tasks", true);
    }

    @FXML
    void HandleBackButtonAction(ActionEvent event) {
        user_state = 0;
        WindowsOpener.open("/TaskAgent/FXMLAdmin.fxml", "Administrator", false);
    }
 
    @FXML
    void HandleAddUserWindow(ActionEvent event) {
        user_state = 9;
        WindowsOpener.open("/TaskAgent/FXMLaddUser.fxml","Add User",true);
    }
    
    @FXML
    void HandleEditUserWindow(ActionEvent event) {
        int id = table_users.getSelectionModel().getSelectedItem().getId();
        if(id > 0) {
            actual_option = id;
            user_state = 10;
            WindowsOpener.open("/TaskAgent/FXMLaddUser.fxml","Add User",true);
        }
    }

    @FXML
    void HandleBackToUserButtonAction(ActionEvent event){
        user_state = 3;
        WindowsOpener.open("/TaskAgent/FXMLUsers.fxml","Users",false);
    }

    @FXML
    void ConfrimUserHandler(ActionEvent event){
        if(actual_option == 0)
            Querys.AddUser(FirstName.getText(), LastName.getText(), Login.getText(), Password.getText(), Email.getText(), comboGroup.getSelectionModel().getSelectedIndex() + 1);
        else
            Querys.editUser(actual_option, FirstName.getText(), LastName.getText(), Login.getText(), Password.getText(), Email.getText(), comboGroup.getSelectionModel().getSelectedIndex() + 1);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(user_state == 1) {
            fillTaskTable();
        } else if(user_state == 3) {
            fillUserTable();
        } else if(user_state == 9) {
            comboGroup.getItems().addAll(
                "User", "Supervisor", "Administrator"
            );
        } else if(user_state == 10) {
            comboGroup.getItems().addAll(
                "User", "Supervisor", "Administrator"
            );
            ResultSet user = db.Query("SELECT * FROM users WHERE id = "+actual_option);
            try {
                if(user.next()) {
                    FirstName.setText(user.getString("firstname"));
                    LastName.setText(user.getString("lastname"));
                    Email.setText(user.getString("email"));
                    Login.setText(user.getString("login"));
                    Password.setText(user.getString("password"));
                    comboGroup.getSelectionModel().select(user.getInt("id_groups") - 1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(user_state == 15) {
            if(actual_option > 0) {
                button_add.setText("Edytuj");
                ResultSet task = db.Query("SELECT * FROM tasks WHERE id = " + actual_option);
                try {
                    if(task.next()) {
                        task_name.setText(task.getString("name"));
                        task_desc.setText(task.getString("description"));
                        task_status.getSelectionModel().select(task.getInt("status") - 1);
                        Date date = new SimpleDateFormat("dd.MM.yyyy").parse(task.getString("date_start"));
                        LocalDate local = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        start.setValue(local);
                        date = new SimpleDateFormat("dd.MM.yyyy").parse(task.getString("date_end"));
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
                    task_supervisior.getItems().add(
                        p.getString("firstname") + " " + p.getString("lastname")
                    );
                    task_user.getItems().add(
                        p.getString("firstname") + " " + p.getString("lastname")
                    );
                }
                task_status.getItems().addAll(task_state);
                task_supervisior.getSelectionModel().select(0);
                task_user.getSelectionModel().select(0);
                task_status.getSelectionModel().select(0);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
