package analizadorcomplejidadtemporal;

import analizadorcomplejidadtemporal.generadores.GeneradorDatos;
import analizadorcomplejidadtemporal.vistas.PantallaInicio;
import java.util.Arrays;
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
