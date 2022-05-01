package validador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    private static final int MAX_INTENTOS_REGISTRO = 3;

    private static final List<Usuario> usuariosRegistrados = new ArrayList<>(Arrays.asList(
            new Usuario("echito", "!echito!"),
            new Usuario("milita", "!milita!"),
            new Usuario("ronito", "!ronito!"),
            new Usuario("lukitas", "!lukitas!"),
            new Usuario("zirito", "!zirito!"),
            new Usuario("agus", "!agus!")
    ));

    public static void main(String[] args) throws Exception {
        boolean terminarEjecucion = false;
        do {
            OperacionTesteo operacionTesteo = mostrarOperacionesYElegir();
            switch (operacionTesteo) {
                case REGISTRO:
                    registrarUsuario();
                    break;
                case LOGIN:
                    logearse();
                    break;
                case EXIT:
                    terminarEjecucion = true;
                    break;
            }

            System.out.println("\n");
        } while (!terminarEjecucion);
    }

    public static OperacionTesteo mostrarOperacionesYElegir() {
        System.out.println("1- Login");
        System.out.println("2- Registrar usuario");
        System.out.println("3- Salir");
        System.out.print("Elegi una opcion: ");
        Scanner scanner = new Scanner(System.in);
        int operacion = scanner.nextInt();
        return OperacionTesteo.of(operacion);
    }

    public static void registrarUsuario() throws Exception {
        Scanner scanner = new Scanner(System.in);

        String username;
        boolean usernameCorrecto = false;
        do {
            System.out.println("Ingresa el nuevo username: ");
            username = scanner.nextLine();
            if (usuarioExisteConUsername(username)) {
                System.out.println("Ya existe otro usuario con ese username!");
            } else {
                usernameCorrecto = true;
            }
        } while (!usernameCorrecto);

        String password;
        boolean passwordCorrecta = false;
        int intentosFallidos = 0;
        do {
            System.out.println("Ingresa la nueva contrasenia: ");
            password = scanner.nextLine();
            ResultadoDeValidacion resultado = validarNuevaContrasenia(password);
            if (resultado.esValido()) {
                Usuario nuevoUser = new Usuario(username, password);
                usuariosRegistrados.add(nuevoUser);
                System.out.println("Usuario creado exitosamente!");
                passwordCorrecta = true;
            } else {
                intentosFallidos++;
                System.out.println("Contrasenia invalida:");
                System.out.println(resultado.getErroresEnLineas());
                if (intentosFallidos > MAX_INTENTOS_REGISTRO) {
                    System.out.println("Demasiados errores, ninosvimos.");
                }
            }
        } while (!passwordCorrecta && intentosFallidos <= MAX_INTENTOS_REGISTRO);
    }

    public static void logearse() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese su usuario: ");
        final String username = scanner.nextLine();

        final Optional<Usuario> optionalUser = getUsuarioPorUsername(username);
        if (!optionalUser.isPresent()) {
            System.out.println("Usuario incorrecto!");
            return;
        }

        final Usuario user = optionalUser.get();
        if (user.estaBloqueado()) {
            System.out.println("Estas bloqueado hasta la fecha " + formatearBloqueadoHasta(user.getBloqueadoHasta()));
            return;
        }

        System.out.print("Ingrese la contraseña: ");
        final String password = scanner.nextLine();
        if (user.validarContrasenia(password)) {
            System.out.println("Usuario " + user.getUsername() + " logeado de perlitass!");
            user.logeoCorrecto();
        } else {
            System.out.println("Contrasenia incorrecta!");
            user.logeoIncorrecto();
            if (user.estaBloqueado()) {
                System.out.println("Estas bloqueado hasta la fecha " + formatearBloqueadoHasta(user.getBloqueadoHasta()));
            }
        }
    }

    public static Optional<Usuario> getUsuarioPorUsername(String username) {
        return usuariosRegistrados.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public static boolean usuarioExisteConUsername(String username) {
        return usuariosRegistrados.stream()
                .anyMatch(u -> u.getUsername().equals(username));
    }

    public static ResultadoDeValidacion validarNuevaContrasenia(String nuevaPassword) {
        ValidadorContrasenia validador = new ValidadorContrasenia();
        return validador.validar(nuevaPassword);
    }

    public static String formatearBloqueadoHasta(LocalDateTime bloqueadoHasta) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return bloqueadoHasta.format(formatter);
    }

}
