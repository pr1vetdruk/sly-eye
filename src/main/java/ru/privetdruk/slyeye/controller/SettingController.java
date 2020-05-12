package ru.privetdruk.slyeye.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.privetdruk.slyeye.model.Exercise;
import ru.privetdruk.slyeye.model.Setting;

import java.time.LocalTime;

public class SettingController {
    @FXML
    private TextField blinkReminderTF;
    @FXML
    private Slider blinkReminderSlider;

    @FXML
    private Button deleteExerciseButton;
    @FXML
    private TableView<Exercise> exerciseTable;
    @FXML
    private TableColumn<Exercise, Integer> exerciseIdColumn;
    @FXML
    private TableColumn<Exercise, LocalTime> exerciseTimeColumn;

    private final Setting settings = new Setting();

    @FXML
    private void initialize() {
        addListener();
        loadTestData();
        fillingOutForms();
    }

    @FXML
    private void onClickDelete() {
        Exercise selectedItem = exerciseTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            settings.getExerciseData().remove(selectedItem);
            exerciseTable.getItems().remove(selectedItem);

            deleteExerciseButton.setDisable(settings.getExerciseData().isEmpty());
        }
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
                deleteExerciseButton.setDisable(false);
            }
        });
    }

    private void fillingOutForms() {
        blinkReminderTF.setText(String.valueOf(settings.getBlinkReminder()));
        exerciseTable.setItems(FXCollections.observableArrayList(settings.getExerciseData()));
        exerciseIdColumn.setCellValueFactory(cellData -> cellData.getValue().exerciseIdProperty().asObject());
        exerciseTimeColumn.setCellValueFactory(cellData -> cellData.getValue().exerciseTimeProperty());
    }

    private void loadTestData() {
        int count = 0, hour = 4, gap = 6, min = 20;

        settings.getExerciseData().add(new Exercise(new SimpleIntegerProperty(++count), new SimpleObjectProperty<>(LocalTime.of((hour += gap), min))));
        settings.getExerciseData().add(new Exercise(new SimpleIntegerProperty(++count), new SimpleObjectProperty<>(LocalTime.of((hour += gap), min))));
        settings.getExerciseData().add(new Exercise(new SimpleIntegerProperty(++count), new SimpleObjectProperty<>(LocalTime.of((hour += gap), min))));
    }
}
