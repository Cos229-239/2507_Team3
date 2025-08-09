package com.ila.playSounds;

import android.content.Context;
import android.media.MediaPlayer;

public class PlaySounds {
    private static PlaySounds instance;
    private final Context context;

    private PlaySounds(Context context)
    {
        this.context = context.getApplicationContext();
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release(); // Release resources
                instance = null;
            }
        });
    }
    public static synchronized PlaySounds getInstance(Context context) {
        if (instance == null) {
            instance = new PlaySounds(context);
        }
        return instance;

    }
    public void playSound(int soundId)
    {
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.setVolume(25,25);
        mediaPlayer.start();
    }
    public void playSound(Context context,int soundId, int volume)//mono audio volume
    {
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.setVolume(volume,volume);
        mediaPlayer.start();
    }
    public void playSound(Context context,int soundId, int volumeLeft,int volumeRight)//stereo
    {
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.setVolume(volumeLeft,volumeRight);
        mediaPlayer.start();
    }
}
