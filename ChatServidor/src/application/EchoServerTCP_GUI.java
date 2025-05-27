package application;

import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.Scene;

//Antes: public class EchoServerTCP_GUI extends Application {
public class EchoServerTCP_GUI {
 public static ListView<String> clientListView = new ListView<>();

 public static void show() {
     Platform.runLater(() -> {
         VBox root = new VBox(clientListView);
         Scene scene = new Scene(root, 300, 400);
         Stage stage = new Stage();
         stage.setTitle("Usuários Conectados");
         stage.setScene(scene);
         stage.show();
     });
 }

 public static void addClient(String ip) {
     Platform.runLater(() -> {
         if (!clientListView.getItems().contains(ip)) {
             clientListView.getItems().add(ip);
         }
     });
 }

 public static void removeClient(String ip) {
     Platform.runLater(() -> clientListView.getItems().remove(ip));
 }
}
