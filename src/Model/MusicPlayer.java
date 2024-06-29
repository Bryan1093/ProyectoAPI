package Model;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import java.io.FileInputStream;

public class MusicPlayer {
    private AdvancedPlayer player;

    public void play(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            player = new AdvancedPlayer(fis);
            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (player != null) {
            player.close();
        }
    }
}
