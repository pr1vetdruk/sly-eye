package ru.privetdruk.slyeye.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import ru.privetdruk.slyeye.Application;
import ru.privetdruk.slyeye.model.Setting;
import ru.privetdruk.slyeye.util.NotificationUtil;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ControlController implements Configurable {
    private Application application;
    private Setting settings;

    private Timer notificationTimer = new Timer();
    private SystemTray tray;
    private TrayIcon trayIcon;

    @Override
    public void configure(Application application) {
        this.application = application;
        settings = application.getSettings();
    }

    @FXML
    private void initialize() {
        Platform.setImplicitExit(false);
        Platform.runLater(this::minimizeToTray);
    }

    @FXML
    private void onClickRun() {
        application.setRun(true);
        addNotificationSchedule();
    }

    @FXML
    private void onClickStop() {
        application.setRun(false);
        notificationTimer.cancel();
        notificationTimer = new Timer();
    }

    @FXML
    private void onClickMinimize() {
        application.getStage().hide();
    }

    @FXML
    private void onClickExit() {
        exit();
    }

    private void minimizeToTray() {
        try {
            Toolkit.getDefaultToolkit();

            if (!java.awt.SystemTray.isSupported()) {
                NotificationUtil.showError("", "No system tray support, application exiting.");
                Platform.exit();
            }

            tray = SystemTray.getSystemTray();

            trayIcon = configureTrayIcon();
            trayIcon.setPopupMenu(configurePopupMenu());

            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            NotificationUtil.showError("", "Unable to init system tray.");
        }
    }

    private void addNotificationSchedule() {
        notificationTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        javax.swing.SwingUtilities.invokeLater(() -> {
                                    trayIcon.displayMessage(
                                            "Hey!",
                                            "it's time to get distracted",
                                            TrayIcon.MessageType.INFO
                                    );
                                }
                        );
                    }
                },
                1_000,
                TimeUnit.MINUTES.toMillis(settings.getBlinkReminder())
        );

    }

    private TrayIcon configureTrayIcon() throws IOException {
        URL iconUrl = Application.class.getResource("/icon/eye-watch-icon-16.png");
        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(iconUrl), "I'm watching you <_<");

        trayIcon.addActionListener(event -> Platform.runLater(application::showStage));

        return trayIcon;
    }

    private PopupMenu configurePopupMenu() {
        java.awt.MenuItem maximizeItem = new java.awt.MenuItem("Maximize");
        maximizeItem.addActionListener(event -> Platform.runLater(application::showStage));

        java.awt.Font monospacedFont = java.awt.Font.decode(Font.MONOSPACED);
        java.awt.Font boldFont = monospacedFont.deriveFont(java.awt.Font.BOLD);
        maximizeItem.setFont(boldFont);

        java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
        exitItem.setFont(monospacedFont);
        exitItem.addActionListener(event -> {
            exit();
        });

        final java.awt.PopupMenu popup = new java.awt.PopupMenu();
        popup.add(maximizeItem);
        popup.addSeparator();
        popup.add(exitItem);

        return popup;
    }

    private void exit() {
        notificationTimer.cancel();
        Platform.exit();
        if (tray != null && trayIcon != null) {
            tray.remove(trayIcon);
        }
    }
}
