package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.conexion_bd_tutorias;

public class procesar_bajas {

    private final conexion_bd_tutorias conexionBD;

    public procesar_bajas() {
        conexionBD = new conexion_bd_tutorias();
    }

    public procesar_bajas(conexion_bd_tutorias conexionBD) {
        this.conexionBD = conexionBD;
    }

    public String eliminarAlumno(String numControl) {
        if (numControl == null || numControl.isEmpty()) {
            return "invalido";
        }

        Connection connection = conexionBD.getConexion();

        if (connection == null) {
            return "error";
        }

        try {
            // Verificar si el alumno existe
            String checkQuery = "SELECT * FROM alumnos WHERE num_control = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, numControl);
            ResultSet resultSet = checkStmt.executeQuery();

            if (!resultSet.next()) {
                return "no_existe";
            }

            // Registrar la baja
            String deleteQuery = "DELETE FROM alumnos WHERE num_control = ?";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
            deleteStmt.setString(1, numControl);
            int rowsAffected = deleteStmt.executeUpdate();

            if (rowsAffected > 0) {
                return "exito";
            } else {
                return "error";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}

