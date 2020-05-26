package ru.privetdruk.slyeye.util;

import ru.privetdruk.slyeye.parser.JaxbParser;

import java.io.File;

public class XMLUtil {
    public static Object read(String filename, Class<?> clazz) {
        Object xmlObject = new Object();
        try {
            xmlObject = clazz.getDeclaredConstructor().newInstance();

            try {
                xmlObject = JaxbParser.getObject(new File(filename), clazz);
            } catch (Exception ex) {
                save(xmlObject, filename);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xmlObject;
    }

    public static void save(Object object, String filename) {
        try {
            JaxbParser.saveObject(new File(filename), object);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
