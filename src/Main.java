// Equipo 1:
// José Alfredo Guzman Moreno - 252524

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Inicio de la prueba");
        int tamanoPrueba = 20;
        int[] arregloDesordenado = GeneradorDatos.generarArregloAleatorio(tamanoPrueba);     
        int[] arregloOrdenado = GeneradorDatos.ordenarArreglo(arregloDesordenado);
        System.out.println("Arreglo desordenado:");
        System.out.println(Arrays.toString(arregloDesordenado));
        System.out.println("\nArreglo ordenado:");
        System.out.println(Arrays.toString(arregloOrdenado));
    }
}
