package TaskAgent;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import static Logic.WindowsOpener.*;
import Models.User;

public class TaskAgent extends Application {
    
    public static DBConnection db;
    public static int id_groups;
    public static int user_id;
    public static int user_state = 0;
    public static User user;
    public static int actual_option = 0;
    public static String[] task_state = {"Otwarte", "Zamknięte", "Zakończone", "W trakcie realizacji"};

    @Override
    public void start(Stage stage) throws Exception {
        db = new DBConnection();
        open("/TaskAgent/FXMLLogin.fxml","Login",false);
    }
  
    public static void main(String[] args) {
        launch(args);
    }
    
}