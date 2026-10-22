/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
 
import cr.ac.una.exception.PersistenciaException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import cr.ac.una.model.Usuario;
import cr.ac.una.repository.UsuarioRepository;
import cr.ac.una.config.ConexionBD;
 
public class UsuarioDao implements UsuarioRepository{

    private Connection conectar() throws SQLException {
        return ConexionBD.obtenerConexion();
    }
 
    @Override
    public void save(Usuario u) {
        String sql = "INSERT INTO Usuario (Carnet, Nombre, Carrera, Estado) VALUES (?, ?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE Nombre = VALUES(Nombre), Carrera = VALUES(Carrera), Estado = VALUES(Estado)";
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, u.getCarnet());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getCarrera());
            ps.setString(4, u.getEstado());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo guardar el usuario " + u.getCarnet(), e);
        }
    }
 
    @Override
    public Optional<Usuario> findById(int carnet) {
        String sql = "SELECT Carnet, Nombre, Carrera, Estado FROM Usuario WHERE Carnet = ?";
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, carnet);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo buscar el usuario " + carnet, e);
        }
    }
 
    @Override
    public List<Usuario> findAll() {
        String sql = "SELECT Carnet, Nombre, Carrera, Estado FROM Usuario ORDER BY Carnet";
        List<Usuario> usuarios = new ArrayList<>();
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                usuarios.add(mapear(rs));
            }
            return usuarios;
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo listar los usuarios", e);
        }
    }
 
    @Override
    public int countActiveAsignaciones(int carnet) {
        String sql = "SELECT COUNT(*) FROM Asignaciones WHERE Usuarioid = ? AND CondicionDevolucion = ?";
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, carnet);
            ps.setString(2, AsignacionesDao.PENDIENTE);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo contar las asignaciones del usuario " + carnet, e);
        }
    }
 
    private Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("Carnet"),
                rs.getString("Nombre"),
                rs.getString("Carrera"),
                rs.getString("Estado"));
    }
}