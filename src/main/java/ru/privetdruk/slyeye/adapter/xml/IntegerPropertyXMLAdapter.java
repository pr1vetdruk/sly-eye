package ru.privetdruk.slyeye.adapter.xml;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class IntegerPropertyXMLAdapter extends XmlAdapter<IntegerProperty, Integer> {
    @Override
    public Integer unmarshal(IntegerProperty v) throws Exception {
        return v.get();
    }

    @Override
    public IntegerProperty marshal(Integer v) throws Exception {
        return new SimpleIntegerProperty(v);
    }
}
