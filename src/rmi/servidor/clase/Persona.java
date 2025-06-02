package rmi.servidor.clase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Persona implements Serializable {
    private int id;
    private String nombre;
    private String correo;
    private String cargo;
    private double sueldo;
}
