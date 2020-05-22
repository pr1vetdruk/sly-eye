package ru.privetdruk.slyeye.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import ru.privetdruk.slyeye.Application;
import ru.privetdruk.slyeye.model.Setting;
import ru.privetdruk.slyeye.util.NotificationUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ControlController implements Configurable<Application> {
    private Application application;
    private Setting settings;

    private final ScheduledExecutorService scheduledNotificationService = Executors.newScheduledThreadPool(4);
    private List<ScheduledFuture<?>> scheduleList = new ArrayList<>();

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
        scheduleList.forEach(task -> task.cancel(false));
        scheduleList.clear();
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
        scheduleList.add(scheduledNotificationService.scheduleAtFixedRate(() -> {
            SwingUtilities.invokeLater(() -> {
                        trayIcon.displayMessage(
                                "Hey!",
                                "it's time to get distracted",
                                TrayIcon.MessageType.INFO
                        );
                    }
            );
        }, 1, settings.getBlinkReminder(), TimeUnit.MINUTES));
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
        scheduledNotificationService.shutdown();
        Platform.exit();
        if (tray != null && trayIcon != null) {
            tray.remove(trayIcon);
        }
    }
}
