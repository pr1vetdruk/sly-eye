package ru.privetdruk.slyeye.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import ru.privetdruk.slyeye.Application;
import ru.privetdruk.slyeye.util.NotificationUtil;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class ControlController {
    private Application application;

    private final Timer notificationTimer = new Timer();

    public void setApplication(Application application) {
        this.application = application;
    }

    @FXML
    private void initialize() {
        Platform.setImplicitExit(false);
        Platform.runLater(this::minimizeToTray);
    }

    @FXML
    public void onClickMinimize() {
        application.getStage().hide();
    }

    public void minimizeToTray() {
        try {
            Toolkit.getDefaultToolkit();

            if (!java.awt.SystemTray.isSupported()) {
                NotificationUtil.showError("", "No system tray support, application exiting.");
                Platform.exit();
            }

            SystemTray tray = SystemTray.getSystemTray();

            TrayIcon trayIcon = configureTrayIcon();
            trayIcon.setPopupMenu(configurePopupMenu(tray, trayIcon));
            addNotificationSchedule(trayIcon);

            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            NotificationUtil.showError("", "Unable to init system tray.");
        }
    }

    private void addNotificationSchedule(TrayIcon trayIcon) {
        notificationTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        javax.swing.SwingUtilities.invokeLater(() ->
                                trayIcon.displayMessage(
                                        "Hey!",
                                        "it's time to get distracted",
                                        TrayIcon.MessageType.INFO
                                )
                        );
                    }
                },
                5_000,
                60_000
        );
    }

    private TrayIcon configureTrayIcon() throws IOException {
        URL iconUrl = Application.class.getResource("/icon/eye-watch-icon-16.png");
        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(iconUrl), "I'm watching you <_<");

        trayIcon.addActionListener(event -> Platform.runLater(application::showStage));

        return trayIcon;
    }

    private PopupMenu configurePopupMenu(SystemTray tray, TrayIcon trayIcon) {
        java.awt.MenuItem maximizeItem = new java.awt.MenuItem("Maximize");
        maximizeItem.addActionListener(event -> Platform.runLater(application::showStage));

        java.awt.Font monospacedFont = java.awt.Font.decode(Font.MONOSPACED);
        java.awt.Font boldFont = monospacedFont.deriveFont(java.awt.Font.BOLD);
        maximizeItem.setFont(boldFont);

        java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
        exitItem.setFont(monospacedFont);
        exitItem.addActionListener(event -> {
            notificationTimer.cancel();
            Platform.exit();
            tray.remove(trayIcon);
        });

        final java.awt.PopupMenu popup = new java.awt.PopupMenu();
        popup.add(maximizeItem);
        popup.addSeparator();
        popup.add(exitItem);

        return popup;
    }


}