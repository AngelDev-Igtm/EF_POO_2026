
package Modelos;

public class Conserje extends Empleado
{
    private String especialidad;

    public Conserje() {
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) 
    {
        if(especialidad == null || especialidad.trim().isEmpty())
        {
            throw new IllegalArgumentException("Error: El campo 'Especialidad' no puede quedar vacío. Intente nuevamente:");
        }
        this.especialidad = especialidad.trim();
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
      
        return "DOC CONSERJE: "+this.nro_doc+" | DEPARTAMENTO: "+((this.departamento == null) ? "SIN DEPARTAMENTO" : this.departamento.getNombre_departamento())+" | SUELDO: S/"+CalcularSueldo()+" | ESPECIALIDAD: "+this.especialidad
                +" | DOC JEFE: "+documentoJefe;
    }    
}