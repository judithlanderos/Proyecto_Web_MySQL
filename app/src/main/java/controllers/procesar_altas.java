package controllers;

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

    }
}
