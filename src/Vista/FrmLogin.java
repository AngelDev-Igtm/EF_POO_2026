package Vista;

import Controladores.LoginController;
import javax.swing.JOptionPane;
import java.util.Arrays;

public class FrmLogin extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmLogin.class.getName());
    private char caracterOculto;
    private final LoginController loginController = new LoginController();

    @SuppressWarnings("unchecked")
    public FrmLogin()
    {
        initComponents();
        caracterOculto = jPasswordField1.getEchoChar();
        getContentPane().setBackground(UIUtil.FONDO);
        UIUtil.cursorMano(jButton1, jCheckBox1);
        getRootPane().setDefaultButton(jButton1);
        jButton1.addActionListener(e -> IniciarSesion());
        setLocationRelativeTo(null);
    }
    private void IniciarSesion()
    {
        String usuario = jTextField1.getText().trim();
        char[] clave = jPasswordField1.getPassword();
        try
        {
            if(usuario.isEmpty() || clave.length == 0)
            {
            JOptionPane.showMessageDialog(this,
                "Debe completar todos los campos.",
                "Campos incompletos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(loginController.ValidarCredenciales(
                usuario, new String(clave)))
        {
            new MenuPrincipal();
            this.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this,
                "Usuario o contraseña incorrectos.",
                "Acceso denegado",
                JOptionPane.ERROR_MESSAGE);

            jPasswordField1.setText("");
            jPasswordField1.requestFocus();
        }
    }
    catch(RuntimeException e)
    {
        JOptionPane.showMessageDialog(this,
            e.getMessage(),
            "Error del sistema",
            JOptionPane.ERROR_MESSAGE);
    }
    finally
    {
        Arrays.fill(clave, '\0');
    }
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlCentro = new javax.swing.JPanel();
        pnlLogin = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        lblPie = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Indigo Loft - Inicio de Sesión");
        setResizable(false);
        setSize(new java.awt.Dimension(460, 520));

        pnlEncabezado.setBackground(new java.awt.Color(35, 59, 87));
        pnlEncabezado.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 18, 18, 18));
        pnlEncabezado.setLayout(new java.awt.GridLayout(2, 1, 0, 4));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(196, 155, 92));
        jLabel1.setText("INDIGO LOFT");
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnlEncabezado.add(jLabel1);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(245, 235, 220));
        jLabel2.setText("Sistema de gestión hotelera");
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnlEncabezado.add(jLabel2);

        getContentPane().add(pnlEncabezado, java.awt.BorderLayout.NORTH);

        pnlCentro.setBackground(new java.awt.Color(245, 242, 235));
        pnlCentro.setOpaque(true);
        pnlCentro.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 24, 16, 24));
        pnlCentro.setLayout(new java.awt.GridBagLayout());
        pnlLogin.setBackground(new java.awt.Color(255, 255, 255));
        pnlLogin.setOpaque(true);
        pnlLogin.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Inicio de sesión", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 12), new java.awt.Color(35, 59, 87))); // NOI18N
        pnlLogin.setLayout(new java.awt.GridBagLayout());
        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(33, 33, 33));
        jLabel3.setText("Ingrese su usuario y contraseña");
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 18, 6, 18);
        pnlLogin.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(35, 59, 87));
        jLabel4.setText("Usuario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 18, 0, 18);
        pnlLogin.add(jLabel4, gridBagConstraints);

        jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jTextField1.setColumns(20);
        jTextField1.addActionListener(this::jTextField1ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 18, 0, 18);
        pnlLogin.add(jTextField1, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(35, 59, 87));
        jLabel6.setText("Contraseña");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 18, 0, 18);
        pnlLogin.add(jLabel6, gridBagConstraints);

        jPasswordField1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jPasswordField1.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 18, 0, 18);
        pnlLogin.add(jPasswordField1, gridBagConstraints);

        jCheckBox1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jCheckBox1.setText("Mostrar contraseña");
        jCheckBox1.setOpaque(false);
        jCheckBox1.addActionListener(this::jCheckBox1ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 14, 0, 18);
        pnlLogin.add(jCheckBox1, gridBagConstraints);

        jButton1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton1.setBackground(new java.awt.Color(35, 59, 87));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ingresar");
        jButton1.setOpaque(true);
        jButton1.setBorderPainted(false);
        jButton1.setFocusPainted(false);
        jButton1.setPreferredSize(new java.awt.Dimension(240, 34));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(14, 18, 18, 18);
        pnlLogin.add(jButton1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        pnlCentro.add(pnlLogin, gridBagConstraints);

        getContentPane().add(pnlCentro, java.awt.BorderLayout.CENTER);

        lblPie.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblPie.setForeground(new java.awt.Color(110, 110, 110));
        lblPie.setText("Proyecto final · Técnicas de Programación Orientada a Objetos");
        lblPie.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPie.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));
        getContentPane().add(lblPie, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        
        if (jCheckBox1.isSelected()) 
        {
            jPasswordField1.setEchoChar((char) 0);
        } else 
        {
        // Ocultar contraseña
            jPasswordField1.setEchoChar(caracterOculto);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmLogin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblPie;
    private javax.swing.JPanel pnlCentro;
    private javax.swing.JPanel pnlEncabezado;
    private javax.swing.JPanel pnlLogin;
    // End of variables declaration//GEN-END:variables
}
