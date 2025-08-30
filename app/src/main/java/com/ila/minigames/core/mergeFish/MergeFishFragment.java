package com.ila.minigames.core.mergeFish;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.ila.R;

public class MergeFishFragment extends Fragment {
    private GameView gameView;
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

        return root;
    }

}
