/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package analizadorcomplejidadtemporal.vistas;

/**
 *
 * @author Admin
 */
public class PantallaSeleccionTamañoArreglo extends javax.swing.JFrame {

    private java.util.List<String> algoritmosSeleccionados;
    private final java.util.List<Integer> tamanosSeleccionados = new java.util.ArrayList<>();
    private boolean hayBusqueda;
    private boolean hayIterRecur;

    // Componentes dinámicos agregados por código
    private javax.swing.JLabel lblDatoBuscar;
    private javax.swing.JTextField txtDatoBuscar;
    private javax.swing.JLabel lblMaxN;
    private javax.swing.JTextField txtMaxN;

    /**
     * Creates new form PantallaSeleccionTamañoArreglo
     */
    public PantallaSeleccionTamañoArreglo() {
        initComponents();
    }

    /**
     * Constructor que recibe los algoritmos seleccionados.
     */
    public PantallaSeleccionTamañoArreglo(java.util.List<String> algoritmos) {
        initComponents();
        this.algoritmosSeleccionados = algoritmos;
        // Detectar qué tipos de algoritmos se seleccionaron
        this.hayBusqueda = false;
        this.hayIterRecur = false;
        for (String alg : algoritmos) {
            if (alg.contains("Búsqueda")) hayBusqueda = true;
            else hayIterRecur = true;
        }
        configurarPantalla();
    }

    /**
     * Configura la pantalla según los tipos de algoritmos seleccionados.
     */
    private void configurarPantalla() {
        btnSiguienteHaciaResultados.setEnabled(false);

        if (hayBusqueda) {
            // Mostrar botones de tamaño + campo de dato a buscar
            int posXLabel = hayIterRecur ? 80 : 200;
            int posY = hayIterRecur ? 510 : 530;
            int posXTxt = hayIterRecur ? 280 : 430;
            int widthTxt = hayIterRecur ? 140 : 200;

            lblDatoBuscar = new javax.swing.JLabel("Dato a buscar:");
            lblDatoBuscar.setFont(new java.awt.Font("Segoe UI", 1, 22));
            lblDatoBuscar.setForeground(java.awt.Color.WHITE);
            jPanel1.add(lblDatoBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(posXLabel, posY, 190, 40));

            txtDatoBuscar = new javax.swing.JTextField("25");
            txtDatoBuscar.setFont(new java.awt.Font("Segoe UI", 0, 22));
            txtDatoBuscar.setBackground(new java.awt.Color(30, 41, 59));
            txtDatoBuscar.setForeground(java.awt.Color.WHITE);
            txtDatoBuscar.setCaretColor(java.awt.Color.WHITE);
            txtDatoBuscar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            aplicarFiltroNumerico(txtDatoBuscar);
            jPanel1.add(txtDatoBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(posXTxt, posY, widthTxt, 40));
        }

        if (hayIterRecur && !hayBusqueda) {
            // Solo iter/recur: ocultar botones de tamaño, mostrar campo N
            btn100.setVisible(false);
            btn1000.setVisible(false);
            btn10000.setVisible(false);
            btn100000.setVisible(false);
            btn500001.setVisible(false);

            txtTituloSeleccionDeAlgoritmos.setText("Selección del valor N");
        }

        if (hayIterRecur) {
            // Agregar campo de texto para N (posición depende de si también hay búsqueda)
            int posXLabel = hayBusqueda ? 80 : 150;
            int posY = hayBusqueda ? 580 : 260;
            int posXTxt = hayBusqueda ? 410 : 580;
            int widthTxt = hayBusqueda ? 140 : 200;
            String textoLabel = hayBusqueda ? "Valor N (iter/recur, 1-1000):" : "Valor máximo de N (1 - 1000):";

            lblMaxN = new javax.swing.JLabel(textoLabel);
            lblMaxN.setFont(new java.awt.Font("Segoe UI", 1, 22));
            lblMaxN.setForeground(java.awt.Color.WHITE);
            jPanel1.add(lblMaxN, new org.netbeans.lib.awtextra.AbsoluteConstraints(posXLabel, posY, 320, 50));

            txtMaxN = new javax.swing.JTextField("40");
            txtMaxN.setFont(new java.awt.Font("Segoe UI", 1, 28));
            txtMaxN.setBackground(new java.awt.Color(30, 41, 59));
            txtMaxN.setForeground(java.awt.Color.WHITE);
            txtMaxN.setCaretColor(java.awt.Color.WHITE);
            txtMaxN.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            aplicarFiltroNumerico(txtMaxN);
            jPanel1.add(txtMaxN, new org.netbeans.lib.awtextra.AbsoluteConstraints(posXTxt, posY, widthTxt, 50));
        }

        // Habilitar siguiente según lo que se necesite
        actualizarBotonSiguiente();

        jPanel1.revalidate();
        jPanel1.repaint();
    }

    /**
     * Aplica un filtro al campo de texto para que solo acepte números (dígitos) y retroceso/borrado.
     */
    private void aplicarFiltroNumerico(javax.swing.JTextField textField) {
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) && c != java.awt.event.KeyEvent.VK_BACK_SPACE && c != java.awt.event.KeyEvent.VK_DELETE) {
                    evt.consume(); // Ignorar cualquier carácter que no sea número
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }

    private void actualizarBotonSiguiente() {
        if (hayBusqueda) {
            // Necesita al menos 2 tamaños seleccionados
            btnSiguienteHaciaResultados.setEnabled(tamanosSeleccionados.size() >= 2);
        } else {
            // Solo iter/recur: siempre se puede continuar
            btnSiguienteHaciaResultados.setEnabled(true);
        }
    }

    /**
     * Alterna la selección de un tamaño de arreglo.
     */
    private void alternarTamano(javax.swing.JButton boton, int tamano) {
        Integer t = tamano;
        if (tamanosSeleccionados.contains(t)) {
            tamanosSeleccionados.remove(t);
            boton.setBackground(new java.awt.Color(30, 41, 59));
            boton.setForeground(java.awt.Color.WHITE);
        } else {
            tamanosSeleccionados.add(t);
            boton.setBackground(new java.awt.Color(0, 204, 204));
            boton.setForeground(new java.awt.Color(18, 24, 36));
        }
        java.util.Collections.sort(tamanosSeleccionados);
        actualizarBotonSiguiente();
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
        btn100 = new javax.swing.JButton();
        btn1000 = new javax.swing.JButton();
        btn10000 = new javax.swing.JButton();
        btn100000 = new javax.swing.JButton();
        btnSiguienteHaciaResultados = new javax.swing.JButton();
        btn500001 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(18, 24, 36));

        jPanel1.setBackground(new java.awt.Color(18, 24, 36));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 2));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(18, 24, 36));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 2));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTituloSeleccionDeAlgoritmos.setFont(new java.awt.Font("Segoe UI", 1, 64)); // NOI18N
        txtTituloSeleccionDeAlgoritmos.setForeground(new java.awt.Color(255, 255, 255));
        txtTituloSeleccionDeAlgoritmos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTituloSeleccionDeAlgoritmos.setText("Selección de tamaño del arreglo");
        jPanel3.add(txtTituloSeleccionDeAlgoritmos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 140));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 140));

        btn100.setBackground(new java.awt.Color(30, 41, 59));
        btn100.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btn100.setForeground(new java.awt.Color(255, 255, 255));
        btn100.setText("100");
        btn100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn100ActionPerformed(evt);
            }
        });
        jPanel1.add(btn100, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 240, 80));

        btn1000.setBackground(new java.awt.Color(30, 41, 59));
        btn1000.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btn1000.setForeground(new java.awt.Color(255, 255, 255));
        btn1000.setText("1000");
        btn1000.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1000ActionPerformed(evt);
            }
        });
        jPanel1.add(btn1000, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 220, 240, 80));

        btn10000.setBackground(new java.awt.Color(30, 41, 59));
        btn10000.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btn10000.setForeground(new java.awt.Color(255, 255, 255));
        btn10000.setText("10000");
        btn10000.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn10000ActionPerformed(evt);
            }
        });
        jPanel1.add(btn10000, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 220, 240, 80));

        btn100000.setBackground(new java.awt.Color(30, 41, 59));
        btn100000.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btn100000.setForeground(new java.awt.Color(255, 255, 255));
        btn100000.setText("100000");
        btn100000.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn100000ActionPerformed(evt);
            }
        });
        jPanel1.add(btn100000, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 410, 240, 80));

        btnSiguienteHaciaResultados.setBackground(new java.awt.Color(30, 41, 59));
        btnSiguienteHaciaResultados.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnSiguienteHaciaResultados.setForeground(new java.awt.Color(255, 255, 255));
        btnSiguienteHaciaResultados.setText("Siguiente");
        btnSiguienteHaciaResultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteHaciaResultadosActionPerformed(evt);
            }
        });
        jPanel1.add(btnSiguienteHaciaResultados, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 570, 240, 80));

        btn500001.setBackground(new java.awt.Color(30, 41, 59));
        btn500001.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btn500001.setForeground(new java.awt.Color(255, 255, 255));
        btn500001.setText("500000");
        btn500001.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn500001ActionPerformed(evt);
            }
        });
        jPanel1.add(btn500001, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 410, 240, 80));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn10000ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn10000ActionPerformed
        alternarTamano(btn10000, 10000);
    }//GEN-LAST:event_btn10000ActionPerformed

    private void btnSiguienteHaciaResultadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteHaciaResultadosActionPerformed
        int datoBuscar = 0;
        int[] tamanos = null;
        int maxN = 0;

        if (hayBusqueda) {
            try {
                datoBuscar = Integer.parseInt(txtDatoBuscar.getText().trim());
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Ingresa un número válido para buscar.");
                return;
            }
            tamanos = tamanosSeleccionados.stream().mapToInt(Integer::intValue).toArray();
        }
        if (hayIterRecur) {
            try {
                maxN = Integer.parseInt(txtMaxN.getText().trim());
                if (maxN < 1 || maxN > 1000) {
                    javax.swing.JOptionPane.showMessageDialog(this, "El valor de N debe estar entre 1 y 1000.");
                    return;
                }
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Ingresa un número válido entre 1 y 1000 para el valor de N.");
                return;
            }
        }

        PantallaResultados pantalla = new PantallaResultados(algoritmosSeleccionados, tamanos, datoBuscar, maxN);
        pantalla.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnSiguienteHaciaResultadosActionPerformed

    private void btn1000ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1000ActionPerformed
        alternarTamano(btn1000, 1000);
    }//GEN-LAST:event_btn1000ActionPerformed

    private void btn100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn100ActionPerformed
        alternarTamano(btn100, 100);
    }//GEN-LAST:event_btn100ActionPerformed

    private void btn100000ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn100000ActionPerformed
        alternarTamano(btn100000, 100000);
    }//GEN-LAST:event_btn100000ActionPerformed

    private void btn500001ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn500001ActionPerformed
        alternarTamano(btn500001, 500000);
    }//GEN-LAST:event_btn500001ActionPerformed

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
            java.util.logging.Logger.getLogger(PantallaSeleccionTamañoArreglo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaSeleccionTamañoArreglo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaSeleccionTamañoArreglo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaSeleccionTamañoArreglo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaSeleccionTamañoArreglo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn100;
    private javax.swing.JButton btn1000;
    private javax.swing.JButton btn10000;
    private javax.swing.JButton btn100000;
    private javax.swing.JButton btn500001;
    private javax.swing.JButton btnSiguienteHaciaResultados;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel txtTituloSeleccionDeAlgoritmos;
    // End of variables declaration//GEN-END:variables
}
