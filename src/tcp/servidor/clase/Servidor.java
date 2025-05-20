package tcp.servidor.clase;

import tcp.servidor.entidades.Empleado;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Servidor {

    public static String getFecha(String nombre, String accion) {
        Date date = new Date();
        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return "Empleado: " + nombre + " | Acción: " + accion + " | Fecha y Hora: " + formato.format(date);
    }

    private static void enviarContenidoArchivo(String nombreArchivo, DataOutputStream dos) throws IOException {
        String rutaArchivo = "C:/Users/solarspectre/Downloads/" + nombreArchivo + ".dat";
        File archivo = new File(rutaArchivo);
        
        if (!archivo.exists()) {
            dos.writeUTF("ERROR: El archivo no existe");
            return;
        }

        try (FileInputStream fis = new FileInputStream(archivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            Empleado empleado = (Empleado) ois.readObject();
            dos.writeUTF("OK");
            dos.writeUTF(empleado.getRegistros().toString());
            
        } catch (ClassNotFoundException e) {
            dos.writeUTF("ERROR: Error al leer el archivo");
        }
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
            DataOutputStream dos = new DataOutputStream(out);
            
            String operacion = dis.readUTF();
            
            if (operacion.equalsIgnoreCase("LEER")) {
                String nombreArchivo = dis.readUTF();
                enviarContenidoArchivo(nombreArchivo, dos);
            } else {
                String nombre = operacion;
                String accion = dis.readUTF();

                if (nombre.equalsIgnoreCase("x")) break;

                String registro = getFecha(nombre, accion);
                String rutaArchivo = "C:/Users/solarspectre/Downloads/" + nombre + ".dat";
                
                List<String> registrosExistentes = new ArrayList<>();
                File archivo = new File(rutaArchivo);
                if (archivo.exists()) {
                    try (FileInputStream fis = new FileInputStream(archivo);
                         ObjectInputStream ois = new ObjectInputStream(fis)) {
                        Empleado empleadoExistente = (Empleado) ois.readObject();
                        registrosExistentes.addAll(empleadoExistente.getRegistros());
                    } catch (Exception e) {
                        System.out.println("Error al leer registros existentes: " + e.getMessage());
                    }
                }

                Empleado empleado = new Empleado();
                empleado.setNombre(nombre);
                registrosExistentes.add(registro);
                for (String reg : registrosExistentes) {
                    empleado.addRegistro(reg);
                }

                try (FileOutputStream fos = new FileOutputStream(rutaArchivo);
                     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(empleado);
                }

                dos.writeUTF("Registro recibido!");
                System.out.println("Registro guardado para " + nombre);
            }
            cliente.close();
        }
        servidor.close();
    }
}
