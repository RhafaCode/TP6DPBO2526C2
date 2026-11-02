// import pustaka gui java
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// class kanvas penampil gambar visual dan jlabel skor
public class View extends JPanel {
    // ukuran lebar dan tinggi kanvas
    private int width = 360;
    private int height = 640;
    private Logic logic;
    private Image backgroundImage;
    private JLabel scoreLabel;

    // konstruktor penyiapan tampilan grafis
    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(width, height));
        setLayout(null);

        // memuat gambar latar belakang
        backgroundImage = new ImageIcon(getClass().getResource("/assets/background.png")).getImage();

        // membuat jlabel untuk menampilkan skor di layar
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 26));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(80, 20, 200, 40);
        add(scoreLabel);

        // mengaktifkan pendengar input tombol keyboard
        setFocusable(true);
        addKeyListener(logic);
    }

    // memperbarui tulisan skor pada jlabel
    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    // menggambar ulang isi komponen kanvas
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // fungsi pemicu gambar objek ke layar
    public void draw(Graphics g) {
        // menggambar latar belakang
        g.drawImage(backgroundImage, 0, 0, width, height, null);

        // menggambar burung pemain
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);
        }

        // menggambar seluruh pipa
        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (Pipe pipe : pipes) {
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        // menampilkan layar penanda game over dan tombol restart r
        if (logic.isGameOver()) {
            g.setColor(new Color(0, 0, 0, 160));
            g.fillRect(0, 0, width, height);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("GAME OVER", 80, 280);

            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("Tekan 'R' untuk Restart", 85, 330);
        }
    }
}