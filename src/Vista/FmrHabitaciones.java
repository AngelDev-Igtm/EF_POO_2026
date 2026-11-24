
package Vista;

import Controladores.HabitacionController;
import Controladores.ReservaController;
import Modelos.Reserva;
import Modelos.Habitacion;
import Modelos.HabitacionSimple;
import Modelos.Suite;
import javax.swing.table.DefaultTableModel;
public class FmrHabitaciones extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FmrHabitaciones.class.getName());
    private HabitacionController habitacionController;
    private ReservaController reservaController;   // para no eliminar habitaciones con reservas activas

    public FmrHabitaciones(HabitacionController habController)
    {
        this(habController, null);
    }

    public FmrHabitaciones(HabitacionController habController, ReservaController resController) 
    {
        initComponents();
         this.habitacionController = habController;
         this.reservaController = resController;
        // el estado de las habitaciones cambia con el check-in / check-out: se refresca al volver
        addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override public void windowGainedFocus(java.awt.event.WindowEvent e) { cargarTablaHabitaciones(); }
        });

        configurarCombosIniciales();
        cargarTablaHabitaciones();
        setTitle("Gestión de Habitaciones - Indigo Loft");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        // mismo estilo que la pantalla Reservas (franja azul, fondo crema, tabla con encabezado azul)
        getContentPane().setBackground(UIUtil.FONDO);
        UIUtil.estilizarTabla(tblHabitaciones);
        tblHabitaciones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        UIUtil.colorearEstado(tblHabitaciones, 2);
        UIUtil.anchos(tblHabitaciones, 110, 130, 120, 110, 140);
        jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);
        UIUtil.cursorMano(btnRegistrar, btnActualizar, btnEliminar, btnBuscar, btnLimpiar, btnCerrar);
        setLocationRelativeTo(null);
    }
    
    private void configurarCombosIniciales() {
        cmbTipoHabitacion.removeAllItems();
        cmbTipoHabitacion.addItem("HabitacionSimple");
        cmbTipoHabitacion.addItem("Suite");

        cmbEstado.removeAllItems();
        cmbEstado.addItem("Disponible");
        cmbEstado.addItem("Ocupada");
        cmbEstado.addItem("Mantenimiento");
        cmbEstado.addItem("Limpieza");

        cmbDatoExtra.removeAllItems();
        cmbDatoExtra.addItem("SI");
        cmbDatoExtra.addItem("NO");

        cmbTipoHabitacion.addActionListener(e -> actualizarPorTipo());
        actualizarPorTipo();
    }
    
    private void actualizarPorTipo() {
        if (cmbTipoHabitacion.getSelectedItem() == null) return;
        String tipo = cmbTipoHabitacion.getSelectedItem().toString();
        if (tipo.equals("HabitacionSimple")) {
            lblDatoExtra.setText("¿Tiene vista a la calle?");
            if (txtNroHabitacion.isEditable()) {
                txtPrecioNoche.setText("90.0");
            }
        } else {
            lblDatoExtra.setText("¿Incluye jacuzzi?");
            if (txtNroHabitacion.isEditable()) {
                txtPrecioNoche.setText("310.0");
            }
        }
    }
    
    private void cargarTablaHabitaciones() {
        DefaultTableModel modelo = (DefaultTableModel) tblHabitaciones.getModel();
        modelo.setRowCount(0);

        for (Habitacion h : habitacionController.ListaHabitaciones) {
            String tipo = h.getClass().getSimpleName();
            String extra = "";

            if (h instanceof HabitacionSimple) {
                extra = ((HabitacionSimple) h).getTieneVistaCalle() ? "Vista calle: SÍ" : "Vista calle: NO";
            } else if (h instanceof Suite) {
                extra = ((Suite) h).getTieneJacuzzi() ? "Jacuzzi: SÍ" : "Jacuzzi: NO";
            }

            modelo.addRow(new Object[]{
                h.getNro_habitacion(),
                tipo,
                UIUtil.capitalizar(h.getEstado()),
                "S/ " + h.getPrecio_noche(),
                extra
            });
        }
    }
    private void limpiarCampos() {
        UIUtil.quitarMarcas(getContentPane());
        txtNroHabitacion.setText("");
        txtNroHabitacion.setEditable(true);
        txtNroHabitacion.setBackground(java.awt.Color.WHITE);
        cmbTipoHabitacion.setSelectedIndex(0);
        cmbTipoHabitacion.setEnabled(true);
        cmbEstado.setSelectedIndex(0);
        cmbDatoExtra.setSelectedIndex(0);
        txtPrecioNoche.setText("90.0");
        tblHabitaciones.clearSelection();
        txtNroHabitacion.requestFocus();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlEncabezado = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        pnlFormulario = new javax.swing.JPanel();
        pnlDatos = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNroHabitacion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbTipoHabitacion = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtPrecioNoche = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        lblDatoExtra = new javax.swing.JLabel();
        cmbDatoExtra = new javax.swing.JComboBox<>();
        lblRelleno = new javax.swing.JLabel();
        pnlSurForm = new javax.swing.JPanel();
        lblNota = new javax.swing.JLabel();
        pnlBotonesForm = new javax.swing.JPanel();
        btnRegistrar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        pnlListado = new javax.swing.JPanel();
        pnlTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHabitaciones = new javax.swing.JTable();
        lblAyuda = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Habitaciones - Indigo Loft");
        setMinimumSize(new java.awt.Dimension(900, 580));
        setSize(new java.awt.Dimension(1000, 620));

        pnlEncabezado.setBackground(new java.awt.Color(35, 59, 87));
        pnlEncabezado.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 18, 12, 18));
        pnlEncabezado.setLayout(new java.awt.GridLayout(2, 1, 0, 2));
        lblTitulo.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(196, 155, 92));
        lblTitulo.setText("GESTIÓN DE HABITACIONES");
        pnlEncabezado.add(lblTitulo);

        lblSubtitulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblSubtitulo.setForeground(new java.awt.Color(245, 235, 220));
        lblSubtitulo.setText("Registra las habitaciones simples y suites, su precio por noche y su estado");
        pnlEncabezado.add(lblSubtitulo);

        getContentPane().add(pnlEncabezado, java.awt.BorderLayout.NORTH);

        pnlFormulario.setOpaque(false);
        pnlFormulario.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 6));
        pnlFormulario.setLayout(new java.awt.BorderLayout());
        pnlDatos.setOpaque(false);
        pnlDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de la habitación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 12), new java.awt.Color(35, 59, 87))); // NOI18N
        pnlDatos.setLayout(new java.awt.GridBagLayout());
        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setText("<html>Nro habitación (100 - 2000) <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 8);
        pnlDatos.add(jLabel2, gridBagConstraints);

        txtNroHabitacion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNroHabitacion.setColumns(14);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        pnlDatos.add(txtNroHabitacion, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setText("<html>Tipo de habitación <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 8);
        pnlDatos.add(jLabel4, gridBagConstraints);

        cmbTipoHabitacion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cmbTipoHabitacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HabitacionSimple", "Suite" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        pnlDatos.add(cmbTipoHabitacion, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setText("<html>Precio por noche S/ <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 8);
        pnlDatos.add(jLabel3, gridBagConstraints);

        txtPrecioNoche.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPrecioNoche.setColumns(14);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        pnlDatos.add(txtPrecioNoche, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("<html>Estado <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 8);
        pnlDatos.add(jLabel5, gridBagConstraints);

        cmbEstado.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disponible", "Ocupada", "Mantenimiento", "Limpieza" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        pnlDatos.add(cmbEstado, gridBagConstraints);

        lblDatoExtra.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblDatoExtra.setText("¿Tiene vista a la calle?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 8);
        pnlDatos.add(lblDatoExtra, gridBagConstraints);

        cmbDatoExtra.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cmbDatoExtra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SI", "NO" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        pnlDatos.add(cmbDatoExtra, gridBagConstraints);

        lblRelleno.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblRelleno.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 8, 8);
        pnlDatos.add(lblRelleno, gridBagConstraints);

        pnlFormulario.add(pnlDatos, java.awt.BorderLayout.CENTER);

        pnlSurForm.setOpaque(false);
        pnlSurForm.setLayout(new java.awt.BorderLayout());
        lblNota.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblNota.setText("<html><font color='#B71C1C'>*</font> Campo obligatorio</html>");
        lblNota.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 10, 0, 0));
        pnlSurForm.add(lblNota, java.awt.BorderLayout.NORTH);

        pnlBotonesForm.setOpaque(false);
        pnlBotonesForm.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));
        pnlBotonesForm.setLayout(new java.awt.GridLayout(2, 3, 6, 6));
        btnRegistrar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnRegistrar.setBackground(new java.awt.Color(46, 125, 50));
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Registrar");
        btnRegistrar.setOpaque(true);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setPreferredSize(new java.awt.Dimension(110, 32));
        btnRegistrar.addActionListener(this::btnRegistrarActionPerformed);
        pnlBotonesForm.add(btnRegistrar);

        btnActualizar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnActualizar.setBackground(new java.awt.Color(46, 125, 50));
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.setOpaque(true);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setPreferredSize(new java.awt.Dimension(110, 32));
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);
        pnlBotonesForm.add(btnActualizar);

        btnEliminar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnEliminar.setBackground(new java.awt.Color(183, 28, 28));
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setOpaque(true);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setPreferredSize(new java.awt.Dimension(110, 32));
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);
        pnlBotonesForm.add(btnEliminar);

        btnBuscar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnBuscar.setBackground(new java.awt.Color(35, 59, 87));
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setOpaque(true);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setPreferredSize(new java.awt.Dimension(110, 32));
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);
        pnlBotonesForm.add(btnBuscar);

        btnLimpiar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnLimpiar.setBackground(new java.awt.Color(35, 59, 87));
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setOpaque(true);
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setPreferredSize(new java.awt.Dimension(110, 32));
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);
        pnlBotonesForm.add(btnLimpiar);

        btnCerrar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btnCerrar.setBackground(new java.awt.Color(35, 59, 87));
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setText("Cerrar");
        btnCerrar.setOpaque(true);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setPreferredSize(new java.awt.Dimension(110, 32));
        btnCerrar.addActionListener(this::btnCerrarActionPerformed);
        pnlBotonesForm.add(btnCerrar);

        pnlSurForm.add(pnlBotonesForm, java.awt.BorderLayout.CENTER);

        pnlFormulario.add(pnlSurForm, java.awt.BorderLayout.SOUTH);

        getContentPane().add(pnlFormulario, java.awt.BorderLayout.WEST);

        pnlListado.setOpaque(false);
        pnlListado.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 6, 12, 12));
        pnlListado.setLayout(new java.awt.BorderLayout(0, 8));
        pnlTabla.setOpaque(false);
        pnlTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Habitaciones registradas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 12), new java.awt.Color(35, 59, 87))); // NOI18N
        pnlTabla.setLayout(new java.awt.BorderLayout());
        tblHabitaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro. habitación", "Tipo", "Estado", "Precio / noche", "Detalle extra"
            }
        ));
        jScrollPane1.setViewportView(tblHabitaciones);

        pnlTabla.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlListado.add(pnlTabla, java.awt.BorderLayout.CENTER);

        lblAyuda.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblAyuda.setText("Para modificar o eliminar: escriba el número de la habitación y presione Buscar.");
        pnlListado.add(lblAyuda, java.awt.BorderLayout.SOUTH);

        getContentPane().add(pnlListado, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        try {
            UIUtil.quitarMarcas(getContentPane());
            String tipo = cmbTipoHabitacion.getSelectedItem().toString();
            boolean valorExtra = cmbDatoExtra.getSelectedItem().toString().equals("SI");
            Habitacion nuevaHab;
            if (tipo.equals("HabitacionSimple")) {
                HabitacionSimple simple = new HabitacionSimple();
                UIUtil.validar(cmbDatoExtra, "¿Tiene vista a la calle?", () -> simple.setTieneVistaCalle(valorExtra));
                nuevaHab = simple;
            } else {
                Suite suite = new Suite();
                UIUtil.validar(cmbDatoExtra, "¿Incluye jacuzzi?", () -> suite.setTieneJacuzzi(valorExtra));
                nuevaHab = suite;
            }
            // cada dato se valida en el modelo; si falla, se indica el campo
            int nro = UIUtil.entero(txtNroHabitacion, "Nro habitación");
            UIUtil.validar(txtNroHabitacion, "Nro habitación", () -> nuevaHab.setNro_habitacion(nro));
            double precio = UIUtil.decimal(txtPrecioNoche, "Precio por noche");
            UIUtil.validar(txtPrecioNoche, "Precio por noche", () -> nuevaHab.setPrecio_noche(precio));
            String estado = cmbEstado.getSelectedItem().toString();
            UIUtil.validar(cmbEstado, "Estado", () -> nuevaHab.setEstado(estado));

            // numero repetido -> se marca el campo del numero
            UIUtil.validar(txtNroHabitacion, "Nro habitación", () -> habitacionController.AñadirHabitacion(nuevaHab));
            cargarTablaHabitaciones();
            UIUtil.mostrarInfo(this, "Habitación registrada correctamente.");
            limpiarCampos();
        } catch (IllegalArgumentException e) {
            UIUtil.mostrarError(this, e);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        Habitacion h;
        try {
            UIUtil.quitarMarcas(getContentPane());
            int nro = UIUtil.entero(txtNroHabitacion, "Nro habitación");
            h = habitacionController.BuscarHabitacion(nro);
            if (h == null) {
                UIUtil.errorEnCampo(txtNroHabitacion, "Nro habitación", "No se encontró ninguna habitación con el número " + nro + ".");
            }
        } catch (IllegalArgumentException e) {
            UIUtil.mostrarError(this, e);
            return;
        }
        txtNroHabitacion.setEditable(false);
        txtNroHabitacion.setBackground(UIUtil.GRIS_CAMPO);
        cmbTipoHabitacion.setSelectedItem(h.getClass().getSimpleName());
        cmbTipoHabitacion.setEnabled(false);
        txtPrecioNoche.setText(String.valueOf(h.getPrecio_noche()));
        cmbEstado.setSelectedItem(UIUtil.capitalizar(h.getEstado()));   // "limpieza" -> "Limpieza"

        if (h instanceof HabitacionSimple) {
            cmbDatoExtra.setSelectedItem(((HabitacionSimple) h).getTieneVistaCalle() ? "SI" : "NO");
        } else if (h instanceof Suite) {
            cmbDatoExtra.setSelectedItem(((Suite) h).getTieneJacuzzi() ? "SI" : "NO");
        }
        UIUtil.mostrarInfo(this, "Habitación encontrada. Modifique los datos y presione Actualizar.");
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        try {
            UIUtil.quitarMarcas(getContentPane());
            int nro = UIUtil.entero(txtNroHabitacion, "Nro habitación");
            Habitacion h = habitacionController.BuscarHabitacion(nro);
            if (h == null) {
                UIUtil.errorEnCampo(txtNroHabitacion, "Nro habitación", "No existe una habitación con ese número. Búsquela primero.");
            }
            double precio = UIUtil.decimal(txtPrecioNoche, "Precio por noche");
            String estado = cmbEstado.getSelectedItem().toString();
            boolean valorExtra = cmbDatoExtra.getSelectedItem().toString().equals("SI");

            // se valida el precio en un objeto auxiliar antes de cambiar nada (asi el estado no queda a medias)
            UIUtil.validar(txtPrecioNoche, "Precio por noche", () -> new HabitacionSimple().setPrecio_noche(precio));
            UIUtil.validar(cmbEstado, "Estado", () -> h.setEstado(estado));
            h.setPrecio_noche(precio);
            if (h instanceof HabitacionSimple) {
                ((HabitacionSimple) h).setTieneVistaCalle(valorExtra);
            } else if (h instanceof Suite) {
                ((Suite) h).setTieneJacuzzi(valorExtra);
            }
            cargarTablaHabitaciones();
            UIUtil.mostrarInfo(this, "Habitación actualizada correctamente.");
            limpiarCampos();
        } catch (IllegalArgumentException e) {
            UIUtil.mostrarError(this, e);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        Habitacion h;
        try {
            UIUtil.quitarMarcas(getContentPane());
            int nro = UIUtil.entero(txtNroHabitacion, "Nro habitación");
            h = habitacionController.BuscarHabitacion(nro);
            if (h == null) {
                UIUtil.errorEnCampo(txtNroHabitacion, "Nro habitación", "No se encontró la habitación " + nro + ".");
            }
            // no se elimina si tiene reservas Confirmadas o En curso
            if (reservaController != null) {
                for (Reserva r : reservaController.ListaReservas) {
                    boolean activa = Reserva.CONFIRMADA.equals(r.getEstadoReserva()) || Reserva.EN_CURSO.equals(r.getEstadoReserva());
                    if (activa && r.getListaHabitaciones().contains(h)) {
                        UIUtil.errorEnCampo(txtNroHabitacion, "Nro habitación", "No se puede eliminar la habitación " + nro
                                + " porque está en la reserva " + r.getCod_reserva() + " (" + r.getEstadoReserva() + ").");
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            UIUtil.mostrarError(this, e);
            return;
        }
        if (UIUtil.confirmar(this, "¿Está seguro de eliminar la habitación Nro. " + h.getNro_habitacion() + "?", "Confirmar eliminación")) {
            habitacionController.ListaHabitaciones.remove(h);
            cargarTablaHabitaciones();
            limpiarCampos();
            UIUtil.mostrarInfo(this, "Habitación eliminada correctamente.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

   
    public static void main(String args[]) {
      
        java.awt.EventQueue.invokeLater(() -> new FmrHabitaciones(new HabitacionController()).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cmbDatoExtra;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbTipoHabitacion;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAyuda;
    private javax.swing.JLabel lblDatoExtra;
    private javax.swing.JLabel lblNota;
    private javax.swing.JLabel lblRelleno;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlBotonesForm;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JPanel pnlEncabezado;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JPanel pnlListado;
    private javax.swing.JPanel pnlSurForm;
    private javax.swing.JPanel pnlTabla;
    private javax.swing.JTable tblHabitaciones;
    private javax.swing.JTextField txtNroHabitacion;
    private javax.swing.JTextField txtPrecioNoche;
    // End of variables declaration//GEN-END:variables
}
