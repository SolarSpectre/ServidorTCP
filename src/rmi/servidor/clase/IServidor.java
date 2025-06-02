package rmi.servidor.clase;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServidor extends Remote {
    // Create
    boolean crear(Persona persona) throws RemoteException;
    
    // Read
    Persona leer(int id) throws RemoteException;
    List<Persona> leerTodos() throws RemoteException;
    
    // Update
    boolean actualizar(Persona persona) throws RemoteException;
    
    // Delete
    boolean eliminar(int id) throws RemoteException;
} 