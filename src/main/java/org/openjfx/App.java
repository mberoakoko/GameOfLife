package org.openjfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        MainView main = new MainView();
        var scene = new Scene(main, 640, 480);
        stage.setScene(scene);
        stage.show();
        main.draw();

    }

    public static void main(String[] args) {
        launch();
    }

}