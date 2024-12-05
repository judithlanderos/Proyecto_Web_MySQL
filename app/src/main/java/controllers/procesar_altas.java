package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.conexion_bd_tutorias;

public class procesar_altas {
    public boolean procesarDatos(
            String numControl,
            String nombre,
            String primerApellido,
            String segundoApellido,
            String fechaNacimiento,
            String telefono,
            String email,
            String carrera
    ) {
        if (!numControl.matches("^[a-zA-Z]\\d{8}$")) {
            System.out.println("Número de control debe ser 1 letra seguida de 8 números");
            return false;
        }

        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            System.out.println("El nombre solo puede contener letras");
            return false;
        }

        if (!primerApellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            System.out.println("El primer apellido solo puede contener letras");
            return false;
        }

        if (!segundoApellido.isEmpty() && !segundoApellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            System.out.println("El segundo apellido solo puede contener letras");
            return false;
        }

        if (!telefono.matches("^\\d{10}$")) {
            System.out.println("El teléfono debe contener 10 dígitos");
            return false;
        }

        if (!email.matches("^[a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]{2,}$")) {
            System.out.println("El correo electrónico no tiene un formato válido");
            return false;
        }
        conexion_bd_tutorias conexionBD = new conexion_bd_tutorias();
        Connection connection = conexionBD.getConexion();

        if (connection == null) {
            System.out.println("Error al conectar a la base de datos.");
            return false;
        }

        try {
            // Verificar si el alumno ya existe
            String checkQuery = "SELECT * FROM alumnos WHERE num_control = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, numControl);
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                System.out.println("El número de control ya existe. Por favor, ingresa otro número de control.");
                return false;
            }

            // Insertar el nuevo alumno
            String insertQuery = "INSERT INTO alumnos (num_control, nombre, primer_apellido, segundo_apellido, fecha_nacimiento, telefono, email, carrera) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, numControl);
            insertStmt.setString(2, nombre);
            insertStmt.setString(3, primerApellido);
            insertStmt.setString(4, segundoApellido);
            insertStmt.setString(5, fechaNacimiento);
            insertStmt.setString(6, telefono);
            insertStmt.setString(7, email);
            insertStmt.setString(8, carrera);

            int rowsInserted = insertStmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Registro agregado correctamente.");
                return true;
            } else {
                System.out.println("Error al insertar el registro.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar los datos: " + e.getMessage());
            return false;
        }
    }
    }
}
