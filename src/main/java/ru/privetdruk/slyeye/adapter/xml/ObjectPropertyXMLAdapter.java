package ru.privetdruk.slyeye.adapter.xml;

import javafx.beans.property.ObjectProperty;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;

public class ObjectPropertyXMLAdapter extends XmlAdapter<String, ObjectProperty<LocalTime>> {

    @Override
    public ObjectProperty<LocalTime> unmarshal(String v) throws Exception {
        return null;
    }

    @Override
    public String marshal(ObjectProperty<LocalTime> v) throws Exception {
        return null;
    }
}
