package ru.privetdruk.slyeye.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Settings")
@XmlType(propOrder = {"blinkReminder", "exerciseData"})
public class Setting {
    private IntegerProperty blinkReminder;

    private ObservableList<Exercise> exerciseData;

    public Setting() {
        this(null);
    }

    public Setting(ObservableList<Exercise> exerciseData) {
        blinkReminder = new SimpleIntegerProperty(20);
        this.exerciseData = exerciseData == null ? FXCollections.observableArrayList() : exerciseData;
    }

    @XmlElement(name = "BlinkReminder")
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

    @XmlElementWrapper(name = "ExerciseData")
    @XmlElement(type = Exercise.class, name = "Exercise")
    public void setExerciseData(ObservableList<Exercise> exerciseData) {
        this.exerciseData = exerciseData;
    }
}
