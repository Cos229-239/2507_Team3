package com.ila.ButtonSound;

import android.content.Context;
import android.media.MediaPlayer;

import com.ila.R;

public class buttonSound {
    public static void playButtonSound(Context context)
    {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.button_knock);
        mediaPlayer.start();
        mediaPlayer.release();
    }
}
