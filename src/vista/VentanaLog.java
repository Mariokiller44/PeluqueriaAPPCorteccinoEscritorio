/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.ConexionBD;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.util.Date;
/**
 *
 * @author Administrador
 */
public class VentanaLog extends javax.swing.JFrame {

    private static String DATABASE_NAME="bd_alcorteccino";
    private static String folderpath="src\\bd";
    private static String DATABASE_PASSWORD="123pelu";
    private static String DATABASE_USERNAME="admin";
    private static String BACKUP_FOLDER;
    private static String savepath="";

    /**
     * Creates new form VentanaLog
     */
    private ConexionBD conexion;
    private Connection con;
    private Logger logger;
    private String tipoUsu;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VentanaLog() {
        initComponents();
        colocarImagenes();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Inicio de sesión")  ;
        jCheckBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jCheckBox1.isSelected()) {
                    textoContrasenia.setEchoChar('\u0000');
                } else {
                    textoContrasenia.setEchoChar('\u2022');
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                realizarCopiaDeSeguridad();
            }
            
        });

    }

    /**
     * Realiza una copia de seguridad de la base de datos.
     */
    private static void realizarCopiaDeSeguridad() {
        // Obtener la fecha actual
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String fechaActual = dateFormat.format(new Date());

        // Nombre del archivo de copia de seguridad
        String nombreArchivoBackup = DATABASE_NAME + "_" + fechaActual;
        savepath = "\"" + folderpath + "\\" + "" + nombreArchivoBackup + ".sql\"";
        String execudecmd = "mysqldump -u" + DATABASE_USERNAME + " -p" + DATABASE_PASSWORD + " --database " + DATABASE_NAME + " -r " + savepath;

        // Ruta completa del archivo de copia de seguridad
        String rutaArchivoBackup = nombreArchivoBackup;

        try {
            // Comando para realizar la copia de seguridad
            String comando = "mysql --user=" + DATABASE_USERNAME + " --password=" + DATABASE_PASSWORD + " " + DATABASE_NAME + " > " + rutaArchivoBackup;

            // Ejecutar el comando en el sistema operativo
            Process proceso = Runtime.getRuntime().exec(execudecmd);
            int resultado = proceso.waitFor();
            if (resultado == 0) {
                System.out.println("Copia de seguridad creada correctamente: " + rutaArchivoBackup);
            } else {
                System.out.println("Error al hacer copia de seguridad");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al crear la copia de seguridad: " + e.getMessage());
        }
    }

    private void colocarImagenes() {
        jLabel1.setIcon(new ImageIcon("./src/images/tijera.png"));
        jLabel2.setIcon(new ImageIcon("./src/images/iconoPeluqueria.png"));
        jLabel3.setIcon(new ImageIcon("./src/images/peine.png"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelGeneral = new javax.swing.JPanel();
        panelTitulo = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        panelBotonera = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        botonAceptar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        botonRegistrarse = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        botonSalir = new javax.swing.JButton();
        panelLI = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelLD = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panelCentral = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textoUsuario = new javax.swing.JTextField();
        textoContrasenia = new javax.swing.JPasswordField();
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelGeneral.setBackground(new java.awt.Color(18, 189, 201));
        panelGeneral.setToolTipText("");
        panelGeneral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                panelGeneralKeyTyped(evt);
            }
        });
        panelGeneral.setLayout(new java.awt.BorderLayout());

        panelTitulo.setBackground(new java.awt.Color(4, 124, 204));
        panelTitulo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        panelTitulo.setToolTipText("");
        panelTitulo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelTitulo.setFocusCycleRoot(true);

        titulo.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        titulo.setForeground(new java.awt.Color(1, 41, 38));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("PELUQUERIA AL-CORTECCINO");

        javax.swing.GroupLayout panelTituloLayout = new javax.swing.GroupLayout(panelTitulo);
        panelTitulo.setLayout(panelTituloLayout);
        panelTituloLayout.setHorizontalGroup(
            panelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
        );
        panelTituloLayout.setVerticalGroup(
            panelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTituloLayout.createSequentialGroup()
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelGeneral.add(panelTitulo, java.awt.BorderLayout.NORTH);

        panelBotonera.setBackground(new java.awt.Color(24, 173, 156));
        panelBotonera.setToolTipText("");
        panelBotonera.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(24, 173, 156));
        jPanel1.setToolTipText("");

        botonAceptar.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        botonAceptar.setMnemonic(KeyEvent.VK_A);
        botonAceptar.setText("ACEPTAR");
        botonAceptar.setAlignmentY(1.5F);
        botonAceptar.setBorder(null);
        botonAceptar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAceptar.setMargin(new java.awt.Insets(14, 14, 3, 14));
        botonAceptar.setMinimumSize(new java.awt.Dimension(45, 22));
        botonAceptar.setName(""); // NOI18N
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });
        botonAceptar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                botonAceptarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                botonAceptarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        panelBotonera.add(jPanel1, new java.awt.GridBagConstraints());

        jPanel2.setBackground(new java.awt.Color(24, 173, 156));
        jPanel2.setToolTipText("");

        botonRegistrarse.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        botonRegistrarse.setText("REGISTRATE");
        botonRegistrarse.setBorder(null);
        botonRegistrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(botonRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        panelBotonera.add(jPanel2, new java.awt.GridBagConstraints());

        jPanel3.setBackground(new java.awt.Color(24, 173, 156));

        botonSalir.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        botonSalir.setText("SALIR");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        panelBotonera.add(jPanel3, new java.awt.GridBagConstraints());

        panelGeneral.add(panelBotonera, java.awt.BorderLayout.SOUTH);

        panelLI.setBackground(new java.awt.Color(18, 189, 201));
        panelLI.setToolTipText("");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelLILayout = new javax.swing.GroupLayout(panelLI);
        panelLI.setLayout(panelLILayout);
        panelLILayout.setHorizontalGroup(
            panelLILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
        );
        panelLILayout.setVerticalGroup(
            panelLILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
        );

        panelGeneral.add(panelLI, java.awt.BorderLayout.WEST);
        panelLI.getAccessibleContext().setAccessibleName("");

        panelLD.setBackground(new java.awt.Color(18, 189, 201));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setToolTipText("");
        jLabel3.setAlignmentY(0.0F);

        javax.swing.GroupLayout panelLDLayout = new javax.swing.GroupLayout(panelLD);
        panelLD.setLayout(panelLDLayout);
        panelLDLayout.setHorizontalGroup(
            panelLDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLDLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelLDLayout.setVerticalGroup(
            panelLDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
        );

        panelGeneral.add(panelLD, java.awt.BorderLayout.EAST);

        panelCentral.setBackground(new java.awt.Color(18, 189, 201));
        panelCentral.setToolTipText("");
        panelCentral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(18, 189, 201));
        jPanel4.setToolTipText("");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        );

        panelCentral.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 160, 158));

        jPanel5.setBackground(new java.awt.Color(18, 189, 201));
        jPanel5.setToolTipText("");
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("CONTRASEÑA:");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 150, 46));

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("USUARIO:");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 150, 46));

        textoUsuario.setForeground(new java.awt.Color(204, 204, 204));
        textoUsuario.setText("Introduce usuario");
        textoUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textoUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textoUsuarioFocusLost(evt);
            }
        });
        textoUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textoUsuarioKeyTyped(evt);
            }
        });
        jPanel5.add(textoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 220, 30));

        textoContrasenia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textoContraseniaKeyTyped(evt);
            }
        });
        jPanel5.add(textoContrasenia, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 190, 30));

        panelCentral.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 202, -1, -1));

        jCheckBox1.setBackground(new java.awt.Color(18, 189, 201));
        jCheckBox1.setText("mostrarContraseña");
        panelCentral.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 130, -1));

        panelGeneral.add(panelCentral, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGeneral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        // TODO add your handling code here:

        if (textoUsuario.getText().isEmpty() || textoUsuario.getText().contains("Introduce usuario")) {
            JOptionPane.showMessageDialog(null, "Usuario incompleto");
        } else if (textoContrasenia.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Contraseña inválida");
        } else {
            iniciarSesion();
        }
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void botonAceptarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_botonAceptarKeyPressed

    }//GEN-LAST:event_botonAceptarKeyPressed

    private void botonRegistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarseActionPerformed
        // TODO add your handling code here:
        irARegistro();
    }//GEN-LAST:event_botonRegistrarseActionPerformed

    private void textoUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textoUsuarioFocusGained
        // TODO add your handling code here:
        if (textoUsuario.getText().equals("Introduce usuario")) {
            textoUsuario.setText("");
            textoUsuario.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_textoUsuarioFocusGained

    private void textoUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textoUsuarioFocusLost
        // TODO add your handling code here:
        if (textoUsuario.getText().isEmpty()) {
            textoUsuario.setForeground(Color.GRAY);
            textoUsuario.setText("Introduce usuario");
        }
    }//GEN-LAST:event_textoUsuarioFocusLost

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_botonSalirActionPerformed

    private void botonAceptarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_botonAceptarKeyTyped
        char cTeclapresionada = evt.getKeyChar();

        if (cTeclapresionada == KeyEvent.VK_ENTER) {
            botonAceptar.doClick();
        }
    }//GEN-LAST:event_botonAceptarKeyTyped

    private void textoUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoUsuarioKeyTyped
        char cTeclapresionada = evt.getKeyChar();

        if (cTeclapresionada == KeyEvent.VK_ENTER) {
            botonAceptar.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_textoUsuarioKeyTyped

    private void textoContraseniaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoContraseniaKeyTyped
        char cTeclapresionada = evt.getKeyChar();

        if (cTeclapresionada == KeyEvent.VK_ENTER) {
            botonAceptar.doClick();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_textoContraseniaKeyTyped

    private void panelGeneralKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panelGeneralKeyTyped

    }//GEN-LAST:event_panelGeneralKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaLog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonRegistrarse;
    private javax.swing.JButton botonSalir;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel panelBotonera;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelGeneral;
    private javax.swing.JPanel panelLD;
    private javax.swing.JPanel panelLI;
    private javax.swing.JPanel panelTitulo;
    private javax.swing.JPasswordField textoContrasenia;
    private javax.swing.JTextField textoUsuario;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    private void iniciarSesion() {
        try {
            String usuario = textoUsuario.getText().toString();
            String contrasenia = textoContrasenia.getText();
            conexion = new ConexionBD(usuario, contrasenia);
            con = conexion.getConnection();
            String passwd = obtenerContrasenia(usuario);
            String contraseniaMD5 = calcularMD5(contrasenia);
            if (contraseniaMD5.contains(passwd)) {
                String sql = "SELECT * FROM usuario WHERE CUENTA LIKE ? AND CONTRASENIA LIKE ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, usuario);
                stmt.setString(2, contraseniaMD5);
                ResultSet resul = stmt.executeQuery();
                if (!resul.next()) {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña no validos");
                    textoContrasenia.setText("");
                    textoUsuario.setText("");
                } else {
                    setId(resul.getInt("id"));
                    JOptionPane.showMessageDialog(null, "Bienvenido " + resul.getString("nombre"));
                    tipoUsu = resul.getString("tipo_de_usuario");
                    try {
                        if (tipoUsu.contains("Personal")) {
                            dispose();
                            VentanaPrincipal vc = new VentanaPrincipal();
                            vc.setValor(id);
                            vc.setTipoUsu(resul.getString("Nombre"));
                            vc.setVisible(true);
                        } else {
                            throw new ClassCastException("Esta aplicación solo la puede utilizar el personal. Pruebe nuestra aplicación Android");
                        }
                    } catch (ClassCastException cse) {
                        JOptionPane.showMessageDialog(null, cse.getMessage());
                    }

                }
                conexion.cerrarConnection();
            }

        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(null, "Error intentando conectar a la base de datos");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Hubo un problema con la base de datos.");
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(null, "Falló la comprobación de contraseña");
        }
    }

    /**
     *
     * @param texto
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String calcularMD5(String texto) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(texto.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    private void irARegistro() {
        VentanaRegistro vr = new VentanaRegistro();
        vr.setVisible(true);
    }

    /**
     *
     * @param usuario
     * @return
     */
    private String obtenerContrasenia(String usuario) {
        String contrasenia = null;
        try {
            conexion = new ConexionBD("usuario", "passwd");
            con = conexion.getConnection();
            String sql = "SELECT CONTRASENIA FROM USUARIO WHERE CUENTA LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                contrasenia = rs.getString("contrasenia");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la conexion.");
        }
        return contrasenia;
    }
}
