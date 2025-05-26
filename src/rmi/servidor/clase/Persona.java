package rmi.servidor.clase;

import lombok.Getter;

public class Persona {

    @Getter private int clave;
    @Getter private String nombre;
    @Getter private String correo;
    @Getter private String cargo;
    @Getter private double sueldo;


    public Persona(int clave, String nombre, String correo, String cargo, double sueldo) {
        this.clave = clave;
        this.nombre = nombre;
        this.correo = correo;
        this.cargo = cargo;
        this.sueldo = sueldo;
    }

}
