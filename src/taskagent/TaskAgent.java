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
    public static Users logged = null;
    public static int user_state;
    public static int actual_option;
    @Override
    public void start(Stage stage) throws Exception {
        
        open_window("/TaskAgent/FXMLLogin.fxml","Login");
    }
    
   /* public static void login(String login, String password) 
    {
        try {
            dbc.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(TaskAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TaskAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       ResultSet check = dbc.Query("Select id_groups from user where login="+login+" and password="+password+";");                
        dbc.close();
       try {
            if(check.next()) {
                id_groups = check.getInt(4);
                user_state = 0;
                actual_option = 0;
                logged = new Users(check.getInt(1), check.getString(2), check.getString(5), check.getString(3), check.getString(6), id_groups);
                switch (id_groups) {
                    case 1:
                        open_window("/TaskAgent/admin.fxml","Administrator");
                        break;
                    case 2:
                        open_window("/TaskAgent/supervisor.fxml","Supervisor");
                        break;
                    case 3:
                        open_window("/TaskAgent/user.fxml","User");
                        break;
                }
                
            } else {
                alert("Error","Złe dane");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TaskAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
   

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
             alert("Error","Nie można otwożyć okna "+e.getLocalizedMessage().toString());
         }
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}