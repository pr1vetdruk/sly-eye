package ru.privetdruk.slyeye.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Set;
import java.util.TreeSet;

public class Setting {
    private final IntegerProperty blinkReminder;
    private final Set<Exercise> exerciseData;

    public Setting() {
        this(null);
    }

    public Setting(Set<Exercise> exerciseData) {
        blinkReminder = new SimpleIntegerProperty(20);
        this.exerciseData = exerciseData == null ? new TreeSet<>() : exerciseData;
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

    public Set<Exercise> getExerciseData() {
        return exerciseData;
    }
}
