package ru.privetdruk.slyeye.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import java.time.LocalTime;

public class Exercise implements Comparable<Exercise> {
    private final IntegerProperty exerciseId;
    private final ObjectProperty<LocalTime> exerciseTime;

    public Exercise() {
        this(null, null);
    }

    public Exercise(IntegerProperty exerciseId, ObjectProperty<LocalTime> exerciseTime) {
        this.exerciseId = exerciseId;
        this.exerciseTime = exerciseTime;
    }

    @Override
    public int compareTo(Exercise o) {
        return Integer.compare(this.exerciseId.get(), o.exerciseId.get());
    }

    public int getExerciseId() {
        return exerciseId.get();
    }

    public IntegerProperty exerciseIdProperty() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId.set(exerciseId);
    }

    public LocalTime getExerciseTime() {
        return exerciseTime.get();
    }

    public ObjectProperty<LocalTime> exerciseTimeProperty() {
        return exerciseTime;
    }

    public void setExerciseTime(LocalTime exerciseTime) {
        this.exerciseTime.set(exerciseTime);
    }
}
