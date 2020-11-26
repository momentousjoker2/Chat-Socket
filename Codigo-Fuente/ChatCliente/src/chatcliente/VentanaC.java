package chatcliente;

import java.awt.GridLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaC extends javax.swing.JFrame {

    private final String DEFAULT_PORT = "5001";
    private final String DEFAULT_IP = "localhost";

    private InputStream inputStream;
    private OutputStream outputStream;

    private DataInputStream entradaDatos;
    private DataOutputStream SalidaDatos;

    String tipo = "soport", ip, puerto, nombre;
    Socket skCliente;
    boolean opcion;

    public VentanaC() throws IOException {
        initComponents();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String ip_puerto_nombre[] = getIP_Puerto_Nombre();
        ip = ip_puerto_nombre[0];
        puerto = ip_puerto_nombre[1];
        nombre = ip_puerto_nombre[2];
        tipo = ip_puerto_nombre[3];
    }

    public void conexion() throws IOException {
        skCliente = new Socket(ip, Integer.parseInt(puerto));

        sesionIniciada(nombre);
        opcion = true;

        this.enviarDatos(tipo);
        this.enviarDatos(nombre);
        if (skCliente.isConnected()) {
            Thread hilo1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        escucharDatos(skCliente);
                    }
                }
            });
            hilo1.start();
            addMensaje("conectado\n");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtHistorial = new javax.swing.JTextArea();
        txtMensaje = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        txtHistorial.setEditable(false);
        txtHistorial.setColumns(20);
        txtHistorial.setRows(5);
        jScrollPane1.setViewportView(txtHistorial);

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtMensaje)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEnviar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnviar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed

        switch (txtMensaje.getText()) {
            case "change to soport":
                txtHistorial.setText("Bienvenido a soport");
                this.enviarDatos(txtMensaje.getText());
                break;
            case "change to sale":
                txtHistorial.setText("Bienvenido a sale");
                this.enviarDatos(txtMensaje.getText());
                break;
            case "change to quejas":
                txtHistorial.setText("Bienvenido a quejas");
                this.enviarDatos(txtMensaje.getText());
                break;
            case "salir":
                enviarDatos("salir");
                cerrarTodo();
                break;
            default:
                this.enviarDatos(txtMensaje.getText());
                break;
        }
        txtMensaje.setText("");
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        enviarDatos("salir");
        cerrarTodo();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaC c = new VentanaC();
                    c.setVisible(true);
                    c.conexion();
                } catch (IOException ex) {

                }

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtHistorial;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables

    private String[] getIP_Puerto_Nombre() {
        String s[] = new String[4];
        s[0] = DEFAULT_IP;
        s[1] = DEFAULT_PORT;
        JTextField ip = new JTextField(20);
        JTextField puerto = new JTextField(20);
        JTextField usuario = new JTextField(20);
        JComboBox combo = new JComboBox();

        ip.setText(DEFAULT_IP);
        puerto.setText(DEFAULT_PORT);
        usuario.setText("Usuario");

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(4, 2));
        myPanel.add(new JLabel("IP del Servidor:"));
        myPanel.add(ip);
        myPanel.add(new JLabel("Puerto de la conexión:"));
        myPanel.add(puerto);
        myPanel.add(new JLabel("Escriba su nombre:"));
        myPanel.add(usuario);
        myPanel.add(new JLabel("Selecione la sala:"));
        myPanel.add(combo);
        combo.addItem("quejas");
        combo.addItem("sale");
        combo.addItem("soport");

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Configuraciones de la comunicación", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            s[0] = ip.getText();
            s[1] = puerto.getText();
            s[2] = usuario.getText();
            s[3] = combo.getSelectedItem().toString();
        } else {
            System.exit(0);
        }
        return s;
    }

    void addMensaje(String mensaje) {
        txtHistorial.append(mensaje);
    }

    void sesionIniciada(String identificador) {
        this.setTitle(" --- " + identificador + " --- ");
    }

    public void escucharDatos(Socket socket) {
        try {
            inputStream = socket.getInputStream();
            entradaDatos = new DataInputStream(inputStream);
            String mensaje = entradaDatos.readUTF();
            addMensaje(mensaje + "\n");
        } catch (IOException ex) {
        }
    }

    public void enviarDatos(String datos) {
        try {
            outputStream = skCliente.getOutputStream();
            SalidaDatos = new DataOutputStream(outputStream);
            SalidaDatos.writeUTF(datos);
            SalidaDatos.flush();

        } catch (IOException ex) {
        }

    }

    public void cerrarTodo() {
        try {
            SalidaDatos.close();
            entradaDatos.close();
            skCliente.close();
            this.setVisible(false);
            this.dispose();
            this.finalize();
        } catch (IOException ex) {
        } catch (Throwable ex) {
            Logger.getLogger(VentanaC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
