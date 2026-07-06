// Equipo 1:
// José Alfredo Guzman Moreno - 252524

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Inicio de la prueba");
        int tamanoPrueba = 20;
        int[] arregloDePrueba = GeneradorDatos.generarArregloAleatorio(tamanoPrueba);
        System.out.println("Arreglo generado: ");        
        System.out.println(Arrays.toString(arregloDePrueba));
    }
}
