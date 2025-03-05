package com.berenjeneitor.facturacion.presentacion;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// clase para leer la config de la empresa
public class EmpresaConfig {
    private Properties properties;

    public EmpresaConfig() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("empresa.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}