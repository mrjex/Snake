package com.group12;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGame extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/StartMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);


    }

    public static void main(String[] args) {
        launch();
    }
}