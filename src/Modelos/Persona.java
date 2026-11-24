package Modelos;

public abstract class Persona 
{
    protected String nombre;
    protected String apellido_pat;
    protected String apellido_mat;
    protected String tipo_doc;
    protected String nro_doc;
    protected String telefono;
    protected String correo;

     
    public Persona() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        if(nombre == null || nombre.trim().isEmpty())
        {
            throw new IllegalArgumentException("Error: El campo 'Nombre' no puede quedar vacío. Intente nuevamente:");
        }
        this.nombre = nombre.trim();
    }

    public String getApellido_pat() {
        return apellido_pat;
    }

    public void setApellido_pat(String apellido_pat) 
    {
        if(apellido_pat == null || apellido_pat.trim().isEmpty())
        {
            throw new IllegalArgumentException("Error: El campo 'Apellido Paterno' no puede quedar vacío. Intente nuevamente:");
        }
        this.apellido_pat = apellido_pat.trim();
    }

    public String getApellido_mat() {
        return apellido_mat;
    }

    public void setApellido_mat(String apellido_mat)
    {
        if(apellido_mat == null || apellido_mat.trim().isEmpty())
        {
            throw new IllegalArgumentException("Error: El campo 'Apellido Materno' no puede quedar vacío. Intente nuevamente:");
        }
        this.apellido_mat = apellido_mat.trim();
    }

    // ------------------------------------------------------------ documento de identidad
    // Reglas para TODAS las personas del sistema (huespedes y empleados de cualquier cargo):
    //   DNI -> exactamente 8 digitos
    //   CE  -> (carne de extranjeria) exactamente 10 digitos
    //   PAS -> (pasaporte) exactamente 9 digitos
    public static final String DNI = "DNI";
    public static final String CE = "CE";
    public static final String PAS = "PAS";

    /** Cantidad de digitos que exige cada tipo de documento (0 si el tipo no es valido). */
    public static int longitudDocumento(String tipo)
    {
        if(tipo == null)
        {
            return 0;
        }
        switch(tipo.trim().toUpperCase())
        {
            case DNI: return 8;
            case CE:  return 10;
            case PAS: return 9;
            default:  return 0;
        }
    }

    /** Nombre del documento para los mensajes al usuario. */
    public static String nombreDocumento(String tipo)
    {
        if(tipo == null)
        {
            return "documento";
        }
        switch(tipo.trim().toUpperCase())
        {
            case DNI: return "DNI";
            case CE:  return "carné de extranjería (CE)";
            case PAS: return "pasaporte (PAS)";
            default:  return "documento";
        }
    }

    /**
     * Valida el numero de documento segun su tipo. Lanza IllegalArgumentException con un
     * mensaje claro si no cumple. Se usa en el modelo y tambien desde las pantallas.
     */
    public static void validarDocumento(String tipo, String nro)
    {
        int largo = longitudDocumento(tipo);
        if(largo == 0)
        {
            throw new IllegalArgumentException("Error: Este tipo de documento no es válido. Intente con DNI, CE o PAS");
        }
        if(nro == null || nro.trim().isEmpty())
        {
            throw new IllegalArgumentException("Error: El campo 'Número de documento' no puede quedar vacío. Intente nuevamente:");
        }
        String limpio = nro.trim();
        //regex para validar la entrada de digitos unicamente
        if(!limpio.matches("^[0-9]+$"))
        {
            throw new IllegalArgumentException("Error: El documento solo debe contener números (sin letras, espacios ni guiones).");
        }
        if(limpio.length() != largo)
        {
            throw new IllegalArgumentException("Error: El " + nombreDocumento(tipo) + " debe tener " + largo
                    + " digitos de forma obligatoria (ingresó " + limpio.length() + "). Intente nuevamente:");
        }
    }

    public String getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(String tipo_doc) 
    {
        if(tipo_doc == null || tipo_doc.trim().isEmpty())
        {
            throw new IllegalArgumentException("Error: El campo 'Tipo de Documento' no puede quedar vacío. Intente nuevamente:");
        }
        if(longitudDocumento(tipo_doc) == 0)
        {
            throw new IllegalArgumentException("Error: Este tipo de documento no es válido. Intente con DNI, CE o PAS");
        }
        String nuevoTipo = tipo_doc.trim().toUpperCase();
        //si cambia el tipo y el numero guardado ya no cumple la nueva regla, se obliga a ingresarlo de nuevo
        if(this.nro_doc != null && longitudDocumento(nuevoTipo) != this.nro_doc.length())
        {
            this.nro_doc = null;
        }
        this.tipo_doc = nuevoTipo;
    }

    public String getNro_doc() {
        return nro_doc;
    }

    public void setNro_doc(String nro_doc) 
    {
        if(nro_doc == null || nro_doc.trim().isEmpty())
        {
            throw new IllegalArgumentException("Error: El campo 'Número de documento' no puede quedar vacío. Intente nuevamente:");
        }
        if(this.tipo_doc == null)
        {
           throw new IllegalArgumentException("Error: Primero debe establecer el tipo de documento.");
        }
        validarDocumento(this.tipo_doc, nro_doc);
        this.nro_doc = nro_doc.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) 
    {
        if(telefono == null || telefono.trim().isEmpty())
        {
            throw new IllegalArgumentException("Error: El campo 'Teléfono' no puede quedar vacío. Intente nuevamente:");
        }
       // Expresión regular regex: 
        // ^[+]? -> Puede o no empezar con un signo más
        // [0-9]{7,15}$ -> solo numeros, y seguido de entre 7 y 15 dígitos
        
        String regexInternacional = "^[+]?[0-9]{7,15}$";
        String telefonoLimpio = telefono.trim().replace(" ", "");
        
        if(telefonoLimpio.matches(regexInternacional))
        {
            this.telefono = telefonoLimpio;
        }
        else
        {
            throw new IllegalArgumentException("Error: Formato de celular invalido en el campo 'Teléfono': debe tener entre 7 y 15 dígitos (puede empezar con +). Intente nuevamente:");
        }
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) 
    {
        if(correo == null || correo.trim().isEmpty())
        {
            throw new IllegalArgumentException("Error: El campo 'Correo' no puede quedar vacío. Intente nuevamente:");
        }
        
        //regex para correo (admite .com, .es, .edu.pe, etc) y si o si con@ y .
        String regexCorreo = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        correo = correo.trim();
        if(!correo.matches(regexCorreo))
        {
            throw new IllegalArgumentException("Error: Formato de correo invalido en el campo 'Correo'. Debe incluir '@' y un dominio válido (ej. usuario@dominio.com).");
        }
        this.correo = correo;
    }
    public String MostrarDatosPersonales()
    {
        return "NOMBRE: "+this.nombre+" | APELLIDO PAT: "+this.apellido_pat+" | APELLIDO MAT: "+this.apellido_mat+" | TIPO DOC: "+this.tipo_doc+" | NRO DOC: "+this.nro_doc
                +" | TELEFONO: "+this.telefono+" | CORREO: "+this.correo;
    }
}