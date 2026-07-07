package analizadorcomplejidadtemporal.algoritmos;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Contiene los algoritmos en su versión iterativa: - Fibonacci - Potencia -
 * Factorial
 */
public class AlgoritmosIterativos {

    /**
     * Fibonacci Iterativo: O(N)
     */
    public static BigDecimal potenciaIterativo(double base, int exponente) {
        BigDecimal bdBase = BigDecimal.valueOf(base);
        BigDecimal resultado = BigDecimal.ONE;
        for (int i = 0; i < exponente; i++) {
            resultado = resultado.multiply(bdBase);
        }
        return resultado;
    }
    /**
     * Potencia Iterativa: O(N)
     */
    public static BigInteger fibonacciIterativo(int n) {
        if (n <= 0) {
            return BigInteger.ZERO;
        }
        if (n == 1) {
            return BigInteger.ONE;
        }

        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;

        for (int i = 2; i <= n; i++) {
            BigInteger temporal = b;
            b = a.add(b);
            a = temporal;
        }
        return b;
    }
    /**
     * Factorial Iterativo: O(N)
     */
    public static BigInteger factorialIterativo(int n) {
        BigInteger resultado = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            resultado = resultado.multiply(BigInteger.valueOf(i));
        }
        return resultado;
    }
}
