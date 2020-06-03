package ru.privetdruk.slyeye.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import ru.privetdruk.slyeye.Application;
import ru.privetdruk.slyeye.model.Setting;
import ru.privetdruk.slyeye.util.NotificationUtil;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ControlController implements Configurable<Application> {
    @SuppressWarnings("deprecation")
    private static final AudioClip audioNotification = Applet.newAudioClip(Application.class.getResource("/sound/MainNotification.wav"));

    @FXML
    private javafx.scene.control.Button runButton;
    @FXML
    private javafx.scene.control.Button stopButton;

    private Application application;
    private Setting settings;

    private final ScheduledExecutorService scheduledNotificationService = Executors.newScheduledThreadPool(4);
    private final List<ScheduledFuture<?>> scheduleList = new ArrayList<>();

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
        runButton.setDisable(true);
        stopButton.setDisable(false);
        addNotificationSchedule();
    }

    @FXML
    private void onClickStop() {
        runButton.setDisable(false);
        stopButton.setDisable(true);
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

            if (!SystemTray.isSupported()) {
                NotificationUtil.showError("", "No system tray support, application exiting.");
                Platform.exit();
            }

            tray = SystemTray.getSystemTray();

            trayIcon = configureTrayIcon();
            trayIcon.setPopupMenu(configurePopupMenu());

            tray.add(trayIcon);
        } catch (AWTException e) {
            NotificationUtil.showError("", "Unable to init system tray.");
        }
    }

    private void addNotificationSchedule() {
        if (settings.getBlinkReminder() > 0) {
            scheduleList.add(scheduledNotificationService.scheduleAtFixedRate(() -> {
                SwingUtilities.invokeLater(() -> {
                    trayIcon.displayMessage(
                            "Hey!",
                            "it's time to get distracted",
                            TrayIcon.MessageType.INFO
                    );

                    audioNotification.play();
                });
            }, 1, settings.getBlinkReminder(), TimeUnit.MINUTES));
        }
    }

    private TrayIcon configureTrayIcon() {
        URL iconUrl = Application.class.getResource("/icon/eye-watch-icon-16.png");
        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(iconUrl), "I'm watching you <_<");

        trayIcon.addActionListener(event -> Platform.runLater(application::showStage));

        return trayIcon;
    }

    private PopupMenu configurePopupMenu() {
        MenuItem maximizeItem = new MenuItem("Maximize");
        maximizeItem.addActionListener(event -> Platform.runLater(application::showStage));

        Font monospacedFont = Font.decode(Font.MONOSPACED);
        Font boldFont = monospacedFont.deriveFont(Font.BOLD);
        maximizeItem.setFont(boldFont);

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setFont(monospacedFont);
        exitItem.addActionListener(event -> {
            exit();
        });

        PopupMenu popup = new PopupMenu();
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
