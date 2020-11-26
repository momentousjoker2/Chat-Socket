package chatservidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Servidor extends Thread {
    public static final ArrayList<Socket> listaquejas = new ArrayList<Socket>();
    public static final ArrayList<Socket> listasoport = new ArrayList<Socket>();
    public static final ArrayList<Socket> listasale = new ArrayList<Socket>();
    
    private ServerSocket skServidor;
    private ClienteConectado cliente;
    public final VentanaS ventana;
    private final int puerto;

    Servidor(int puerto, VentanaS ventana) {
        this.ventana = ventana;
        this.puerto = puerto;
    }

    @Override
    public void run() {

        try {
            skServidor = new ServerSocket(puerto);
            ventana.setVisible(true);
            ventana.agregarLog("Servidor Arrancado  \nIP: " + skServidor.getInetAddress().getHostAddress() + "\nEscucho puerto " + skServidor.getLocalPort());
            do {
                Socket skCliente = skServidor.accept();

                DataInputStream flujo = new DataInputStream(skCliente.getInputStream());
                String tipo = flujo.readUTF();

                String nombre = flujo.readUTF();

                ventana.agregarLog("\ncliente conectado con ip" + skCliente.getInetAddress() + " con el nombre:" + nombre);

                cliente = new ClienteConectado(skCliente, tipo,nombre,this);
            } while (true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error fatal", JOptionPane.ERROR_MESSAGE);

            System.out.println(e.getMessage());
        }
    }

}
