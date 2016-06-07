
package Logic;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class WindowsOpener { // klasa odpowiedzialna za otwieranie i zamykanie okienek
    public static Stage current;
    public static void alert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static void close_window() {//metoda odpowiedzialna za zamykanie okna
        Stage stage = (Stage) current.getScene().getWindow();
        stage.close();
    }
    
    public static void logout() {//metoda odpowiedzialna za wylogowywanie
        open("/TaskAgent/FXMLLogin.fxml","Login.fxml", false);
    }

    public static void open(String window, String title, boolean resize) {//metoda odpowiedzialna za otwieranie okna
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(WindowsOpener.class.getClass().getResource(window));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));  
            stage.show();
            stage.setTitle(title);
            stage.setResizable(false);
            if(current != null) {
                close_window();
            }
            current = stage;
         } catch(Exception e) {
             alert("Error","Nie można otwożyć okna " + e.getMessage());
         }
    }
}
