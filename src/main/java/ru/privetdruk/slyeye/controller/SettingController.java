package ru.privetdruk.slyeye.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ru.privetdruk.slyeye.Application;
import ru.privetdruk.slyeye.model.Exercise;
import ru.privetdruk.slyeye.model.Setting;
import ru.privetdruk.slyeye.util.XMLUtil;

import java.io.IOException;
import java.time.LocalTime;
import java.util.stream.Collectors;

public class SettingController implements Configurable<Application> {
    @FXML
    private TextField blinkReminderTF;
    @FXML
    private Slider blinkReminderSlider;

    @FXML
    private Button editExerciseButton;
    @FXML
    private Button deleteExerciseButton;
    @FXML
    private TableView<Exercise> exerciseTable;
    @FXML
    private TableColumn<Exercise, LocalTime> exerciseTimeColumn;

    private Application application;

    @FXML
    private void onClickAdd() {
        Exercise newExercise = new Exercise();
        boolean isSave = showExerciseDialog(newExercise);
        if (isSave) {
            addExercise(newExercise);
        }
    }

    @FXML
    private void onClickEdit() {
        Exercise selectedItem = exerciseTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Exercise editExercise = new Exercise(selectedItem);
            boolean isSave = showExerciseDialog(editExercise);
            if (isSave && !selectedItem.equals(editExercise)) {
                exerciseTable.getItems().remove(selectedItem);
                addExercise(editExercise);
            }
        }
    }

    @FXML
    private void onClickDelete() {
        Exercise selectedItem = exerciseTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            exerciseTable.getItems().remove(selectedItem);
            saveSettings();

            boolean isEmptySettings = exerciseTable.getItems().isEmpty();
            editExerciseButton.setDisable(isEmptySettings);
            deleteExerciseButton.setDisable(isEmptySettings);
        }
    }

    @Override
    public void configure(Application application) {
        this.application = application;

        loadingSettings();
        addListener();
        fillingOutForms();
    }

    private void addExercise(Exercise exercise) {
        boolean isContains = exerciseTable.getItems().stream().anyMatch(item -> item.equals(exercise));

        if (!isContains) {
            exerciseTable.getItems().add(exercise);
            FXCollections.sort(exerciseTable.getItems());
            saveSettings();
        }
    }

    private void loadingSettings() {
        Setting settings = (Setting) XMLUtil.read("settings.xml", Setting.class);
        deleteDuplicateExercise(settings);
        application.setSettings(settings);
    }

    private void deleteDuplicateExercise(Setting settings) {
        long distinct = settings.getExerciseData().stream().distinct().count();
        long total = settings.getExerciseData().size();

        if (distinct != total) {
            ObservableList<Exercise> distinctExerciseData = FXCollections.observableArrayList(settings.getExerciseData().stream().distinct().collect(Collectors.toList()));
            settings.setExerciseData(distinctExerciseData);
            saveSettings();
        }
    }

    private void saveSettings() {
        XMLUtil.save(application.getSettings(), "settings.xml");
    }

    private void addListener() {
        blinkReminderSlider.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            blinkReminderTF.setText(Integer.toString(newValue.intValue()));
        }));

        blinkReminderTF.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            double value;
            try {
                value = Double.parseDouble(newValue);
                double min = blinkReminderSlider.getMin();
                double max = blinkReminderSlider.getMax();
                value = value < min ? min : Math.min(value, max);
            } catch (IllegalArgumentException e) {
                value = 0;
            }

            blinkReminderTF.setText(String.valueOf((int) value));
            blinkReminderSlider.setValue(value);
        }));

        exerciseTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (exerciseTable.getSelectionModel().getSelectedItem() != null) {
                editExerciseButton.setDisable(false);
                deleteExerciseButton.setDisable(false);
            }
        });
    }

    private void fillingOutForms() {
        blinkReminderTF.setText(String.valueOf(application.getSettings().getBlinkReminder()));
        exerciseTable.setItems(application.getSettings().getExerciseData());
        exerciseTimeColumn.setCellValueFactory(cellData -> cellData.getValue().exerciseTimeProperty());
    }

    private boolean showExerciseDialog(Exercise selectedItem) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Application.class.getResource("/view/ExerciseDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Exercise");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.initOwner(application.getStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ExerciseDialogController controller = loader.getController();
            controller.configure(selectedItem);

            dialogStage.showAndWait();

            return controller.isSave();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
