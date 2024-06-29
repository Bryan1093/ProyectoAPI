package Model;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {
    private List<String> playlist;
    private AdvancedPlayer player;
    private Thread playerThread;

    public MusicPlayer() {
        this.playlist = new ArrayList<>();
        initializePlaylist();  // Aquí se inicializa la lista de reproducción
    }

    private void initializePlaylist() {
        playlist.add("C:\\Users\\bryan\\IdeaProjects\\Proyecto_API\\src\\res\\02. The Black.mp3");
        playlist.add("C:\\Users\\bryan\\IdeaProjects\\Proyecto_API\\src\\res\\09. Here I Am.mp3");
        // Agrega más canciones si es necesario
    }

    public void play() {
        if (playlist.isEmpty()) {
            System.out.println("No hay canciones en la lista de reproducción.");
            return;
        }

        if (player != null && playerThread != null && playerThread.isAlive()) {
            player.close();
            playerThread.interrupt();
        }

        playerThread = new Thread(() -> {
            try {
                for (String song : playlist) {
                    FileInputStream fileInputStream = new FileInputStream(song);
                    player = new AdvancedPlayer(fileInputStream);

                    System.out.println("Reproduciendo: " + song);
                    player.play();
                }
            } catch (JavaLayerException | IOException e) {
                e.printStackTrace();
            }
        });

        playerThread.start();
    }

    public void stop() {
        if (player != null) {
            player.close();
        }
        if (playerThread != null) {
            playerThread.interrupt();
        }
    }
}