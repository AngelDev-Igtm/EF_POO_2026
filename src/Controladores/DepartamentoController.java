package Controladores;

import DAO.DepartamentoDAO;
import java.sql.SQLException;
import Modelos.Departamento;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
public class DepartamentoController
{
    public ArrayList<Departamento> ListaDepartamentos;
    private final DepartamentoDAO dao;
    
    public DepartamentoController()
    {
        dao = new DepartamentoDAO();
        try
        {
            ListaDepartamentos = dao.Listar();
        }
        catch (SQLException e)
        {
            throw new IllegalStateException("Error al cargar los departamentos: " + e.getMessage(), e);
        }
    }
    public Departamento BuscarDepartamento(String cod)
    {
        for(Departamento d : ListaDepartamentos)
        {
            if(cod.equalsIgnoreCase(d.getId_departamento()))
            {
                return d;
            }
        }
        return null;
    }
    
    public void AñadirDepartamento(Departamento d)
    {
        if (d == null)
        {
            throw new IllegalArgumentException("El departamento no puede ser nulo.");
        }
        if (BuscarDepartamento(d.getId_departamento()) != null)
        {
            throw new IllegalArgumentException("Ya existe un departamento con ese ID.");
        }
        for (Departamento dp : ListaDepartamentos)
        {
            if (dp.getNombre_departamento().equalsIgnoreCase(d.getNombre_departamento()))
            {
                throw new IllegalArgumentException("Ya existe un departamento con ese nombre.");
            }
        }
        
        try
        {
            if (dao.Registrar(d))
            {
                ListaDepartamentos.add(d);
            }
            else
            {
                throw new IllegalArgumentException("No se pudo registrar el departamento.");
            }
        }
        catch (SQLException e)
        {
            throw new IllegalArgumentException("Error al registrar: " + e.getMessage(), e);
        }
    }
    
    public void ActualizarDepartamento(Departamento actualizado)
    {
        if (actualizado == null)
        {
            throw new IllegalArgumentException("El departamento no puede ser nulo.");
        }
        Departamento existente = BuscarDepartamento(actualizado.getId_departamento());
        
        if (existente == null)
        {
            throw new IllegalArgumentException("No existe un departamento con ese ID.");
        }
        try
        {
            if (dao.Actualizar(actualizado))
            {
                existente.setNombre_departamento(actualizado.getNombre_departamento());
                existente.setPresupuesto_mensual(actualizado.getPresupuesto_mensual());
                existente.setGerente_a_cargo(actualizado.getGerente_a_cargo());
            }
            else
            {
                throw new IllegalArgumentException("No se pudo actualizar el departamento.");
            }
        }
        catch (SQLException e)
        {
            throw new IllegalArgumentException("Error al actualizar: " + e.getMessage(), e);
        }
    }
    
    public void EliminarDepartamento(String id)
    {
        if (id == null || id.trim().isEmpty())
        {
            throw new IllegalArgumentException("Debe ingresar el ID del departamento.");
        }
        Departamento departamento = BuscarDepartamento(id.trim());
        if (departamento == null)
        {
            throw new IllegalArgumentException("No existe un departamento con ese ID.");
        }
        try
        {
            if (dao.Eliminar(departamento.getId_departamento()))
            {
                ListaDepartamentos.remove(departamento);
            }
            else
            {
                throw new IllegalArgumentException("No se pudo eliminar el departamento.");
            }
        }
        catch (SQLException e)
        {
            throw new IllegalArgumentException("Error al eliminar: " + e.getMessage(), e);
        }
    }
    
    public Boolean EstaVacio()
    {
        return ListaDepartamentos.isEmpty();
    }
    public void ExportarListaDepartamentosTXT(String nombre_archivo) throws IOException
    {
        try(PrintWriter escritor = new PrintWriter(new FileWriter(nombre_archivo)))
        {
            escritor.println("==========================================================================================");
            escritor.println("                            📋 REPORTE GENERAL DE DEPARTAMENTOS 📋                          ");
            escritor.println("==========================================================================================");
            
            if(ListaDepartamentos.isEmpty())
            {
                escritor.println("NO HAY NINGUN DEPARTAMENTO REGISTRADO");
            }
            else
            {
                for(Departamento d : ListaDepartamentos)
                {
                    escritor.println(d.MostrarDatosDepartamento());
                    escritor.println("--------------------------------------------------------------------------------------");
                }       
            }
            escritor.println("==========================================================================================");
            escritor.println("Generado automáticamente por el Sistema de Hotel 'Indigo Loft' 🏨✨");
        }
    }
}