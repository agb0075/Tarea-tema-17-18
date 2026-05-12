import java.util.Scanner;

/**
 * CLASE PRINCIPAL: SISTEMA DE GESTIÓN DE CINE
 *
 * Esta clase actúa como el controlador del programa (Capa de Interfaz).
 * Se ha refactorizado siguiendo el principio de responsabilidad única,
 * delegando la lógica de datos y visualización a métodos especializados.
 *
 * @author Marcos Rodriguez, Faisal, Alejandro Gómez
 * @version 2.0 (Refactorizada)
 */
public class Main {

    // Instancia global de Scanner para evitar múltiples aperturas/cierres de
    // System.in
    private static Scanner sc = new Scanner(System.in);

    /**
     * Punto de entrada del programa.
     * Controla el ciclo de vida de la aplicación y el menú principal.
     */
    public static void main(String[] args) {

        /*
         * 1. CONFIGURACIÓN INICIAL (SETUP)
         * Se inicializan las estructuras de datos necesarias antes de arrancar el menú.
         */

        // Creamos un array de 7 objetos Sala independientes
        Sala[] salas = inicializarSalas(7);

        // Creamos la cartelera vinculando películas reales a las salas creadas
        Cartelera[] listaCartelera = inicializarCartelera(salas);

        int opcion = 0;

        /*
         * 2. BUCLE PRINCIPAL DE LA INTERFAZ
         * Mantiene la aplicación ejecutándose hasta que el usuario decida salir
         * .
         */
        while (opcion != 3) {
            mostrarMenuPrincipal();

            // Usamos leerEntero() para garantizar que la entrada sea numérica y no rompa el
            // switch
            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    // Muestra la lista de películas, horarios y estados de sala
                    imprimirCartelera(listaCartelera);
                    break;

                case 2:
                    // Inicia el flujo de reserva (Selección de peli -> Ver sala -> Elegir asiento)
                    procesoDeReserva(listaCartelera);
                    break;

                case 3:
                    System.out.println("Cerrando flujos de datos... ¡Adiós!");
                    break;

                default:
                    System.out.println(" Opción no reconocida. Por favor, elija 1, 2 o 3.");
            }
        }

        // Cerramos el Scanner al finalizar la ejecución por buena práctica de memoria
        sc.close();
    }




/////////////////////////////////////////////////////////////////////////
    /**
     * Instancia las salas del cine.
     *
     * @param cantidad Número total de salas a crear.
     * @return Array de objetos Sala numerados del 1 al N.
     */
    private static Sala[] inicializarSalas(int cantidad) {
        Sala[] salas = new Sala[cantidad];
        for (int i = 0; i < cantidad; i++) {
            // Pasamos i+1 para que el ID de la sala empiece en 1 y no en 0
            salas[i] = new Sala(i + 1);
        }
        return salas;
    }

    /**
     * Crea la base de datos temporal (Cartelera) del sistema.
     * REFACTORIZACIÓN: Separado del main para facilitar la carga de datos desde
     * archivos en el futuro.
     */
    private static Cartelera[] inicializarCartelera(Sala[] salas) {
        // Vinculamos cada película a una de las salas inicializadas previamente
        return new Cartelera[] {
                new Cartelera(new Pelicula("Matrix", 136, "Ciencia Ficción"), 8.7, salas[0], "17:00"),
                new Cartelera(new Pelicula("Deadpool", 108, "Acción/Comedia"), 8.0, salas[1], "19:00"),
                new Cartelera(new Pelicula("El club de la lucha", 139, "Drama"), 8.8, salas[2], "22:00"),
                new Cartelera(new Pelicula("Cruella", 134, "Comedia/Crimen"), 7.3, salas[3], "16:30"),
                new Cartelera(new Pelicula("El diablo viste de prada", 109, "Comedia/Drama"), 6.9, salas[4], "18:30")
        };
    }

    /**
     * Imprime el encabezado visual del menú.
     */
    private static void mostrarMenuPrincipal() {
        System.out.println("\n========================================");
        System.out.println("       SISTEMA DE RESERVAS CINE");
        System.out.println("========================================");
        System.out.println("1. Ver Cartelera Actual");
        System.out.println("2. Reservar Asiento");
        System.out.println("3. Salir del Programa");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Itera sobre la cartelera e imprime los detalles de cada proyección.
     */
    private static void imprimirCartelera(Cartelera[] lista) {
        System.out.println("\n--- CARTELERA DISPONIBLE ---");
        for (int i = 0; i < lista.length; i++) {
            // El índice i+1 es para que el usuario vea "Pelicula 1" en lugar de "Pelicula
            // 0"
            System.out.print("[" + (i + 1) + "] ");
            lista[i].mostrarInfoCartelera();
        }
    }

    /**
     * GESTIÓN DE RESERVA (Lógica compleja extraída)
     * Este método guía al usuario por el proceso de compra de entradas.
     */
    private static void procesoDeReserva(Cartelera[] lista) {
        // Mostrar lista simplificada de títulos para elegir
        System.out.println("\n--- ELIGE UNA PELÍCULA ---");
        for (int i = 0; i < lista.length; i++) {
            System.out.println("[" + (i + 1) + "] " + lista[i].getPelicula().getTitulo());
        }

        System.out.print("\nIntroduzca el número de la película: ");
        int sel = leerEntero() - 1; // Ajustamos la entrada (1-5) al índice real (0-4)

        // Verificamos que la película seleccionada exista en el array
        if (sel >= 0 && sel < lista.length) {
            Cartelera eleccion = lista[sel];
            Sala sala = eleccion.getSala();
            char continuar;

            /*
             * BUCLE DE RESERVA MÚLTIPLE:
             * Permite reservar varios asientos de la misma película sin salir al menú
             * principal
             */
            do {
                sala.mostrarAsientos(); // Imprime el mapa de la sala (X para ocupado)
                System.out.println("\nPelícula: " + eleccion.getPelicula().getTitulo());

                // Entradas de usuario ajustadas de 1-5 a 0-4 para la matriz interna
                System.out.print("Elija Fila (1-5): ");
                int f = leerEntero() - 1;
                System.out.print("Elija Columna (1-5): ");
                int c = leerEntero() - 1;

                // Validación de límites para evitar un ArrayIndexOutOfBoundsException
                if (f >= 0 && f < 5 && c >= 0 && c < 5) {
                    if (sala.reservarAsiento(f, c)) {
                        // Confirmación exitosa con todos los detalles del objeto Cartelera
                        System.out.println("\n EXITO: Reserva de \"" + eleccion.getPelicula().getTitulo() +
                                "\" | Sala: " + sala.getNumero() + " | Hora: " + eleccion.getHorario());
                    } else {
                        System.out.println(" ERROR: El asiento [" + (f + 1) + "," + (c + 1) + "] ya está ocupado.");
                    }
                } else {
                    System.out.println(" ERROR: Coordenadas inválidas (use valores de 1 a 5).");
                }

                // Control de repetición del bucle de reserva
                System.out.print("\n¿Desea añadir otro asiento para esta misma sesión? (s/n): ");
                continuar = sc.next().toLowerCase().charAt(0);

            } while (continuar == 's');

        } else {
            System.out.println(" ERROR: La película seleccionada no existe.");
        }
    }

    /**
     * LECTURA DE DATOS SEGURA
     * Evita que el programa se cuelgue ante errores de tipo de dato
     * (InputMismatchException).
     * 
     * @return El número entero validado introducido por el usuario.
     */
    private static int leerEntero() {
        // Mientras el búfer no contenga un entero, pedimos repetir la entrada
        while (!sc.hasNextInt()) {
            System.out.print(" ERROR: Debe introducir un número entero. Reintente: ");
            sc.next(); // Limpia la entrada no válida
        }
        return sc.nextInt();
    }
}