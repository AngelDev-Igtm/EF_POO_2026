package DAO;

import Conexion.Conexion;
import Controladores.EmpleadoController;
import Controladores.HabitacionController;
import Controladores.HuespedController;
import Modelos.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.time.LocalDate;

public class ReservaDAO
{
    private void BloquearReservas(Connection cn) throws SQLException
    {
        String sql = """
                     SELECT COUNT_BIG(*)
                     FROM dbo.RESERVA WITH (TABLOCKX, HOLDLOCK)
                     """;
        try(PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery())
        {
            rs.next();
        }
    }
    
    private void ValidarDisponibilidad(Connection cn,Reserva r) throws SQLException
    {
        String sql = """
            SELECT COUNT(*)
            FROM dbo.RESERVA r
            INNER JOIN dbo.RESERVA_HABITACION rh
                ON r.cod_reserva = rh.cod_reserva
            WHERE rh.nro_habitacion = ?
              AND r.cod_reserva <> ?
              AND r.estado_reserva IN ('Confirmada', 'En curso')
              AND r.fecha_inicio < ?
              AND r.fecha_fin > ?
            """;

        String sqlEstado = """
            SELECT estado FROM dbo.HABITACION
            WITH (UPDLOCK, HOLDLOCK)
            WHERE nro_habitacion = ?
            """;

        if(r.getListaHabitaciones().isEmpty())
        {
            throw new SQLException(
                "Debe seleccionar al menos una habitacion.");
        }

        try(PreparedStatement ps = cn.prepareStatement(sql);
            PreparedStatement pe = cn.prepareStatement(sqlEstado))
        {
            for(Habitacion h : r.getListaHabitaciones())
            {
                pe.setInt(1, h.getNro_habitacion());

                try(ResultSet rs = pe.executeQuery())
                {
                    if(!rs.next() || "Mantenimiento".equalsIgnoreCase(rs.getString(1)))
                    {
                        throw new SQLException("Habitacion no disponible: " + h.getNro_habitacion());
                    }
                }

                ps.setInt(1, h.getNro_habitacion());
                ps.setString(2, r.getCod_reserva());
                ps.setDate(3, Date.valueOf(r.getFecha_fin()));
                ps.setDate(4, Date.valueOf(r.getFecha_inicio()));

                try(ResultSet rs = ps.executeQuery())
                {
                    if(rs.next() && rs.getInt(1) > 0)
                    {
                        throw new SQLException(
                            "La habitacion "
                            + h.getNro_habitacion()
                            + " tiene una reserva en esas fechas.");
                    }
                }
            }
        }
    }

    private void RegistrarHabitaciones(Connection cn,Reserva r) throws SQLException
    {
        String sql = """
            INSERT INTO dbo.RESERVA_HABITACION
            (cod_reserva, nro_habitacion)
            VALUES (?, ?)
            """;

        try(PreparedStatement ps = cn.prepareStatement(sql))
        {
            for(Habitacion h : r.getListaHabitaciones())
            {
                ps.setString(1, r.getCod_reserva());
                ps.setInt(2, h.getNro_habitacion());
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }

    public void Registrar(Reserva r) throws SQLException
    {
        String sql = """
            INSERT INTO dbo.RESERVA
            (cod_reserva, fecha_inicio, fecha_fin,
             fecha_salida_real, estado_reserva,
             nro_doc_huesped, nro_doc_recepcionista)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        String sqlFactura = """
            INSERT INTO dbo.FACTURA
            (cod_factura, cod_reserva, fecha_emision)
            VALUES (?, ?, ?)
            """;

        try(Connection cn = Conexion.conectar())
        {
            cn.setAutoCommit(false);

            try
            {
                BloquearReservas(cn);
                ValidarDisponibilidad(cn, r);

                try(PreparedStatement ps =
                        cn.prepareStatement(sql))
                {
                    ps.setString(1, r.getCod_reserva());
                    ps.setDate(2, Date.valueOf(r.getFecha_inicio()));
                    ps.setDate(3, Date.valueOf(r.getFecha_fin()));
                    ps.setNull(4, Types.DATE);
                    ps.setString(5, r.getEstadoReserva());
                    ps.setString(6, r.getCliente().getNro_doc());
                    ps.setString(7,
                        r.getRecepcionista_a_cargo().getNro_doc());

                    ps.executeUpdate();
                }

                RegistrarHabitaciones(cn, r);

                try(PreparedStatement ps =
                        cn.prepareStatement(sqlFactura))
                {
                    ps.setString(1,
                        r.getFactura().getCod_factura());
                    ps.setString(2, r.getCod_reserva());
                    ps.setDate(3, Date.valueOf(
                        r.getFactura().getFecha_emision()));

                    ps.executeUpdate();
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

    public void Actualizar(Reserva r) throws SQLException
    {
        String sql = """
            UPDATE dbo.RESERVA
            SET fecha_inicio = ?, fecha_fin = ?
            WHERE cod_reserva = ?
              AND estado_reserva = 'Confirmada'
            """;

        String sqlEliminar = """
            DELETE FROM dbo.RESERVA_HABITACION
            WHERE cod_reserva = ?
            """;

        try(Connection cn = Conexion.conectar())
        {
            cn.setAutoCommit(false);

            try
            {
                BloquearReservas(cn);
                ValidarDisponibilidad(cn, r);

                try(PreparedStatement ps =
                        cn.prepareStatement(sql))
                {
                    ps.setDate(1, Date.valueOf(r.getFecha_inicio()));
                    ps.setDate(2, Date.valueOf(r.getFecha_fin()));
                    ps.setString(3, r.getCod_reserva());

                    if(ps.executeUpdate() != 1)
                    {
                        throw new SQLException(
                            "La reserva no se puede modificar.");
                    }
                }

                try(PreparedStatement ps =
                        cn.prepareStatement(sqlEliminar))
                {
                    ps.setString(1, r.getCod_reserva());
                    ps.executeUpdate();
                }

                RegistrarHabitaciones(cn, r);

                cn.commit();
            }
            catch(SQLException | RuntimeException e)
            {
                cn.rollback();
                throw e;
            }
        }
    }

    public void CambiarEstado(Reserva r,String estadoAnterior,String estadoNuevo,LocalDate salidaReal,String estadoHabitacionAnterior,String estadoHabitacionNuevo)throws SQLException
    {
        String sql = """
            UPDATE dbo.RESERVA
            SET estado_reserva = ?,
                fecha_salida_real = ?
            WHERE cod_reserva = ?
              AND estado_reserva = ?
            """;

        String sqlHabitacion = """
            UPDATE dbo.HABITACION
            SET estado = ?
            WHERE nro_habitacion = ?
              AND UPPER(estado) = UPPER(?)
            """;

        try(Connection cn = Conexion.conectar())
        {
            cn.setAutoCommit(false);

            try
            {
                BloquearReservas(cn);

                try(PreparedStatement ps =
                        cn.prepareStatement(sql))
                {
                    ps.setString(1, estadoNuevo);

                    if(salidaReal == null)
                    {
                        ps.setNull(2, Types.DATE);
                    }
                    else
                    {
                        ps.setDate(2, Date.valueOf(salidaReal));
                    }

                    ps.setString(3, r.getCod_reserva());
                    ps.setString(4, estadoAnterior);

                    if(ps.executeUpdate() != 1)
                    {
                        throw new SQLException(
                            "El estado de la reserva ha cambiado.");
                    }
                }

                if(estadoHabitacionNuevo != null)
                {
                    try(PreparedStatement ps =
                            cn.prepareStatement(sqlHabitacion))
                    {
                        for(Habitacion h :
                                r.getListaHabitaciones())
                        {
                            ps.setString(1, estadoHabitacionNuevo);
                            ps.setInt(2, h.getNro_habitacion());
                            ps.setString(3, estadoHabitacionAnterior);

                            if(ps.executeUpdate() != 1)
                            {
                                throw new SQLException(
                                    "Estado de habitacion invalido: "
                                    + h.getNro_habitacion());
                            }
                        }
                    }
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

    public void Eliminar(Reserva r) throws SQLException
    {
        String[] consultas =
        {
            "DELETE FROM dbo.FACTURA WHERE cod_reserva = ?",
            "DELETE FROM dbo.RESERVA_HABITACION WHERE cod_reserva = ?",
            """
            DELETE FROM dbo.RESERVA
            WHERE cod_reserva = ?
              AND estado_reserva <> 'En curso'
            """
        };

        try(Connection cn = Conexion.conectar())
        {
            cn.setAutoCommit(false);

            try
            {
                BloquearReservas(cn);

                for(int i = 0; i < consultas.length; i++)
                {
                    try(PreparedStatement ps =
                            cn.prepareStatement(consultas[i]))
                    {
                        ps.setString(1, r.getCod_reserva());

                        int filas = ps.executeUpdate();

                        if(i == 2 && filas != 1)
                        {
                            throw new SQLException(
                                "No se puede eliminar la reserva.");
                        }
                    }
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

    public ArrayList<Reserva> Listar(
            HuespedController huespedes,
            EmpleadoController empleados,
            HabitacionController habitaciones)
            throws SQLException
    {
        LinkedHashMap<String, Reserva> mapa =
                new LinkedHashMap<>();

        String sql = """
            SELECT r.*, f.cod_factura, f.fecha_emision,
                   rh.nro_habitacion
            FROM dbo.RESERVA r
            LEFT JOIN dbo.FACTURA f
                ON r.cod_reserva = f.cod_reserva
            LEFT JOIN dbo.RESERVA_HABITACION rh
                ON r.cod_reserva = rh.cod_reserva
            ORDER BY r.fecha_inicio, r.cod_reserva,
                     rh.nro_habitacion
            """;

        try(Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery())
        {
            while(rs.next())
            {
                String codigo = rs.getString("cod_reserva");
                Reserva r = mapa.get(codigo);

                if(r == null)
                {
                    Date salida =
                        rs.getDate("fecha_salida_real");

                    r = new Reserva(
                        codigo,
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_fin").toLocalDate(),
                        salida == null ? null : salida.toLocalDate(),
                        rs.getString("estado_reserva"));

                    Huesped h = huespedes.Buscarhuesped(
                        rs.getString("nro_doc_huesped"));

                    Empleado e = empleados.BuscarEmpleado(
                        rs.getString("nro_doc_recepcionista"));

                    if(h == null ||
                        !(e instanceof Recepcionista))
                    {
                        throw new SQLException(
                            "Relaciones invalidas en reserva " + codigo);
                    }

                    r.setCliente(h);
                    r.setRecepcionista_a_cargo((Recepcionista)e);

                    String factura = rs.getString("cod_factura");

                    if(factura == null)
                    {
                        throw new SQLException(
                            "Reserva sin factura: " + codigo);
                    }

                    r.restaurarFactura(new Factura(
                        factura,
                        rs.getDate("fecha_emision").toLocalDate()));

                    mapa.put(codigo, r);
                }

                if(rs.getObject("nro_habitacion") != null)
                {
                    Habitacion hab =
                        habitaciones.BuscarHabitacion(
                            rs.getInt("nro_habitacion"));

                    if(hab == null)
                    {
                        throw new SQLException(
                            "Habitacion no encontrada.");
                    }

                    r.getListaHabitaciones().add(hab);
                }
            }
        }

        for(Reserva r : mapa.values())
        {
            if(r.getListaHabitaciones().isEmpty())
            {
                throw new SQLException(
                    "Reserva sin habitaciones: "
                    + r.getCod_reserva());
            }
        }

        return new ArrayList<>(mapa.values());
    }
}