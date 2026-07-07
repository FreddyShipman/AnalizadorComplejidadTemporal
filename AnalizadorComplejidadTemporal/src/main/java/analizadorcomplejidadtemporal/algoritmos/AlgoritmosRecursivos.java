package analizadorcomplejidadtemporal.algoritmos;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Contiene los algoritmos en su versión recursiva: - Fibonacci - Potencia -
 * Factorial
 */
public class AlgoritmosRecursivos {



    // corre instantáneo incluso para N=1000
    public static BigDecimal potenciaRecursivo(double base, int exponente) {
        if (exponente == 0) {
            return BigDecimal.ONE;
        }
        return BigDecimal.valueOf(base).multiply(potenciaRecursivo(base, exponente - 1));
    }

    public static BigInteger fibonacciRecursivo(int n) {
        if (n <= 0) {
            return BigInteger.ZERO;
        }
        if (n == 1) {
            return BigInteger.ONE;
        }
        return fibonacciRecursivo(n - 1).add(fibonacciRecursivo(n - 2));
    }
    
        public static BigInteger factorialRecursivo(int n) {
        if (n <= 1) {
            return BigInteger.ONE;
        }
        return BigInteger.valueOf(n).multiply(factorialRecursivo(n - 1));
    }

}
