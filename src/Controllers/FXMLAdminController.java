package Controllers;

import Logic.Querys;
import Logic.WindowsOpener;
import Models.Task;
import Models.User;
import static TaskAgent.TaskAgent.db;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLAdminController {

    @FXML
    private ComboBox<?> Supervisor;

    @FXML
    private ComboBox<?> Group;

    @FXML
    private TextField Email;

    @FXML
    private TextField FirstName;

    @FXML
    private Button Back;

    @FXML
    private TextField LastName;

    @FXML
    private TextField Login;

    @FXML
    private Button Confrim;

    @FXML
    private PasswordField AddPassword;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private TextField task_name, task_desc;
    
    @FXML
    private ComboBox<User> task_supervisior;

    @FXML
    private Button Tasks;

    @FXML
    private Label LogAs;

    @FXML
    private Button Logout;

    @FXML
    private Button Users;
   
    @FXML
    private TableView<Task> task_table = new TableView<>();
    @FXML
    private ObservableList<Task> data1 = FXCollections.observableArrayList();
    @FXML
    public TableColumn colName, colDesc;

    @FXML
    void HandleLogoutButtonAction(ActionEvent event) {
        WindowsOpener.logout();
    }

    @FXML
    void addTaskButton(ActionEvent event) {
        String si;
        String name = task_name.getText();
        String desc = task_desc.getText();
        int supervisor = task_supervisior.getSelectionModel().getSelectedItem().user_id;
        
        Querys.addTask(name, desc, supervisor);
    }

    void fillTaskTable() {
        ResultSet p = db.Query("SELECT * FROM tasks");
        data1.clear();
        task_table.setEditable(true);
        try {
            
            while (p.next()) {
                data1.add(new Task(p.getInt("id"),p.getString("name"),p.getString("desc")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        task_table.setItems(data1);
    }

    @FXML
    void HandleEditUserButtonAction(ActionEvent event) {
        WindowsOpener.open("/TaskAgent/FxmlEditUser.fxml", "Edit User", true);
    }

    @FXML
    void HandleTasksButtonAction(ActionEvent event) {
        WindowsOpener.open("/TaskAgent/FXMLTasks.fxml", "Tasks", true);
    }

    @FXML
    void HandleBackButtonAction(ActionEvent event) {
        WindowsOpener.open("/TaskAgent/FXMLAdmin.fxml", "Administrator", false);
    }
    
    @FXML
    void HandleAddUserButtonAction(ActionEvent event){
        WindowsOpener.open("/TaskAgent/FXMLaddUser.fxml","Add User",false);
    }
    @FXML
    void AddUserHandler(ActionEvent event){
        
      //  Querys.AddUser(FirstName, LastName, Login, AddPassword, Email, 2,  Supervisor.getSelectionModel().getSelectedItem().Supervisor);
    }

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    

}
