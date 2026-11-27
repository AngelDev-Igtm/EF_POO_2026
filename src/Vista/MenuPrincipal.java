package Vista;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Image;
import Controladores.EmpleadoController;
import Controladores.HabitacionController;
import Controladores.HuespedController;
import Controladores.ReservaController;


import Controladores.DepartamentoController;

public class MenuPrincipal extends javax.swing.JFrame {

    private JPanel panelPrincipal;
    private JLabel lblFondo;
    private JButton btnDepartamentos, btnHabitaciones, btnEmpleados, btnHuespedes, btnReservas, btnReportes;
    
    private DepartamentoController departamentoController = new DepartamentoController();
    private EmpleadoController empleadoController = new EmpleadoController(departamentoController);
    private HuespedController huespedController = new HuespedController();
    private HabitacionController habitacionController = new HabitacionController();  
    private ReservaController reservaController = new ReservaController(huespedController,empleadoController,habitacionController);    
    
    public MenuPrincipal() 
    {
        initComponents();
        configurarEfectoHover();
        configurarEtiquetas();
          
        jButton3.addActionListener(e -> 
        {
            FrmDepartamentos ventana = new FrmDepartamentos(departamentoController,empleadoController);
            ventana.setVisible(true);
        });
          
        // HUÉSPEDES
        jButton2.addActionListener(e -> 
        {
            FrmHuespedes ventana = new FrmHuespedes(huespedController);
            ventana.setVisible(true);
        });
        
        // RESERVAS (check-in / check-out incluidos)
        jButton4.addActionListener(e -> 
        {
            FrmReservas ventana = new FrmReservas(huespedController, habitacionController, reservaController, empleadoController);
            ventana.setVisible(true);
        });
        
        // REPORTES
        jButton5.addActionListener(e -> 
        {
            FrmReportes ventana = new FrmReportes(departamentoController, empleadoController, habitacionController, reservaController, huespedController);
            ventana.setVisible(true);
        });
         // EMPLEADOS
        jButton7.addActionListener(e -> 
        {
            FrmEmpleados ventana = new FrmEmpleados(empleadoController, departamentoController);
            ventana.setVisible(true);
        });        
        //HABITACIONES
        jButton1.addActionListener(e -> 
        {
            FmrHabitaciones ventana = new FmrHabitaciones(habitacionController, reservaController);
            ventana.setVisible(true);
        });
        // configuración de la ventana principal
        setTitle("Indigo Loft - Menú Principal");
        setSize(924, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        colocarFondo();
        setVisible(true);
    }

private void inicializarBotones() 
{
    // Creamos los 6 botones
        btnDepartamentos = new JButton("Departamentos");
        btnHabitaciones = new JButton("Habitaciones");
        btnEmpleados = new JButton("Empleados");
        btnHuespedes = new JButton("Huéspedes");
        btnReservas = new JButton("Reservas");
        btnReportes = new JButton("Reportes");

        // Asignamos coordenadas exactas
        // Fila 1
        btnDepartamentos.setBounds(150, 180, 230, 70);
        btnHabitaciones.setBounds(400, 180, 230, 70);
        btnEmpleados.setBounds(650, 180, 230, 70);

        // Fila 2
        btnHuespedes.setBounds(150, 320, 230, 70);
        btnReservas.setBounds(400, 320, 230, 70);
        btnReportes.setBounds(650, 320, 230, 70);

        // Los agregamos al panel PRINCIPAL (para que queden ENCIMA del fondo)
        panelPrincipal.add(btnDepartamentos);
        panelPrincipal.add(btnHabitaciones);
        panelPrincipal.add(btnEmpleados);
        panelPrincipal.add(btnHuespedes);
        panelPrincipal.add(btnReservas);
        panelPrincipal.add(btnReportes);

        // Forzamos el orden de las capas: Imagen al fondo (-1) y los botones al frente
        panelPrincipal.setComponentZOrder(lblFondo, panelPrincipal.getComponentCount() - 1);
}
    
private void configurarEfectoHover() 
{
    javax.swing.JButton[] botones = 
    {
        jButton3, 
        jButton1, 
        jButton7,
        jButton2, 
        jButton4, 
        jButton5,
    };

    for (javax.swing.JButton boton : botones) {

        // Fondo oscuro translúcido
        boton.setBackground(new java.awt.Color(30, 25, 21, 205));
        boton.setForeground(new java.awt.Color(245, 235, 220));

        boton.setFont(new java.awt.Font("SansSerif", 1, 17));

        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setFocusPainted(false);

        // Borde dorado
        boton.setBorder(javax.swing.BorderFactory.createCompoundBorder
        (new javax.swing.border.LineBorder(new java.awt.Color(196, 155, 92), 2, true)
        ,new javax.swing.border.EmptyBorder(10, 15, 10, 15)));

        boton.setBorderPainted(true);

        boton.addMouseListener(new java.awt.event.MouseAdapter() 
        {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boton.setBackground(
                    new java.awt.Color(75, 58, 42, 225)
                );
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                boton.setBackground(
                    new java.awt.Color(30, 25, 21, 205)
                );
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                boton.setBackground(
                    new java.awt.Color(15, 12, 10, 235)
                );
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (boton.contains(e.getPoint())) {
                    boton.setBackground(
                        new java.awt.Color(75, 58, 42, 225)
                    );
                } else {
                    boton.setBackground(
                        new java.awt.Color(30, 25, 21, 205)
                    );
                }
            }
        });
    }
}

private void configurarEtiquetas() {

    // Franja superior con el mismo estilo de Reservas: titulo dorado y subtitulo crema
    jLabel1.setForeground(UIUtil.DORADO);
    jLabel2.setForeground(new java.awt.Color(245, 235, 220));

    // Pie de página
    jLabel5.setForeground(new java.awt.Color(245, 235, 220));
}

private void colocarFondo() 
{
    try 
    {
        ImageIcon imagenOriginal = new ImageIcon(
            getClass().getResource("/Vista/fondo_indigo.jpg")
        );

        Image imagenEscalada = imagenOriginal.getImage()
            .getScaledInstance(924, 700, Image.SCALE_SMOOTH);

        JLabel fondo = new JLabel(new ImageIcon(imagenEscalada));

        fondo.setBounds(0, 0, 924, 700);

        getContentPane().add(
            fondo,
            new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 924, 700)
        );

        getContentPane().setComponentZOrder(
            fondo,
            getContentPane().getComponentCount() - 1
        );
    } catch (Exception e) 
    {
        System.out.println("Error al cargar el fondo: " + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Indigo Loft - Menú Principal");
        setResizable(false);
        setSize(new java.awt.Dimension(924, 700));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlEncabezado.setBackground(new java.awt.Color(35, 59, 87));
        pnlEncabezado.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 18, 12, 18));
        pnlEncabezado.setLayout(new java.awt.GridLayout(2, 1, 0, 2));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(196, 155, 92));
        jLabel1.setText("INDIGO LOFT · MENÚ PRINCIPAL");
        pnlEncabezado.add(jLabel1);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(245, 235, 220));
        jLabel2.setText("Seleccione el módulo que desea administrar");
        pnlEncabezado.add(jLabel2);

        getContentPane().add(pnlEncabezado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 924, 64));

        jButton3.setBackground(new java.awt.Color(35, 59, 87));
        jButton3.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("DEPARTAMENTOS");
        jButton3.setBorderPainted(false);
        jButton3.setFocusPainted(false);
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 170, 260, 90));

        jButton1.setBackground(new java.awt.Color(35, 59, 87));
        jButton1.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("HABITACIONES");
        jButton1.setBorderPainted(false);
        jButton1.setFocusPainted(false);
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(332, 170, 260, 90));

        jButton7.setBackground(new java.awt.Color(35, 59, 87));
        jButton7.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("EMPLEADOS");
        jButton7.setBorderPainted(false);
        jButton7.setFocusPainted(false);
        jButton7.addActionListener(this::jButton7ActionPerformed);
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(612, 170, 260, 90));

        jButton2.setBackground(new java.awt.Color(35, 59, 87));
        jButton2.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("HUÉSPEDES");
        jButton2.setBorderPainted(false);
        jButton2.setFocusPainted(false);
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 290, 260, 90));

        jButton4.setBackground(new java.awt.Color(35, 59, 87));
        jButton4.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("RESERVAS");
        jButton4.setBorderPainted(false);
        jButton4.setFocusPainted(false);
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(332, 290, 260, 90));

        jButton5.setBackground(new java.awt.Color(35, 59, 87));
        jButton5.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("REPORTES");
        jButton5.setBorderPainted(false);
        jButton5.setFocusPainted(false);
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(612, 290, 260, 90));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(245, 235, 220));
        jLabel5.setText("Indigo Loft  |  Sistema de gestión hotelera");
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setBackground(new java.awt.Color(35, 59, 87));
        jLabel5.setOpaque(true);
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 632, 924, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel pnlEncabezado;
    // End of variables declaration//GEN-END:variables

}