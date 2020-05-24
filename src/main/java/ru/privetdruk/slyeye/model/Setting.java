package ru.privetdruk.slyeye.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Setting {
    private final IntegerProperty blinkReminder;
    private final ObservableList<Exercise> exerciseData;

    public Setting() {
        this(null);
    }

    public Setting(ObservableList<Exercise> exerciseData) {
        blinkReminder = new SimpleIntegerProperty(20);
        this.exerciseData = exerciseData == null ? FXCollections.observableArrayList() : exerciseData;
    }

    public int getBlinkReminder() {
        return blinkReminder.get();
    }

    public IntegerProperty blinkReminderProperty() {
        return blinkReminder;
    }

    public void setBlinkReminder(int blinkReminder) {
        this.blinkReminder.set(blinkReminder);
    }

    public ObservableList<Exercise> getExerciseData() {
        return exerciseData;
    }
}
