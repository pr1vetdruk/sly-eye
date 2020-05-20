package ru.privetdruk.slyeye.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import ru.privetdruk.slyeye.model.Exercise;
import ru.privetdruk.slyeye.util.DateTimeUtil;

import java.time.LocalTime;

public class ExerciseDialogController implements Configurable<Exercise> {
    @FXML
    private Label timeLabel;

    @FXML
    private Slider hourSlider;

    @FXML
    private Slider minuteSlider;

    private Exercise exercise = Exercise.generateEmpty();
    private LocalTime exerciseTime = exercise.getExerciseTime();

    @FXML
    private void initialize() {
        hourSlider.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            exerciseTime = LocalTime.of(newValue.intValue(), exerciseTime.getMinute());
            updateTimeLabel();
        }));

        minuteSlider.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            exerciseTime = LocalTime.of(exerciseTime.getHour(), newValue.intValue());
            updateTimeLabel();
        }));
    }

    @FXML
    private void onClickSave() {
        exercise.setExerciseTime(exerciseTime);
        closeWindow();
    }

    @FXML
    private void onClickClose() {
        closeWindow();
    }

    @Override
    public void configure(Exercise selectedItem) {
        exercise = selectedItem;
        exerciseTime = exercise.getExerciseTime();

        updateTimeLabel();
        hourSlider.setValue(exerciseTime.getHour());
        minuteSlider.setValue(exerciseTime.getMinute());
    }

    private void updateTimeLabel() {
        timeLabel.setText(DateTimeUtil.convertTimeToString(exerciseTime));
    }

    private void closeWindow() {
        Stage window = (Stage) timeLabel.getScene().getWindow();
        window.close();
    }

    public Exercise getExercise() {
        return exercise;
    }
}
