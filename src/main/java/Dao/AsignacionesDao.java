/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import exception.PersistenciaException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Asignacion;
import model.CondicionDevolucion;
import model.Herramienta;
import repository.AsignacionRepository;
/**
 *
 * @author User
 */
public class AsignacionesDao implements AsignacionRepository{
    
    private static final String URL =
            "jdbc:mysql://10.123.0.165:3306/gamabasis_ex1p3g4?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "gamabasis_ex1p3g4";
    private static final String CONTRASENA = "8GuO3r7cM%";
 
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
 
    private static final String COLUMNAS =
            "Id, Usuarioid, Herramientaid, Horasolicitud, Horadevolucion, CondicionDevolucion";
    
    
    @Override
    public void save(Asignacion a) {
        if (a.getId() == 0) {
            insertar(a);
        } else {
            actualizar(a);
        }
    }
    
    @Override
    public Optional<Asignacion> findById(int id) {
        String sql = "SELECT " + COLUMNAS + " FROM Asignaciones WHERE Id = ?";
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
                return Optional.empty();
            }
 
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo buscar la asignacion " + id, e);
        }
    }
    
     @Override
    public Optional<Asignacion> findActiveByHerramientaId(int codigo) {
        String sql = "SELECT " + COLUMNAS + " FROM Asignaciones "
                   + "WHERE Herramientaid = ? AND Horadevolucion IS NULL "
                   + "ORDER BY Horasolicitud DESC LIMIT 1";
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
                return Optional.empty();
            }
 
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo buscar la asignacion activa de la herramienta " + codigo, e);
        }
    }
    
    @Override
    public List<Herramienta> findHerramientasByUsuarioId(int carnet) {
        String sql = "SELECT h.Codigo, h.Nombre, h.Tipo, h.Estado "
                   + "FROM Herramienta h "
                   + "INNER JOIN Asignaciones a ON a.Herramientaid = h.Codigo "
                   + "WHERE a.Usuarioid = ? AND a.Horadevolucion IS NULL "
                   + "ORDER BY a.Horasolicitud";
        List<Herramienta> herramientas = new ArrayList<>();
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, carnet);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    herramientas.add(new Herramienta(
                            rs.getInt("Codigo"),
                            rs.getString("Nombre"),
                            rs.getString("Tipo"),
                            rs.getString("Estado")));
                }
            }
            return herramientas;
 
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo listar las herramientas del usuario " + carnet, e);
        }
    }
    
    @Override
    public List<Asignacion> findHistorialByHerramientaId(int codigo) {
        String sql = "SELECT " + COLUMNAS + " FROM Asignaciones "
                   + "WHERE Herramientaid = ? ORDER BY Horasolicitud DESC";
        List<Asignacion> historial = new ArrayList<>();
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    historial.add(mapear(rs));
                }
            }
            return historial;
 
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo obtener el historial de la herramienta " + codigo, e);
        }
    }
    
    private void insertar(Asignacion a) {
        String sql = "INSERT INTO Asignaciones (Usuarioid, Herramientaid, Horasolicitud, Horadevolucion, CondicionDevolucion) "
                   + "VALUES (?, ?, ?, ?, ?)";
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
 
            ps.setInt(1, a.getUsuarioId());
            ps.setInt(2, a.getHerramientaId());
            ps.setTimestamp(3, Timestamp.valueOf(a.getHorasolicitud()));
            ponerFecha(ps, 4, a.getHoradevolucion());
            ponerCondicion(ps, 5, a.getCondicionDevolucion());
            ps.executeUpdate();
 
            // Id AUTO_INCREMENT generado por MySQL
            try (ResultSet llaves = ps.getGeneratedKeys()) {
                if (llaves.next()) {
                    a.setId(llaves.getInt(1));
                }
            }
 
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo guardar la asignacion", e);
        }
    }
 
    private void actualizar(Asignacion a) {
        String sql = "UPDATE Asignaciones SET Horadevolucion = ?, CondicionDevolucion = ? WHERE Id = ?";
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ponerFecha(ps, 1, a.getHoradevolucion());
            ponerCondicion(ps, 2, a.getCondicionDevolucion());
            ps.setInt(3, a.getId());
 
            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("No existe la asignacion " + a.getId());
            }
 
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo actualizar la asignacion " + a.getId(), e);
        }
    }
    
    private Asignacion mapear(ResultSet rs) throws SQLException {
        Timestamp devolucion = rs.getTimestamp("Horadevolucion");
 
        return new Asignacion(
                rs.getInt("Id"),
                rs.getInt("Usuarioid"),
                rs.getInt("Herramientaid"),
                rs.getTimestamp("Horasolicitud").toLocalDateTime(),
                devolucion == null ? null : devolucion.toLocalDateTime(),
                aCondicion(rs.getString("CondicionDevolucion")));
    }
 
    private void ponerFecha(PreparedStatement ps, int posicion, LocalDateTime fecha) throws SQLException {
        if (fecha == null) {
            ps.setNull(posicion, Types.TIMESTAMP);
        } else {
            ps.setTimestamp(posicion, Timestamp.valueOf(fecha));
        }
    }
    
    private void ponerCondicion(PreparedStatement ps, int posicion, CondicionDevolucion condicion) throws SQLException {
        if (condicion == null) {
            ps.setNull(posicion, Types.VARCHAR);
        } else {
            ps.setString(posicion, condicion.getEstado());
        }
    }
    
    private CondicionDevolucion aCondicion(String texto) {
        if (texto == null) {
            return null;
        }
        for (CondicionDevolucion c : CondicionDevolucion.values()) {
            if (c.getEstado().equalsIgnoreCase(texto) || c.name().equalsIgnoreCase(texto)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Condicion de devolucion desconocida: " + texto);
    }
}
