package DAO;

import Conexion.Conexion;
import Modelos.Departamento;
import Modelos.Gerente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.math.BigDecimal;

public class DepartamentoDAO
{
    public boolean Registrar(Departamento d) throws SQLException
    {
        String sql = """
                     INSERT INTO dbo.DEPARTAMENTO
                     (id_departamento, nombre_departamento,
                     presupuesto_mensual, nro_doc_gerente)
                     VALUES (?, ?, ?, ?)
                     """;
        try (Connection cn = Conexion.conectar();
                PreparedStatement ps = cn.prepareStatement(sql))
        {
            ps.setString(1, d.getId_departamento());
            ps.setString(2, d.getNombre_departamento());
            ps.setBigDecimal(3, BigDecimal.valueOf(d.getPresupuesto_mensual()));
            
            if (d.getGerente_a_cargo() != null)
            {
                ps.setString(4,d.getGerente_a_cargo().getNro_doc());
            }
            else
            {
                ps.setNull(4, Types.VARCHAR);
            }
            return ps.executeUpdate() > 0;
        }
    }

    public boolean Actualizar(Departamento d) throws SQLException
    {
        String sql = """
            UPDATE dbo.DEPARTAMENTO
            SET nombre_departamento = ?,
                presupuesto_mensual = ?,
                nro_doc_gerente = ?
            WHERE id_departamento = ?
            """;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql))
        {
            ps.setString(1, d.getNombre_departamento());
            ps.setBigDecimal(2,
                    BigDecimal.valueOf(d.getPresupuesto_mensual()));

            if (d.getGerente_a_cargo() != null)
            {
                ps.setString(3,
                    d.getGerente_a_cargo().getNro_doc());
            }
            else
            {
                ps.setNull(3, Types.VARCHAR);
            }

            ps.setString(4, d.getId_departamento());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean Eliminar(String id) throws SQLException
    {
        String sql = """
            DELETE FROM dbo.DEPARTAMENTO
            WHERE id_departamento = ?
            """;

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql))
        {
            ps.setString(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    public Departamento Buscar(String id) throws SQLException
    {
        String sql = consultaBase()
                + " WHERE d.id_departamento = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql))
        {
            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    return ConvertirDepartamento(rs);
                }
            }
        }

        return null;
    }

    public ArrayList<Departamento> Listar() throws SQLException
    {
        ArrayList<Departamento> lista = new ArrayList<>();

        String sql = consultaBase()
                + " ORDER BY d.nombre_departamento";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                lista.add(ConvertirDepartamento(rs));
            }
        }

        return lista;
    }

    private String consultaBase()
    {
        return """
            SELECT d.id_departamento,
                   d.nombre_departamento,
                   d.presupuesto_mensual,
                   d.nro_doc_gerente,
                   p.tipo_doc,
                   p.nombre,
                   p.apellido_pat,
                   p.apellido_mat,
                   p.telefono,
                   p.correo,
                   e.sueldo,
                   g.bono_gestion
            FROM dbo.DEPARTAMENTO d
            LEFT JOIN dbo.GERENTE g
                ON d.nro_doc_gerente = g.nro_doc
            LEFT JOIN dbo.EMPLEADO e
                ON g.nro_doc = e.nro_doc
            LEFT JOIN dbo.PERSONA p
                ON e.nro_doc = p.nro_doc
            """;
    }

    private Departamento ConvertirDepartamento(ResultSet rs)
            throws SQLException
    {
        Departamento d = new Departamento(
                rs.getString("id_departamento"));

        d.setNombre_departamento(
                rs.getString("nombre_departamento"));

        d.setPresupuesto_mensual(
                rs.getDouble("presupuesto_mensual"));

        String documento = rs.getString("nro_doc_gerente");

        if (documento != null)
        {
            Gerente g = new Gerente();

            g.setTipo_doc(rs.getString("tipo_doc"));
            g.setNro_doc(documento);
            g.setNombre(rs.getString("nombre"));
            g.setApellido_pat(rs.getString("apellido_pat"));
            g.setApellido_mat(rs.getString("apellido_mat"));
            g.setTelefono(rs.getString("telefono"));
            g.setCorreo(rs.getString("correo"));
            g.setSueldo(rs.getDouble("sueldo"));
            g.setBono_gestion(rs.getDouble("bono_gestion"));

            d.setGerente_a_cargo(g);
        }

        return d;
    }
}