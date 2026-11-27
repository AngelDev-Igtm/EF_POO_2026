package Vista;

import Controladores.DepartamentoController;
import Controladores.EmpleadoController;
import Modelos.Administrativo;
import Modelos.Conserje;
import Modelos.Departamento;
import Modelos.Empleado;
import Modelos.Gerente;
import Modelos.Recepcionista;
import javax.swing.table.DefaultTableModel;

  

    public class FrmEmpleados extends javax.swing.JFrame {

        private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmEmpleados.class.getName());
        private EmpleadoController empleadoController;
        private DepartamentoController departamentoController;

        public FrmEmpleados(EmpleadoController empController, DepartamentoController depController) {
            initComponents();
            this.empleadoController = empController;
            this.departamentoController = depController;

            configurarCombosIniciales();
            cargarDepartamentosEnCombo();
            cargarJefesEnCombo();
            cargarTablaEmpleados();

            // DNI 8 digitos, CE 20 y pasaporte 9: el campo solo acepta numeros y respeta el largo del tipo
            UIUtil.restringirDocumento(cmbTipoDoc, txtNroDoc);
            // mismo estilo que la pantalla Reservas (franja azul, fondo crema, tabla con encabezado azul)
            getContentPane().setBackground(UIUtil.FONDO);
            UIUtil.estilizarTabla(tblEmpleados);
            tblEmpleados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            UIUtil.anchos(tblEmpleados, 150, 190, 105, 120, 95, 130, 140);
            jScrollPane1.getViewport().setBackground(java.awt.Color.WHITE);
            UIUtil.cursorMano(btnRegistrar, btnActualizar, btnEliminar, btnBuscar, btnLimpiar, btnCerrar);
            setLocationRelativeTo(null);
            
            //actualiza los datos de los departamentos y los gerentes creados en el form de departamentos
            this.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                cargarDepartamentosEnCombo();
                cargarJefesEnCombo();
                cargarTablaEmpleados();
            }
            @Override
            public void windowLostFocus(java.awt.event.WindowEvent e) {}
        });
        }

        private void configurarCombosIniciales() {
            cmbTipoDoc.removeAllItems();
            cmbTipoDoc.addItem("DNI");
            cmbTipoDoc.addItem("CE");
            cmbTipoDoc.addItem("PAS");

            cmbCargo.removeAllItems();
            cmbCargo.addItem("Gerente");
            cmbCargo.addItem("Administrativo");
            cmbCargo.addItem("Conserje");
            cmbCargo.addItem("Recepcionista");

            cmbCargo.addActionListener(e -> actualizarEtiquetaCargo());
            actualizarEtiquetaCargo();
        }

        private void actualizarEtiquetaCargo() {
            if (cmbCargo.getSelectedItem() == null) {
                return;
            }
            String cargo = cmbCargo.getSelectedItem().toString();
            switch (cargo) {
                case "Gerente":
                    lblDatoExtra.setText(UIUtil.textoObligatorio("Bono de gestión S/"));
                    break;
                case "Administrativo":
                    lblDatoExtra.setText(UIUtil.textoObligatorio("Nro de anexo (6 dígitos)"));
                    break;
                case "Conserje":
                    lblDatoExtra.setText(UIUtil.textoObligatorio("Especialidad"));
                    break;
                case "Recepcionista":
                    lblDatoExtra.setText(UIUtil.textoObligatorio("Turno (mañana, tarde, noche o completo)"));
                    break;
            }
        }

        private void cargarDepartamentosEnCombo() {
            Object previo = cmbDepartamento.getSelectedItem();
            cmbDepartamento.removeAllItems();
            cmbDepartamento.addItem("Seleccione un departamento");
            for (Departamento dep : departamentoController.ListaDepartamentos) {
                cmbDepartamento.addItem(dep.getId_departamento() + " - " + dep.getNombre_departamento());
            }
            if (previo != null) {
                cmbDepartamento.setSelectedItem(previo);
            }
        }

        private void cargarJefesEnCombo() {
            Object previo = cmbJefe.getSelectedItem();
            cmbJefe.removeAllItems();
            cmbJefe.addItem("Sin jefe");
            // HU-HOT-002: el jefe solo puede ser un gerente o un administrativo
            for (Empleado emp : empleadoController.ListaEmpleados) {
                if (emp instanceof Gerente || emp instanceof Administrativo) {
                    String texto = emp.getNro_doc() + " - " + emp.getNombre() + " " + emp.getApellido_pat();
                    cmbJefe.addItem(texto);
                }
            }
            if (previo != null) {
                cmbJefe.setSelectedItem(previo);
            }
        }

        private Departamento obtenerDepartamentoSeleccionado() {
            if (cmbDepartamento.getSelectedIndex() <= 0) {
                return null;
            }
            String seleccionado = cmbDepartamento.getSelectedItem().toString();
            String idDep = seleccionado.split(" - ")[0].trim();
            return departamentoController.BuscarDepartamento(idDep);
        }

        private Empleado obtenerJefeSeleccionado() {
            if (cmbJefe.getSelectedIndex() <= 0) {
                return null;
            }
            String seleccionado = cmbJefe.getSelectedItem().toString();
            String docJefe = seleccionado.split(" - ")[0].trim();
            return empleadoController.BuscarEmpleado(docJefe);
        }

        private void cargarTablaEmpleados() {
            DefaultTableModel modelo = (DefaultTableModel) tblEmpleados.getModel();
            modelo.setRowCount(0);

            for (Empleado emp : empleadoController.ListaEmpleados) {
                String cargo = emp.getClass().getSimpleName();
                String nombreDep = (emp.getDepartamento() != null) ? emp.getDepartamento().getNombre_departamento() : "Sin depa";
                String extra = "";

                if (emp instanceof Gerente) {
                    extra = "Bono: S/" + ((Gerente) emp).getBono_gestion();
                } else if (emp instanceof Administrativo) {
                    extra = "Anexo: " + ((Administrativo) emp).getNro_anexo();
                } else if (emp instanceof Conserje) {
                    extra = "Esp: " + ((Conserje) emp).getEspecialidad();
                } else if (emp instanceof Recepcionista) {
                    extra = "Turno: " + ((Recepcionista) emp).getTurno();
                }

                 String jefeNombre = (emp.getJefe() != null) ? emp.getJefe().getNombre() + " " + emp.getJefe().getApellido_pat() : "Sin jefe";

            modelo.addRow(new Object[]{
                emp.getNro_doc(),
                emp.getNombre() + " " + emp.getApellido_pat() + " " + emp.getApellido_mat(),
                cargo,
                nombreDep,
                "S/ " + emp.CalcularSueldo(),
                extra,
                jefeNombre
            });
            }
        }

        private void limpiarCampos() {
            UIUtil.quitarMarcas(getContentPane());
            txtNombre.setText("");
            txtApellidoPat.setText("");
            txtApellidoMat.setText("");
            txtNroDoc.setText("");
            txtTelefono.setText("");
            txtCorreo.setText("");
            txtSueldo.setText("");
            txtDatoExtra.setText("");
            cmbTipoDoc.setSelectedIndex(0);
            cmbCargo.setSelectedIndex(0);
            if (cmbDepartamento.getItemCount() > 0) {
                cmbDepartamento.setSelectedIndex(0);
            }
            if (cmbJefe.getItemCount() > 0) {
                cmbJefe.setSelectedIndex(0);
            }
            tblEmpleados.clearSelection();
            txtNroDoc.setEditable(true);
            txtNroDoc.setBackground(java.awt.Color.WHITE);
            cmbTipoDoc.setEnabled(true);
            cmbCargo.setEnabled(true);
            txtNombre.requestFocus();
        }

        /** Nombre del dato propio de cada cargo (para los mensajes de error). */
        private String nombreDatoExtra(String cargo) {
            switch (cargo) {
                case "Gerente": return "Bono de gestión";
                case "Administrativo": return "Número de anexo";
                case "Conserje": return "Especialidad";
                default: return "Turno";
            }
        }

        /** Asigna al empleado el dato propio de su cargo, indicando el campo si es invalido. */
        private void asignarDatoExtra(Empleado emp) {
            String campo = nombreDatoExtra(emp instanceof Gerente ? "Gerente" : emp instanceof Administrativo ? "Administrativo"
                    : emp instanceof Conserje ? "Conserje" : "Recepcionista");
            if (emp instanceof Gerente) {
                double bono = UIUtil.decimal(txtDatoExtra, campo);
                UIUtil.validar(txtDatoExtra, campo, () -> ((Gerente) emp).setBono_gestion(bono));
            } else if (emp instanceof Administrativo) {
                UIUtil.validar(txtDatoExtra, campo, () -> ((Administrativo) emp).setNro_anexo(txtDatoExtra.getText()));
            } else if (emp instanceof Conserje) {
                UIUtil.validar(txtDatoExtra, campo, () -> ((Conserje) emp).setEspecialidad(txtDatoExtra.getText()));
            } else if (emp instanceof Recepcionista) {
                UIUtil.validar(txtDatoExtra, campo, () -> ((Recepcionista) emp).setTurno(txtDatoExtra.getText()));
            }
        }

        /** Cambia el departamento del empleado y mantiene al dia la lista de empleados de cada departamento. */
        private void moverDepartamento(Empleado emp, Departamento nuevo) {
            Departamento anterior = emp.getDepartamento();

            // Si antes era gerente de otro departamento y se está moviendo, lo liberamos de ese puesto
            if (emp instanceof Gerente && anterior != null && anterior != nuevo) {
                Gerente g = (Gerente) emp;
                if (anterior.getGerente_a_cargo() == g) {
                    anterior.setGerente_a_cargo(null);
                }
            }

            if (anterior != null && anterior != nuevo) {
                anterior.QuitarEmpleado(emp);
            }

            emp.setDepartamento(nuevo);

            if (nuevo != null) {
                nuevo.AñadirEmpleado(emp);
                // Si el empleado es un Gerente y el departamento no tiene gerente (o lo asignamos como tal):
                if (emp instanceof Gerente) {
                    nuevo.setGerente_a_cargo((Gerente) emp);
                }
            }
        }

        /** Cambia el jefe del empleado y actualiza la lista de subordinados del jefe anterior y del nuevo. */
        private void cambiarJefe(Empleado emp, Empleado nuevoJefe) {
            UIUtil.validar(cmbJefe, "Jefe", () -> emp.setJefe(nuevoJefe));   // propio jefe / jerarquia circular
            for (Empleado otro : empleadoController.ListaEmpleados) {
                if (otro != nuevoJefe) {
                    otro.getListaSubordinados().remove(emp);
                }
            }
            if (nuevoJefe != null && !nuevoJefe.getListaSubordinados().contains(emp)) {
                nuevoJefe.AgregarSubordinado(emp);
            }
        }

        private Departamento departamentoObligatorio() {
            if (departamentoController.ListaDepartamentos.isEmpty()) {
                UIUtil.errorEnCampo(cmbDepartamento, "Departamento",
                        "No se puede registrar un empleado porque aún no existen departamentos.\nRegistre primero un departamento.");
            }
            Departamento dep = obtenerDepartamentoSeleccionado();
            if (dep == null) {
                UIUtil.errorEnCampo(cmbDepartamento, "Departamento", "Error: Seleccione un departamento válido de la lista.");
            }
            return dep;
        }

        /** Datos personales y sueldo (comunes a todos los cargos). */
        private void asignarDatosPersonales(Empleado emp, boolean conDocumento) {
            UIUtil.validar(txtNombre, "Nombre", () -> emp.setNombre(txtNombre.getText()));
            UIUtil.validar(txtApellidoPat, "Apellido paterno", () -> emp.setApellido_pat(txtApellidoPat.getText()));
            UIUtil.validar(txtApellidoMat, "Apellido materno", () -> emp.setApellido_mat(txtApellidoMat.getText()));
            UIUtil.validar(txtTelefono, "Teléfono", () -> emp.setTelefono(txtTelefono.getText()));
            double sueldo = UIUtil.decimal(txtSueldo, "Sueldo base");
            UIUtil.validar(txtSueldo, "Sueldo base", () -> emp.setSueldo(sueldo));
            UIUtil.validar(txtCorreo, "Correo", () -> emp.setCorreo(txtCorreo.getText()));
            if (conDocumento) {
                Object tipo = cmbTipoDoc.getSelectedItem();
                UIUtil.validar(cmbTipoDoc, "Tipo de documento", () -> emp.setTipo_doc(tipo == null ? null : tipo.toString()));
                UIUtil.validar(txtNroDoc, "Nro documento", () -> emp.setNro_doc(txtNroDoc.getText()));
            }
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
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtApellidoPat = new javax.swing.JTextField();
        txtApellidoMat = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbTipoDoc = new javax.swing.JComboBox<>();
        txtNroDoc = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lbl9 = new javax.swing.JLabel();
        cmbCargo = new javax.swing.JComboBox<>();
        txtSueldo = new javax.swing.JTextField();
        lblDatoExtra = new javax.swing.JLabel();
        txtDatoExtra = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cmbDepartamento = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cmbJefe = new javax.swing.JComboBox<>();
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
        tblEmpleados = new javax.swing.JTable();
        lblAyuda = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Empleados - Indigo Loft");
        setMinimumSize(new java.awt.Dimension(1000, 660));
        setSize(new java.awt.Dimension(1100, 700));

        pnlEncabezado.setBackground(new java.awt.Color(35, 59, 87));
        pnlEncabezado.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 18, 12, 18));
        pnlEncabezado.setLayout(new java.awt.GridLayout(2, 1, 0, 2));

        lblTitulo.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(196, 155, 92));
        lblTitulo.setText("GESTIÓN DE EMPLEADOS");
        pnlEncabezado.add(lblTitulo);

        lblSubtitulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblSubtitulo.setForeground(new java.awt.Color(245, 235, 220));
        lblSubtitulo.setText("Registra, busca, actualiza y elimina al personal del hotel (gerentes, administrativos, conserjes y recepcionistas)");
        pnlEncabezado.add(lblSubtitulo);

        getContentPane().add(pnlEncabezado, java.awt.BorderLayout.NORTH);

        pnlFormulario.setOpaque(false);
        pnlFormulario.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 6));
        pnlFormulario.setLayout(new java.awt.BorderLayout());

        pnlDatos.setOpaque(false);
        pnlDatos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del empleado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 12), new java.awt.Color(35, 59, 87))); // NOI18N
        pnlDatos.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setText("<html>Nombres <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 8);
        pnlDatos.add(jLabel2, gridBagConstraints);

        txtNombre.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNombre.setColumns(10);
        txtNombre.addActionListener(this::txtNombreActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        pnlDatos.add(txtNombre, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setText("<html>Apellido paterno <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 4);
        pnlDatos.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setText("<html>Apellido materno <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 8);
        pnlDatos.add(jLabel4, gridBagConstraints);

        txtApellidoPat.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtApellidoPat.setColumns(10);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 4);
        pnlDatos.add(txtApellidoPat, gridBagConstraints);

        txtApellidoMat.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtApellidoMat.setColumns(10);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 8);
        pnlDatos.add(txtApellidoMat, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("<html>Tipo de documento <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 4);
        pnlDatos.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setText("<html>Nro documento <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 8);
        pnlDatos.add(jLabel6, gridBagConstraints);

        cmbTipoDoc.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cmbTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI", "CE", "PAS" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 4);
        pnlDatos.add(cmbTipoDoc, gridBagConstraints);

        txtNroDoc.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNroDoc.setColumns(10);
        txtNroDoc.addActionListener(this::txtNroDocActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 8);
        pnlDatos.add(txtNroDoc, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setText("<html>Teléfono <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 4);
        pnlDatos.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel8.setText("<html>Correo <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 8);
        pnlDatos.add(jLabel8, gridBagConstraints);

        txtTelefono.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtTelefono.setColumns(10);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 4);
        pnlDatos.add(txtTelefono, gridBagConstraints);

        txtCorreo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCorreo.setColumns(10);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 8);
        pnlDatos.add(txtCorreo, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel10.setText("<html>Cargo <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 4);
        pnlDatos.add(jLabel10, gridBagConstraints);

        lbl9.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lbl9.setText("<html>Sueldo base S/ <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 8);
        pnlDatos.add(lbl9, gridBagConstraints);

        cmbCargo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cmbCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gerente", "Administrativo", "Conserje", "Recepcionista" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 4);
        pnlDatos.add(cmbCargo, gridBagConstraints);

        txtSueldo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtSueldo.setColumns(10);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 8);
        pnlDatos.add(txtSueldo, gridBagConstraints);

        lblDatoExtra.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblDatoExtra.setText("<html>Bono de gestión S/ <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 8);
        pnlDatos.add(lblDatoExtra, gridBagConstraints);

        txtDatoExtra.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDatoExtra.setColumns(10);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        pnlDatos.add(txtDatoExtra, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel11.setText("<html>Departamento <font color='#B71C1C'>*</font></html>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 8);
        pnlDatos.add(jLabel11, gridBagConstraints);

        cmbDepartamento.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cmbDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un departamento" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        pnlDatos.add(cmbDepartamento, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setText("Jefe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 8);
        pnlDatos.add(jLabel12, gridBagConstraints);

        cmbJefe.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cmbJefe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sin jefe" }));
        cmbJefe.addActionListener(this::cmbJefeActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 8, 0, 8);
        pnlDatos.add(cmbJefe, gridBagConstraints);

        lblRelleno.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblRelleno.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
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
        lblNota.setText("<html><font color='#B71C1C'>*</font> Campo obligatorio · Documento: DNI 8, CE 10 y PAS 9 dígitos</html>");
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
        pnlTabla.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Personal registrado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 12), new java.awt.Color(35, 59, 87))); // NOI18N
        pnlTabla.setLayout(new java.awt.BorderLayout());

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Documento", "Nombre completo", "Cargo", "Departamento", "Sueldo total", "Dato extra", "Jefe"
            }
        ));
        jScrollPane1.setViewportView(tblEmpleados);

        pnlTabla.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlListado.add(pnlTabla, java.awt.BorderLayout.CENTER);

        lblAyuda.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblAyuda.setText("Para modificar o eliminar: escriba el número de documento y presione Buscar.");
        pnlListado.add(lblAyuda, java.awt.BorderLayout.SOUTH);

        getContentPane().add(pnlListado, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtNroDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNroDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNroDocActionPerformed

    private void cmbJefeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbJefeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbJefeActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        try {
            UIUtil.quitarMarcas(getContentPane());
            String cargo = cmbCargo.getSelectedItem().toString();
            Empleado nuevoEmpleado;
            switch (cargo) {
                case "Gerente": nuevoEmpleado = new Gerente(); break;
                case "Administrativo": nuevoEmpleado = new Administrativo(); break;
                case "Conserje": nuevoEmpleado = new Conserje(); break;
                default: nuevoEmpleado = new Recepcionista(); break;
            }
            // cada dato se valida en el modelo; si falla, se indica el campo
            asignarDatosPersonales(nuevoEmpleado, true);
            asignarDatoExtra(nuevoEmpleado);
            Departamento dep = departamentoObligatorio();
            Empleado jefe = obtenerJefeSeleccionado();
            nuevoEmpleado.setDepartamento(dep);
            nuevoEmpleado.setJefe(jefe);

            // documento repetido -> se marca el campo del documento
            UIUtil.validar(txtNroDoc, "Nro documento", () -> empleadoController.AñadirEmpleado(nuevoEmpleado));
            moverDepartamento(nuevoEmpleado, dep);
            if (jefe != null) {
                cambiarJefe(nuevoEmpleado, jefe);
            }

            cargarTablaEmpleados();
            cargarJefesEnCombo();
            UIUtil.mostrarInfo(this, "Empleado registrado correctamente.");
            limpiarCampos();
        } catch (IllegalArgumentException e) {
            UIUtil.mostrarError(this, e);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        String doc = txtNroDoc.getText().trim();
        Empleado emp = doc.isEmpty() ? null : empleadoController.BuscarEmpleado(doc);

        if (emp == null) {
            UIUtil.mostrarError(this, new CampoInvalidoException(txtNroDoc, "Nro documento",
                    "Primero busque (por número de documento) el empleado que desea eliminar."));
            return;
        }

        if (UIUtil.confirmar(this, "¿Está seguro de eliminar a " + emp.getNombre() + " " + emp.getApellido_pat() + "?","Confirmar eliminación")) 
        {
            try
            {
                empleadoController.EliminarEmpleado(emp);
            }
            catch(IllegalArgumentException e)
            {
                UIUtil.mostrarError(this, e);
                return;
            }
            if (emp.getDepartamento() != null) {
                emp.getDepartamento().QuitarEmpleado(emp);
            }
            for (Departamento d : departamentoController.ListaDepartamentos) {
                if (d.getGerente_a_cargo() == emp) {
                    d.setGerente_a_cargo(null);
                }
            }
            for (Empleado otro : empleadoController.ListaEmpleados) {
                otro.getListaSubordinados().remove(emp);
                if (otro.getJefe() == emp) {
                    otro.setJefe(null);
                }
            }
            cargarTablaEmpleados();
            cargarJefesEnCombo();
            limpiarCampos();
            UIUtil.mostrarInfo(this, "Empleado eliminado correctamente.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        UIUtil.quitarMarcas(getContentPane());
        String doc = txtNroDoc.getText().trim();
        try {
            if (doc.isEmpty()) {
                UIUtil.errorEnCampo(txtNroDoc, "Nro documento", "Ingrese el número de documento a buscar.");
            }
            Object tipo = cmbTipoDoc.getSelectedItem();
            UIUtil.validar(txtNroDoc, "Nro documento", () -> Modelos.Persona.validarDocumento(tipo.toString(), doc));
            if (empleadoController.BuscarEmpleado(doc) == null) {
                UIUtil.errorEnCampo(txtNroDoc, "Nro documento", "No se encontró ningún empleado con ese documento.");
            }
        } catch (IllegalArgumentException ex) {
            UIUtil.mostrarError(this, ex);
            return;
        }
        Empleado emp = empleadoController.BuscarEmpleado(doc);

        txtNombre.setText(emp.getNombre());
        txtApellidoPat.setText(emp.getApellido_pat());
        txtApellidoMat.setText(emp.getApellido_mat());
        cmbTipoDoc.setSelectedItem(emp.getTipo_doc().toUpperCase());
        txtNroDoc.setText(emp.getNro_doc());
        txtNroDoc.setEditable(false);
        txtNroDoc.setBackground(UIUtil.GRIS_CAMPO);
        cmbTipoDoc.setEnabled(false);
        txtTelefono.setText(emp.getTelefono());
        txtCorreo.setText(emp.getCorreo());
        txtSueldo.setText(String.valueOf(emp.getSueldo()));

        cmbCargo.setSelectedItem(emp.getClass().getSimpleName());
        cmbCargo.setEnabled(false);

        if (emp instanceof Gerente) {
            txtDatoExtra.setText(String.valueOf(((Gerente) emp).getBono_gestion()));
        } else if (emp instanceof Administrativo) {
            txtDatoExtra.setText(((Administrativo) emp).getNro_anexo());
        } else if (emp instanceof Conserje) {
            txtDatoExtra.setText(((Conserje) emp).getEspecialidad());
        } else if (emp instanceof Recepcionista) {
            txtDatoExtra.setText(((Recepcionista) emp).getTurno());
        }

        if (emp.getDepartamento() != null) {
            String buscarDep = emp.getDepartamento().getId_departamento() + " - " + emp.getDepartamento().getNombre_departamento();
            cmbDepartamento.setSelectedItem(buscarDep);
        } else {
            cmbDepartamento.setSelectedIndex(0);
        }
        if (emp.getJefe() != null) {
            String buscarJefe = emp.getJefe().getNro_doc() + " - " + emp.getJefe().getNombre() + " " + emp.getJefe().getApellido_pat();
            cmbJefe.setSelectedItem(buscarJefe);
        } else {
            cmbJefe.setSelectedIndex(0);
        }
        UIUtil.mostrarInfo(this, "Empleado encontrado. Modifique los datos y presione Actualizar.");
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        try {
            UIUtil.quitarMarcas(getContentPane());
            String doc = txtNroDoc.getText().trim();
            Empleado emp = doc.isEmpty() ? null : empleadoController.BuscarEmpleado(doc);
            if (emp == null) {
                UIUtil.errorEnCampo(txtNroDoc, "Nro documento", "Primero busque un empleado válido (por número de documento).");
            }
            // se valida todo sobre una copia: si un dato falla, el empleado no queda a medio modificar
            Empleado copia;
            if (emp instanceof Gerente) { copia = new Gerente(); }
            else if (emp instanceof Administrativo) { copia = new Administrativo(); }
            else if (emp instanceof Conserje) { copia = new Conserje(); }
            else { copia = new Recepcionista(); }
            asignarDatosPersonales(copia, false);
            asignarDatoExtra(copia);
            
            Departamento dep = departamentoObligatorio();
            Empleado nuevoJefe = obtenerJefeSeleccionado();
            Empleado jefeAnterior = emp.getJefe();
            
            for(Empleado j = nuevoJefe; j != null; j = j.getJefe())
            {
                if(j == emp)
                {
                    throw new IllegalArgumentException("No se puede crear una jerarquía circular.");
                }
            }
            copia.setTipo_doc(emp.getTipo_doc());
            copia.setNro_doc(emp.getNro_doc());
            copia.setDepartamento(dep);
            copia.setJefe(nuevoJefe);
            empleadoController.ActualizarEmpleado(copia);
            cambiarJefe(emp, nuevoJefe);

            emp.setNombre(copia.getNombre());
            emp.setApellido_pat(copia.getApellido_pat());
            emp.setApellido_mat(copia.getApellido_mat());
            emp.setTelefono(copia.getTelefono());
            emp.setCorreo(copia.getCorreo());
            emp.setSueldo(copia.getSueldo());
            asignarDatoExtra(emp);
            moverDepartamento(emp, dep);

            cargarTablaEmpleados();
            cargarJefesEnCombo();
            UIUtil.mostrarInfo(this, "Empleado actualizado correctamente."
                    + (jefeAnterior != nuevoJefe ? "\nSe actualizó también la lista de subordinados del jefe." : ""));
            limpiarCampos();
        } catch (IllegalArgumentException e) 
        {
            UIUtil.mostrarError(this, e);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed
public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new FrmEmpleados(new EmpleadoController(), new DepartamentoController()).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cmbCargo;
    private javax.swing.JComboBox<String> cmbDepartamento;
    private javax.swing.JComboBox<String> cmbJefe;
    private javax.swing.JComboBox<String> cmbTipoDoc;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl9;
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
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JTextField txtApellidoMat;
    private javax.swing.JTextField txtApellidoPat;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDatoExtra;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNroDoc;
    private javax.swing.JTextField txtSueldo;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

    }