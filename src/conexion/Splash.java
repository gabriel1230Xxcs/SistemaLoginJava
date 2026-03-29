package conexion;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame {

    JProgressBar barra;
    JLabel texto;

    public Splash() {
        setSize(400, 250);
        setLocationRelativeTo(null);
        setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(20,20,20));
        panel.setLayout(new BorderLayout());

        texto = new JLabel("Cargando sistema...", JLabel.CENTER);
        texto.setForeground(Color.WHITE);
        texto.setFont(new Font("Segoe UI", Font.BOLD, 16));

        barra = new JProgressBar();
        barra.setBackground(Color.DARK_GRAY);
        barra.setForeground(new Color(0,150,255));
        barra.setBorderPainted(false);

        panel.add(texto, BorderLayout.CENTER);
        panel.add(barra, BorderLayout.SOUTH);

        add(panel);

        // 🔊 Sonido (opcional)
        reproducirSonido();

        // 🔥 Animación de carga
        cargar();
    }

    private void cargar() {
        new Thread(() -> {
            try {
                for(int i = 0; i <= 100; i++){
                    barra.setValue(i);
                    Thread.sleep(20);
                }

                // 🔥 Abre Login
                new Login().setVisible(true);
                dispose();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // 🔊 SONIDO (OPCIONAL)
    private void reproducirSonido() {
        try {
            javax.sound.sampled.AudioInputStream audio =
                javax.sound.sampled.AudioSystem.getAudioInputStream(new java.io.File("inicio.wav"));

            javax.sound.sampled.Clip clip =
                javax.sound.sampled.AudioSystem.getClip();

            clip.open(audio);
            clip.start();

        } catch (Exception e) {
            System.out.println("No se pudo reproducir sonido");
        }
    }
}