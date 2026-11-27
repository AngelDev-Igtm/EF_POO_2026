package Controladores;

public class LoginController
{
    private static final String USUARIO = System.getenv("INDIGO_LOGIN_USER");

    private static final String CONTRASENA = System.getenv("INDIGO_LOGIN_PASSWORD");

    public boolean ValidarCredenciales(String usuario, String contrasena)
    {
        if(USUARIO == null || CONTRASENA == null || USUARIO.isBlank() || CONTRASENA.isBlank())
        {
            throw new IllegalStateException("Las credenciales del sistema no estan configuradas.");
        }

        return USUARIO.equals(usuario) && CONTRASENA.equals(contrasena);
    }
}