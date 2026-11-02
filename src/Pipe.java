// import pustaka grafis java
import java.awt.*;

// class model objek pipa rintangan
public class Pipe {
    // variabel atribut posisi dan ukuran pipa
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;
    private int velocityX;
    private boolean passed;

    // konstruktor pembuat objek pipa
    public Pipe(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        // kecepatan pipa bergeser ke kiri sebesar 2 piksel
        this.velocityX = -2;
        this.passed = false;
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
    // mengambil lebar pipa
    public int getWidth() { return width; }
    // mengambil tinggi pipa
    public int getHeight() { return height; }
    // mengambil gambar pipa
    public Image getImage() { return image; }
    // mengambil kecepatan pipa bergeser
    public int getVelocityX() { return velocityX; }
    // mengecek status apakah pipa sudah dilewati
    public boolean isPassed() { return passed; }
    // mengubah status pipa sudah dilewati
    public void setPassed(boolean passed) { this.passed = passed; }
}