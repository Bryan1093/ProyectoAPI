package Model;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class MusicPlayer {
    private AdvancedPlayer player;
    private List<String> playlist;
    private int currentSongIndex;

    public MusicPlayer() {
        this.currentSongIndex = 0;
    }

    public void setPlaylist(List<String> playlist) {
        this.playlist = playlist;
    }

    public void play() {
        if (playlist == null || playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }

        String currentSong = playlist.get(currentSongIndex);
        play(currentSong);

        // Listener para avanzar a la siguiente canción al terminar la actual
        player.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackFinished(PlaybackEvent evt) {
                nextSong();
            }
        });
    }

    private void play(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            player = new AdvancedPlayer(fis);
            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (player != null) {
            player.close();
        }
    }

    private void nextSong() {
        currentSongIndex++;
        if (currentSongIndex < playlist.size()) {
            play(playlist.get(currentSongIndex));
        } else {
            System.out.println("Playlist ended.");
        }
    }
}