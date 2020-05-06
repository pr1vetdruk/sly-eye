package ru.privetdruk.slyeye;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("SlyEye");

        initRootLayout();
        initControl();
        initSettings();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Application.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initControl() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Application.class.getResource("view/ControlView.fxml"));
            AnchorPane controlView = loader.load();

            rootLayout.setTop(controlView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSettings() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Application.class.getResource("view/SettingView.fxml"));
            AnchorPane settingView = loader.load();

            rootLayout.setBottom(settingView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
