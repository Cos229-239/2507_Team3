package com.ila.playSounds;

import android.content.Context;
import android.media.MediaPlayer;

public class PlaySounds {
    private static volatile PlaySounds instance;//volatile for fast syncronization
    private final Context context;
    private MediaPlayer mediaPlayer;

    private PlaySounds(Context context) //private constructor
    {
        this.context = context.getApplicationContext();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release(); // Release resources
            }
        });
    }
    public static PlaySounds getInstance(Context context)//double check locking singleton creation
    {
        if (instance == null)
            synchronized (PlaySounds.class)
            {
                if(instance == null)
                {
                    instance= new PlaySounds(context);
                }
            }
        return instance;

    }
    public void playSound(int soundId)
    {
        mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.setVolume(10,10);
        mediaPlayer.start();
    }
    public void playSound(Context context,int soundId, int volume)//mono audio volume
    {
        mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.setVolume(volume,volume);
        mediaPlayer.start();
    }
    public void playSound(Context context,int soundId, int volumeLeft,int volumeRight)//stereo
    {
        mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.setVolume(volumeLeft,volumeRight);
        mediaPlayer.start();
    }
}
