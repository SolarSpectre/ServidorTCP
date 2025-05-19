package tcp.servidor.entidades;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter @Setter
    private String nombre;
    private List<String> registros;

    public Empleado() {
        registros = new ArrayList<>();
    }

    public void addRegistro(String registro) {
        registros.add(registro);
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "nombre='" + nombre + '\'' +
                ", registros=" + registros +
                '}';
    }
}
