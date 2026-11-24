package Vista;

import javax.swing.JComponent;

/**
 * Error de validacion asociado a un campo concreto del formulario.
 * Guarda el componente (para resaltarlo y darle el foco) y el nombre que ve el usuario,
 * ademas del mensaje original que lanzo el modelo o el controlador.
 */
public class CampoInvalidoException extends IllegalArgumentException
{
    private final transient JComponent campo;
    private final String nombreCampo;

    public CampoInvalidoException(JComponent campo, String nombreCampo, String mensaje)
    {
        super(mensaje);
        this.campo = campo;
        this.nombreCampo = nombreCampo;
    }

    public JComponent getCampo()
    {
        return campo;
    }

    public String getNombreCampo()
    {
        return nombreCampo;
    }
}
