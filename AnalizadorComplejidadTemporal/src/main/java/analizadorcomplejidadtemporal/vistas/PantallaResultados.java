/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package analizadorcomplejidadtemporal.vistas;

import analizadorcomplejidadtemporal.generadores.GeneradorDatos;
import analizadorcomplejidadtemporal.medidor.MedidorTiempo;
import java.util.LinkedHashMap;

/**
 *
 * @author Admin
 */
public class PantallaResultados extends javax.swing.JFrame {

    private java.util.List<String> algoritmosSeleccionados;

    // Configuración recibida
    private int[] tamanos;     // Tamaños de arreglo (null si no hay búsqueda)
    private int datoBuscar;    // Dato a buscar
    private int maxN;          // Valor máximo N para iter/recur (0 si no hay iter/recur)

    /**
     * Creates new form PantallaResultados
     */
    public PantallaResultados() {
        initComponents();
    }

    /**
     * Constructor unificado que recibe toda la configuración.
     * @param algoritmos  algoritmos seleccionados
     * @param tamanos     tamaños de arreglo (null si no hay búsqueda)
     * @param datoBuscar  dato a buscar (ignorado si no hay búsqueda)
     * @param maxN        valor máximo N (0 si no hay iter/recur)
     */
    public PantallaResultados(java.util.List<String> algoritmos, int[] tamanos, int datoBuscar, int maxN) {
        this.algoritmosSeleccionados = algoritmos;
        this.tamanos = tamanos;
        this.datoBuscar = datoBuscar;
        this.maxN = maxN;
        initComponents();
        ejecutarBenchmarks();
    }

    // Compatibilidad con constructor anterior
    public PantallaResultados(java.util.List<String> algoritmosSeleccionados, int[] tamanos, int datoBuscar) {
        this(algoritmosSeleccionados, tamanos, datoBuscar, 0);
    }

    public PantallaResultados(java.util.List<String> algoritmosSeleccionados, int maxN) {
        this(algoritmosSeleccionados, null, 0, maxN);
    }

    public PantallaResultados(java.util.List<String> algoritmosSeleccionados) {
        this.algoritmosSeleccionados = algoritmosSeleccionados;
        initComponents();
    }

    /**
     * Ejecuta los benchmarks y muestra la gráfica.
     */
    private void ejecutarBenchmarks() {
        txtTituloSeleccionDeAlgoritmos.setText("Ejecutando...");
        btnSiguienteHaciaInicio.setEnabled(false);

        new Thread(() -> {
            // Separar algoritmos por tipo
            java.util.List<String> algBusqueda = new java.util.ArrayList<>();
            java.util.List<String> algIterRecur = new java.util.ArrayList<>();
            for (String alg : algoritmosSeleccionados) {
                if (alg.contains("Búsqueda")) algBusqueda.add(alg);
                else algIterRecur.add(alg);
            }

            LinkedHashMap<String, double[]> resultados = new LinkedHashMap<>();
            int[] valoresX;

            if (!algBusqueda.isEmpty() && algIterRecur.isEmpty()) {
                // === MODO BÚSQUEDA ===
                valoresX = tamanos;
                // Generar UN arreglo por tamaño (compartido entre algoritmos para comparación justa)
                for (int i = 0; i < tamanos.length; i++) {
                    int tam = tamanos[i];
                    int[] arregloAleatorio = GeneradorDatos.generarArregloAleatorio(tam);
                    int[] arregloOrdenado = GeneradorDatos.ordenarArreglo(arregloAleatorio);

                    for (String algoritmo : algBusqueda) {
                        double[] tiempos = resultados.computeIfAbsent(algoritmo, k -> new double[tamanos.length]);
                        // Binaria necesita arreglo ordenado, las demás usan aleatorio
                        int[] arreglo = algoritmo.equals("Búsqueda Binaria") ? arregloOrdenado : arregloAleatorio;
                        tiempos[i] = MedidorTiempo.medirBusqueda(algoritmo, arreglo, datoBuscar);
                    }
                }

            } else if (algBusqueda.isEmpty() && !algIterRecur.isEmpty()) {
                // === MODO ITERATIVO/RECURSIVO ===
                int numPuntos = 10;
                if (maxN <= 10) numPuntos = maxN;
                valoresX = new int[numPuntos];
                for (int i = 0; i < numPuntos; i++) {
                    valoresX[i] = Math.max(1, (int) Math.round((double) (i + 1) / numPuntos * maxN));
                }

                for (String algoritmo : algIterRecur) {
                    double[] tiempos = new double[valoresX.length];
                    boolean timeout = false;
                    for (int i = 0; i < valoresX.length; i++) {
                        if (timeout) {
                            tiempos[i] = -1; // Marcar como timeout
                        } else {
                            tiempos[i] = MedidorTiempo.medirIterativoRecursivo(algoritmo, valoresX[i]);
                            if (tiempos[i] < 0) timeout = true; // Si hubo timeout, saltar el resto
                        }
                    }
                    resultados.put(algoritmo, tiempos);
                }

            } else {
                // === MODO MIXTO: búsqueda + iter/recur ===
                // Usar los tamaños de arreglo como eje X para todos
                valoresX = tamanos;

                // Búsqueda: usar arreglos
                for (int i = 0; i < tamanos.length; i++) {
                    int tam = tamanos[i];
                    int[] arregloAleatorio = GeneradorDatos.generarArregloAleatorio(tam);
                    int[] arregloOrdenado = GeneradorDatos.ordenarArreglo(arregloAleatorio);

                    for (String algoritmo : algBusqueda) {
                        double[] tiempos = resultados.computeIfAbsent(algoritmo, k -> new double[tamanos.length]);
                        int[] arreglo = algoritmo.equals("Búsqueda Binaria") ? arregloOrdenado : arregloAleatorio;
                        tiempos[i] = MedidorTiempo.medirBusqueda(algoritmo, arreglo, datoBuscar);
                    }
                }

                // Iter/recur: usar los mismos valores del eje X como N
                for (String algoritmo : algIterRecur) {
                    double[] tiempos = new double[tamanos.length];
                    for (int i = 0; i < tamanos.length; i++) {
                        // Limitar N a 1000 como dice el requisito
                        int n = Math.min(tamanos[i], 1000);
                        tiempos[i] = MedidorTiempo.medirIterativoRecursivo(algoritmo, n);
                    }
                    resultados.put(algoritmo, tiempos);
                }
            }

            final int[] ejeX = valoresX;
            final LinkedHashMap<String, double[]> res = resultados;

            javax.swing.SwingUtilities.invokeLater(() -> {
                txtTituloSeleccionDeAlgoritmos.setText("Resultados");
                btnSiguienteHaciaInicio.setEnabled(true);
                mostrarGrafica(ejeX, res);
            });
        }).start();
    }

    /**
     * Muestra la gráfica con los resultados.
     */
    private void mostrarGrafica(int[] valoresX, LinkedHashMap<String, double[]> resultados) {
        GraficaPersonalizada grafica = new GraficaPersonalizada();

        // Determinar etiqueta del eje X
        boolean todoBusqueda = true;
        boolean todoIterRecur = true;
        for (String alg : algoritmosSeleccionados) {
            if (alg.contains("Búsqueda")) todoIterRecur = false;
            else todoBusqueda = false;
        }
        String etiqueta;
        if (todoBusqueda) etiqueta = "Tamaño del arreglo";
        else if (todoIterRecur) etiqueta = "Valor de N";
        else etiqueta = "Tamaño de entrada";

        grafica.setDatos(valoresX, resultados, etiqueta);

        panelGraficoResultados.setLayout(new java.awt.BorderLayout());
        panelGraficoResultados.removeAll();
        panelGraficoResultados.add(grafica, java.awt.BorderLayout.CENTER);
        panelGraficoResultados.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 2),
            "Comparativa: " + String.join(" vs ", algoritmosSeleccionados),
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Segoe UI", 1, 14),
            java.awt.Color.WHITE
        ));
        panelGraficoResultados.revalidate();
        panelGraficoResultados.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtTituloSeleccionDeAlgoritmos = new javax.swing.JLabel();
        btnSiguienteHaciaInicio = new javax.swing.JButton();
        panelGraficoResultados = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(18, 24, 36));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 2));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(18, 24, 36));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 2));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTituloSeleccionDeAlgoritmos.setFont(new java.awt.Font("Segoe UI", 1, 64)); // NOI18N
        txtTituloSeleccionDeAlgoritmos.setForeground(new java.awt.Color(255, 255, 255));
        txtTituloSeleccionDeAlgoritmos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTituloSeleccionDeAlgoritmos.setText("Resultados");
        jPanel3.add(txtTituloSeleccionDeAlgoritmos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 140));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 140));

        btnSiguienteHaciaInicio.setBackground(new java.awt.Color(30, 41, 59));
        btnSiguienteHaciaInicio.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnSiguienteHaciaInicio.setForeground(new java.awt.Color(255, 255, 255));
        btnSiguienteHaciaInicio.setText("Siguiente");
        btnSiguienteHaciaInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteHaciaInicioActionPerformed(evt);
            }
        });
        jPanel1.add(btnSiguienteHaciaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 590, 240, 60));

        javax.swing.GroupLayout panelGraficoResultadosLayout = new javax.swing.GroupLayout(panelGraficoResultados);
        panelGraficoResultados.setLayout(panelGraficoResultadosLayout);
        panelGraficoResultadosLayout.setHorizontalGroup(
            panelGraficoResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
        );
        panelGraficoResultadosLayout.setVerticalGroup(
            panelGraficoResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );

        jPanel1.add(panelGraficoResultados, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 960, 410));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1099, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSiguienteHaciaInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteHaciaInicioActionPerformed
        PantallaInicio pantalla = new PantallaInicio();
        pantalla.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnSiguienteHaciaInicioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaResultados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSiguienteHaciaInicio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panelGraficoResultados;
    private javax.swing.JLabel txtTituloSeleccionDeAlgoritmos;
    // End of variables declaration//GEN-END:variables
}
