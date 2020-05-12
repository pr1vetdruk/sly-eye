package ru.privetdruk.slyeye.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import java.time.LocalTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        if (!Objects.equals(exerciseId, exercise.exerciseId)) return false;
        return Objects.equals(exerciseTime, exercise.exerciseTime);
    }

    @Override
    public int hashCode() {
        int result = exerciseId != null ? exerciseId.hashCode() : 0;
        result = 31 * result + (exerciseTime != null ? exerciseTime.hashCode() : 0);
        return result;
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
