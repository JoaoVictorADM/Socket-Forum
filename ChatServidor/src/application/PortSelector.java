package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PortSelector extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Digite a porta para iniciar o servidor:");
        TextField portField = new TextField();
        Button startButton = new Button("Iniciar Servidor");

        VBox root = new VBox(10, label, portField, startButton);
        root.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(root, 300, 150);

        startButton.setOnAction(e -> {
            try {
                int port = Integer.parseInt(portField.getText());
                new EchoServerTCP_Thread_Server(port).start();

                // Abrir GUI de clientes conectados
                EchoServerTCP_GUI.show();
                // Fechar tela de seleção de porta
                primaryStage.close();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Porta inválida!");
                alert.showAndWait();
            }
        });

        primaryStage.setTitle("Configuração do Servidor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
