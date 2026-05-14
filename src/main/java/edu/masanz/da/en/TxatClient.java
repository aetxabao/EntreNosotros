package edu.masanz.da.en;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TxatClient extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                TxatClient.class.getResource("/edu/masanz/da/en/txat-client.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Txat client");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}