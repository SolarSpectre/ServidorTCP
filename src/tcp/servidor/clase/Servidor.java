package tcp.servidor.clase;

import tcp.servidor.entidades.Empleado;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Servidor {

    public static String getFecha(String nombre, String accion) {
        Date date = new Date();
        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return "Empleado: " + nombre + " | Acción: " + accion + " | Fecha y Hora: " + formato.format(date);
    }

    public static void procesarSolicitud(int puerto) throws Exception {
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor de Fechas...");

        while (true) {
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado");

            InputStream in = cliente.getInputStream();
            OutputStream out = cliente.getOutputStream();

            DataInputStream dis = new DataInputStream(in);
            String nombre = dis.readUTF();
            String accion = dis.readUTF();

            if (nombre.equalsIgnoreCase("x")) break;

            String registro = getFecha(nombre, accion);

            Empleado empleado = new Empleado();
            empleado.setNombre(nombre);
            empleado.addRegistro(registro);

            DataOutputStream dos = new DataOutputStream(out);
            dos.writeUTF("Registro recibido!");

            String rutaArchivo = "C:/Users/APP DISTRIBUIDAS/Downloads/" + nombre + ".dat";

            FileOutputStream fos = new FileOutputStream(rutaArchivo, true);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(empleado);

            oos.close();
            fos.close();
            System.out.println("Registro guardado para " + nombre);
            cliente.close();
        }
        servidor.close();
    }
}
