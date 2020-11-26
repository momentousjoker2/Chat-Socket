package chatservidor;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class VentanaS extends javax.swing.JFrame {

    private final String DEFAULT_PORT = "5001";

    public VentanaS() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String puerto = getPuerto();
        Servidor s = new Servidor(Integer.parseInt(puerto), this);
        s.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtClientes = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Log del Servidor"));

        txtClientes.setEditable(false);
        txtClientes.setColumns(20);
        txtClientes.setRows(5);
        jScrollPane1.setViewportView(txtClientes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private String getPuerto() {
        String p = DEFAULT_PORT;

        JTextField puerto = new JTextField(20);
        puerto.setText(p);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(2, 1));
        myPanel.add(new JLabel("Puerto de la conexión:"));
        myPanel.add(puerto);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Configuraciones de la comunicación", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            p = puerto.getText();
        } else {
            System.exit(0);
        }
        return p;
    }

    void agregarLog(String texto) {
        txtClientes.append(texto);
    }

    public static void main(String args[]) {
        VentanaS v = new VentanaS();
        v.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtClientes;
    // End of variables declaration//GEN-END:variables

}
