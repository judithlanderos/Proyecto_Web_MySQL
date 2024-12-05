package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import database.conexion_bd_tutorias;

public class procesar_cambios {

    public Map<String, String> validarDatos(
            String numControl,
            String nombre,
            String apellidoP,
            String apellidoM,
            String fechaNacimiento,
            String telefono,
            String email,
            String carrera
    ) {
        Map<String, String> errores = new HashMap<>();

        // Validaciones
        if (nombre == null || nombre.isEmpty()) {
            errores.put("nombre", "El nombre es obligatorio.");
        } else if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            errores.put("nombre", "El nombre solo debe contener letras y espacios.");
        }

        if (apellidoP == null || apellidoP.isEmpty()) {
            errores.put("apellidoP", "El primer apellido es obligatorio.");
        } else if (!apellidoP.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            errores.put("apellidoP", "El primer apellido solo debe contener letras y espacios.");
        }

        if (apellidoM != null && !apellidoM.isEmpty() && !apellidoM.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            errores.put("apellidoM", "El segundo apellido solo debe contener letras y espacios.");
        }

        if (fechaNacimiento == null || fechaNacimiento.isEmpty()) {
            errores.put("fecha_nacimiento", "La fecha de nacimiento es obligatoria.");
        } else if (!fechaNacimiento.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            errores.put("fecha_nacimiento", "La fecha de nacimiento no es válida.");
        }

        if (telefono != null && !telefono.isEmpty() && !telefono.matches("^\\d{10}$")) {
            errores.put("telefono", "El teléfono debe contener 10 dígitos sin guiones.");
        }

        if (email == null || email.isEmpty()) {
            errores.put("email", "El correo electrónico es obligatorio.");
        } else if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            errores.put("email", "El correo electrónico no es válido.");
        }

        if (carrera == null || carrera.isEmpty()) {
            errores.put("carrera", "La carrera es obligatoria.");
        }

        return errores;
    }

    public boolean modificarAlumno(
            String numControl,
            String nombre,
            String apellidoP,
            String apellidoM,
            String fechaNacimiento,
            String telefono,
            String email,
            String carrera
    ) {
        conexion_bd_tutorias conexionBD = new conexion_bd_tutorias();
        Connection connection = conexionBD.getConexion();

        if (connection == null) {
            return false;
        }

        try {
            String query = "UPDATE alumnos SET nombre = ?, apellidoP = ?, apellidoM = ?, fecha_nacimiento = ?, telefono = ?, email = ?, Carrera_carrera_id = ? WHERE num_control = ?";
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, nombre);
            stmt.setString(2, apellidoP);
            stmt.setString(3, apellidoM);
            stmt.setString(4, fechaNacimiento);
            stmt.setString(5, telefono);
            stmt.setString(6, email);
            stmt.setString(7, carrera);
            stmt.setString(8, numControl);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
