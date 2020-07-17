package ru.privetdruk.slyeye.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import ru.privetdruk.slyeye.Application;
import ru.privetdruk.slyeye.concurrent.TaskExecutor;
import ru.privetdruk.slyeye.concurrent.TaskTime;
import ru.privetdruk.slyeye.util.NotificationUtil;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ControlController implements Configurable<Application> {
    @SuppressWarnings("deprecation")
    private static final AudioClip audioNotification = Applet.newAudioClip(Application.class.getResource("/sound/MainNotification.wav"));

    @FXML
    private javafx.scene.control.Button runButton;
    @FXML
    private javafx.scene.control.Button stopButton;

    private Application application;

    private final TaskExecutor taskExecutor = new TaskExecutor();

    private SystemTray tray;
    private TrayIcon trayIcon;

    @Override
    public void configure(Application application) {
        this.application = application;
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
        onClickMinimize();
    }

    @FXML
    private void onClickStop() {
        runButton.setDisable(false);
        stopButton.setDisable(true);
        taskExecutor.cancel();
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
        class Notification implements Runnable {
            private final String text;

            public Notification(String text) {
                this.text = text;
            }

            @Override
            public void run() {
                trayIcon.displayMessage(
                        "Hey!",
                        text,
                        TrayIcon.MessageType.INFO
                );

                audioNotification.play();
            }
        }

        if (application.getSettings().getBlinkReminder() > 0) {
            int blinkReminder = application.getSettings().getBlinkReminder();

            taskExecutor.startExecution(
                    new Notification("it's time to get distracted"),
                    blinkReminder,
                    blinkReminder,
                    TimeUnit.MINUTES);
        }

        application.getSettings().getExerciseData().forEach(item -> {
            taskExecutor.startExecution(new Notification("it's time to do the exercises"), new TaskTime(item.getExerciseTime()));
        });
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
        taskExecutor.stop();

        Platform.exit();
        if (tray != null && trayIcon != null) {
            tray.remove(trayIcon);
        }
    }
}
