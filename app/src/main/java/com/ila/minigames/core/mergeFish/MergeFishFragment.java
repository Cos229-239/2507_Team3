package com.ila.minigames.core.mergeFish;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.ila.R;

public class MergeFishFragment extends Fragment {
    private GameView gameView;
    private ImageButton changeGameState;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState) {
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        View root = inflater.inflate(R.layout.merge_fish, container, false);

        // Find the container view in the inflated layout
        FrameLayout gameContainer = root.findViewById(R.id.game_container);

        // Create an instance of your custom GameView
        gameView = new GameView(getContext(), null);

        // Add the GameView to the FrameLayout
        gameContainer.addView(gameView);

        //buttons added after view to ensure usability
        changeGameState = root.findViewById(R.id.pause_button);
        changeGameState.setOnClickListener(v -> switchGameState());
        return root;
    }
    private void switchGameState()
    {
        if(gameView.getGameState())
        {
            pause_game();
        }else {
            play_game();
        }
    }

    private void play_game()
    {
        gameView.startGameLoop();
        changeGameState.setImageResource(R.drawable.pause);

    }
    private void pause_game()
    {
        gameView.stopGameLoop();
        changeGameState.setImageResource(R.drawable.play_button);
    }



}
