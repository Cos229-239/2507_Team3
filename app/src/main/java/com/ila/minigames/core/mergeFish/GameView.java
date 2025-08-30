package com.ila.minigames.core.mergeFish;
import java.util.ArrayList;
import android.os.Handler;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.view.ViewTreeObserver;

import com.ila.R;

public class GameView extends View{
    private Paint paint;
    private Rect spriteBounds;

    private Bitmap spriteBitmap; // Our image
    private final int spriteSize = 100;

    private boolean isLooping = false;
    private Handler handler;
    private Runnable gameLoop;

    private final long frameRate = 16L; // Approximately 60 frames per second (1000/60)


        public GameView(Context context, AttributeSet attrs) {
            super(context, attrs);

            paint = new Paint();
            paint.setColor(Color.RED);
            spriteBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.clown_fish);
            spriteBounds = new Rect(0, 0, spriteSize, spriteSize);
            handler = new Handler(Looper.getMainLooper());
            gameLoop = new Runnable() {
                @Override
                public void run() {
                    if (isAttachedToWindow() && isLooping) {
                        updateGameState();
                        invalidate();
                        handler.postDelayed(this, frameRate);
                    }
                }
            };
        }
        @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw the bitmap onto the canvas within the specified bounds
        canvas.drawBitmap(spriteBitmap, null, spriteBounds, paint);
    }
    public void startGameLoop() {
        if (!isLooping) {
            isLooping = true;
            handler.post(gameLoop);
        }
    }

    public void stopGameLoop() {
        if (isLooping) {
            isLooping = false;
            handler.removeCallbacks(gameLoop);
        }
    }
    public void updateGameState()
    {
        return;
    }

}
