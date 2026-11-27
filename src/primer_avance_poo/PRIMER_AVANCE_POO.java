package primer_avance_poo;

import Vista.FrmLogin;

public class PRIMER_AVANCE_POO
{
    public static void main(String[] args)
    {
        java.awt.EventQueue.invokeLater(() ->
        {
            new FrmLogin().setVisible(true);
        });
    }
}