/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorcomplejidadtemporal.generadores;

// Equipo 1:
// José Alfredo Guzman Moreno - 252524

import java.util.Arrays;
import java.util.Random;

public class GeneradorDatos {
    public static int[] generarArregloAleatorio(int tamano) {
        int[] arreglo = new int[tamano];
        Random random = new Random();
        for (int i = 0; i < tamano; i++) {
            arreglo[i] = random.nextInt(50) + 1;
        }
        return arreglo;
    }

    public static int[] ordenarArreglo(int[] arregloOriginal) {
        int[] arregloOrdenado = Arrays.copyOf(arregloOriginal, arregloOriginal.length);
        Arrays.sort(arregloOrdenado);
        return arregloOrdenado;
    }
}
