package ru.privetdruk.slyeye.adapter.xml;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;

public class LocalTimeOPXMLAdapter extends XmlAdapter<String, ObjectProperty<LocalTime>> {
    @Override
    public ObjectProperty<LocalTime> unmarshal(String v) throws Exception {
        return new SimpleObjectProperty<>(LocalTime.parse(v));
    }

    @Override
    public String marshal(ObjectProperty<LocalTime> v) throws Exception {
        return v.get().toString();
    }
}
