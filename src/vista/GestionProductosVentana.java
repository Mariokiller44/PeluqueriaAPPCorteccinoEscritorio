/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 * Clase GestionProductosVentana el cual es una ventana para gestionar los
 * productos
 *
 * @author Mario
 */
import controlador.ConsultasPersonal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class GestionProductosVentana extends javax.swing.JFrame {

    /**
     * Declaracion de atributos
     */
    private JTable productosTable;
    private DefaultTableModel tableModel;
    private ConsultasPersonal consultas;
    private Connection con;
    private String tipoUsu;
    private JPopupMenu popupMenu = new JPopupMenu();
    private JButton agregarButton;
    private JButton eliminarButton;
    private JButton salirButton;
    private VentanaPrincipal ventanaPrincipal;
    private int id;

    /**
     * Este método devuelve el valor de la variable id.
     *
     * @return El valor de la variable id.
     */
    public int getId() {
        return id;
    }

    /**
     * Este método establece el valor de la variable id.
     *
     * @param id El nuevo valor de la variable id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece la ventana principal.
     *
     * @param ventanaPrincipal La instancia de la ventana principal.
     */
    public void setVentanaPrincipal(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
    }

    /**
     * Este método devuelve el valor de la variable tipoUsu.
     *
     * @return El valor de la variable tipoUsu.
     */
    public String getTipoUsu() {
        return tipoUsu;
    }

    /**
     * Este método establece el valor de la variable tipoUsu.
     *
     * @param tipoUsu El nuevo valor de la variable tipoUsu.
     */
    public void setTipoUsu(String tipoUsu) {
        this.tipoUsu = tipoUsu;
    }

    /**
     * Constructor de la clase GestionProductosVentana.
     */
    public GestionProductosVentana() {
        initComponents();
        setTitle("Gestión de Productos"); // Establecer el título de la ventana
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Configurar el comportamiento al cerrar la ventana
        setIconImage(getIconImage()); // Establecer la imagen del ícono de la ventana
        setResizable(false); // Deshabilitar la capacidad de redimensionar la ventana

        setLocationRelativeTo(null); // Mostrar la ventana en el centro de la pantalla

        // Verificar el tipo de usuario y realizar acciones correspondientes
        if (tipoUsu != null) {
            if (!tipoUsu.isEmpty()) {
                comprobarTipoUsuario();
                aniadirmenuPopUp();
            }
        }

        cargarProductos(); // Cargar los productos en la tabla

        ventanaPrincipal = new VentanaPrincipal(); // Crear una instancia de la ventana principal

        // Agregar un WindowListener para controlar el cierre de la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int decision = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres salir?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
                if (decision == JOptionPane.YES_OPTION) {
                    dispose(); // Cerrar la ventana actual
                    ventanaPrincipal.setVisible(true); // Mostrar la ventana principal
                }
            }
        });
    }

    private void initComponents() {
        // Crear el modelo de la tabla y la tabla con el modelo
        tableModel = new DefaultTableModel(new Object[]{"Nombre", "Cantidad"}, 0);
        productosTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No permitir la edición de celdas en la tabla
            }
        };

        // Crear un panel de desplazamiento para la tabla
        JScrollPane scrollPane = new JScrollPane(productosTable);
        scrollPane.getViewport().setBackground(new Color(186, 179, 179)); // Establecer color de fondo del panel de desplazamiento

        // Personalizar el renderizador de encabezado para cambiar el color de los títulos
        JTableHeader header = productosTable.getTableHeader();
        header.setBackground(new Color(74, 159, 255));
        header.setForeground(Color.WHITE);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) productosTable.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        renderer.setFont(new Font("Arial", Font.BOLD, 14));

        // Crear botones y agregarles acciones
        agregarButton = new JButton("Agregar producto");
        agregarButton.addActionListener(e -> agregarProducto());
        agregarButton.setForeground(Color.WHITE); // Establecer color de texto del botón

        eliminarButton = new JButton("Eliminar producto");
        eliminarButton.addActionListener(e -> eliminarProducto());
        eliminarButton.setForeground(Color.WHITE); // Establecer color de texto del botón

        salirButton = new JButton("Atrás");
        salirButton.addActionListener(e -> salir());
        salirButton.setForeground(Color.WHITE); // Establecer color de texto del botón

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(agregarButton);
        buttonPanel.add(eliminarButton);
        buttonPanel.add(salirButton);

        //setLayout(new BorderLayout()); // Establecer el diseño de la ventana
        add(scrollPane, BorderLayout.CENTER); // Agregar el panel de desplazamiento al centro de la ventana
        add(buttonPanel, BorderLayout.SOUTH); // Agregar el panel de botones en la parte inferior de la ventana

        pack(); // Ajustar el tamaño de la ventana según su contenido
    }

    /**
     * Este método añade un menú emergente a la tabla de productos.
     */
    public void aniadirmenuPopUp() {
        // Crear el menú emergente "modificarCantidad"
        if (popupMenu.getComponentCount() == 0) {
            // Crear el elemento de menú "Modificar Cantidad"
            JMenuItem modificarCantidadItem = new JMenuItem("Modificar Cantidad");
            modificarCantidadItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Obtener la fila seleccionada en la tabla
                    int filaSeleccionada = productosTable.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        // Obtener el nombre y la cantidad actual del producto seleccionado
                        String nombreProducto = (String) tableModel.getValueAt(filaSeleccionada, 0);
                        String cantidadActualStr = String.valueOf(tableModel.getValueAt(filaSeleccionada, 1));

                        // Mostrar un cuadro de diálogo para ingresar la nueva cantidad
                        String nuevaCantidadStr = JOptionPane.showInputDialog("Ingrese la nueva cantidad para el producto \"" + nombreProducto + "\":", cantidadActualStr);
                        if (nuevaCantidadStr != null) {
                            try {
                                int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
                                // Lógica para actualizar el campo STOCK del producto con la nueva cantidad en la base de datos
                                if (nuevaCantidad < 0) {
                                    throw new NumberFormatException("No se puede insertar una cantidad menor a 0");
                                } else {
                                    // Actualizar la cantidad en la tabla y en la base de datos
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

            // Agregar el elemento de menú al menú emergente
            popupMenu.add(modificarCantidadItem);
        } else if (popupMenu.getComponentCount() == 1) {
            // Crear el elemento de menú "Modificar Nombre"
            JMenuItem modificarNombre = new JMenuItem("Modificar Nombre");
            modificarNombre.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Obtener la fila seleccionada en la tabla
                    int filaSeleccionada = productosTable.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        // Obtener el nombre del producto seleccionado
                        String nombreProducto = (String) tableModel.getValueAt(filaSeleccionada, 0);

                        // Mostrar un cuadro de diálogo para ingresar el nuevo nombre
                        String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del producto: ");
                        if (nuevoNombre != null) {
                            if (esNombreValido(nuevoNombre)) {
                                // Actualizar el nombre en la tabla y en la base de datos
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

            // Obtener el tipo de usuario
            tipoUsu = getTipoUsu();
            if (tipoUsu.equals("Administrador")) {
                // Agregar el elemento de menú al menú emergente si el tipo de usuario es "Administrador"
                popupMenu.add(modificarNombre);
            }
        }

        // Asignar el menú emergente a la tabla de productos
        productosTable.setComponentPopupMenu(popupMenu);
    }

    /**
     * Este método devuelve la imagen del ícono de la aplicación.
     *
     * @return La imagen del ícono de la aplicación.
     */
    @Override
    public Image getIconImage() {
        // Obtener la imagen del ícono de la aplicación
        Image retValue = Toolkit.getDefaultToolkit().getImage("./src/images/iconoDeAppEscritorio.png");
        return retValue;
    }

    /**
     * Este método verifica si un nombre es válido, es decir, si no contiene
     * números.
     *
     * @param nuevoNombre El nombre a verificar.
     * @return true si el nombre es válido, false si contiene números.
     */
    private boolean esNombreValido(String nuevoNombre) {
        // Verificar si el nombre contiene números
        for (char c : nuevoNombre.toCharArray()) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Este método obtiene el ID de un producto dado su nombre.
     *
     * @param nombreProducto El nombre del producto.
     * @return El ID del producto.
     */
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

    /**
     * Actualiza el nombre de un producto en la base de datos.
     *
     * @param idProducto El ID del producto a actualizar.
     * @param nuevoNombre El nuevo nombre del producto.
     */
    private void actualizarNombreProducto(int idProducto, String nuevoNombre) {
        consultas.realizarConexion();
        con = consultas.getCon();
        String consulta = "UPDATE PRODUCTOS SET NOMBRE = ? WHERE ID = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(consulta);
            stmt.setString(1, nuevoNombre);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Nombre actualizado correctamente");
        } catch (SQLException e) {
            e.getMessage();
            JOptionPane.showMessageDialog(this, "Error al actualizar el nombre del producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualiza el stock de un producto en la base de datos.
     *
     * @param idProducto El ID del producto a actualizar.
     * @param nuevaCantidad La nueva cantidad de stock del producto.
     */
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

    /**
     * Carga los productos desde la base de datos y los muestra en la tabla.
     */
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

    /**
     * Agrega un nuevo producto a la tabla y a la base de datos.
     */
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

    /**
     * Elimina un producto de la tabla y de la base de datos.
     */
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

    /**
     * Cierra la ventana actual y vuelve a la ventana principal.
     */
    private void salir() {
        int decision = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres salir del menú?", "Salir", JOptionPane.YES_NO_CANCEL_OPTION);
        if (decision == JOptionPane.YES_OPTION) {
            dispose();
            VentanaPrincipal vc = new VentanaPrincipal();
            vc.setId(id);
            vc.setTipoUsu(tipoUsu);
            vc.setVisible(true);
        }
    }

    /**
     * Método de entrada principal del programa.
     *
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GestionProductosVentana ventana = new GestionProductosVentana();
                ventana.setVisible(true);
            }
        });
    }

    /**
     * Comprueba el tipo de usuario y habilita/deshabilita los botones según
     * corresponda.
     */
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
