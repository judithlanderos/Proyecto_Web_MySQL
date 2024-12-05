package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.conexion_bd_tutorias;

public class registrar_usuario {

    private final Connection conexion;

    public registrar_usuario() {
        conexion_bd_tutorias conexionBD = conexion_bd_tutorias.getInstancia();
        this.conexion = conexionBD.getConexion();
    }

    public String registrarUsuario(String usuario, String password, String correo) {
        if (conexion == null) {
            return "Error de conexión con la base de datos.";
        }

        try {
            // Validar si el correo ya está registrado
            String sqlVerificarCorreo = "SELECT * FROM usuarios WHERE Correo = ?";
            PreparedStatement stmtVerificar = conexion.prepareStatement(sqlVerificarCorreo);
            stmtVerificar.setString(1, correo);
            ResultSet resultSet = stmtVerificar.executeQuery();

            if (resultSet.next()) {
                return "El correo electrónico ya está registrado.";
            }

            // Cifrar la contraseña y el nombre de usuario
            String usuarioCifrado = cifrarSHA1(usuario);
            String passwordCifrada = cifrarSHA1(password);

            // Insertar el nuevo usuario
            String sqlInsertar = "INSERT INTO usuarios (Nombre_Usuario, Password, Correo) VALUES (?, ?, ?)";
            PreparedStatement stmtInsertar = conexion.prepareStatement(sqlInsertar);
            stmtInsertar.setString(1, usuarioCifrado);
            stmtInsertar.setString(2, passwordCifrada);
            stmtInsertar.setString(3, correo);

            if (stmtInsertar.executeUpdate() > 0) {
                // Simular envío de correo
                enviarCorreo(correo);
                return "Usuario registrado correctamente.";
            } else {
                return "Error al registrar el usuario.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al procesar la solicitud.";
        }
    }

    private String cifrarSHA1(String texto) {
        try {
            java.security.MessageDigest sha1 = java.security.MessageDigest.getInstance("SHA-1");
            byte[] result = sha1.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void enviarCorreo(String correo) {
        System.out.println("Correo enviado a: " + correo);
    }
}
