package ru.privetdruk.slyeye.model;

import ru.privetdruk.slyeye.adapter.xml.LocalTimeXMLAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalTime;
import java.util.List;

@XmlRootElement(name = "NotificationSettings")
@XmlType(propOrder = {"blinkReminder", "localTime"})
public class NotificationSettings {
    private int blinkReminder;

    private List<LocalTime> localTime;

    public int getBlinkReminder() {
        return blinkReminder;
    }

    @XmlElement
    public void setBlinkReminder(int blinkReminder) {
        this.blinkReminder = blinkReminder;
    }

    public List<LocalTime> getLocalTime() {
        return localTime;
    }

    @XmlElementWrapper(name="exercise")
    @XmlElement(name="time")
    @XmlJavaTypeAdapter(value = LocalTimeXMLAdapter.class)
    public void setLocalTime(List<LocalTime> localTime) {
        this.localTime = localTime;
    }
}

