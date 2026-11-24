package Modelos;

public class Administrativo extends Empleado
{
    //numero de telefono de su escritorio para que pueda recibir llamadas a directamente
    private String nro_anexo;

    public Administrativo() {
    }

    public String getNro_anexo() {
        return nro_anexo;
    }

    public void setNro_anexo(String nro_anexo) 
    {
        if(nro_anexo == null || nro_anexo.trim().isEmpty())
        {
            throw new IllegalArgumentException("Error: El campo 'Número de anexo' no puede quedar vacío. Intente nuevamente:");
        }
        nro_anexo = nro_anexo.trim();
        if(!nro_anexo.matches("^[0-9]{6}$"))
        {
            throw new IllegalArgumentException("Error: El campo 'Número de anexo' debe tener exactamente 6 dígitos numéricos. Intente nuevamente:");
        }
        this.nro_anexo = nro_anexo;
    }
    
    @Override
    public double CalcularSueldo() 
    {
        return this.sueldo;
    }
    
    @Override
    public String MostrarDatosLaborales() 
    {
        String documentoJefe = (this.jefe != null) 
            ? this.jefe.getNro_doc() 
            : "SIN JEFE";
        return "DOC ADMINISTRATIVO: "+this.nro_doc+" | DEPARTAMENTO: "+((this.departamento == null) ? "SIN DEPARTAMENTO" : this.departamento.getNombre_departamento())+" | SUELDO: S/"+CalcularSueldo()+
                " | DOC JEFE: "+documentoJefe
                + "| NRO ANEXO: "+this.nro_anexo;
    }
    public String MostrarListaSubordinados()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------LISTA DE SUBORDINADOS-----------------------------\n");
        for(Empleado e : ListaSubordinados)
        {
            sb.append(e.MostrarDatosLaborales());
            sb.append("\n");
        }
        return sb.toString();
    }
}