package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion_bd_tutorias {
    private Connection conexion;
    private final String host = "jdbc:mysql://localhost:3306/Proyecto_Tutorias";
    private final String usuario = "judith";
    private final String password = "judilth@3";

    public conexion_bd_tutorias() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conexion = DriverManager.getConnection(host, usuario, password);

            if (conexion != null) {
                System.out.println("Conexión a la base de datos establecida.");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error en la conexión a la base de datos: " + e.getMessage());
        }
    }

    public static conexion_bd_tutorias getInstancia() {
        return null;
    }


    public Connection getConexion() {
        return conexion;
    }
}
