package Controllers;

import Logic.Querys;
import Logic.WindowsOpener;
import Models.User;
import static TaskAgent.TaskAgent.actual_option;
import static TaskAgent.TaskAgent.id_groups;
import static TaskAgent.TaskAgent.user_state;
import static TaskAgent.TaskAgent.user;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

public class FXMLRaportController implements Initializable {
    @FXML
    private ComboBox raport_option;
    @FXML
    private CheckBox user_info, task_date, supervisor, task_status, task_desc;
    
    public int boolToInt(boolean value) {
        if(value)
            return 1;
        return 0;
    }
    @FXML
    private void generate(ActionEvent event) throws SQLException {
        int[] options = {boolToInt(user_info.isSelected()),boolToInt(task_date.isSelected()),boolToInt(supervisor.isSelected()),boolToInt(task_status.isSelected()),boolToInt(task_desc.isSelected())};
        if(id_groups == 1) {
            int sort = raport_option.getSelectionModel().getSelectedIndex();
            Creator C = new Creator(user, true, sort, options);
            actual_option = 0;
            closeWindow(event);
            return;
        }
        if(actual_option > 0) {
            User user = Querys.getUserById(actual_option);
            int sort = raport_option.getSelectionModel().getSelectedIndex();
            Creator C = new Creator(user, true, sort, options);
            actual_option = 0;
            closeWindow(event);
        } else {
            int sort = raport_option.getSelectionModel().getSelectedIndex();
            Creator C = new Creator(sort, options);
            System.out.println("Raport główny");
            closeWindow(event);
        }
    }
    
    @FXML
    private void closeWindow(ActionEvent event) {
        if(id_groups == 1) {
            user_state = 0;
            WindowsOpener.open("/TaskAgent/FXMLUser.fxml", "Kierownik", false);
        } else if(id_groups == 2) {
            user_state = 1;
            WindowsOpener.open("/TaskAgent/FXMLSupervisor.fxml", "Kierownik", false);
        } else {
            if(user_state == 21) {
                user_state = 3;
                WindowsOpener.open("/TaskAgent/FXMLUsers.fxml", "Użytkownicy", true);
            } else {
                user_state = 1;
                WindowsOpener.open("/TaskAgent/FXMLTasks.fxml", "Zadania", true);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        task_status.setSelected(true);
        task_desc.setSelected(true);
        user_info.setSelected(true);
        task_date.setSelected(true);
        supervisor.setSelected(true);
        String[] states = {"Brak", "Sortowanie datami", "Sortowanie statusami", "Sortowanie kierownikiem"};
        raport_option.getItems().addAll(states);
        raport_option.getSelectionModel().select(0);
    }    
    
}
