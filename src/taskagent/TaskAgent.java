package TaskAgent;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TaskAgent extends Application {
    public static Stage current;
   // public static DBConnection dbc;
    public static int id_groups;
    public static int user_id;
    public static int user_state;
    public static int actual_option;
    @Override
    public void start(Stage stage) throws Exception {
        
        open_window("/TaskAgent/FXMLLogin.fxml","Login");
    }
    
  
             //   logged = new Users(check.getInt(1), check.getString(2), check.getString(5), check.getString(3), check.getString(6), id_groups);
             
    
   

    public static void alert(String title, String text) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static void close_window() {
        Stage stage = (Stage) current.getScene().getWindow();
        stage.close();
    }
    
    public static void logout() {
        open_window("/TaskAgent/FXMLLogin.fxml","FXMLLogin.fxml");
    }
     public static void open_window(String window, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TaskAgent.class.getClass().getResource(window));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
            stage.setTitle(title);
            if(current != null) {
                close_window();
            }
            current = stage;
         } catch(Exception e) {
             alert("Error","Nie można otwożyć okna "+e.getLocalizedMessage());
         }
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}