package Controllers;

import Logic.Querys;
import Logic.WindowsOpener;
import Models.User;
import static TaskAgent.TaskAgent.actual_option;
import static TaskAgent.TaskAgent.id_groups;
import static TaskAgent.TaskAgent.user_state;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class FXMLRaportController implements Initializable {
    @FXML
    private ComboBox raport_option;
    
    @FXML
    private void generate(ActionEvent event) throws SQLException {
        User user = Querys.getUserById(actual_option);
        int sort = raport_option.getSelectionModel().getSelectedIndex();
        Creator C = new Creator(user, true, sort);
        actual_option = 0;
        showTasks(event);
    }
    
    @FXML
    private void showTasks(ActionEvent event) {
        if(id_groups == 2) {
            user_state = 1;
            WindowsOpener.open("/TaskAgent/FXMLSupervisor.fxml", "Supervisor", false);
        } else {
            user_state = 3;
            WindowsOpener.open("/TaskAgent/FXMLUsers.fxml", "Użytkownicy", true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] states = {"Brak", "Sortowanie datami", "Sortowanie statusami"};
        raport_option.getItems().addAll(states);
        raport_option.getSelectionModel().select(0);
    }    
    
}
