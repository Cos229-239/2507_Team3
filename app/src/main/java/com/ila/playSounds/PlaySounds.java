package com.ila.playSounds;

import android.content.Context;
import android.media.MediaPlayer;

public class PlaySounds {
    public static void playSound(Context context, int soundId) //just play something
    {
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.setVolume(25, 25);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release(); // Release resources
            }
        });
    }
    public static void playSound(Context context, int soundId, int volume)//mono audio volume
    {
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.setVolume(volume,volume);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release(); // Release resources
            }
        });
    }
    public static void playSound(Context context, int soundId, int volumeLeft,int volumeRight)//stereo
    {
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.setVolume(volumeLeft,volumeRight);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release(); // Release resources
            }
        });
    }
}
