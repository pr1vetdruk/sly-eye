package ru.privetdruk.slyeye.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.privetdruk.slyeye.adapter.xml.IntegerPropertyXMLAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Settings")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = {"blinkReminder", "exerciseData"})
public class Setting {
    @XmlElement(name = "BlinkReminder")
    @XmlJavaTypeAdapter(IntegerPropertyXMLAdapter.class)
    private IntegerProperty blinkReminder;

    @XmlElement(name = "ExerciseData")
    private ObservableList<Exercise> exerciseData;

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

    public void setExerciseData(ObservableList<Exercise> exerciseData) {
        this.exerciseData = exerciseData;
    }
}
