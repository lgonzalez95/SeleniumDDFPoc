package com.lg.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {
    public static void main(String[] args) throws IOException {
        Properties config = new Properties();
        FileInputStream fileProperties = new FileInputStream("src/test/resources/properties/Config.properties");
        config.load(fileProperties);
        System.out.println( config.getProperty("browser"));
    }
}
