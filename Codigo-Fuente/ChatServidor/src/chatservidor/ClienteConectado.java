package chatservidor;

import java.net.Socket;
import java.io.*;
import java.util.*;

public class ClienteConectado extends Thread {

    private final Socket socket;
    private Servidor host;
    private String tipo;
    public String nombre;

    public ClienteConectado(Socket skCliente, String tipo, String nombre, Servidor host) {
        this.host = host;
        this.socket = skCliente;
        this.tipo = tipo;
        this.nombre = nombre;
        switch (tipo) {
            case "soport":
                host.listasoport.add(socket);
                break;
            case "sale":
                host.listasale.add(socket);
                break;
            case "quejas":
                host.listaquejas.add(socket);
                break;
        }
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String mensaje = escuchar();
                if (!mensaje.equals("salir")) {

                    switch (this.tipo) {
                        case "soport":
                            prereenviar(mensaje, host.listasoport);
                            break;
                        case "sale":
                            prereenviar(mensaje, host.listasale);
                            break;
                        case "quejas":
                            prereenviar(mensaje, host.listaquejas);
                            break;
                    }
                } else {

                    host.listasale.remove(socket);
                    host.listasoport.remove(socket);
                    host.listaquejas.remove(socket);
                    host.ventana.agregarLog(" dice bye!\n");
                    break;
                }

            }
        } catch (IOException e) {
            host.listasale.remove(socket);
            host.listasoport.remove(socket);
            host.listaquejas.remove(socket);
            host.ventana.agregarLog(" dice bye!\n");
        }
    }

    private void reenviar(String mensaje, ArrayList<Socket> listaCliente) {
        OutputStream os;
        try {
            for (int cont = 0; cont < listaCliente.size(); cont++) {
                os = listaCliente.get(cont).getOutputStream();
                DataOutputStream flujoDOS = new DataOutputStream(os);
                flujoDOS.writeUTF(nombre + mensaje);
            }
        } catch (IOException e) {

        }
    }

    private String escuchar() throws IOException {
        DataInputStream flujo = new DataInputStream(socket.getInputStream());
        return flujo.readUTF();
    }

    private void prereenviar(String mensaje, ArrayList<Socket> listaCliente) {
        switch (mensaje) {
            case "change to quejas":
                this.tipo = "quejas";
                host.listasale.remove(socket);
                host.listasoport.remove(socket);
                host.listaquejas.remove(socket);
                host.listaquejas.add(socket);

                break;
            case "change to sale":
                this.tipo = "sale";
                host.listasale.remove(socket);
                host.listasoport.remove(socket);
                host.listaquejas.remove(socket);
                host.listasale.add(socket);

                break;
            case "change to soport":
                this.tipo = "soport";
                host.listasale.remove(socket);
                host.listasoport.remove(socket);
                host.listaquejas.remove(socket);
                host.listasoport.add(socket);

                break;
            default:
                reenviar(": " + mensaje, listaCliente);
                break;
        }
    }

}
