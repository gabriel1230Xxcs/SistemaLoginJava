package conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Registro extends JFrame {

    JTextField txtUsuario, txtNombre, txtApellido, txtTelefono, txtCorreo;
    JPasswordField txtPassword, txtConfirmar;

    public Registro() {
        setTitle("Nuevo Usuario");
        setSize(420, 450);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(25,25,25));
        panel.setLayout(new GridLayout(9,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));

        JLabel titulo = new JLabel("🧑‍💼 REGISTRAR USUARIO", JLabel.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        panel.add(titulo);

        txtUsuario = crearCampo("Usuario");
        panel.add(txtUsuario);

        txtNombre = crearCampo("Nombre");
        panel.add(txtNombre);

        txtApellido = crearCampo("Apellido");
        panel.add(txtApellido);

        txtTelefono = crearCampo("Teléfono");
        panel.add(txtTelefono);

        txtCorreo = crearCampo("Correo");
        panel.add(txtCorreo);

        txtPassword = crearPassword("Contraseña");
        panel.add(txtPassword);

        txtConfirmar = crearPassword("Confirmar Contraseña");
        panel.add(txtConfirmar);

        JButton btnGuardar = crearBoton("💾 Registrar");
        panel.add(btnGuardar);

        add(panel);

        btnGuardar.addActionListener(e -> guardar());
    }

    // 🎨 CAMPO MODERNO
    private JTextField crearCampo(String texto){
        JTextField txt = new JTextField();
        txt.setBorder(BorderFactory.createTitledBorder(texto));
        txt.setBackground(Color.WHITE);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return txt;
    }

    private JPasswordField crearPassword(String texto){
        JPasswordField txt = new JPasswordField();
        txt.setBorder(BorderFactory.createTitledBorder(texto));
        txt.setBackground(Color.WHITE);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return txt;
    }

    // 🔥 BOTÓN MODERNO
    private JButton crearBoton(String texto){
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(0,200,120));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(0,230,140));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(0,200,120));
            }
        });

        return btn;
    }

    private void guardar() {

        String usuario = txtUsuario.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        String pass = new String(txtPassword.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        // VALIDACIONES
        if(usuario.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ||
           telefono.isEmpty() || correo.isEmpty() || pass.isEmpty() || confirmar.isEmpty()) {

            JOptionPane.showMessageDialog(this, "⚠️ Completa todos los campos");
            return;
        }

        if(!pass.equals(confirmar)){
            JOptionPane.showMessageDialog(this, "❌ Las contraseñas no coinciden");
            return;
        }

        try {
            Connection con = Conexion.conectar();

            String sql = "INSERT INTO usuarios(usuario,nombre,apellido,telefono,correo,password) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, telefono);
            ps.setString(5, correo);
            ps.setString(6, pass);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Usuario registrado");

            limpiar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
        }
    }

    private void limpiar(){
        txtUsuario.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtPassword.setText("");
        txtConfirmar.setText("");
    }
}