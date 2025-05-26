package rmi.servidor.clase;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class ServidorImpl extends UnicastRemoteObject implements Servidor {

    private static final String DB_URL = "jdbc:sqlite:empleados.db";
    public ServidorImpl() throws RemoteException {
        super();
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    @Override
    public String consultar(int id) throws Exception {
        String resultado = "No existen datos del empleado";

        String sql = "SELECT nombre, correo, cargo, sueldo FROM empleado WHERE id = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String correo = rs.getString("correo");
                    String cargo = rs.getString("cargo");
                    double sueldo = rs.getDouble("sueldo");

                    resultado = "Nombre:  " + nombre + "\n"
                            + "Correo:  " + correo + "\n"
                            + "Cargo:  " + cargo + "\n"
                            + "Sueldo:  " + sueldo + "\n";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = "Error consultando el empleado: " + e.getMessage();
        }

        return resultado;
    }
}
