package ru.privetdruk.slyeye.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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

    private Exercise exercise;

    @Override
    public void configure(Exercise selectedItem) {
        exercise = selectedItem;

        LocalTime exerciseTime = exercise.getExerciseTime();
        timeLabel.setText(DateTimeUtil.convertTimeToString(exerciseTime));
        hourSlider.setValue(exerciseTime.getHour());
        minuteSlider.setValue(exerciseTime.getMinute());
    }

    public Exercise getExercise() {
        return exercise;
    }
}
