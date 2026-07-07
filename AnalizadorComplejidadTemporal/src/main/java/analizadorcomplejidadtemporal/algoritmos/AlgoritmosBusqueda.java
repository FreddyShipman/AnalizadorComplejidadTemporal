package analizadorcomplejidadtemporal.algoritmos;

/**
 * Contiene los algoritmos de búsqueda: - Búsqueda Lineal - Búsqueda Lineal con
 * Centinela - Búsqueda Binaria
 */
public class AlgoritmosBusqueda {

    /**
     * Búsqueda Lineal Secuencial
     */
    public static int busquedaLineal(int[] arreglo, int dato) {
        int n = arreglo.length;

        for (int i = 0; i < n; i++) {
            if (arreglo[i] == dato) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Búsqueda Lineal con Centinela
     */
    public static int busquedaCentinela(int[] arreglo, int dato) {
        int n = arreglo.length;

        if (n == 0) {
            return -1;
        }
        int ultimoElemento = arreglo[n - 1];
        arreglo[n - 1] = dato;

        int i = 0;
        while (arreglo[i] != dato) {
            i = i + 1;
        }
        arreglo[n - 1] = ultimoElemento;
        if (i < n - 1) {
            return i;
        }

        if (ultimoElemento == dato) {
            return n - 1;
        }

        return -1;
    }

    /**
     * Búsqueda Binaria (Requiere arreglo ordenado)
     */
    public static int busquedaBinaria(int[] arreglo, int dato) {
        int inicio = 0;
        int fin = arreglo.length - 1;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;

            if (arreglo[medio] == dato) {
                return medio;
            }

            if (arreglo[medio] < dato) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }

        return -1;
    }
}
