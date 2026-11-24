package Vista;

import Modelos.Habitacion;
import Modelos.HabitacionSimple;
import Modelos.Persona;
import Modelos.Reserva;
import Modelos.Suite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import java.util.function.IntSupplier;

/**
 * Estilos y utilidades comunes a las pantallas Huespedes, Reservas y Reportes.
 * Sigue los estandares de GUI del informe (seccion 14.1): sans-serif 12 pt para
 * texto y 16 pt para titulos; azul oscuro en encabezados y botones principales,
 * verde para confirmar, rojo para cancelar y gris para campos desactivados.
 */
public final class UIUtil
{
    public static final Color AZUL = new Color(35, 59, 87);
    public static final Color DORADO = new Color(196, 155, 92);
    public static final Color VERDE = new Color(46, 125, 50);
    public static final Color ROJO = new Color(183, 28, 28);
    public static final Color GRIS_CAMPO = new Color(224, 224, 224);
    public static final Color FONDO = new Color(245, 242, 235);

    public static final Font TEXTO = new Font("SansSerif", Font.PLAIN, 12);
    public static final Font TEXTO_NEGRITA = new Font("SansSerif", Font.BOLD, 12);
    public static final Font TITULO = new Font("SansSerif", Font.BOLD, 16);

    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private UIUtil() { }

    // ---------------------------------------------------------------- componentes

    /** Franja azul oscuro con el titulo de la pantalla. */
    public static JPanel crearEncabezado(String titulo, String subtitulo)
    {
        JPanel p = new JPanel(new java.awt.GridLayout(2, 1, 0, 2));
        p.setBackground(AZUL);
        p.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(TITULO);
        lblTitulo.setForeground(DORADO);

        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setFont(TEXTO);
        lblSub.setForeground(new Color(245, 235, 220));

        p.add(lblTitulo);
        p.add(lblSub);
        return p;
    }

    /** Etiqueta de campo; si es obligatorio se marca con un asterisco rojo. */
    public static JLabel etiqueta(String texto, boolean obligatorio)
    {
        JLabel l = new JLabel(obligatorio
                ? "<html>" + texto + " <font color='#B71C1C'>*</font></html>"
                : texto);
        l.setFont(TEXTO);
        return l;
    }

    public static JButton boton(String texto, Color fondo)
    {
        JButton b = new JButton(texto);
        b.setFont(TEXTO_NEGRITA);
        b.setBackground(fondo);
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(Math.max(110, b.getPreferredSize().width + 20), 32));
        return b;
    }

    /** Cursor de mano sobre los botones (el diseñador visual no lo genera). */
    public static void cursorMano(javax.swing.AbstractButton... botones)
    {
        for (javax.swing.AbstractButton b : botones)
        {
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    /** Campo bloqueado = fondo gris; desbloqueado = fondo blanco. */
    public static void bloquear(JTextComponent campo, boolean bloqueado)
    {
        campo.setEditable(!bloqueado);
        campo.setBackground(bloqueado ? GRIS_CAMPO : Color.WHITE);
    }

    /** Tabla de solo lectura, con el estilo comun (encabezado azul, filas de 24 px, ordenable). */
    public static JTable crearTabla(String... columnas)
    {
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0)
        {
            @Override
            public boolean isCellEditable(int fila, int columna)
            {
                return false;
            }
        };
        JTable t = new JTable(modelo);
        estilizarTabla(t);
        return t;
    }

    /**
     * Aplica el estilo comun a una tabla ya creada (por ejemplo, desde un formulario de NetBeans):
     * solo lectura, encabezado azul, filas de 24 px y ordenable.
     */
    public static void estilizarTabla(JTable t)
    {
        t.setDefaultEditor(Object.class, null);   // las celdas no se editan
        t.setFont(TEXTO);
        t.setRowHeight(24);
        t.setAutoCreateRowSorter(true);
        t.setSelectionBackground(new Color(222, 205, 170));
        t.setSelectionForeground(Color.BLACK);
        t.setGridColor(new Color(220, 220, 220));
        t.getTableHeader().setReorderingAllowed(false);
        t.getTableHeader().setFont(TEXTO_NEGRITA);
        t.getTableHeader().setBackground(AZUL);
        t.getTableHeader().setForeground(Color.WHITE);
        t.getTableHeader().setPreferredSize(new Dimension(100, 28));
        // el encabezado respeta el color en cualquier Look & Feel
        t.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
        {
            {
                setHorizontalAlignment(SwingConstants.LEFT);
            }
            @Override
            public Component getTableCellRendererComponent(JTable tabla, Object valor, boolean sel, boolean foco, int fila, int col)
            {
                super.getTableCellRendererComponent(tabla, valor, false, false, fila, col);
                setBackground(AZUL);
                setForeground(Color.WHITE);
                setFont(TEXTO_NEGRITA);
                setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
                return this;
            }
        });
    }

    /** Ancho preferido de cada columna (si la tabla tiene menos columnas, se ignoran las sobrantes). */
    public static void anchos(JTable t, int... anchos)
    {
        for (int i = 0; i < anchos.length && i < t.getColumnModel().getColumnCount(); i++)
        {
            t.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    public static DefaultTableModel modelo(JTable t)
    {
        return (DefaultTableModel) t.getModel();
    }

    /** Valor de la columna indicada en la fila seleccionada (null si no hay seleccion). */
    public static Object valorSeleccionado(JTable t, int columna)
    {
        int fila = t.getSelectedRow();
        if (fila < 0)
        {
            return null;
        }
        return t.getModel().getValueAt(t.convertRowIndexToModel(fila), columna);
    }

    /** Pinta el texto de la columna de estado: Confirmada azul, En curso verde, Finalizada gris, Cancelada rojo. */
    public static void colorearEstado(JTable t, int columna)
    {
        t.getColumnModel().getColumn(columna).setCellRenderer(new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable tabla, Object valor, boolean sel, boolean foco, int fila, int col)
            {
                super.getTableCellRendererComponent(tabla, valor, sel, foco, fila, col);
                String v = String.valueOf(valor);
                Color c = Color.BLACK;
                if (v.equals(Reserva.CONFIRMADA) || v.equals("Reservada")) { c = AZUL; }
                else if (v.equals(Reserva.EN_CURSO) || v.equals("Ocupada")) { c = VERDE; }
                else if (v.equals(Reserva.FINALIZADA)) { c = Color.GRAY; }
                else if (v.equals(Reserva.CANCELADA)) { c = ROJO; }
                else if (v.equals("Mantenimiento") || v.equals("Limpieza")) { c = new Color(190, 110, 0); }
                setForeground(c);
                setFont(TEXTO_NEGRITA);
                return this;
            }
        });
    }

    public static void mostrarError(Component padre, String mensaje)
    {
        JOptionPane.showMessageDialog(padre, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarInfo(Component padre, String mensaje)
    {
        JOptionPane.showMessageDialog(padre, mensaje, "Indigo Loft", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Dialogo con texto en fuente monoespaciada (detalles, historiales, comprobantes). */
    public static void mostrarTexto(Component padre, String titulo, String texto)
    {
        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        area.setCaretPosition(0);
        JScrollPane sp = new JScrollPane(area);
        sp.setPreferredSize(new Dimension(760, 340));
        JOptionPane.showMessageDialog(padre, sp, titulo, JOptionPane.PLAIN_MESSAGE);
    }

    // -------------------------------------------------------------------- fechas

    public static JSpinner crearSpinnerFecha(LocalDate inicial)
    {
        JSpinner sp = new JSpinner();
        configurarSpinnerFecha(sp, inicial);
        sp.setPreferredSize(new Dimension(110, 28));
        return sp;
    }

    /** Convierte un JSpinner (por ejemplo, el de un formulario de NetBeans) en selector de fecha dd/MM/yyyy. */
    public static void configurarSpinnerFecha(JSpinner sp, LocalDate inicial)
    {
        Date d = Date.from(inicial.atStartOfDay(ZoneId.systemDefault()).toInstant());
        sp.setModel(new SpinnerDateModel(d, null, null, java.util.Calendar.DAY_OF_MONTH));
        sp.setEditor(new JSpinner.DateEditor(sp, "dd/MM/yyyy"));
        sp.setFont(TEXTO);
    }

    public static void fijarFecha(JSpinner sp, LocalDate f)
    {
        sp.setValue(Date.from(f.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    public static LocalDate fecha(JSpinner sp)
    {
        return ((Date) sp.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // ------------------------------------------------------- texto de objetos del modelo

    public static String nombreCompleto(Persona p)
    {
        if (p == null)
        {
            return "-";
        }
        return p.getNombre() + " " + p.getApellido_pat() + " " + p.getApellido_mat();
    }

    public static String tipoHabitacion(Habitacion h)
    {
        if (h instanceof Suite) { return "Suite"; }
        if (h instanceof HabitacionSimple) { return "Simple"; }
        return "Estándar";
    }

    public static String detalleHabitacion(Habitacion h)
    {
        if (h instanceof Suite)
        {
            return Boolean.TRUE.equals(((Suite) h).getTieneJacuzzi()) ? "Con jacuzzi" : "Sin jacuzzi";
        }
        if (h instanceof HabitacionSimple)
        {
            return Boolean.TRUE.equals(((HabitacionSimple) h).getTieneVistaCalle()) ? "Vista a la calle" : "Sin vista a la calle";
        }
        return "";
    }

    /** "disponible" -> "Disponible". */
    public static String capitalizar(String s)
    {
        if (s == null || s.isEmpty())
        {
            return "";
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }

    public static String habitacionesTexto(Reserva r)
    {
        StringBuilder sb = new StringBuilder();
        for (Habitacion h : r.getListaHabitaciones())
        {
            if (sb.length() > 0) { sb.append(", "); }
            sb.append(h.getNro_habitacion());
        }
        return sb.toString();
    }

    public static String soles(double monto)
    {
        return String.format(java.util.Locale.US, "%.2f", monto);
    }

    /** Columnas comunes de las tablas de reservas (reservas, reportes, historial). */
    public static final String[] COLUMNAS_RESERVA =
    {
        "Código", "Documento", "Huésped", "Habitaciones", "Entrada", "Salida", "Monto S/", "Estado", "Registró"
    };

    public static final int[] ANCHOS_RESERVA = { 100, 90, 190, 100, 85, 85, 80, 90, 170 };

    public static Object[] filaReserva(Reserva r)
    {
        LocalDate salida = (r.getFecha_salida_real() != null) ? r.getFecha_salida_real() : r.getFecha_fin();
        return new Object[]
        {
            r.getCod_reserva(),
            r.getCliente().getNro_doc(),
            nombreCompleto(r.getCliente()),
            habitacionesTexto(r),
            r.getFecha_inicio().format(FORMATO_FECHA),
            salida.format(FORMATO_FECHA),
            soles(r.CalcularMonto()),
            r.getEstadoReserva(),
            nombreCompleto(r.getRecepcionista_a_cargo())
        };
    }

    public static JPanel panelConBorde(String titulo)
    {
        JPanel p = new JPanel();
        p.setOpaque(false);
        javax.swing.border.TitledBorder b = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(DORADO), titulo);
        b.setTitleFont(TEXTO_NEGRITA);
        b.setTitleColor(AZUL);
        p.setBorder(b);
        return p;
    }

    // ------------------------------------------------------- documento de identidad

    /**
     * Restringe un campo de numero de documento segun el tipo elegido en el combo
     * (DNI = 8 digitos, CE = 20, PAS = 9): solo deja escribir o pegar digitos y no deja pasar
     * del largo del tipo. Si se cambia el tipo, recorta lo que sobre. El borde se pone verde
     * cuando el numero esta completo y rojo mientras este incompleto.
     */
    public static void restringirDocumento(JComboBox<?> cboTipo, JTextField txtNro)
    {
        IntSupplier largo = () -> Persona.longitudDocumento(
                cboTipo.getSelectedItem() == null ? null : cboTipo.getSelectedItem().toString());
        instalarFiltroDigitos(txtNro, largo);

        Runnable alCambiarTipo = () ->
        {
            int max = largo.getAsInt();
            String t = txtNro.getText();
            if (max > 0 && t.length() > max)
            {
                txtNro.setText(t.substring(0, max));
            }
            String tipo = cboTipo.getSelectedItem() == null ? "" : cboTipo.getSelectedItem().toString();
            txtNro.setToolTipText("Solo números: " + Persona.nombreDocumento(tipo) + " de " + max + " dígitos");
            pintarEstadoDocumento(txtNro, max);
        };
        cboTipo.addActionListener(e -> alCambiarTipo.run());
        txtNro.putClientProperty("repintarDocumento", (Runnable) () -> pintarEstadoDocumento(txtNro, largo.getAsInt()));
        txtNro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener()
        {
            private void cambio() { pintarEstadoDocumento(txtNro, largo.getAsInt()); }
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { cambio(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { cambio(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { cambio(); }
        });
        alCambiarTipo.run();
    }

    /** Campo que solo acepta digitos, hasta 'maximo' caracteres (para busquedas por documento). */
    public static void soloDigitos(JTextField campo, int maximo)
    {
        instalarFiltroDigitos(campo, () -> maximo);
        campo.setToolTipText("Solo números: DNI 8 dígitos, pasaporte 9 dígitos, CE 20 dígitos");
    }

    /** Verifica que un documento buscado tenga 8 (DNI), 9 (PAS) o 20 (CE) digitos. */
    public static boolean largoDocumentoValido(String doc)
    {
        return doc != null && doc.matches("^([0-9]{8,9}|[0-9]{20})$");
    }

    /** Mensaje comun cuando el documento buscado no tiene un largo valido. */
    public static final String MENSAJE_LARGO_DOCUMENTO =
            "El documento debe tener 8 dígitos (DNI), 9 dígitos (pasaporte) o 20 dígitos (CE).";

    /** Texto de etiqueta con el asterisco rojo de campo obligatorio. */
    public static String textoObligatorio(String texto)
    {
        return "<html>" + texto + " <font color='#B71C1C'>*</font></html>";
    }

    private static void instalarFiltroDigitos(JTextField campo, IntSupplier maximo)
    {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter()
        {
            @Override
            public void insertString(FilterBypass fb, int offset, String texto, AttributeSet attr) throws BadLocationException
            {
                replace(fb, offset, 0, texto, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int largo, String texto, AttributeSet attr) throws BadLocationException
            {
                String digitos = (texto == null) ? "" : texto.replaceAll("[^0-9]", "");
                int max = maximo.getAsInt();
                int espacio = (max <= 0) ? Integer.MAX_VALUE : max - (fb.getDocument().getLength() - largo);
                if (digitos.length() > espacio)
                {
                    digitos = digitos.substring(0, Math.max(0, espacio));
                }
                if (texto != null && !texto.isEmpty() && digitos.length() < texto.length())
                {
                    java.awt.Toolkit.getDefaultToolkit().beep();   // se intento escribir una letra o pasar el limite
                }
                fb.replace(offset, largo, digitos, attr);
            }
        });
    }

    private static final Border BORDE_OK = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(VERDE, 1), BorderFactory.createEmptyBorder(1, 3, 1, 3));
    private static final Border BORDE_INCOMPLETO = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ROJO, 1), BorderFactory.createEmptyBorder(1, 3, 1, 3));

    private static void pintarEstadoDocumento(JTextField campo, int requerido)
    {
        if (Boolean.TRUE.equals(campo.getClientProperty("marcadoError")))
        {
            return;   // mientras tenga la marca de error se deja en rojo
        }
        if (campo.getClientProperty("bordeOriginal") == null)
        {
            campo.putClientProperty("bordeOriginal", campo.getBorder() == null
                    ? BorderFactory.createEmptyBorder() : campo.getBorder());
        }
        int n = campo.getText().length();
        if (n == 0)
        {
            campo.setBorder((Border) campo.getClientProperty("bordeOriginal"));
        }
        else
        {
            campo.setBorder(n == requerido ? BORDE_OK : BORDE_INCOMPLETO);
        }
    }

    // ------------------------------------------------- estilo comun de las ventanas

    /**
     * Aplica a una ventana hecha con el diseñador de NetBeans el mismo estilo de la pantalla
     * Reservas: franja azul con titulo dorado, fondo crema, textos SansSerif 12, tablas con
     * encabezado azul y botones planos (verde = registrar/guardar, rojo = eliminar/cancelar,
     * azul = el resto). 'ocultar' son los titulos antiguos que reemplaza la franja.
     */
    public static void aplicarEstilo(java.awt.Window ventana, String titulo, String subtitulo, JComponent... ocultar)
    {
        RootPaneContainer rpc = (RootPaneContainer) ventana;
        for (JComponent c : ocultar)
        {
            c.setVisible(false);
        }
        Container contenido = rpc.getContentPane();
        estilizarArbol(contenido);
        contenido.setBackground(FONDO);
        if (contenido instanceof JComponent)
        {
            ((JComponent) contenido).setOpaque(true);
            ((JComponent) contenido).setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        }

        JPanel nuevo = new JPanel(new BorderLayout());
        nuevo.setBackground(FONDO);
        nuevo.add(crearEncabezado(titulo, subtitulo), BorderLayout.NORTH);
        nuevo.add(contenido, BorderLayout.CENTER);
        rpc.setContentPane(nuevo);
        ventana.setBackground(FONDO);
        ventana.pack();
    }

    private static void estilizarArbol(Container padre)
    {
        for (Component c : padre.getComponents())
        {
            if (c instanceof JTable)
            {
                estilizarTabla((JTable) c);
            }
            else if (c instanceof JButton)
            {
                estilizarBoton((JButton) c);
            }
            else if (c instanceof JCheckBox || c instanceof JRadioButton)
            {
                ((AbstractButton) c).setOpaque(false);
                c.setFont(TEXTO);
            }
            else if (c instanceof JLabel)
            {
                JLabel l = (JLabel) c;
                l.setFont(TEXTO);
                l.setForeground(new Color(33, 33, 33));
                l.setText(textoNormal(l.getText()));
            }
            else if (c instanceof JComboBox)
            {
                c.setFont(TEXTO);
                c.setForeground(Color.BLACK);
                c.setBackground(Color.WHITE);
            }
            else if (c instanceof JTextComponent)
            {
                c.setFont(TEXTO);
                JTextComponent t = (JTextComponent) c;
                t.setBackground(t.isEditable() ? Color.WHITE : GRIS_CAMPO);
                t.setForeground(Color.BLACK);
            }
            else if (c instanceof javax.swing.JScrollPane)
            {
                ((javax.swing.JScrollPane) c).getViewport().setBackground(Color.WHITE);
                estilizarArbol((Container) c);
            }
            else if (c instanceof JTabbedPane)
            {
                c.setFont(TEXTO_NEGRITA);
                estilizarArbol((Container) c);
            }
            else if (c instanceof JPanel)
            {
                ((JPanel) c).setOpaque(false);
                estilizarArbol((Container) c);
            }
            else if (c instanceof Container)
            {
                estilizarArbol((Container) c);
            }
        }
    }

    /** Boton plano como los de Reservas; el color se elige por la accion que realiza. */
    public static void estilizarBoton(JButton b)
    {
        String t = b.getText() == null ? "" : b.getText().toUpperCase();
        Color fondo = AZUL;
        if (t.contains("REGISTR") || t.contains("GUARD") || t.contains("ACTUALIZ") || t.contains("CONFIRM"))
        {
            fondo = VERDE;
        }
        else if (t.contains("ELIMIN") || t.contains("CANCEL") || t.contains("BORRAR"))
        {
            fondo = ROJO;
        }
        b.setText(textoNormal(b.getText()));
        b.setFont(TEXTO_NEGRITA);
        b.setBackground(fondo);
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /** "SUELDO BASE:" -> "Sueldo base:" (respeta siglas como DNI, CE, PAS, ID o S/). */
    private static String textoNormal(String texto)
    {
        if (texto == null || texto.isEmpty() || texto.startsWith("<html>") || !texto.equals(texto.toUpperCase()))
        {
            return texto;   // ya esta en minusculas/mixto o es HTML: se deja igual
        }
        java.util.Set<String> siglas = new java.util.HashSet<>(java.util.Arrays.asList("ID", "DNI", "CE", "PAS", "S/", "S/.", "SI", "NO"));
        StringBuilder sb = new StringBuilder();
        for (String palabra : texto.split(" ", -1))
        {
            String base = palabra.replaceAll("[:()¿?*]", "");
            String p = siglas.contains(base) ? palabra : palabra.toLowerCase();
            if (sb.length() == 0 && !siglas.contains(base))
            {
                p = capitalizarPrimeraLetra(p);
            }
            if (sb.length() > 0) { sb.append(' '); }
            sb.append(p);
        }
        return sb.toString();
    }

    private static String capitalizarPrimeraLetra(String s)
    {
        for (int i = 0; i < s.length(); i++)
        {
            if (Character.isLetter(s.charAt(i)))
            {
                return s.substring(0, i) + Character.toUpperCase(s.charAt(i)) + s.substring(i + 1);
            }
        }
        return s;
    }

    // ------------------------------------------- errores indicando el campo que fallo

    private static final Color FONDO_ERROR = new Color(255, 235, 238);
    private static final Border BORDE_ERROR = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ROJO, 2), BorderFactory.createEmptyBorder(0, 2, 0, 2));

    /**
     * Ejecuta una asignacion (normalmente un setter del modelo o un metodo del controlador)
     * y, si lanza IllegalArgumentException, la convierte en un error del campo indicado.
     */
    public static void validar(JComponent campo, String nombreCampo, Runnable accion)
    {
        try
        {
            accion.run();
        }
        catch (CampoInvalidoException ex)
        {
            throw ex;
        }
        catch (IllegalArgumentException ex)
        {
            throw new CampoInvalidoException(campo, nombreCampo, ex.getMessage());
        }
    }

    /** Lanza un error asociado al campo indicado. */
    public static CampoInvalidoException errorEnCampo(JComponent campo, String nombreCampo, String mensaje)
    {
        throw new CampoInvalidoException(campo, nombreCampo, mensaje);
    }

    /** Texto obligatorio (sin espacios a los costados). */
    public static String obligatorio(JTextComponent campo, String nombreCampo)
    {
        String t = campo.getText().trim();
        if (t.isEmpty())
        {
            errorEnCampo(campo, nombreCampo, "Error: El campo '" + nombreCampo + "' no puede quedar vacío. Intente nuevamente:");
        }
        return t;
    }

    /** Numero decimal obligatorio (acepta coma o punto como separador). */
    public static double decimal(JTextComponent campo, String nombreCampo)
    {
        String t = obligatorio(campo, nombreCampo).replace(",", ".");
        try
        {
            return Double.parseDouble(t);
        }
        catch (NumberFormatException ex)
        {
            throw new CampoInvalidoException(campo, nombreCampo,
                    "Error: El campo '" + nombreCampo + "' debe ser un número válido (ejemplo: 1500 o 1500.50). Ingresó: \"" + t + "\"");
        }
    }

    /** Numero entero obligatorio. */
    public static int entero(JTextComponent campo, String nombreCampo)
    {
        String t = obligatorio(campo, nombreCampo);
        try
        {
            return Integer.parseInt(t);
        }
        catch (NumberFormatException ex)
        {
            throw new CampoInvalidoException(campo, nombreCampo,
                    "Error: El campo '" + nombreCampo + "' debe ser un número entero (sin letras ni decimales). Ingresó: \"" + t + "\"");
        }
    }

    /**
     * Muestra el error. Si viene de un campo, lo resalta en rojo, le da el foco y el
     * mensaje indica en que campo ocurrio.
     */
    public static void mostrarError(Component padre, Exception ex)
    {
        if (ex instanceof CampoInvalidoException)
        {
            CampoInvalidoException ce = (CampoInvalidoException) ex;
            marcarCampo(ce.getCampo());
            JOptionPane.showMessageDialog(padre,
                    "Campo con error: " + ce.getNombreCampo() + "\n\n" + ce.getMessage(),
                    "Error en el campo: " + ce.getNombreCampo(), JOptionPane.ERROR_MESSAGE);
            if (ce.getCampo() != null)
            {
                ce.getCampo().requestFocusInWindow();
                if (ce.getCampo() instanceof JTextComponent)
                {
                    ((JTextComponent) ce.getCampo()).selectAll();
                }
            }
        }
        else
        {
            mostrarError(padre, ex.getMessage());
        }
    }

    /** Resalta el campo; la marca se quita sola cuando el usuario lo corrige. */
    public static void marcarCampo(JComponent c)
    {
        if (c == null)
        {
            return;
        }
        JComponent destino = (c instanceof JTable && c.getParent() != null && c.getParent().getParent() instanceof JComponent)
                ? (JComponent) c.getParent().getParent()      // tabla -> su JScrollPane
                : c;
        if (!Boolean.TRUE.equals(destino.getClientProperty("marcadoError")))
        {
            destino.putClientProperty("bordeSinError", destino.getBorder());
            destino.putClientProperty("fondoSinError", destino.getBackground());
            destino.putClientProperty("marcadoError", Boolean.TRUE);
        }
        destino.setBorder(BORDE_ERROR);
        if (destino instanceof JTextComponent)
        {
            destino.setBackground(FONDO_ERROR);
        }
        instalarLimpiezaDeMarca(c, destino);
    }

    /** Quita la marca roja de un campo (si la tenia). */
    public static void quitarMarca(JComponent destino)
    {
        if (!Boolean.TRUE.equals(destino.getClientProperty("marcadoError")))
        {
            return;
        }
        destino.putClientProperty("marcadoError", Boolean.FALSE);
        destino.setBorder((Border) destino.getClientProperty("bordeSinError"));
        Object fondo = destino.getClientProperty("fondoSinError");
        if (fondo instanceof Color)
        {
            destino.setBackground((Color) fondo);
        }
        Object repintar = destino.getClientProperty("repintarDocumento");
        if (repintar instanceof Runnable)
        {
            ((Runnable) repintar).run();
        }
    }

    /** Quita todas las marcas rojas de una ventana (por ejemplo al limpiar el formulario). */
    public static void quitarMarcas(Container padre)
    {
        for (Component c : padre.getComponents())
        {
            if (c instanceof JComponent)
            {
                quitarMarca((JComponent) c);
            }
            if (c instanceof Container)
            {
                quitarMarcas((Container) c);
            }
        }
    }

    private static void instalarLimpiezaDeMarca(JComponent c, JComponent destino)
    {
        if (Boolean.TRUE.equals(c.getClientProperty("limpiezaInstalada")))
        {
            return;
        }
        c.putClientProperty("limpiezaInstalada", Boolean.TRUE);
        if (c instanceof JTextComponent)
        {
            ((JTextComponent) c).getDocument().addDocumentListener(new javax.swing.event.DocumentListener()
            {
                private void cambio() { javax.swing.SwingUtilities.invokeLater(() -> quitarMarca(destino)); }
                @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { cambio(); }
                @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { cambio(); }
                @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { }
            });
        }
        else if (c instanceof JComboBox)
        {
            ((JComboBox<?>) c).addActionListener(e -> quitarMarca(destino));
        }
        else if (c instanceof JTable)
        {
            ((JTable) c).getSelectionModel().addListSelectionListener(e -> quitarMarca(destino));
        }
        else if (c instanceof JSpinner)
        {
            ((JSpinner) c).addChangeListener(e -> quitarMarca(destino));
        }
    }

    /** Pregunta Si/No; devuelve true si el usuario confirma. */
    public static boolean confirmar(Component padre, String mensaje, String titulo)
    {
        return JOptionPane.showConfirmDialog(padre, mensaje, titulo, JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }
}
