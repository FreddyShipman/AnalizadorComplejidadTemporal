package analizadorcomplejidadtemporal.medidor;

import analizadorcomplejidadtemporal.algoritmos.AlgoritmosBusqueda;
import analizadorcomplejidadtemporal.algoritmos.AlgoritmosIterativos;
import analizadorcomplejidadtemporal.algoritmos.AlgoritmosRecursivos;

/**
 * Motor encargado de realizar los benchmarks de tiempo de ejecución.
 * Ejecuta los algoritmos midiendo el tiempo con System.nanoTime()
 * y repitiendo pruebas para calcular promedios representativos.
 */
public class MedidorTiempo {

    private static final int REPETICIONES = 10;
    private static final long TIMEOUT_NS = 5_000_000_000L; // 5 segundos

    /**
     * Mide el tiempo promedio de un algoritmo de búsqueda en nanosegundos.
     */
    public static double medirBusqueda(String algoritmo, int[] arreglo, int dato) {
        // Warm-up
        ejecutarBusqueda(algoritmo, arreglo.clone(), dato);

        long totalNano = 0;
        for (int i = 0; i < REPETICIONES; i++) {
            // Centinela modifica el arreglo, usar copia
            int[] copia = arreglo.clone();
            long inicio = System.nanoTime();
            ejecutarBusqueda(algoritmo, copia, dato);
            long fin = System.nanoTime();
            totalNano += (fin - inicio);
        }
        return (double) totalNano / REPETICIONES;
    }

    /**
     * Mide el tiempo promedio de un algoritmo iterativo/recursivo en nanosegundos.
     * Retorna -1 si excede el timeout.
     */
    public static double medirIterativoRecursivo(String algoritmo, int n) {
        // Warm-up con valor pequeño
        try {
            ejecutarIterativoRecursivo(algoritmo, Math.min(n, 10));
        } catch (StackOverflowError e) {
            return -1;
        }

        long totalNano = 0;
        for (int i = 0; i < REPETICIONES; i++) {
            long inicio = System.nanoTime();
            try {
                ejecutarIterativoRecursivo(algoritmo, n);
            } catch (StackOverflowError e) {
                return -1;
            }
            long fin = System.nanoTime();
            long duracion = fin - inicio;
            totalNano += duracion;

            // Si una sola ejecución excede el timeout, abortar
            if (duracion > TIMEOUT_NS) {
                return -1;
            }
        }
        return (double) totalNano / REPETICIONES;
    }

    private static void ejecutarBusqueda(String algoritmo, int[] arreglo, int dato) {
        switch (algoritmo) {
            case "Búsqueda Lineal":
                AlgoritmosBusqueda.busquedaLineal(arreglo, dato);
                break;
            case "Búsqueda Binaria":
                AlgoritmosBusqueda.busquedaBinaria(arreglo, dato);
                break;
            case "Búsqueda Centinela":
                AlgoritmosBusqueda.busquedaCentinela(arreglo, dato);
                break;
        }
    }

    private static void ejecutarIterativoRecursivo(String algoritmo, int n) {
        switch (algoritmo) {
            case "Fibonacci Iterativo":
                AlgoritmosIterativos.fibonacciIterativo(n);
                break;
            case "Potencia Iterativa":
                AlgoritmosIterativos.potenciaIterativo(2, n);
                break;
            case "Factorial Iterativo":
                AlgoritmosIterativos.factorialIterativo(n);
                break;
            case "Fibonacci Recursivo":
                AlgoritmosRecursivos.fibonacciRecursivo(n);
                break;
            case "Potencia Recursiva":
                AlgoritmosRecursivos.potenciaRecursivo(2, n);
                break;
            case "Factorial Recursivo":
                AlgoritmosRecursivos.factorialRecursivo(n);
                break;
        }
    }

    /**
     * Determina si un nombre de algoritmo es de tipo búsqueda.
     */
    public static boolean esBusqueda(String algoritmo) {
        return algoritmo.contains("Búsqueda");
    }
}
