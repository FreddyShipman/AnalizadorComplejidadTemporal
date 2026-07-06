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
    
//    public static void main(String[] args) {
//        System.out.println("Inicio de la prueba");
//        int tamanoPrueba = 20;
//        int[] arregloDesordenado = GeneradorDatos.generarArregloAleatorio(tamanoPrueba);     
//        int[] arregloOrdenado = GeneradorDatos.ordenarArreglo(arregloDesordenado);
//        System.out.println("Arreglo desordenado:");
//        System.out.println(Arrays.toString(arregloDesordenado));
//        System.out.println("\nArreglo ordenado:");
//        System.out.println(Arrays.toString(arregloOrdenado));
//    }
}
