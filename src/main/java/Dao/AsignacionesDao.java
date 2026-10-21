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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Asignacion;
import model.CondicionDevolucion;
import model.Herramienta;
import repository.AsignacionRepository;
 
public class AsignacionesDao implements AsignacionRepository {
    private static final String URL =
            "jdbc:mysql://10.123.0.165:3306/gamabasis_ex1p3g4?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "gamabasis_ex1p3g4";
    private static final String CONTRASENA = "8GuO3r7cM%";
 
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
 
    public static final String PENDIENTE = "Pendiente";
 
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
                   + "WHERE Herramientaid = ? AND CondicionDevolucion = ? "
                   + "ORDER BY Horasolicitud DESC LIMIT 1";
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            ps.setString(2, PENDIENTE);
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
        String sql = "SELECT h.Codigo, h.Nombre, h.Tipo, h.Estado, h.DuracionMaximaHoras "
                   + "FROM Herramienta h "
                   + "INNER JOIN Asignaciones a ON a.Herramientaid = h.Codigo "
                   + "WHERE a.Usuarioid = ? AND a.CondicionDevolucion = ? "
                   + "ORDER BY a.Horasolicitud";
        List<Herramienta> herramientas = new ArrayList<>();
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, carnet);
            ps.setString(2, PENDIENTE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    herramientas.add(new Herramienta(
                            rs.getInt("Codigo"),
                            rs.getString("Nombre"),
                            rs.getString("Tipo"),
                            rs.getString("Estado"),
                            rs.getInt("DuracionMaximaHoras")));
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
                   + "SELECT ?, h.Codigo, ?, DATE_ADD(?, INTERVAL h.DuracionMaximaHoras HOUR), ? "
                   + "FROM Herramienta h WHERE h.Codigo = ?";
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            Timestamp solicitud = Timestamp.valueOf(a.getHorasolicitud());
            ps.setInt(1, a.getUsuarioId());
            ps.setTimestamp(2, solicitud);
            ps.setTimestamp(3, solicitud);
            ps.setString(4, PENDIENTE);
            ps.setInt(5, a.getHerramientaId());
 
            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("No existe la herramienta " + a.getHerramientaId());
            }
 
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
        if (a.getHoradevolucion() == null || a.getCondicionDevolucion() == null) {
            throw new IllegalArgumentException(
                    "Para actualizar la asignacion " + a.getId() + " debe tener hora y condicion de devolucion");
        }
 
        String sql = "UPDATE Asignaciones SET Horadevolucion = ?, CondicionDevolucion = ? WHERE Id = ?";
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(a.getHoradevolucion()));
            ps.setString(2, a.getCondicionDevolucion().getEstado());
            ps.setInt(3, a.getId());
 
            if (ps.executeUpdate() == 0) {
                throw new IllegalArgumentException("No existe la asignacion " + a.getId());
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo actualizar la asignacion " + a.getId(), e);
        }
    }
 
    private Asignacion mapear(ResultSet rs) throws SQLException {
        String condicionTexto = rs.getString("CondicionDevolucion");
        boolean pendiente = condicionTexto == null || PENDIENTE.equalsIgnoreCase(condicionTexto);
 
        return new Asignacion(
                rs.getInt("Id"),
                rs.getInt("Usuarioid"),
                rs.getInt("Herramientaid"),
                rs.getTimestamp("Horasolicitud").toLocalDateTime(),
                pendiente ? null : rs.getTimestamp("Horadevolucion").toLocalDateTime(),
                pendiente ? null : aCondicion(condicionTexto));
    }
 
    private CondicionDevolucion aCondicion(String texto) {
        for (CondicionDevolucion c : CondicionDevolucion.values()) {
            if (c.getEstado().equalsIgnoreCase(texto) || c.name().equalsIgnoreCase(texto)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Condicion de devolucion desconocida: " + texto);
    }
}