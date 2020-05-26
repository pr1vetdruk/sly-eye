package ru.privetdruk.slyeye.parser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;

public class JaxbParser {
    public static Object getObject(File file, Class<?> c) throws JAXBException {
        return JAXBContext.newInstance(c).createUnmarshaller().unmarshal(file);
    }

    public static void saveObject(File file, Object o) throws JAXBException {
        JAXBContext.newInstance(o.getClass()).createMarshaller().marshal(o, file);
    }
}
