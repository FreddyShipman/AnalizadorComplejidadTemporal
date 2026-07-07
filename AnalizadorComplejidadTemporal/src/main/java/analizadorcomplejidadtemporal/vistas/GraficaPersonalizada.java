package analizadorcomplejidadtemporal.vistas;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Componente gráfico personalizado para renderizar la gráfica comparativa.
 * Dibuja ejes X e Y, líneas de tiempo para cada algoritmo,
 * leyenda y resalta el algoritmo con mejor tiempo.
 */
public class GraficaPersonalizada extends JPanel {

    private int[] valoresEjeX;             // Tamaños de entrada (N o tamaño de arreglo)
    private LinkedHashMap<String, double[]> resultados; // Algoritmo -> tiempos (nanosegundos)
    private String etiquetaEjeX = "Tamaño de entrada";
    private String mejorAlgoritmo = "";

    private static final Color[] COLORES = {
        new Color(0, 204, 204),    // Cyan
        new Color(255, 99, 132),   // Rosa
        new Color(255, 206, 86),   // Amarillo
    };

    private static final int MARGEN_IZQ = 100;
    private static final int MARGEN_DER = 30;
    private static final int MARGEN_SUP = 30;
    private static final int MARGEN_INF = 80;

    public GraficaPersonalizada() {
        setBackground(new Color(18, 24, 36));
    }

    /**
     * Establece los datos a graficar.
     * @param valoresX  valores del eje X (tamaños de entrada)
     * @param resultados mapa ordenado: nombre algoritmo -> array de tiempos en ns
     * @param etiquetaX  etiqueta para el eje X
     */
    public void setDatos(int[] valoresX, LinkedHashMap<String, double[]> resultados, String etiquetaX) {
        this.valoresEjeX = valoresX;
        this.resultados = resultados;
        this.etiquetaEjeX = etiquetaX;
        calcularMejorAlgoritmo();
        repaint();
    }

    private void calcularMejorAlgoritmo() {
        if (resultados == null || resultados.isEmpty()) return;

        double menorTiempoTotal = Double.MAX_VALUE;
        for (Map.Entry<String, double[]> entry : resultados.entrySet()) {
            double suma = 0;
            int validos = 0;
            for (double t : entry.getValue()) {
                if (t >= 0) {
                    suma += t;
                    validos++;
                }
            }
            if (validos > 0 && suma < menorTiempoTotal) {
                menorTiempoTotal = suma;
                mejorAlgoritmo = entry.getKey();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (resultados == null || valoresEjeX == null || resultados.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();
        int anchoGrafica = ancho - MARGEN_IZQ - MARGEN_DER;
        int altoGrafica = alto - MARGEN_SUP - MARGEN_INF;

        if (anchoGrafica <= 0 || altoGrafica <= 0) return;

        // Encontrar el valor máximo de Y (en nanosegundos)
        double maxY = 0;
        for (double[] tiempos : resultados.values()) {
            for (double t : tiempos) {
                if (t > maxY) maxY = t;
            }
        }
        if (maxY <= 0) maxY = 1;

        // Dibujar cuadrícula
        g2.setColor(new Color(40, 50, 70));
        g2.setStroke(new BasicStroke(1));
        int numLineas = 5;
        for (int i = 0; i <= numLineas; i++) {
            int y = MARGEN_SUP + (int) ((double) i / numLineas * altoGrafica);
            g2.drawLine(MARGEN_IZQ, y, ancho - MARGEN_DER, y);
        }

        // Dibujar ejes
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(MARGEN_IZQ, MARGEN_SUP, MARGEN_IZQ, alto - MARGEN_INF);
        g2.drawLine(MARGEN_IZQ, alto - MARGEN_INF, ancho - MARGEN_DER, alto - MARGEN_INF);

        // Etiquetas del eje Y
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        for (int i = 0; i <= numLineas; i++) {
            int y = MARGEN_SUP + (int) ((double) i / numLineas * altoGrafica);
            double valor = maxY * (1.0 - (double) i / numLineas);
            String etiqueta = formatearTiempo(valor);
            FontMetrics fm = g2.getFontMetrics();
            g2.setColor(new Color(180, 180, 180));
            g2.drawString(etiqueta, MARGEN_IZQ - fm.stringWidth(etiqueta) - 8, y + fm.getAscent() / 2);
        }

        // Etiqueta eje Y rotada
        g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        g2.setColor(Color.WHITE);
        AffineTransform original = g2.getTransform();
        g2.rotate(-Math.PI / 2, 18, alto / 2);
        g2.drawString("Tiempo", 18, alto / 2);
        g2.setTransform(original);

        // Etiquetas del eje X
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        for (int i = 0; i < valoresEjeX.length; i++) {
            int x = MARGEN_IZQ + (int) ((double) i / (valoresEjeX.length - 1) * anchoGrafica);
            String label = formatearNumero(valoresEjeX[i]);
            FontMetrics fm = g2.getFontMetrics();
            g2.setColor(new Color(180, 180, 180));
            g2.drawString(label, x - fm.stringWidth(label) / 2, alto - MARGEN_INF + 20);
        }

        // Etiqueta eje X
        g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(etiquetaEjeX, ancho / 2 - fm.stringWidth(etiquetaEjeX) / 2, alto - 10);

        // Dibujar líneas de cada algoritmo
        int colorIdx = 0;
        int leyendaY = MARGEN_SUP + 15;
        for (Map.Entry<String, double[]> entry : resultados.entrySet()) {
            String nombre = entry.getKey();
            double[] tiempos = entry.getValue();
            Color color = COLORES[colorIdx % COLORES.length];
            boolean esMejor = nombre.equals(mejorAlgoritmo);

            // Línea más gruesa para el mejor
            g2.setStroke(esMejor ? new BasicStroke(4) : new BasicStroke(2));
            g2.setColor(color);

            int prevX = -1, prevY = -1;
            for (int i = 0; i < tiempos.length && i < valoresEjeX.length; i++) {
                if (tiempos[i] < 0) continue; // timeout

                int x = MARGEN_IZQ + (int) ((double) i / (valoresEjeX.length - 1) * anchoGrafica);
                int y = MARGEN_SUP + altoGrafica - (int) (tiempos[i] / maxY * altoGrafica);
                y = Math.max(MARGEN_SUP, Math.min(alto - MARGEN_INF, y));

                // Punto
                g2.fillOval(x - 4, y - 4, 8, 8);

                // Línea
                if (prevX >= 0) {
                    g2.drawLine(prevX, prevY, x, y);
                }
                prevX = x;
                prevY = y;
            }

            // Leyenda
            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            String textoLeyenda = esMejor ? "★ " + nombre + " (MEJOR)" : nombre;
            g2.setColor(color);
            g2.fillRect(ancho - MARGEN_DER - 250, leyendaY - 10, 14, 14);
            g2.setColor(esMejor ? Color.YELLOW : Color.WHITE);
            g2.drawString(textoLeyenda, ancho - MARGEN_DER - 230, leyendaY);
            leyendaY += 22;

            colorIdx++;
        }
    }

    private String formatearTiempo(double nanos) {
        if (nanos >= 1_000_000_000) {
            return String.format("%.2f s", nanos / 1_000_000_000.0);
        } else if (nanos >= 1_000_000) {
            return String.format("%.2f ms", nanos / 1_000_000.0);
        } else if (nanos >= 1_000) {
            return String.format("%.1f µs", nanos / 1_000.0);
        } else {
            return String.format("%.0f ns", nanos);
        }
    }

    private String formatearNumero(int n) {
        if (n >= 1_000_000) return (n / 1_000_000) + "M";
        if (n >= 1_000) return (n / 1_000) + "K";
        return String.valueOf(n);
    }
}
