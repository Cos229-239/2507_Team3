package com.ila.minigames.core.mergeFish;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.content.Context;

import com.ila.R;

public class GameView extends View{
    ArrayList<Fish> fishlist;
    protected boolean running;
    private Paint paint;
    private Rect spriteBounds;
    public ArrayList<Fish> getFish(){return fishlist;}
    private Bitmap spriteBitmap; // Our image
    private int spriteX;
    private int spriteY;
    private final int spriteSize = 100;

        public GameView(Context context, AttributeSet attrs)
        {
            super(context, attrs);
            paint = new Paint();
            paint.setColor(Color.RED);
            spriteX = 0;
            spriteY = 0;
            spriteBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.clown_fish);
            spriteBounds = new Rect(spriteX, spriteY, spriteX + spriteSize, spriteY + spriteSize);
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            canvas.drawBitmap(spriteBitmap, null, spriteBounds, paint);
        }
        public void stopGame()
        {
        running = false;
        }
        public void nxtFrame(){
        int size = fishlist.size();
        for(int i = 0; i < size; i++){
            fishlist.get(i).swim();
        }
        invalidate();
        }
}
