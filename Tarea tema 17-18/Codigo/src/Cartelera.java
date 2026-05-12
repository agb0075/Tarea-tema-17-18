/**
 * Clase que vincula una película con una sala y un horario.
 */
public class Cartelera {
    private Pelicula pelicula;
    private double puntuacion;
    private Sala sala;
    private String horario;

    public Cartelera(Pelicula pelicula, double puntuacion, Sala sala, String horario) {
        this.pelicula = pelicula;
        this.puntuacion = puntuacion;
        this.sala = sala;
        this.horario = horario;
    }

    /**
     * REFACTORIZACIÓN: Se ha estandarizado la salida de texto para mejorar la
     * legibilidad
     */
    public void mostrarInfoCartelera() {
        System.out.printf("%-25s | Sala: %d | Hora: %s | Nota: %.1f%n",
                pelicula.getTitulo(), sala.getNumero(), horario, puntuacion);
    }

    // Getters necesarios para la comunicación entre clases
    public String getHorario() {
        return horario;
    }

    public Sala getSala() {
        return sala;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }
}