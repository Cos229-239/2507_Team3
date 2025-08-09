package com.ila.Sounds;

import android.content.Context;
import android.media.MediaPlayer;

public class PlaySounds {
    public static void playSound(Context context, int soundId)
    {
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(context, soundId);
        mediaPlayer.setVolume(25,25);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release(); // Release resources
            }
        });
    }
}
