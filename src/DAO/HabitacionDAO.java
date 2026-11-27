package DAO;

import Conexion.Conexion;
import Modelos.Habitacion;
import Modelos.HabitacionSimple;
import Modelos.Suite;
import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;

public class HabitacionDAO
{
    public void Registrar(Habitacion h) throws SQLException
    {
        String sql = """
            INSERT INTO dbo.HABITACION
            (nro_habitacion, estado, precio_noche)
            VALUES (?, ?, ?)
            """;

        try(Connection cn = Conexion.conectar())
        {
            cn.setAutoCommit(false);

            try(PreparedStatement ps = cn.prepareStatement(sql))
            {
                ps.setInt(1, h.getNro_habitacion());
                ps.setString(2, h.getEstado());
                ps.setBigDecimal(3,
                    BigDecimal.valueOf(h.getPrecio_noche()));

                ps.executeUpdate();

                if(h instanceof HabitacionSimple simple)
                {
                    String sqlSimple = """
                        INSERT INTO dbo.HABITACION_SIMPLE
                        (nro_habitacion, tieneVistaCalle)
                        VALUES (?, ?)
                        """;

                    try(PreparedStatement ps2 =
                            cn.prepareStatement(sqlSimple))
                    {
                        ps2.setInt(1, h.getNro_habitacion());
                        ps2.setBoolean(2, simple.getTieneVistaCalle());
                        ps2.executeUpdate();
                    }
                }
                else if(h instanceof Suite suite)
                {
                    String sqlSuite = """
                        INSERT INTO dbo.SUITE
                        (nro_habitacion, tieneJacuzzi)
                        VALUES (?, ?)
                        """;

                    try(PreparedStatement ps2 =
                            cn.prepareStatement(sqlSuite))
                    {
                        ps2.setInt(1, h.getNro_habitacion());
                        ps2.setBoolean(2, suite.getTieneJacuzzi());
                        ps2.executeUpdate();
                    }
                }
                else
                {
                    throw new SQLException("Tipo de habitacion invalido.");
                }

                cn.commit();
            }
            catch(SQLException | RuntimeException e)
            {
                cn.rollback();
                throw e;
            }
        }
    }

    public void Actualizar(Habitacion h) throws SQLException
    {
        String sql = """
            UPDATE dbo.HABITACION
            SET estado = ?, precio_noche = ?
            WHERE nro_habitacion = ?
            """;

        try(Connection cn = Conexion.conectar())
        {
            cn.setAutoCommit(false);

            try(PreparedStatement ps = cn.prepareStatement(sql))
            {
                ps.setString(1, h.getEstado());
                ps.setBigDecimal(2,
                    BigDecimal.valueOf(h.getPrecio_noche()));
                ps.setInt(3, h.getNro_habitacion());

                if(ps.executeUpdate() != 1)
                {
                    throw new SQLException("Habitacion no encontrada.");
                }

                if(h instanceof HabitacionSimple simple)
                {
                    String sqlSimple = """
                        UPDATE dbo.HABITACION_SIMPLE
                        SET tieneVistaCalle = ?
                        WHERE nro_habitacion = ?
                        """;

                    try(PreparedStatement ps2 =
                            cn.prepareStatement(sqlSimple))
                    {
                        ps2.setBoolean(1, simple.getTieneVistaCalle());
                        ps2.setInt(2, h.getNro_habitacion());

                        if(ps2.executeUpdate() != 1)
                        {
                            throw new SQLException(
                                "Habitacion simple no encontrada.");
                        }
                    }
                }
                else if(h instanceof Suite suite)
                {
                    String sqlSuite = """
                        UPDATE dbo.SUITE
                        SET tieneJacuzzi = ?
                        WHERE nro_habitacion = ?
                        """;

                    try(PreparedStatement ps2 =
                            cn.prepareStatement(sqlSuite))
                    {
                        ps2.setBoolean(1, suite.getTieneJacuzzi());
                        ps2.setInt(2, h.getNro_habitacion());

                        if(ps2.executeUpdate() != 1)
                        {
                            throw new SQLException("Suite no encontrada.");
                        }
                    }
                }
                else
                {
                    throw new SQLException("Tipo de habitacion invalido.");
                }

                cn.commit();
            }
            catch(SQLException | RuntimeException e)
            {
                cn.rollback();
                throw e;
            }
        }
    }

    public void Eliminar(int nro) throws SQLException
    {
        String sqlSimple = """
            DELETE FROM dbo.HABITACION_SIMPLE
            WHERE nro_habitacion = ?
            """;

        String sqlSuite = """
            DELETE FROM dbo.SUITE
            WHERE nro_habitacion = ?
            """;

        String sqlHabitacion = """
            DELETE FROM dbo.HABITACION
            WHERE nro_habitacion = ?
            """;

        try(Connection cn = Conexion.conectar())
        {
            cn.setAutoCommit(false);

            try(PreparedStatement ps1 = cn.prepareStatement(sqlSimple);
                PreparedStatement ps2 = cn.prepareStatement(sqlSuite);
                PreparedStatement ps3 = cn.prepareStatement(sqlHabitacion))
            {
                ps1.setInt(1, nro);
                ps1.executeUpdate();

                ps2.setInt(1, nro);
                ps2.executeUpdate();

                ps3.setInt(1, nro);

                if(ps3.executeUpdate() != 1)
                {
                    throw new SQLException("Habitacion no encontrada.");
                }

                cn.commit();
            }
            catch(SQLException | RuntimeException e)
            {
                cn.rollback();
                throw e;
            }
        }
    }

    public ArrayList<Habitacion> Listar() throws SQLException
    {
        ArrayList<Habitacion> lista = new ArrayList<>();

        String sql = """
            SELECT h.nro_habitacion,
                   h.estado,
                   h.precio_noche,
                   hs.nro_habitacion AS id_simple,
                   hs.tieneVistaCalle,
                   s.nro_habitacion AS id_suite,
                   s.tieneJacuzzi
            FROM dbo.HABITACION h
            LEFT JOIN dbo.HABITACION_SIMPLE hs
                ON h.nro_habitacion = hs.nro_habitacion
            LEFT JOIN dbo.SUITE s
                ON h.nro_habitacion = s.nro_habitacion
            ORDER BY h.nro_habitacion
            """;

        try(Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery())
        {
            while(rs.next())
            {
                Habitacion h;

                boolean esSimple = rs.getObject("id_simple") != null;
                boolean esSuite = rs.getObject("id_suite") != null;

                if(esSimple == esSuite)
                {
                    throw new SQLException(
                        "Tipo inconsistente en habitacion "
                        + rs.getInt("nro_habitacion"));
                }

                if(esSimple)
                {
                    HabitacionSimple simple = new HabitacionSimple();

                    simple.setTieneVistaCalle(
                        rs.getBoolean("tieneVistaCalle"));

                    h = simple;
                }
                else
                {
                    Suite suite = new Suite();

                    suite.setTieneJacuzzi(
                        rs.getBoolean("tieneJacuzzi"));

                    h = suite;
                }

                h.setNro_habitacion(
                    rs.getInt("nro_habitacion"));

                h.setEstado(rs.getString("estado"));

                h.setPrecio_noche(
                    rs.getDouble("precio_noche"));

                lista.add(h);
            }
        }

        return lista;
    }
}