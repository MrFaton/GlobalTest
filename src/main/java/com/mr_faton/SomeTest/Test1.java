package com.mr_faton.SomeTest;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Mr_Faton on 11.09.2015.
 */
public class Test1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        final Player player = new Player();
        new Thread(new Runnable() {
            @Override
            public void run() {
                player.play();
            }
        }).start();

        Thread.sleep(20_000);
        player.stop();

        Thread.sleep(20_000);
    }
}

class Player {
    private static final String FILE = "Alarm.wav";
    private final AudioStream audioStream;

    public Player() throws IOException {

        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(FILE);
        audioStream = new AudioStream(inputStream);

    }

    public void play() {
        AudioPlayer.player.start(audioStream);
    }

    public void stop() {
        AudioPlayer.player.stop(audioStream);
    }
}