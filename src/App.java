// import pustaka gui java
import javax.swing.*;
import java.awt.*;

// class utama pengeksekusi program
public class App {
    // metode main utama program java
    public static void main(String[] args) {
        // membuat jendela utama jframe
        JFrame frame = new JFrame("FlappyBird - TP6 DPBO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // menggunakan cardlayout untuk pengatur navigasi antar panel
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // menginstansiasi objek logika dan tampilan game
        Logic logic = new Logic();
        View gameView = new View(logic);
        logic.setView(gameView);

        // membuat menu utama dan penanganan perpindahan layar
        MainMenu mainMenu = new MainMenu(() -> {
            cardLayout.show(mainPanel, "GAME");
            gameView.requestFocusInWindow();
            logic.startGame();
        });

        // mendaftarkan layar menu dan layar game ke panel utama
        mainPanel.add(mainMenu, "MENU");
        mainPanel.add(gameView, "GAME");

        // menampilkan jendela ke layar monitor
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}