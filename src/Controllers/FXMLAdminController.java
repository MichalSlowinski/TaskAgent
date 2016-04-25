package Controllers;

import Logic.Querys;
import Logic.WindowsOpener;
import Models.Task;
import static TaskAgent.DBConnection.Execute;
import static TaskAgent.TaskAgent.db;
import static TaskAgent.TaskAgent.user_state;
import static TaskAgent.TaskAgent.actual_option;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLAdminController implements Initializable {
    @FXML
    private Button AddUser;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField task_name, task_desc;
    @FXML
    private ComboBox task_supervisior, task_user;
    @FXML
    private Button Tasks;
    @FXML
    private Label LogAs;
    @FXML
    private Button Logout;
    @FXML
    private Button Users, task_button;
    @FXML
    private Button Back;
    @FXML
    private ComboBox UserSupervisor, UserGroup;

    @FXML
    private TextField UserLastName;

    @FXML
    private PasswordField UserPassword;

    @FXML
    private TextField UserFirstName;

    @FXML
    private Button BackToUser;

    @FXML
    private Button UserConfrim;

    @FXML
    private TextField UserEmail;

    @FXML
    private TextField UserLogin;

    @FXML
    private TableView<Task> task_table = new TableView<>();
    @FXML
    private ObservableList<Task> data1 = FXCollections.observableArrayList();
    @FXML
    public TableColumn colName, colDesc, colSupervisor, colUser;

    @FXML
    void HandleLogoutButtonAction(ActionEvent event) {
        WindowsOpener.logout();
    }

    @FXML
    void addTaskButton(ActionEvent event) {
        String name = task_name.getText();
        String desc = task_desc.getText();
        String[] supervisor = task_supervisior.getSelectionModel().getSelectedItem().toString().split(" ");
        String[] user = task_user.getSelectionModel().getSelectedItem().toString().split(" ");
        if(actual_option == 0) {
            Querys.addTask(name, desc, supervisor, user);
        } else {
            Querys.editTask(actual_option, name, desc, supervisor, user);
            actual_option = 0;
            task_button.setText("Dodaj");
        }
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
                data1.add(new Task(p.getInt("id"),p.getString("name"),p.getString("description"), p.getString("super_name") + " " + p.getString("super_last"), p.getString("first_2") + " " + p.getString("last_2")));
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
    
    @FXML
    void editTask(ActionEvent event) {
        Task task = task_table.getSelectionModel().getSelectedItem();
        if(task.getId() > 0) {
            actual_option = task.getId();
            task_button.setText("Edytuj");
            task_name.setText(task.getName());
            task_desc.setText(task.getDescription());
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
    void HandleAddUserWindow(ActionEvent event){
        user_state=0;
        WindowsOpener.open("/TaskAgent/FXMLaddUser.fxml","Add User",false);    }
    @FXML
    void HandleBackToUserButtonAction(ActionEvent event){
        user_state=0;
        WindowsOpener.open("/TaskAgent/FXMLUsers.fxml","Users",false);
    }
    @FXML
    void ConfrimUserHandler(ActionEvent event) throws SQLException{
        String ufn= UserFirstName.getText();
        String uln= UserLastName.getText();
        String ul= UserLogin.getText();
        String up= UserPassword.getText();
        String ue= UserEmail.getText();
        String[] Usersupervisor = UserSupervisor.getSelectionModel().getSelectedItem().toString().split(" ");
        String Usergroup = UserGroup.getSelectionModel().getSelectedItem().toString();
       Querys.addUser(ufn,uln,ul,up,ue,Usergroup,Usersupervisor);//TODO add User method
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(user_state == 1) {
            fillTaskTable();
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
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
