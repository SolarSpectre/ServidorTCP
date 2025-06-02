package rmi.servidor.test;

import rmi.servidor.clase.IServidor;
import rmi.servidor.clase.ServidorImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Test {
    private static final int PORT = 1099;
    private static final String SERVICE_NAME = "ServidorEmpleados";

    public static void main(String[] args) {
        try {
            IServidor servidor = new ServidorImpl();
            
            Registry registry = LocateRegistry.createRegistry(PORT);
            
            registry.rebind(SERVICE_NAME, servidor);
            
            System.out.println("Servidor RMI está corriendo en el puerto " + PORT);
            System.out.println("Nombre del servicio: " + SERVICE_NAME);
            
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
