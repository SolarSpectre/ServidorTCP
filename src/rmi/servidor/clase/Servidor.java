package rmi.servidor.clase;

import java.rmi.Remote;

public interface Servidor extends Remote {
    public String consultar(int id) throws Exception;
}
