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
import model.Herramienta;
import repository.HerramientaRepository;

/**
 *
 * @author User
 */
public class HerramientaDao implements HerramientaRepository {
    private static final String URL =
            "jdbc:mysql://10.123.0.165:3306/gamabasis_ex1p3g4?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "gamabasis_ex1p3g4";
    private static final String CONTRASENA = "8GuO3r7cM%";
 
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
    
     @Override
    public void save(Herramienta h) {
        String sql = "INSERT INTO Herramienta (Codigo, Nombre, Tipo, Estado) VALUES (?, ?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE Nombre = VALUES(Nombre), Tipo = VALUES(Tipo), Estado = VALUES(Estado)";
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, h.getCodigo());
            ps.setString(2, h.getNombre());
            ps.setString(3, h.getTipo());
            ps.setString(4, h.getEstadoHerramienta());
            ps.executeUpdate();
 
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo guardar la herramienta " + h.getCodigo(), e);
        }
    }
    
    @Override
    public Optional<Herramienta> findById(int codigo) {
        String sql = "SELECT Codigo, Nombre, Tipo, Estado FROM Herramienta WHERE Codigo = ?";
 
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
            throw new PersistenciaException("No se pudo buscar la herramienta " + codigo, e);
        }
    }
    
    @Override
    public List<Herramienta> findAll() {
        String sql = "SELECT Codigo, Nombre, Tipo, Estado FROM Herramienta ORDER BY Codigo";
        List<Herramienta> herramientas = new ArrayList<>();
 
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                herramientas.add(mapear(rs));
            }
            return herramientas;
 
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo listar las herramientas", e);
        }
    }
    
    private Herramienta mapear(ResultSet rs) throws SQLException {
        return new Herramienta(
                rs.getInt("Codigo"),
                rs.getString("Nombre"),
                rs.getString("Tipo"),
                rs.getString("Estado"));
    }
}
