package analizadorcomplejidadtemporal;

import analizadorcomplejidadtemporal.vistas.PantallaInicio;
import javax.swing.SwingUtilities;

/**
 * Clase principal del proyecto.
 * Inicializa la aplicación.
 */
public class AnalizadorComplejidadTemporal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Iniciando interfaz gráfica del Analizador...");
            PantallaInicio ventana = new PantallaInicio();
            ventana.setVisible(true);
        });
    }
}
