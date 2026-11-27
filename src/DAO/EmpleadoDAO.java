package DAO;

import Conexion.Conexion;
import Modelos.*;
import java.sql.*;
import java.util.*;

public class EmpleadoDAO {
    private int ejecutar(Connection cn, String sql, Object... datos) throws SQLException 
    {
        try (PreparedStatement ps = cn.prepareStatement(sql)) 
        {
            for (int i=0; i<datos.length; i++) ps.setObject(i+1, datos[i]);
            return ps.executeUpdate();
        }
    }
    private String departamento(Empleado e) {
        return e.getDepartamento()==null ? null : e.getDepartamento().getId_departamento();
    }
    private String jefe(Empleado e) {
        return e.getJefe()==null ? null : e.getJefe().getNro_doc();
    }
    private String tabla(Empleado e) {
        if(e instanceof Gerente) return "GERENTE";
        if(e instanceof Administrativo) return "ADMINISTRATIVO";
        if(e instanceof Conserje) return "CONSERJE";
        if(e instanceof Recepcionista) return "RECEPCIONISTA";
        throw new IllegalArgumentException("Cargo no válido.");
    }
    private String columna(Empleado e) {
        return switch(tabla(e)) {
            case "GERENTE" -> "bono_gestion";
            case "ADMINISTRATIVO" -> "nro_anexo";
            case "CONSERJE" -> "especialidad";
            default -> "turno";
        };
    }
    private Object extra(Empleado e) {
        if(e instanceof Gerente g) return g.getBono_gestion();
        if(e instanceof Administrativo a) return a.getNro_anexo();
        if(e instanceof Conserje c) return c.getEspecialidad();
        return ((Recepcionista)e).getTurno();
    }
    private void asignarGerente(Connection cn, Empleado e) throws SQLException {
        if(!(e instanceof Gerente)) return;
        ejecutar(cn,"UPDATE dbo.DEPARTAMENTO SET nro_doc_gerente=NULL WHERE nro_doc_gerente=?",e.getNro_doc());
        if(departamento(e)!=null && ejecutar(cn,"UPDATE dbo.DEPARTAMENTO SET nro_doc_gerente=? WHERE id_departamento=? AND nro_doc_gerente IS NULL",e.getNro_doc(),departamento(e))!=1)
            throw new SQLException("El departamento ya tiene un gerente asignado.");
    }
    public void Registrar(Empleado e) throws SQLException {
        try(Connection cn=Conexion.conectar()) {
            cn.setAutoCommit(false);
            try {
                ejecutar(cn,"INSERT INTO dbo.PERSONA (nro_doc,tipo_doc,nombre,apellido_pat,apellido_mat,telefono,correo) VALUES (?,?,?,?,?,?,?)",e.getNro_doc(),e.getTipo_doc(),e.getNombre(),e.getApellido_pat(),e.getApellido_mat(),e.getTelefono(),e.getCorreo());
                ejecutar(cn,"INSERT INTO dbo.EMPLEADO (nro_doc,sueldo,nro_doc_jefe,id_departamento) VALUES (?,?,?,?)",e.getNro_doc(),e.getSueldo(),jefe(e),departamento(e));
                ejecutar(cn,"INSERT INTO dbo."+tabla(e)+" (nro_doc,"+columna(e)+") VALUES (?,?)",e.getNro_doc(),extra(e));
                asignarGerente(cn,e);
                cn.commit();
            } catch(SQLException | RuntimeException ex) { cn.rollback(); throw ex; }
        }
    }
    public void Actualizar(Empleado e) throws SQLException {
        try(Connection cn=Conexion.conectar()) {
            cn.setAutoCommit(false);
            try {
                ejecutar(cn,"UPDATE dbo.PERSONA SET nombre=?,apellido_pat=?,apellido_mat=?,telefono=?,correo=? WHERE nro_doc=?",e.getNombre(),e.getApellido_pat(),e.getApellido_mat(),e.getTelefono(),e.getCorreo(),e.getNro_doc());
                if(ejecutar(cn,"UPDATE dbo.EMPLEADO SET sueldo=?,nro_doc_jefe=?,id_departamento=? WHERE nro_doc=?",e.getSueldo(),jefe(e),departamento(e),e.getNro_doc())!=1) throw new SQLException("Empleado no encontrado.");
                if(ejecutar(cn,"UPDATE dbo."+tabla(e)+" SET "+columna(e)+"=? WHERE nro_doc=?",extra(e),e.getNro_doc())!=1) throw new SQLException("Cargo no encontrado.");
                asignarGerente(cn,e);
                cn.commit();
            } catch(SQLException | RuntimeException ex) { cn.rollback(); throw ex; }
        }
    }
    public void Eliminar(Empleado e) throws SQLException {
        try(Connection cn=Conexion.conectar()) {
            cn.setAutoCommit(false);
            try {
                ejecutar(cn,"UPDATE dbo.DEPARTAMENTO SET nro_doc_gerente=NULL WHERE nro_doc_gerente=?",e.getNro_doc());
                ejecutar(cn,"UPDATE dbo.EMPLEADO SET nro_doc_jefe=NULL WHERE nro_doc_jefe=?",e.getNro_doc());
                ejecutar(cn,"DELETE FROM dbo."+tabla(e)+" WHERE nro_doc=?",e.getNro_doc());
                if(ejecutar(cn,"DELETE FROM dbo.EMPLEADO WHERE nro_doc=?",e.getNro_doc())!=1) throw new SQLException("Empleado no encontrado.");
                ejecutar(cn,"DELETE FROM dbo.PERSONA WHERE nro_doc=? AND NOT EXISTS (SELECT 1 FROM dbo.HUESPED WHERE nro_doc=?)",e.getNro_doc(),e.getNro_doc());
                cn.commit();
            } catch(SQLException | RuntimeException ex) { cn.rollback(); throw ex; }
        }
    }
    public ArrayList<Empleado> Listar(ArrayList<Departamento> departamentos) throws SQLException {
        ArrayList<Empleado> lista=new ArrayList<>();
        Map<String,Empleado> empleados=new HashMap<>();
        Map<Empleado,String> jefes=new HashMap<>();
        String sql="""
            SELECT p.*, e.sueldo,e.id_departamento,e.nro_doc_jefe,
            g.bono_gestion,a.nro_anexo,c.especialidad,r.turno
            FROM dbo.EMPLEADO e JOIN dbo.PERSONA p ON p.nro_doc=e.nro_doc
            LEFT JOIN dbo.GERENTE g ON g.nro_doc=e.nro_doc
            LEFT JOIN dbo.ADMINISTRATIVO a ON a.nro_doc=e.nro_doc
            LEFT JOIN dbo.CONSERJE c ON c.nro_doc=e.nro_doc
            LEFT JOIN dbo.RECEPCIONISTA r ON r.nro_doc=e.nro_doc
            """;
        try(Connection cn=Conexion.conectar(); PreparedStatement ps=cn.prepareStatement(sql); ResultSet rs=ps.executeQuery())
        {
            while(rs.next()) 
            {
                Empleado e;
                if(rs.getObject("bono_gestion")!=null) { Gerente g=new Gerente(); g.setBono_gestion(rs.getDouble("bono_gestion")); e=g; }
                else if(rs.getString("nro_anexo")!=null) { Administrativo a=new Administrativo(); a.setNro_anexo(rs.getString("nro_anexo")); e=a; }
                else if(rs.getString("especialidad")!=null) { Conserje c=new Conserje(); c.setEspecialidad(rs.getString("especialidad")); e=c; }
                else if(rs.getString("turno")!=null) { Recepcionista r=new Recepcionista(); r.setTurno(rs.getString("turno")); e=r; }
                else throw new SQLException("Empleado sin cargo: "+rs.getString("nro_doc"));
                e.setTipo_doc(rs.getString("tipo_doc")); e.setNro_doc(rs.getString("nro_doc"));
                e.setNombre(rs.getString("nombre")); e.setApellido_pat(rs.getString("apellido_pat")); e.setApellido_mat(rs.getString("apellido_mat"));
                e.setTelefono(rs.getString("telefono")); e.setCorreo(rs.getString("correo")); e.setSueldo(rs.getDouble("sueldo"));
                String id=rs.getString("id_departamento");
                for(Departamento d:departamentos) if(d.getId_departamento().equalsIgnoreCase(id)) { e.setDepartamento(d); break; }
                lista.add(e); empleados.put(e.getNro_doc(),e); jefes.put(e,rs.getString("nro_doc_jefe"));
            }
        }
        for(Empleado e:lista) { Empleado j=empleados.get(jefes.get(e)); e.setJefe(j); if(j!=null) j.AgregarSubordinado(e); }
        for(Departamento d:departamentos) {
            d.getListaEmpleados().clear();
            Gerente g=d.getGerente_a_cargo();
            if(g!=null) d.setGerente_a_cargo((Gerente)empleados.get(g.getNro_doc()));
        }
        for(Empleado e:lista) if(e.getDepartamento()!=null) e.getDepartamento().AñadirEmpleado(e);
        return lista;
    }
}
