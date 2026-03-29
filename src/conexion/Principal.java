package conexion;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Principal extends JFrame {

    JTable tabla;
    DefaultTableModel modelo;
    JLabel lblTotal;

    public Principal() {
        setTitle("Sistema Profesional");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔥 SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(15,15,15));
        sidebar.setPreferredSize(new Dimension(220,0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("  SISTEMA");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));

        sidebar.add(logo);

        JButton btnNuevo = crearBoton("🧑‍💼  Nuevo Usuario");
        JButton btnEditar = crearBoton("📝  Editar");
        JButton btnEliminar = crearBoton("❌  Eliminar");
        JButton btnCerrar = crearBoton("🔒  Cerrar Sesión");

        sidebar.add(btnNuevo);
        sidebar.add(btnEditar);
        sidebar.add(btnEliminar);
        sidebar.add(btnCerrar);

        add(sidebar, BorderLayout.WEST);

        // 🔥 PANEL PRINCIPAL
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(30,30,30));
        main.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // 🔝 HEADER
        JLabel titulo = new JLabel("Dashboard");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));

        main.add(titulo, BorderLayout.NORTH);

        // 🔥 DASHBOARD
        JPanel cards = new JPanel(new GridLayout(1,1,15,15));
        cards.setBackground(new Color(30,30,30));

        lblTotal = crearCard("Usuarios", "0");
        cards.add(lblTotal);

        main.add(cards, BorderLayout.CENTER);

        // 🧾 TABLA
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);

        tabla.setBackground(new Color(45,45,45));
        tabla.setForeground(Color.WHITE);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTableHeader headerTabla = tabla.getTableHeader();
        headerTabla.setBackground(new Color(0,150,255));
        headerTabla.setForeground(Color.WHITE);
        headerTabla.setFont(new Font("Segoe UI", Font.BOLD, 14));

        modelo.addColumn("ID");
        modelo.addColumn("Usuario");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Telefono");
        modelo.addColumn("Correo");

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        main.add(scroll, BorderLayout.SOUTH);

        add(main, BorderLayout.CENTER);

        cargarDatos();
        contarUsuarios();

        // 🔥 EVENTOS
        btnNuevo.addActionListener(e -> new Registro().setVisible(true));
        btnEliminar.addActionListener(e -> eliminar());
        btnEditar.addActionListener(e -> editar());
        btnCerrar.addActionListener(e -> cerrarSesion());
    }

    // 🎨 BOTONES PRO
    private JButton crearBoton(String texto){
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(200,40));
        btn.setBackground(new Color(0,150,255));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // 🔥 HOVER
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0,180,255));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(0,150,255));
            }
        });

        return btn;
    }

    // 🎯 CARD
    private JLabel crearCard(String titulo, String valor){
        JLabel lbl = new JLabel(titulo + ": " + valor, JLabel.CENTER);
        lbl.setOpaque(true);
        lbl.setBackground(new Color(0,150,255));
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        return lbl;
    }

    private void cargarDatos() {
        try {
            Connection con = Conexion.conectar();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM usuarios");

            while(rs.next()){
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("correo")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
        }
    }

    private void contarUsuarios() {
        try {
            Connection con = Conexion.conectar();
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM usuarios");

            if(rs.next()){
                lblTotal.setText("Usuarios: " + rs.getInt(1));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();

        if(fila >= 0){
            int id = Integer.parseInt(modelo.getValueAt(fila, 0).toString());

            try {
                Connection con = Conexion.conectar();
                PreparedStatement ps = con.prepareStatement("DELETE FROM usuarios WHERE id=?");
                ps.setInt(1, id);
                ps.executeUpdate();

                modelo.setRowCount(0);
                cargarDatos();
                contarUsuarios();

                JOptionPane.showMessageDialog(this, "Usuario eliminado");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario");
        }
    }

    private void editar() {
        int fila = tabla.getSelectedRow();

        if(fila >= 0){
            int id = Integer.parseInt(modelo.getValueAt(fila, 0).toString());

            String usuario = JOptionPane.showInputDialog("Usuario:", modelo.getValueAt(fila,1));

            try {
                Connection con = Conexion.conectar();
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE usuarios SET usuario=? WHERE id=?"
                );

                ps.setString(1, usuario);
                ps.setInt(2, id);
                ps.executeUpdate();

                modelo.setRowCount(0);
                cargarDatos();

                JOptionPane.showMessageDialog(this, "Usuario actualizado");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario");
        }
    }

    private void cerrarSesion() {
        if(JOptionPane.showConfirmDialog(this,"¿Cerrar sesión?")==0){
            new Login().setVisible(true);
            this.dispose();
        }
    }
}