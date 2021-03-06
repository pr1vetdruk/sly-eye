package ru.privetdruk.slyeye;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.privetdruk.slyeye.controller.Configurable;
import ru.privetdruk.slyeye.model.Setting;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private Stage stage;
    private BorderPane rootLayout;

    private Setting settings;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        configureStage(stage);
        initRootLayout();
        initControl();
        initSettings();
    }

    public void showStage() {
        if (stage != null) {
            stage.show();
            stage.toFront();
        }
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Application.class.getResource("/view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initControl() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Application.class.getResource("/view/Control.fxml"));
            AnchorPane controlView = loader.load();
            rootLayout.setTop(controlView);

            configureController(loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSettings() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Application.class.getResource("/view/Setting.fxml"));
            AnchorPane settingView = loader.load();
            rootLayout.setBottom(settingView);

            configureController(loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureStage(Stage stage) {
        this.stage = stage;
        this.stage.initStyle(StageStyle.TRANSPARENT);
        this.stage.setTitle("SlyEye");
        this.stage.getIcons().add(new javafx.scene.image.Image(Application.class.getResourceAsStream("/icon/eye-watch-icon-64.png")));
    }

    private void configureController(FXMLLoader loader) {
        Configurable<Application> controller = loader.getController();
        controller.configure(this);
    }

    public Stage getStage() {
        return stage;
    }

    public Setting getSettings() {
        return settings;
    }

    public void setSettings(Setting settings) {
        this.settings = settings;
    }
}
