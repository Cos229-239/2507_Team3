package com.ila.playSounds;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.ila.R;

public class PlaySounds {
    private static volatile PlaySounds instance;//volatile for fast synchronization
    private final Context context;
    private MediaPlayer mediaPlayer;

    private PlaySounds(Context context) //private constructor
    {
        this.context = context.getApplicationContext();
        preload();
    }
    public static PlaySounds getInstance(Context context)//double check locking singleton creation
    {
        if (instance == null)
            synchronized (PlaySounds.class)
            {
                if(instance == null)
                {
                    instance = new PlaySounds(context);
                }
            }
        return instance;
        //hi
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
    private void preload()
    {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release(); // Release resources
            }
        });
    }

}
