/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author mescr
 */
import controlador.ConexionBD;
import controlador.ConsultasPersonal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import style.GestionDeProductos;

public class GestionProductosVentana extends JFrame {

    private JTable productosTable;
    private DefaultTableModel tableModel;
    private ConsultasPersonal consultas;
    private Connection con;
    private String tipoUsu;
    private JPopupMenu popupMenu = new JPopupMenu();
    ;
    private final JButton cargarButton;
    private final JButton agregarButton;
    private final JButton eliminarButton;
    private final JButton salirButton;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoUsu() {
        return tipoUsu;
    }

    public void setTipoUsu(String tipoUsu) {
        this.tipoUsu = tipoUsu;
    }

    public GestionProductosVentana() {
        setTitle("Gestión de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setResizable(false);

        tableModel = new DefaultTableModel(new Object[]{"Nombre", "Cantidad"}, 0);
        productosTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }

        };
        JScrollPane scrollPane = new JScrollPane(productosTable);
        scrollPane.getViewport().setBackground(new Color(186, 179, 179)); // Establecer color de fondo del panel de desplazamiento

        // Personalizar el renderizador de encabezado para cambiar el color de los títulos
        JTableHeader header = productosTable.getTableHeader();
        header.setBackground(new Color(74, 159, 255));
        header.setForeground(Color.WHITE);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) productosTable.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        renderer.setFont(new Font("Arial", Font.BOLD, 14));

        cargarButton = new JButton("Cargar productos");
        cargarButton.addActionListener(e -> cargarProductos());
        cargarButton.setBackground(new Color(42, 89, 42)); // Establecer color de fondo del botón
        cargarButton.setForeground(Color.WHITE); // Establecer color de texto del botón

        agregarButton = new JButton("Agregar producto");
        agregarButton.addActionListener(e -> agregarProducto());
        agregarButton.setBackground(new Color(42, 89, 42)); // Establecer color de fondo del botón
        agregarButton.setForeground(Color.WHITE); // Establecer color de texto del botón

        eliminarButton = new JButton("Eliminar producto");
        eliminarButton.addActionListener(e -> eliminarProducto());
        eliminarButton.setBackground(new Color(42, 89, 42)); // Establecer color de fondo del botón
        eliminarButton.setForeground(Color.WHITE); // Establecer color de texto del botón

        salirButton = new JButton("Atrás");
        salirButton.addActionListener(e -> salir());
        salirButton.setBackground(new Color(42, 89, 42)); // Establecer color de fondo del botón
        salirButton.setForeground(Color.WHITE); // Establecer color de texto del botón

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(42, 89, 42)); // Establecer color de fondo del panel
        buttonPanel.add(cargarButton);
        buttonPanel.add(agregarButton);
        buttonPanel.add(eliminarButton);
        buttonPanel.add(salirButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Mostrar la ventana en el centro de la pantalla
        if (tipoUsu != null) {
            if (!tipoUsu.isEmpty()) {
                comprobarTipoUsuario();
                aniadirmenuPopUp();
            }
        }
    }

    public void aniadirmenuPopUp() {
        // Crear el menú emergente "modificarCantidad"
        if (popupMenu.getComponentCount() == 0) {
            JMenuItem modificarCantidadItem = new JMenuItem("Modificar Cantidad");
            modificarCantidadItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int filaSeleccionada = productosTable.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String nombreProducto = (String) tableModel.getValueAt(filaSeleccionada, 0);
                        String cantidadActualStr = String.valueOf(tableModel.getValueAt(filaSeleccionada, 1));
                        String nuevaCantidadStr = JOptionPane.showInputDialog("Ingrese la nueva cantidad para el producto \"" + nombreProducto + "\":", cantidadActualStr);
                        if (nuevaCantidadStr != null) {
                            try {
                                int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
                                // Lógica para actualizar el campo STOCK del producto con la nueva cantidad en la base de datos
                                if (nuevaCantidad < 0) {
                                    throw new NumberFormatException("No se puede insertar una cantidad menor a 0");
                                } else {
                                    tableModel.setValueAt(nuevaCantidadStr, filaSeleccionada, 1);
                                    int idProducto = obtenerIdProducto(nombreProducto);
                                    actualizarStockProducto(idProducto, nuevaCantidad);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                                JOptionPane.showMessageDialog(GestionProductosVentana.this, "Ingrese un valor numérico válido", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            });

            popupMenu.add(modificarCantidadItem);
        } else if (popupMenu.getComponentCount() == 1) {
            JMenuItem modificarNombre = new JMenuItem("Modificar Nombre");
            modificarNombre.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int filaSeleccionada = productosTable.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String nombreProducto = (String) tableModel.getValueAt(filaSeleccionada, 0);
                        String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del producto: ");
                        if (nuevoNombre != null) {
                            if (esNombreValido(nuevoNombre)) {
                                tableModel.setValueAt(nuevoNombre, filaSeleccionada, 0);
                                int idProducto = obtenerIdProducto(nombreProducto);
                                actualizarNombreProducto(idProducto, nuevoNombre);
                            } else {
                                JOptionPane.showMessageDialog(null, "El nombre ingresado contiene números y no es válido.");
                            }
                        }
                    }
                }
            });
            tipoUsu = getTipoUsu();
            if (tipoUsu.equals("Administrador")) {
                popupMenu.add(modificarNombre);
            }
        }
        productosTable.setComponentPopupMenu(popupMenu);
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage("./src/images/iconoDeAppEscritorio.png");
        return retValue;
    }

    private boolean esNombreValido(String nuevoNombre) {
        for (char c : nuevoNombre.toCharArray()) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private int obtenerIdProducto(String nombreProducto) {
        int retorno = 0;
        try {
            consultas.realizarConexion();
            con = consultas.getCon();
            String consulta = "SELECT ID FROM PRODUCTOS WHERE NOMBRE =?";
            PreparedStatement stmt = con.prepareStatement(consulta);
            stmt.setString(1, nombreProducto);
            ResultSet resul = stmt.executeQuery();
            while (resul.next()) {
                retorno = resul.getInt("id");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al coger el id");
        }
        return retorno;
    }

    private void actualizarNombreProducto(int idProducto, String nuevoNombre) {
        consultas.realizarConexion();
        con = consultas.getCon();
        String consulta = "UPDATE PRODUCTOS SET NOMBRE = ? WHERE ID = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(consulta);
            stmt.setString(1, nuevoNombre);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Stock actualizado correctamente");
        } catch (SQLException e) {
            e.getMessage();
            JOptionPane.showMessageDialog(this, "Error al actualizar el stock del producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarStockProducto(int idProducto, int nuevaCantidad) {
        consultas.realizarConexion();
        con = consultas.getCon();
        String consulta = "UPDATE PRODUCTOS SET STOCK = ? WHERE ID = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(consulta);
            stmt.setInt(1, nuevaCantidad);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Stock actualizado correctamente");

        } catch (SQLException e) {
            e.getMessage();
            JOptionPane.showMessageDialog(this, "Error al actualizar el stock del producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarProductos() {
        // Limpia la tabla antes de cargar los productos
        tableModel.setRowCount(0);
        consultas = new ConsultasPersonal();
        try {
            // Conexión a la base de datos
            consultas.realizarConexion();
            con = consultas.getCon();
            // Realizar consulta
            String sql = "SELECT NOMBRE, STOCK FROM PRODUCTOS";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Llenar la tabla con los resultados de la consulta
            while (resultSet.next()) {
                String nombre = resultSet.getString("NOMBRE");
                int stock = resultSet.getInt("STOCK");
                tableModel.addRow(new Object[]{nombre, stock});
            }

            // Cerrar conexión y liberar recursos
            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los productos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarProducto() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:");
        if (nombre != null && !nombre.isEmpty()) {
            String stockStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad de stock:");
            if (stockStr != null && !stockStr.isEmpty()) {
                try {
                    int stock = Integer.parseInt(stockStr);

                    // Agregar el nuevo producto a la tabla y a la base de datos
                    tableModel.addRow(new Object[]{nombre, stock});
                    consultas.realizarConexion();
                    con = consultas.getCon();
                    PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTOS (NOMBRE, STOCK) VALUES (?, ?)");
                    statement.setString(1, nombre);
                    statement.setInt(2, stock);
                    statement.executeUpdate();
                    statement.close();
                    con.close();
                } catch (NumberFormatException | SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al agregar el producto", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void eliminarProducto() {
        int selectedRow = productosTable.getSelectedRow();
        if (selectedRow != -1) {
            String nombre = (String) tableModel.getValueAt(selectedRow, 0);
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el producto '" + nombre + "'?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                // Eliminar el producto de la tabla y de la base de datos
                tableModel.removeRow(selectedRow);
                try {
                    consultas.realizarConexion();
                    con = consultas.getCon();
                    PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTOS WHERE NOMBRE = ?");
                    statement.setString(1, nombre);
                    statement.executeUpdate();
                    statement.close();
                    con.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el producto", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void salir() {
        int decision = JOptionPane.showConfirmDialog(null, "¿Estas seguro de que quieres salir del menu?", "Salir", JOptionPane.YES_NO_CANCEL_OPTION);
        if (decision == JOptionPane.YES_OPTION) {
            dispose();
            VentanaPrincipal vc = new VentanaPrincipal();
            vc.setId(id);
            vc.setTipoUsu(tipoUsu);
            vc.setVisible(true);
        }
    }

    public static void main(String[] args) {
        try {
            GestionDeProductos.registerCustomDefaultsSource("style");
            UIManager.setLookAndFeel(new GestionDeProductos());
        } catch (UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null, "Error tratando de cargar el tema");
        }
        SwingUtilities.invokeLater(() -> {
            GestionProductosVentana ventana = new GestionProductosVentana();
            ventana.setVisible(true);
        });
    }

    private void comprobarTipoUsuario() {
        if (!tipoUsu.equals("Administrador")) {
            agregarButton.setEnabled(false);
            eliminarButton.setEnabled(false);
        } else {
            agregarButton.setEnabled(true);
            eliminarButton.setEnabled(true);
        }
    }
}
