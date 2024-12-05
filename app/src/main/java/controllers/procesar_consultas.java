package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import database.conexion_bd_tutorias;

public class procesar_consultas {

    public Map<String, String> consultarAlumno(String numControl) {
        Map<String, String> alumno = new HashMap<>();

        // Validar que el número de control es válido
        if (numControl == null || numControl.isEmpty() || !numControl.matches("\\d+")) {
            alumno.put("error", "Por favor, ingresa un número de control válido.");
            return alumno;
        }

        conexion_bd_tutorias conexionBD = new conexion_bd_tutorias();
        Connection connection = conexionBD.getConexion();

        if (connection == null) {
            alumno.put("error", "Error al conectar con la base de datos.");
            return alumno;
        }

        try {
            String query = "SELECT num_control, nombre, apellidoP, apellidoM, fecha_nacimiento, telefono, email, Carrera_carrera_id FROM alumnos WHERE num_control = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, numControl);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                alumno.put("numControl", rs.getString("num_control"));
                alumno.put("nombre", rs.getString("nombre"));
                alumno.put("apellidoP", rs.getString("apellidoP"));
                alumno.put("apellidoM", rs.getString("apellidoM"));
                alumno.put("fechaNacimiento", rs.getString("fecha_nacimiento"));
                alumno.put("telefono", rs.getString("telefono"));
                alumno.put("email", rs.getString("email"));
                alumno.put("carrera", rs.getString("Carrera_carrera_id"));
            } else {
                alumno.put("error", "No se encontró el registro del alumno.");
            }

        } catch (SQLException e) {
            alumno.put("error", "Error al realizar la consulta: " + e.getMessage());
        }

        return alumno;
    }
}
