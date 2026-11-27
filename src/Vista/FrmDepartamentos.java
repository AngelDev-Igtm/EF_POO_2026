package Vista;
import Controladores.DepartamentoController;
import javax.swing.table.DefaultTableModel;
import Modelos.Departamento;
import Controladores.EmpleadoController;
import Modelos.Empleado;
import Modelos.Gerente;

public class FrmDepartamentos extends javax.swing.JFrame 
{
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmDepartamentos.class.getName());
    private DepartamentoController departamentoController;
    private EmpleadoController empleadoController;
    
    public FrmDepartamentos(DepartamentoController controlador,EmpleadoController controladorEmpleados) 
    {
        initComponents();
        jTextField1.setEditable(true);
        this.departamentoController = controlador;
        this.empleadoController = controladorEmpleados;
        CargarGerentesEnComboBox();
        
        jComboBox1.addActionListener(e -> { Object seleccionado = jComboBox1.getSelectedItem();
        if (seleccionado != null && seleccionado.toString().equals("+ Registrar nuevo gerente")) 
        {
            DlgRegistrarGerente ventana = new DlgRegistrarGerente(
            this,
            true,
            empleadoController
        );
            int antes = empleadoController.ListaEmpleados.size();
            ventana.setVisible(true);
            // Actualizar la lista después de cerrar la ventana y dejar elegido al gerente recien registrado
            CargarGerentesEnComboBox();
            if (empleadoController.ListaEmpleados.size() > antes)
            {
                Empleado nuevo = empleadoController.ListaEmpleados.get(empleadoController.ListaEmpleados.size() - 1);
                if (nuevo instanceof Gerente)
                {
                    jComboBox1.setSelectedItem(TextoGerente((Gerente) nuevo));
                }
            }
            else
            {
                jComboBox1.setSelectedIndex(0);
            }
        }
        });
        // al volver de Empleados se refrescan los gerentes y la tabla
        addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override public void windowGainedFocus(java.awt.event.WindowEvent e)
            {
                Object previo = jComboBox1.getSelectedItem();
                CargarGerentesEnComboBox();
                if (previo != null && !previo.toString().startsWith("+")) { jComboBox1.setSelectedItem(previo); }
                CargarTablaDepartamentos();
            }
        });

        // mismo estilo que la pantalla Reservas (franja azul, fondo crema, tabla con encabezado azul)
        getContentPane().setBackground(UIUtil.FONDO);
        UIUtil.estilizarTabla(jTable1);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        UIUtil.anchos(jTable1, 90, 170, 150, 200);
        jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);
        UIUtil.cursorMano(jButton1, jButton2, jButton3, jButton4, jButton5, btnCerrar);
        setLocationRelativeTo(null);
        CargarTablaDepartamentos();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        pnlEncabezado = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        pnlFormulario = new javax.swing.JPanel();
        pnlDatos = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        lblRelleno = new javax.swing.JLabel();
        pnlSurForm = new javax.swing.JPanel();
        lblNota = new javax.swing.JLabel();
        pnlBotonesForm = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        pnlListado = new javax.swing.JPanel();
        pnlTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblAyuda = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Departamentos - Indigo Loft");
        setMinimumSize(new java.awt.Dimension(900, 560));
        setSize(new java.awt.Dimension(980, 600));

        pnlEncabezado.setBackground(new java.awt.Color(35, 59, 87));
        pnlEncabezado.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 18, 12, 18));
        pnlEncabezado.setLayout(new java.awt.GridLayout(2, 1, 0, 2));

        lblTitulo.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(196, 155, 92));
        lblTitulo.setText("GESTIÓN DE DEPARTAMENTOS");
        pnlEncabezado.add(lblTitulo);

        lblSubtitulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblSubtitulo.setForeground(new java.awt.Color(245, 235, 220));
        lblSubtitulo.setText("Administra los departamentos del hotel, su presupuesto y el gerente responsable");
        pnlEncabezado.add(lblSubtitulo);

        getContentPane().add(pnlEncabezado, java.awt.BorderLayout.NORTH);

        pnlFormulario.setOpaque(false);
        pnlFormulario.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 6));
        pnlFormulario.setLayout(new java.awt.BorderLayout());

        pnlDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del departamento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 12), new java.awt.Color(35, 59, 87))); // NOI18N
        pnlDatos.setOpaque(false);
        pnlDatos.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setText("ID del departamento (DEP-XXX)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 8);
        pnlDatos.add(jLabel3, gridBagConstraints);

        jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jTextField1.setColumns(14);
        jTextField1.setToolTipText("Se genera al registrar. Escríbalo para buscar, actualizar o eliminar.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        pnlDatos.add(jTextField1, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setText("<html>Nombre del departamento <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 8);
        pnlDatos.add(jLabel4, gridBagConstraints);

        jTextField2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jTextField2.setColumns(14);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        pnlDatos.add(jTextField2, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("<html>Presupuesto mensual S/ <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 8);
        pnlDatos.add(jLabel5, gridBagConstraints);

        jTextField3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jTextField3.setColumns(14);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        pnlDatos.add(jTextField3, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setText("<html>Gerente a cargo <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 8);
        pnlDatos.add(jLabel6, gridBagConstraints);

        jComboBox1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un gerente" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        pnlDatos.add(jComboBox1, gridBagConstraints);

        lblRelleno.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblRelleno.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
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

        jButton1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton1.setBackground(new java.awt.Color(46, 125, 50));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Registrar");
        jButton1.setOpaque(true);
        jButton1.setBorderPainted(false);
        jButton1.setFocusPainted(false);
        jButton1.setPreferredSize(new java.awt.Dimension(110, 32));
        jButton1.addActionListener(this::jButton1ActionPerformed);
        pnlBotonesForm.add(jButton1);

        jButton2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton2.setBackground(new java.awt.Color(46, 125, 50));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Actualizar");
        jButton2.setOpaque(true);
        jButton2.setBorderPainted(false);
        jButton2.setFocusPainted(false);
        jButton2.setPreferredSize(new java.awt.Dimension(110, 32));
        jButton2.addActionListener(this::jButton2ActionPerformed);
        pnlBotonesForm.add(jButton2);

        jButton3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton3.setBackground(new java.awt.Color(183, 28, 28));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Eliminar");
        jButton3.setOpaque(true);
        jButton3.setBorderPainted(false);
        jButton3.setFocusPainted(false);
        jButton3.setPreferredSize(new java.awt.Dimension(110, 32));
        jButton3.addActionListener(this::jButton3ActionPerformed);
        pnlBotonesForm.add(jButton3);

        jButton5.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton5.setBackground(new java.awt.Color(35, 59, 87));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Buscar");
        jButton5.setOpaque(true);
        jButton5.setBorderPainted(false);
        jButton5.setFocusPainted(false);
        jButton5.setPreferredSize(new java.awt.Dimension(110, 32));
        jButton5.addActionListener(this::jButton5ActionPerformed);
        pnlBotonesForm.add(jButton5);

        jButton4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton4.setBackground(new java.awt.Color(35, 59, 87));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Limpiar");
        jButton4.setOpaque(true);
        jButton4.setBorderPainted(false);
        jButton4.setFocusPainted(false);
        jButton4.setPreferredSize(new java.awt.Dimension(110, 32));
        jButton4.addActionListener(this::jButton4ActionPerformed);
        pnlBotonesForm.add(jButton4);

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
        pnlTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Departamentos registrados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 12), new java.awt.Color(35, 59, 87))); // NOI18N
        pnlTabla.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Departamento", "Presupuesto mensual S/", "Gerente a cargo"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        pnlTabla.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlListado.add(pnlTabla, java.awt.BorderLayout.CENTER);

        lblAyuda.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblAyuda.setText("Para modificar o eliminar: escriba el ID del departamento y presione Buscar.");
        pnlListado.add(lblAyuda, java.awt.BorderLayout.SOUTH);

        getContentPane().add(pnlListado, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void CargarGerentesEnComboBox()
    {
        // Limpiar los elementos anteriores
        jComboBox1.removeAllItems();
        // Primera opción
        jComboBox1.addItem("Seleccione un gerente");
        // Recorrer todos los empleados registrados
        for (Empleado empleado : empleadoController.ListaEmpleados)
        {
            if (empleado instanceof Gerente)
            {
                Gerente gerente = (Gerente) empleado;
                jComboBox1.addItem(TextoGerente(gerente));
            }
        }
        // Opción para registrar un nuevo gerente
        jComboBox1.addItem("+ Registrar nuevo gerente");
    }
    //-- El nombre del gerente q registremos
    private String ObtenerNombreGerente(Departamento departamento) 
    {
        Gerente gerente = departamento.getGerente_a_cargo();
        if (gerente == null) 
        {
            return "Sin gerente";
        }
        return gerente.getNombre() + " " + gerente.getApellido_pat() + " " + gerente.getApellido_mat();
    }
    
    private Gerente ObtenerGerenteSeleccionado() 
    {

    Object seleccionado = jComboBox1.getSelectedItem();

    if (seleccionado == null) 
    {
        return null;
    }

    for (Empleado empleado : empleadoController.ListaEmpleados) 
    {
        if (empleado instanceof Gerente) 
        {    
            Gerente gerente = (Gerente) empleado;
            if (TextoGerente(gerente).equals(seleccionado.toString())) {
                return gerente;
            }
        }
    }
    return null;
    }
    private void CargarTablaDepartamentos()
    {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
        for (Departamento departamento : departamentoController.ListaDepartamentos) 
        {
            modelo.addRow(new Object[]
            {
                departamento.getId_departamento(),
                departamento.getNombre_departamento(),
                UIUtil.soles(departamento.getPresupuesto_mensual()),
                ObtenerNombreGerente(departamento)
            });
        }
    }
    
    private String TextoGerente(Gerente gerente)
    {
        return gerente.getNombre() + " " + gerente.getApellido_pat() + " " + gerente.getApellido_mat() + " - " + gerente.getTipo_doc().toUpperCase() + ": " + gerente.getNro_doc();
    }

    /** Gerente elegido en el combo (null si dejo "Seleccione un gerente"). */
    private Gerente GerenteDelCombo()
    {
        if (jComboBox1.getSelectedIndex() == jComboBox1.getItemCount() - 1)
        {
            UIUtil.errorEnCampo(jComboBox1, "Gerente a cargo", "Seleccione un gerente de la lista (o registre uno nuevo).");
        }
        return ObtenerGerenteSeleccionado();
    }

    /** Un gerente solo puede estar a cargo de un departamento. */
    private void ValidarGerenteLibre(Gerente g, Departamento propio)
    {
        if (g == null)
        {
            return;
        }
        for (Departamento d : departamentoController.ListaDepartamentos)
        {
            if (d != propio && d.getGerente_a_cargo() == g)
            {
                UIUtil.errorEnCampo(jComboBox1, "Gerente a cargo", "Error: " + g.getNombre() + " " + g.getApellido_pat()
                        + " ya está a cargo del departamento " + d.getNombre_departamento() + ".");
            }
        }
    }

    /** Asigna el gerente al departamento y lo deja como empleado de ese departamento. */
    private void AsignarGerente(Departamento d, Gerente g)
    {
        d.setGerente_a_cargo(g);
        if (g != null)
        {
            if (g.getDepartamento() != null && g.getDepartamento() != d)
            {
                g.getDepartamento().QuitarEmpleado(g);
            }
            g.setDepartamento(d);
            d.AñadirEmpleado(g);
        }
    }

    private void ValidarNombreUnico(String nombre, Departamento propio)
    {
        for (Departamento d : departamentoController.ListaDepartamentos)
        {
            if (d != propio && d.getNombre_departamento().equalsIgnoreCase(nombre.trim()))
            {
                UIUtil.errorEnCampo(jTextField2, "Nombre del departamento", "Error: Ya existe un departamento con este nombre");
            }
        }
    }

    private Departamento DepartamentoDelId(String accion)
    {
        String id = jTextField1.getText().trim();
        if (id.isEmpty())
        {
            UIUtil.errorEnCampo(jTextField1, "ID del departamento", "Ingrese el ID del departamento (formato DEP-XXX) para " + accion + ".");
        }
        Departamento d = departamentoController.BuscarDepartamento(id);
        if (d == null)
        {
            UIUtil.errorEnCampo(jTextField1, "ID del departamento", "Error: No existe un departamento con el ID " + id + ".");
        }
        return d;
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try 
        {
            UIUtil.quitarMarcas(getContentPane());
            Departamento departamento = new Departamento();
            // cada dato se valida en el modelo; si falla, se indica el campo
            UIUtil.validar(jTextField2, "Nombre del departamento", () -> departamento.setNombre_departamento(jTextField2.getText()));
            ValidarNombreUnico(jTextField2.getText(), null);
            double presupuesto = UIUtil.decimal(jTextField3, "Presupuesto mensual");
            UIUtil.validar(jTextField3, "Presupuesto mensual", () -> departamento.setPresupuesto_mensual(presupuesto));
            Gerente gerenteSeleccionado = GerenteDelCombo();
            ValidarGerenteLibre(gerenteSeleccionado, null);

            // Registrar en el controlador (y recien despues asignar el gerente)
            UIUtil.validar(jTextField2, "Nombre del departamento", () -> departamentoController.AñadirDepartamento(departamento));
            AsignarGerente(departamento, gerenteSeleccionado);

            CargarTablaDepartamentos();
            UIUtil.mostrarInfo(this, "Departamento registrado correctamente.\nID asignado: " + departamento.getId_departamento());
            LimpiarFormulario();
        } 
        catch (IllegalArgumentException e) 
        {
            UIUtil.mostrarError(this, e);
        }  
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Departamento departamento;
        try
        {
            UIUtil.quitarMarcas(getContentPane());
            departamento = DepartamentoDelId("buscar");
        }
        catch (IllegalArgumentException e)
        {
            UIUtil.mostrarError(this, e);
            return;
        }
        //Mostrar datos encontrados
        jTextField1.setText(departamento.getId_departamento());
        jTextField2.setText(departamento.getNombre_departamento());
        jTextField3.setText(String.valueOf(departamento.getPresupuesto_mensual()));
        if (departamento.getGerente_a_cargo() != null)
        {
            jComboBox1.setSelectedItem(TextoGerente(departamento.getGerente_a_cargo()));
        }
        else
        {
            jComboBox1.setSelectedIndex(0);
        }
        UIUtil.mostrarInfo(this, "Departamento encontrado. Modifique los datos y presione Actualizar.");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try 
        {
            UIUtil.quitarMarcas(getContentPane());
            Departamento departamento = DepartamentoDelId("actualizar");
            // se valida en un objeto auxiliar para no dejar el departamento a medio modificar
            Departamento aux = new Departamento();
            UIUtil.validar(jTextField2, "Nombre del departamento", () -> aux.setNombre_departamento(jTextField2.getText()));
            ValidarNombreUnico(jTextField2.getText(), departamento);
            double presupuesto = UIUtil.decimal(jTextField3, "Presupuesto mensual");
            UIUtil.validar(jTextField3, "Presupuesto mensual", () -> aux.setPresupuesto_mensual(presupuesto));
            Gerente gerente = GerenteDelCombo();
            ValidarGerenteLibre(gerente, departamento);
            
            Departamento actualizado = new Departamento(departamento.getId_departamento());
            actualizado.setNombre_departamento(aux.getNombre_departamento());
            actualizado.setPresupuesto_mensual(aux.getPresupuesto_mensual());
            actualizado.setGerente_a_cargo(departamento.getGerente_a_cargo());
            
            departamentoController.ActualizarDepartamento(actualizado);
            CargarTablaDepartamentos();
            UIUtil.mostrarInfo(this, "Departamento actualizado correctamente.");
            LimpiarFormulario();
        }
        catch (IllegalArgumentException e) 
        {
            UIUtil.mostrarError(this, e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Departamento departamento;
        try 
        {
            UIUtil.quitarMarcas(getContentPane());
            departamento = DepartamentoDelId("eliminar");
            int asignados = 0;
            for (Empleado e : empleadoController.ListaEmpleados)
            {
                if (e.getDepartamento() == departamento) { asignados++; }
            }
            if (asignados > 0)
            {
                UIUtil.errorEnCampo(jTextField1, "ID del departamento", "No se puede eliminar el departamento "
                        + departamento.getNombre_departamento() + " porque tiene " + asignados
                        + " empleado(s) asignado(s).\nCámbielos de departamento primero (pantalla Empleados).");
            }
        }
        catch (IllegalArgumentException e)
        {
            UIUtil.mostrarError(this, e);
            return;
        }
        if (!UIUtil.confirmar(this, "¿Está seguro de eliminar el departamento " + departamento.getNombre_departamento() + "?", "Confirmar eliminación"))
        {
            return;
        }
        try
        {
            departamentoController.EliminarDepartamento(departamento.getId_departamento());
            CargarTablaDepartamentos();
            LimpiarFormulario();
            UIUtil.mostrarInfo(this,"Departamento eliminado correctamente.");
        }
        catch (IllegalArgumentException e)
        {
            UIUtil.mostrarError(this, e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        LimpiarFormulario();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void LimpiarFormulario()
    {
        UIUtil.quitarMarcas(getContentPane());
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jComboBox1.setSelectedIndex(0);
        jTable1.clearSelection();
        jTextField2.requestFocus();
    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    public static void main(String args[]) 
    {
        try 
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName())) 
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) 
        {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new FrmDepartamentos(new DepartamentoController(), new EmpleadoController()).setVisible(true));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lblAyuda;
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
    // End of variables declaration//GEN-END:variables
}