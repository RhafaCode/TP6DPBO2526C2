// import pustaka gui java
import javax.swing.*;
import java.awt.*;

// class penampil panel menu utama di awal program
public class MainMenu extends JPanel {
    // ukuran lebar dan tinggi layar menu
    private int width = 360;
    private int height = 640;
    private Image backgroundImage;
    private JButton startButton;
    private JButton exitButton;

    // konstruktor pembuat menu utama
    public MainMenu(Runnable onStartClick) {
        setPreferredSize(new Dimension(width, height));
        setLayout(null);

        // memuat gambar latar menu
        backgroundImage = new ImageIcon(getClass().getResource("/assets/background.png")).getImage();

        // label judul game
        JLabel titleLabel = new JLabel("FLAPPY BIRD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(30, 140, 300, 50);
        add(titleLabel);

        // tombol 1 untuk memulai permainan
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setBounds(100, 290, 160, 45);
        startButton.setFocusable(false);
        startButton.addActionListener(e -> onStartClick.run());
        add(startButton);

        // tombol 2 untuk keluar dari program
        exitButton = new JButton("Exit Game");
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setBounds(100, 360, 160, 45);
        exitButton.setFocusable(false);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }

    // menggambar latar belakang panel menu
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, width, height, null);
    }
}