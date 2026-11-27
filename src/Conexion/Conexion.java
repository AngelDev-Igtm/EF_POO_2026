package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion 
{
    private static final String URL = "jdbc:sqlserver://localhost:1433;" + "databaseName=IndigoLoftDB;" + "encrypt=true;" + "trustServerCertificate=true;";

    private static final String USUARIO = "indigo_app";
    private static final String CONTRASENA = System.getenv("INDIGO_DB_PASSWORD");

    public static Connection conectar() throws SQLException 
    {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}