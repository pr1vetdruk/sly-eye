package ru.privetdruk.slyeye.util;

import javafx.scene.control.Alert;

public class NotificationUtil {
    private NotificationUtil() {

    }

    public static void show(Alert.AlertType typeMessage, String headerText, String contentText) {
        Alert errorAlert = new Alert(typeMessage);
        errorAlert.setHeaderText(headerText);
        errorAlert.setContentText(contentText);
        errorAlert.showAndWait();
    }

    public static void showError(String headerText, String contentText) {
        show(Alert.AlertType.ERROR, headerText, contentText);
    }
}
