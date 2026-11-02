// import pustaka gui dan pergerakan event java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// class penanganan logika fisika dan alur permainan
public class Logic implements ActionListener, KeyListener {
    // ukuran lebar dan tinggi layar permainan
    private int frameWidth = 360;
    private int frameHeight = 640;

    // atribut posisi dan ukuran burung awal
    private int playerStartPosX = frameWidth / 8;
    private int playerStartPosY = frameHeight / 2;
    private int playerWidth = 34;
    private int playerHeight = 24;

    // atribut ukuran pipa
    private int pipeStartPosX = frameWidth;
    private int pipeWidth = 64;
    private int pipeHeight = 512;

    // timer pengulang frame game dan gravitasi
    private Timer gameLoop;
    private Timer pipesCooldown;
    private int gravity = 1;

    // variabel penampung komponen pendukung
    private View view;
    private Player player;
    private ArrayList<Pipe> pipes;
    private Image birdImage, lowerPipeImage, upperPipeImage;

    // atribut status game over dan penampung skor
    private boolean gameOver = false;
    private int score = 0;
    private Sound soundManager;

    // konstruktor penyiapan logika awal permainan
    public Logic() {
        // membuat objek pemutar audio
        soundManager = new Sound();

        // memuat gambar aset dari folder
        birdImage = new ImageIcon(getClass().getResource("/assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("/assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("/assets/upperPipe.png")).getImage();

        // membuat objek burung dan array list pipa
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<>();

        // timer penambahan pipa baru setiap 1.5 detik
        pipesCooldown = new Timer(1500, e -> placePipes());
        // timer pengulang animasi game 60 frame per detik
        gameLoop = new Timer(1000 / 60, this);
    }

    // fungsi memulai permainan baru
    public void startGame() {
        resetGame();
    }

    // fungsi pembuat pasangan pipa secara acak
    public void placePipes() {
        // menghitung posisi y pipa atas secara acak
        int randomPosY = (int) (0 - (pipeHeight / 4) - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        // membuat pipa bagian atas
        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        // membuat pipa bagian bawah
        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + pipeHeight + openingSpace, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    // fungsi penggerak fisika objek dan aturan permainan
    public void move() {
        // jika game over maka hentikan pergerakan
        if (gameOver) return;

        // menerapkan efek gravitasi ke burung
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        // pergerakan pipa dan pemeriksaan kondisi
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());

            // penambahan skor +1 saat berhasil melewati sepasang pipa
            if (!pipe.isPassed() && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                pipe.setPassed(true);
                if (i % 2 == 0) {
                    score++;
                    soundManager.playSound("/assets/score.wav", 0.0f);
                }
            }

            // mengecek benturan antara burung dan pipa
            if (player.getBounds().intersects(pipe.getBounds())) {
                triggerGameOver();
            }
        }

        // mengecek kondisi burung jatuh menyentuh batas bawah layar
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            triggerGameOver();
        }
    }

    // fungsi penanganan kondisi game over
    private void triggerGameOver() {
        if (!gameOver) {
            gameOver = true;
            gameLoop.stop();
            pipesCooldown.stop();
            soundManager.stopBGM();

            // memutar suara benturan kalah dengan volume lebih keras (+5.0f db)
            soundManager.playSound("/assets/hit.wav", 5.0f);
        }
    }

    // fungsi mengulang kembali permainan dari awal
    public void resetGame() {
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);
        pipes.clear();
        score = 0;
        gameOver = false;

        pipesCooldown.start();
        gameLoop.start();
        soundManager.playBGM("/assets/bgm.wav");
    }

    // eksekusi perulangan frame utama
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if (view != null) {
            view.updateScore(score);
            view.repaint();
        }
    }

    // mendeteksi penekanan tombol keyboard
    @Override
    public void keyPressed(KeyEvent e) {
        // tombol spasi untuk melompat
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            player.setVelocityY(-10);

            // memutar suara lompat dengan volume lebih pelan (-18.0f db)
            soundManager.playSound("/assets/jump.wav", -18.0f);
        }

        // tombol r untuk restart setelah kalah
        if ((e.getKeyCode() == KeyEvent.VK_R || e.getKeyChar() == 'r' || e.getKeyChar() == 'R') && gameOver) {
            resetGame();
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    // mengatur acuan class tampilan
    public void setView(View view) { this.view = view; }
    // mengambil objek burung
    public Player getPlayer() { return player; }
    // mengambil daftar pipa
    public ArrayList<Pipe> getPipes() { return pipes; }
    // mengecek status kalah
    public boolean isGameOver() { return gameOver; }
    // mengambil nilai skor
    public int getScore() { return score; }
}