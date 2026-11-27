package DAO;

import Conexion.Conexion;
import Modelos.Huesped;
import java.sql.*;
import java.util.ArrayList;

public class HuespedDAO
{
    public void Registrar(Huesped h) throws SQLException
    {
        String sqlPersona = """
            INSERT INTO dbo.PERSONA
            (nro_doc, tipo_doc, nombre, apellido_pat,
             apellido_mat, telefono, correo)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        String sqlHuesped = """
            INSERT INTO dbo.HUESPED
            (nro_doc, nacionalidad, preferencias, esFrecuente)
            VALUES (?, ?, ?, ?)
            """;

        try(Connection cn = Conexion.conectar())
        {
            cn.setAutoCommit(false);

            try(PreparedStatement ps1 = cn.prepareStatement(sqlPersona);
                PreparedStatement ps2 = cn.prepareStatement(sqlHuesped))
            {
                ps1.setString(1, h.getNro_doc());
                ps1.setString(2, h.getTipo_doc());
                ps1.setString(3, h.getNombre());
                ps1.setString(4, h.getApellido_pat());
                ps1.setString(5, h.getApellido_mat());
                ps1.setString(6, h.getTelefono());
                ps1.setString(7, h.getCorreo());
                ps1.executeUpdate();

                ps2.setString(1, h.getNro_doc());
                ps2.setString(2, h.getNacionalidad());
                ps2.setString(3, h.getPreferencias());
                ps2.setBoolean(4, h.getEsFrecuente());
                ps2.executeUpdate();

                cn.commit();
            }
            catch(SQLException e)
            {
                cn.rollback();
                throw e;
            }
        }
    }

    public void Actualizar(Huesped h) throws SQLException
    {
        String sqlPersona = """
            UPDATE dbo.PERSONA
            SET nombre = ?, apellido_pat = ?,
                apellido_mat = ?, telefono = ?, correo = ?
            WHERE nro_doc = ?
            """;

        String sqlHuesped = """
            UPDATE dbo.HUESPED
            SET nacionalidad = ?, preferencias = ?,
                esFrecuente = ?
            WHERE nro_doc = ?
            """;

        try(Connection cn = Conexion.conectar())
        {
            cn.setAutoCommit(false);

            try(PreparedStatement ps1 = cn.prepareStatement(sqlPersona);
                PreparedStatement ps2 = cn.prepareStatement(sqlHuesped))
            {
                ps1.setString(1, h.getNombre());
                ps1.setString(2, h.getApellido_pat());
                ps1.setString(3, h.getApellido_mat());
                ps1.setString(4, h.getTelefono());
                ps1.setString(5, h.getCorreo());
                ps1.setString(6, h.getNro_doc());

                if(ps1.executeUpdate() != 1)
                {
                    throw new SQLException("Persona no encontrada.");
                }

                ps2.setString(1, h.getNacionalidad());
                ps2.setString(2, h.getPreferencias());
                ps2.setBoolean(3, h.getEsFrecuente());
                ps2.setString(4, h.getNro_doc());

                if(ps2.executeUpdate() != 1)
                {
                    throw new SQLException("Huesped no encontrado.");
                }

                cn.commit();
            }
            catch(SQLException e)
            {
                cn.rollback();
                throw e;
            }
        }
    }

    public void Eliminar(String doc) throws SQLException
    {
        String sqlHuesped = """
            DELETE FROM dbo.HUESPED
            WHERE nro_doc = ?
            """;

        String sqlPersona = """
            DELETE FROM dbo.PERSONA
            WHERE nro_doc = ?
            AND NOT EXISTS
            (
                SELECT 1 FROM dbo.EMPLEADO
                WHERE nro_doc = ?
            )
            """;

        try(Connection cn = Conexion.conectar())
        {
            cn.setAutoCommit(false);

            try(PreparedStatement ps1 = cn.prepareStatement(sqlHuesped);
                PreparedStatement ps2 = cn.prepareStatement(sqlPersona))
            {
                ps1.setString(1, doc);

                if(ps1.executeUpdate() != 1)
                {
                    throw new SQLException("Huesped no encontrado.");
                }

                ps2.setString(1, doc);
                ps2.setString(2, doc);
                ps2.executeUpdate();

                cn.commit();
            }
            catch(SQLException e)
            {
                cn.rollback();
                throw e;
            }
        }
    }

    public ArrayList<Huesped> Listar() throws SQLException
    {
        ArrayList<Huesped> lista = new ArrayList<>();

        String sql = """
            SELECT p.nro_doc, p.tipo_doc, p.nombre,
                   p.apellido_pat, p.apellido_mat,
                   p.telefono, p.correo,
                   h.nacionalidad, h.preferencias,
                   h.esFrecuente
            FROM dbo.HUESPED h
            INNER JOIN dbo.PERSONA p
                ON h.nro_doc = p.nro_doc
            ORDER BY p.nombre
            """;

        try(Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery())
        {
            while(rs.next())
            {
                Huesped h = new Huesped();

                h.setTipo_doc(rs.getString("tipo_doc"));
                h.setNro_doc(rs.getString("nro_doc"));
                h.setNombre(rs.getString("nombre"));
                h.setApellido_pat(rs.getString("apellido_pat"));
                h.setApellido_mat(rs.getString("apellido_mat"));
                h.setTelefono(rs.getString("telefono"));
                h.setCorreo(rs.getString("correo"));
                h.setNacionalidad(rs.getString("nacionalidad"));
                h.setPreferencias(rs.getString("preferencias"));
                h.setEsFrecuente(rs.getBoolean("esFrecuente"));

                lista.add(h);
            }
        }

        return lista;
    }
}