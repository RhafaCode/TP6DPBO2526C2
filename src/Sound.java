// import pustaka audio java
import javax.sound.sampled.*;
import java.net.URL;

// class pengelola efek suara dan musik
public class Sound {
    // variabel tempat menyimpan clip musik bgm
    private Clip bgmClip;

    // fungsi memutar efek suara dengan pengatur desibel volume
    public void playSound(String path, float volumeInDecibels) {
        try {
            // mengambil file suara dari direktori
            URL url = getClass().getResource(path);
            if (url != null) {
                // membaca data alur audio
                AudioInputStream ais = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);

                // mengatur volume jika fitur kontrol didukung
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float min = gainControl.getMinimum();
                    float max = gainControl.getMaximum();
                    float targetVolume = Math.max(min, Math.min(volumeInDecibels, max));
                    gainControl.setValue(targetVolume);
                }

                // membunyikan efek suara
                clip.start();
            }
        } catch (Exception e) {
            // menampilkan pesan error jika audio gagal dibaca
            System.err.println("gagal memutar audio: " + path);
        }
    }

    // fungsi pemutar efek suara dengan volume default
    public void playSound(String path) {
        playSound(path, 0.0f);
    }

    // fungsi memutar musik latar belakang secara terus menerus
    public void playBGM(String path) {
        try {
            // mengambil file lagu dari direktori
            URL url = getClass().getResource(path);
            if (url != null) {
                // membaca data alur musik
                AudioInputStream ais = AudioSystem.getAudioInputStream(url);
                bgmClip = AudioSystem.getClip();
                bgmClip.open(ais);

                // mengecilkan suara musik latar agar tidak menutupi efek
                if (bgmClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gainControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(-10.0f);
                }

                // memutar musik berulang tanpa henti
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            // menampilkan pesan error jika musik latar gagal dibaca
            System.err.println("gagal memutar bgm: " + path);
        }
    }

    // fungsi menghentikan musik latar belakang
    public void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
        }
    }
}