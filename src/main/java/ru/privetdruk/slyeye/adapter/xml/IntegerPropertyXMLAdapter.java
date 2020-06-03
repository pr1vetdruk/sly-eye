package ru.privetdruk.slyeye.adapter.xml;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class IntegerPropertyXMLAdapter extends XmlAdapter<Integer, IntegerProperty> {
    @Override
    public IntegerProperty unmarshal(Integer v) {
        return new SimpleIntegerProperty(v);
    }

    @Override
    public Integer marshal(IntegerProperty v) {
        return v.get();
    }
}
