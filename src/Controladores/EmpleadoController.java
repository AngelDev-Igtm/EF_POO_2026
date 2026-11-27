package Controladores;

import Modelos.Empleado;
import Modelos.Administrativo;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import Modelos.Gerente;
import DAO.EmpleadoDAO;
import java.sql.SQLException;

public class EmpleadoController 
{
    public ArrayList<Empleado> ListaEmpleados;
    private final EmpleadoDAO dao = new EmpleadoDAO();
    
    public EmpleadoController()
    {
        this(new DepartamentoController());
    }
    public EmpleadoController(DepartamentoController departamentos)
    {
        try
        {
            ListaEmpleados = dao.Listar(departamentos.ListaDepartamentos);
        }
        catch(SQLException e)
        {
            throw new IllegalStateException("Error al cargar empleados: " + e.getMessage(), e);
        }
    }
    public Empleado BuscarEmpleado(String doc)
    {
        for(Empleado e : ListaEmpleados)
        {
            if(doc.trim().equalsIgnoreCase(e.getNro_doc().trim()))
            {
                return e;
            }
        }
        return null;
    }
    
    public void AñadirEmpleado(Empleado e)
    {
        if(e == null)
        {
            throw new IllegalArgumentException("El empleado no puede ser nulo.");
        }
        if(BuscarEmpleado(e.getNro_doc()) != null)
        {
            throw new IllegalArgumentException("Ya existe un empleado con ese documento.");
        }
        try
        {
            dao.Registrar(e);
            ListaEmpleados.add(e);
        }
        catch(SQLException ex)
        {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
    
    public void ActualizarEmpleado(Empleado e)
    {
        try
        {
            dao.Actualizar(e);
        }
        catch(SQLException ex)
        {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
    
    public void EliminarEmpleado(Empleado e)
    {
        try
        {
            dao.Eliminar(e);
            ListaEmpleados.remove(e);
        }
        catch(SQLException ex)
        {
            if(ex.getErrorCode() == 547)
            {
                throw new IllegalArgumentException("No se puede eliminar al empleado porque " + "tiene registros asociados en el sistema.", ex);
            }
            throw new IllegalArgumentException("Error al eliminar el empleado: "+ ex.getMessage(), ex);
        }
    }
    public Boolean EstaVacia()
    {
        return ListaEmpleados.isEmpty();
    }
    public void ExportarListaEmpleadosTXT(String nombre_archivo) throws IOException
    {
        try(PrintWriter escritor = new PrintWriter(new FileWriter(nombre_archivo)))
        {
            escritor.println("==========================================================================================");
            escritor.println("                              📋 REPORTE GENERAL DE EMPLEADOS 📋                          ");
            escritor.println("==========================================================================================");
            
            if(ListaEmpleados.isEmpty())
            {
                escritor.println("NO HAY NINGUN EMPLEADO REGISTRADO");
            }
            else
            {
                //por polimorfismo ponemos esto, las clases hijas heredan de una clase abstracta empleado
                for(Empleado e : ListaEmpleados)
                {
                    
                        escritor.println(e.MostrarDatosPersonales());
                        escritor.println(e.MostrarDatosLaborales());
                        if(e instanceof Gerente)
                        {
                            escritor.println(((Gerente) e).MostrarListaSubordinados());
                        }
                        if(e instanceof Administrativo)
                        {
                            escritor.println(((Administrativo) e).MostrarListaSubordinados());
                        }
                    
                   escritor.println("--------------------------------------------------------------------------------------");
                }
            }
            escritor.println("==========================================================================================");
            escritor.println("Generado automáticamente por el Sistema de Hotel 'Indigo Loft' 🏨✨");
        }
    }
}