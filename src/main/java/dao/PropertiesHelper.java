package dao;

import exceptions.PropertyException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {

    private final Properties properties;

    public PropertiesHelper(String fileName) {
        this.properties = new Properties();
        try {
            this.properties.load(new FileReader(fileName));
        } catch (IOException e) {
            throw new PropertyException(e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
