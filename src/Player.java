// import pustaka grafis java
import java.awt.*;

// class model objek burung pemain
public class Player {
    // variabel atribut posisi dan ukuran burung
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;
    private int velocityY;

    // konstruktor pembuat objek burung
    public Player(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.velocityY = 0;
    }

    // mengambil area kotak objek untuk deteksi benturan
    public Rectangle getBounds() {
        return new Rectangle(posX, posY, width, height);
    }

    // mengambil posisi x
    public int getPosX() { return posX; }
    // mengatur posisi x
    public void setPosX(int posX) { this.posX = posX; }
    // mengambil posisi y
    public int getPosY() { return posY; }
    // mengatur posisi y
    public void setPosY(int posY) { this.posY = posY; }
    // mengambil lebar burung
    public int getWidth() { return width; }
    // mengambil tinggi burung
    public int getHeight() { return height; }
    // mengambil gambar burung
    public Image getImage() { return image; }
    // mengambil kecepatan jatuh
    public int getVelocityY() { return velocityY; }
    // mengatur kecepatan jatuh
    public void setVelocityY(int velocityY) { this.velocityY = velocityY; }
}