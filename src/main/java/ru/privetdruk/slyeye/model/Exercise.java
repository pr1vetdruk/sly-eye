package ru.privetdruk.slyeye.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalTime;
import java.util.Objects;

public class Exercise implements Comparable<Exercise> {
    private final ObjectProperty<LocalTime> exerciseTime;

    public Exercise() {
        this(LocalTime.of(0, 0));
    }

    public Exercise(LocalTime localTime) {
        this.exerciseTime = new SimpleObjectProperty<>(localTime);
    }

    public Exercise(Exercise exercise) {
        exerciseTime = new SimpleObjectProperty<>(exercise.exerciseTime.get());
    }

    @Override
    public int compareTo(Exercise o) {
        return this.exerciseTime.get().compareTo(o.exerciseTime.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        return Objects.equals(exerciseTime, exercise.exerciseTime);
    }

    @Override
    public int hashCode() {
        return exerciseTime != null ? exerciseTime.hashCode() : 0;
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
