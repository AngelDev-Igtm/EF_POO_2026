
package Modelos;

public abstract class Habitacion 
{
    protected int nro_habitacion;
    protected String estado;
    protected double precio_noche;

    public Habitacion() {
    }

    public int getNro_habitacion() {
        return nro_habitacion;
    }

    public void setNro_habitacion(int nro_habitacion) 
    {
        if(nro_habitacion<100 || nro_habitacion>2000)
        {
            throw new IllegalArgumentException("Error: Debe llenar el campo 'Número de habitación' de forma correcta. Intente nuevamente:");
        }
        this.nro_habitacion = nro_habitacion;
    }
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) 
    {
        if(estado == null || estado.trim().isEmpty())
        {
            throw new IllegalArgumentException("Error: Debe completar el campo 'Estado de la habitación' primero. Intente nuevamente:");
        }
        if(!estado.equalsIgnoreCase("Disponible") && !estado.equalsIgnoreCase("ocupada") && !estado.equalsIgnoreCase("mantenimiento")
                && !estado.equalsIgnoreCase("Limpieza"))
        {
            throw new IllegalArgumentException("Error: Debe llenar el campo 'Estado de la habitación' con informacion valida. Intente nuevamente:");
        }
        this.estado = estado;
    }

    public double getPrecio_noche() {
        return precio_noche;
    }

    public void setPrecio_noche(double precio_noche) 
    {
        if(precio_noche<=0)
        {
            throw new IllegalArgumentException("Error: Debe llenar el campo 'Precio de la habitación' con un valor adecuado. Intente nuevamente:");
        }
        this.precio_noche = precio_noche;
    }
    public abstract String MostrarDatosHabitacion();
}