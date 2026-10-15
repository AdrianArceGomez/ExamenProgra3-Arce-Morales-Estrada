/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 *
 * @author User
 */
public class ConexionDB {
    
    private static final String ARCHIVO = "db.properties";

    private static String url;
    private static String usuario;
    private static String clave;

    private ConexionDB() {
    }
    
    public static Connection obtenerConexion() throws SQLException {
        if (url == null) {
            cargarConfiguracion();
        }
        return DriverManager.getConnection(url, usuario, clave);
    }

    private static synchronized void cargarConfiguracion() throws SQLException {
        if (url != null) {
            return;
        }
        Properties props = new Properties();
        try (InputStream in = ConexionDB.class.getClassLoader().getResourceAsStream(ARCHIVO)) {
            if (in == null) {
                throw new SQLException("No se encontró " + ARCHIVO + " en src/main/resources. "
                        + "Copie db.properties.example como db.properties y complete sus datos.");
            }
            props.load(in);
        } catch (IOException e) {
            throw new SQLException("No se pudo leer " + ARCHIVO, e);
        }
        String u = props.getProperty("db.url");
        if (u == null || u.isBlank()) {
            throw new SQLException("La propiedad db.url no está definida en " + ARCHIVO);
        }
        usuario = props.getProperty("db.usuario", "");
        clave = props.getProperty("db.clave", "");
        url = u.trim();
    }
}
