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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Usuario;
import repository.UsuarioRepository;


/**
 *
 * @author User
 */
public class UsuarioDao implements UsuarioRepository{
    
    private static final String URL =
            "jdbc:mysql://10.123.0.165:3306/gamabasis_ex1p3g4?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "gamabasis_ex1p3g4";
    private static final String CONTRASENA = "8GuO3r7cM%";
 
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
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
        String sql = "SELECT COUNT(*) FROM Asignaciones WHERE Usuarioid = ? AND Horadevolucion IS NULL";
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, carnet);
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
