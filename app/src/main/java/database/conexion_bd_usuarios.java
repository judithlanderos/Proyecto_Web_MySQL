package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class conexion_bd_usuarios {
    private static conexion_bd_usuarios instancia = null;
    private Connection conexion;
    private final String host = "jdbc:mysql://localhost:3306/usuario_bd_escuela";
    private final String usuario = "judith";
    private final String password = "judilth@3";

    // Constructor privado para el patrón Singleton
    public conexion_bd_usuarios() {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión
            conexion = DriverManager.getConnection(host, usuario, password);
            System.out.println("Conexión a la base de datos establecida.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error en la conexión a la base de datos: " + e.getMessage());
        }
    }

    // Método estático para obtener la instancia única
    public static conexion_bd_usuarios getInstancia() {
        if (instancia == null) {
            instancia = new conexion_bd_usuarios();
        }
        return instancia;
    }

    // Método para obtener la conexión
    public Connection getConexion() {
        return conexion;
    }

    // Método para validar usuario
    public boolean validarUsuario(String usuario, String password) {
        boolean esValido = false;

        try {
            // Consulta SQL para validar usuario
            String query = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";
            PreparedStatement statement = conexion.prepareStatement(query);

            // Vincular parámetros
            statement.setString(1, usuario);
            statement.setString(2, password);

            // Ejecutar consulta
            ResultSet resultSet = statement.executeQuery();

            // Verificar si el usuario existe
            esValido = resultSet.next();

        } catch (SQLException e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
        }

        return esValido;
    }
}
