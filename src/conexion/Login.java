package conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame {

    JTextField txtUsuario;
    JPasswordField txtPassword;

    public Login() {
        setTitle("Sistema Profesional");
        setSize(420, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //  Animación inicial
        setOpacity(1f);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(20,20,20));
        panel.setLayout(new GridLayout(8,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));

        JLabel titulo = new JLabel("🔐 INICIAR SESIÓN", JLabel.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        panel.add(titulo);

        txtUsuario = new JTextField();
        txtUsuario.setBorder(BorderFactory.createTitledBorder("Usuario"));
        panel.add(txtUsuario);

        txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        panel.add(txtPassword);

        JCheckBox ver = new JCheckBox("Mostrar contraseña");
        ver.setBackground(new Color(20,20,20));
        ver.setForeground(Color.WHITE);
        panel.add(ver);

        JButton btnEntrar = crearBoton("🔓 Entrar", new Color(0,150,255));
        JButton btnRegistro = crearBoton("🧑‍💼 Nuevo Usuario", new Color(0,200,120));
        JButton btnSalir = crearBoton("❌ Salir", new Color(200,50,50));

        panel.add(btnEntrar);
        panel.add(btnRegistro);
        panel.add(btnSalir);

        add(panel);

        // EVENTOS
        btnEntrar.addActionListener(e -> login());

        btnRegistro.addActionListener(e -> new Registro().setVisible(true));

        btnSalir.addActionListener(e -> {
            int op = JOptionPane.showConfirmDialog(this, "¿Deseas salir del sistema?");
            if(op == 0){
                cerrarConAnimacion();
            }
        });

        ver.addActionListener(e -> {
            if(ver.isSelected()){
                txtPassword.setEchoChar((char)0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });
    }

    //  BOTONES PRO
    private JButton crearBoton(String texto, Color color){
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });

        return btn;
    }

    //  LOGIN (ARREGLADO)
    private void login() {

        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if(usuario.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this, "⚠️ Completa todos los campos");
            return;
        }

        try {
            Connection con = Conexion.conectar();

            if(con == null){
                JOptionPane.showMessageDialog(this, "❌ Error de conexión a la base de datos");
                return;
            }

            String sql = "SELECT * FROM usuarios WHERE usuario=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, password); 

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                JOptionPane.showMessageDialog(this, "✅ Bienvenido " + usuario);

                new Principal().setVisible(true);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "❌ Usuario o contraseña incorrectos");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    //  ANIMACIÓN AL CERRAR
    private void cerrarConAnimacion() {
        new Thread(() -> {
            try {
                for(float i = 1; i >= 0; i -= 0.05){
                    setOpacity(i);
                    Thread.sleep(30);
                }
                System.exit(0);
            } catch (Exception e) {
                System.exit(0);
            }
        }).start();
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}