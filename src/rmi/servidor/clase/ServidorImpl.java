package rmi.servidor.clase;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServidorImpl extends UnicastRemoteObject implements IServidor {
    private static final String DB_URL = "jdbc:sqlite:empleados.db";
    
    public ServidorImpl() throws RemoteException {
        super();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    @Override
    public boolean crear(Persona persona) throws RemoteException {
        String sql = "INSERT INTO empleado (nombre, correo, cargo, sueldo) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getCorreo());
            pstmt.setString(3, persona.getCargo());
            pstmt.setDouble(4, persona.getSueldo());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Persona leer(int id) throws RemoteException {
        String sql = "SELECT * FROM empleado WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Persona(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("cargo"),
                    rs.getDouble("sueldo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Persona> leerTodos() throws RemoteException {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT * FROM empleado";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                personas.add(new Persona(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("cargo"),
                    rs.getDouble("sueldo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personas;
    }

    @Override
    public boolean actualizar(Persona persona) throws RemoteException {
        String sql = "UPDATE empleado SET nombre = ?, correo = ?, cargo = ?, sueldo = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getCorreo());
            pstmt.setString(3, persona.getCargo());
            pstmt.setDouble(4, persona.getSueldo());
            pstmt.setInt(5, persona.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) throws RemoteException {
        String sql = "DELETE FROM empleado WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
