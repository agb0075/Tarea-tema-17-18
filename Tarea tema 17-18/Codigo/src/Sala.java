/**
 * Gestiona la ocupación de asientos en una sala específica.
 */
public class Sala {
    private int numero;
    private boolean[][] asientos;

    public Sala(int numero) {
        this.numero = numero;
        this.asientos = new boolean[5][5]; 
    }

    public int getNumero() { return numero; }

    /**
     * Muestra el mapa de la sala. 
     * REFACTORIZACIÓN: Se añade el ajuste visual para que el usuario vea coordenadas 1-5.
     */
    public void mostrarAsientos() {
        System.out.println("\n--- PANTALLA (Sala " + numero + ") ---");
        for (int i = 0; i < 5; i++) {
            System.out.print("Fila " + (i + 1) + "  ");
            for (int j = 0; j < 5; j++) {
                // REFACTORIZACIÓN: Uso de operador ternario para simplificar el dibujo
                System.out.print(asientos[i][j] ? "[ X ] " : "[" + (i+1) + "," + (j+1) + "] ");
            }
            System.out.println();
        }
    }

    /**
     * @param f Fila (índice 0-4)
     * @param c Columna (índice 0-4)
     * @return true si se reservó, false si estaba ocupado.
     */
    public boolean reservarAsiento(int f, int c) {
        if (!asientos[f][c]) {
            asientos[f][c] = true;
            return true;
        }
        return false;
    }
}