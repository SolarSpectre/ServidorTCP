package rmi.servidor.clase;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServidor extends Remote {
    boolean crear(Persona persona) throws RemoteException;
    
    Persona leer(int id) throws RemoteException;
    List<Persona> leerTodos() throws RemoteException;
    
    boolean actualizar(Persona persona) throws RemoteException;
    
    boolean eliminar(int id) throws RemoteException;
} 