package Controladores;

import DAO.HabitacionDAO;
import java.sql.SQLException;
import Modelos.HabitacionSimple;
import Modelos.Suite;
import Modelos.Habitacion;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HabitacionController 
{
    public ArrayList<Habitacion> ListaHabitaciones;
    private final HabitacionDAO dao = new HabitacionDAO();
    
    public HabitacionController()
    {
        try
        {
            ListaHabitaciones = dao.Listar();
        }
        catch(SQLException e)
        {
            throw new IllegalStateException("Error al cargar habitaciones: " + e.getMessage(), e);
        }
    }
    public Habitacion BuscarHabitacion(int nro_hab)
    {
        for(Habitacion h : ListaHabitaciones)
        {
            if(nro_hab == h.getNro_habitacion())
            {
                return h;
            }
        }
        return null;
    }
    public void AñadirHabitacion(Habitacion h)
    {
        if(h == null)
        {
            throw new IllegalArgumentException("La habitacion no puede ser nula.");
        }
        if(BuscarHabitacion(h.getNro_habitacion()) != null)
        {
            throw new IllegalArgumentException("Ya existe una habitacion con ese numero.");
        }
        try
        {
            dao.Registrar(h);
            ListaHabitaciones.add(h);
        }
        catch(SQLException e)
        {
            throw new IllegalArgumentException("Error al registrar: " + e.getMessage(), e);
        }
    }
    public Boolean EstaVacia()
    {
        return ListaHabitaciones.isEmpty();
    }
    public void ExportarListaHabitacionesTXT(String nombre_archivo) throws IOException
    {
        try(PrintWriter escritor = new PrintWriter(new FileWriter(nombre_archivo)))
        {
            escritor.println("==========================================================================================");
            escritor.println("                            📋 REPORTE GENERAL DE HABITACIONES 📋                          ");
            escritor.println("==========================================================================================");
            
            if(ListaHabitaciones.isEmpty())
            {
                escritor.println("NO HAY NINGUNA HABITACION REGISTRADA");
            }
            else
            {
                for(Habitacion h : ListaHabitaciones)
                {
                    escritor.println(h.MostrarDatosHabitacion());
                    escritor.println("--------------------------------------------------------------------------------------");
                }
            }
            escritor.println("==========================================================================================");
            escritor.println("Generado automáticamente por el Sistema de Hotel 'Indigo Loft' 🏨✨");
        }
    }
    public void ActualizarHabitacion(Habitacion actualizada)
    {
        if(actualizada == null)
        {
            throw new IllegalArgumentException("La habitacion no puede ser nula.");
        }
        Habitacion existente = BuscarHabitacion(actualizada.getNro_habitacion());
       
        if(existente == null)
        {
            throw new IllegalArgumentException("La habitacion no existe.");
        }
        
        if(existente.getClass() != actualizada.getClass())
        {
            throw new IllegalArgumentException("No se puede cambiar el tipo de habitacion.");
        }
        try
        {
            dao.Actualizar(actualizada);
            existente.setEstado(actualizada.getEstado());
            existente.setPrecio_noche(actualizada.getPrecio_noche());
        
            if(existente instanceof HabitacionSimple simple)
            {
                simple.setTieneVistaCalle(((HabitacionSimple) actualizada).getTieneVistaCalle());
            }
            else if(existente instanceof Suite suite)
            {
                suite.setTieneJacuzzi(((Suite) actualizada).getTieneJacuzzi());
            }
        }
        catch(SQLException e)
        {
            throw new IllegalArgumentException("Error al actualizar: " + e.getMessage(), e);
        }
    }
    public void EliminarHabitacion(int nro)
    {
        Habitacion h = BuscarHabitacion(nro);
        
        if(h == null)
        {
            throw new IllegalArgumentException("La habitacion no existe.");
        }
        try
        {
            dao.Eliminar(nro);
            ListaHabitaciones.remove(h);
        }
        catch(SQLException e)
        {
            throw new IllegalArgumentException("Error al eliminar: " + e.getMessage(), e);
        }
    }
}