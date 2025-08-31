package com.ila.ui.home;

import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.ila.playSounds.PlaySounds;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ila.R;
import com.ila.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ImageButton backPack_button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        ImageButton games_button = binding.buttonGames;
        games_button.setOnClickListener(v -> ButtonClicked(1));

        ImageButton notif_button = binding.buttonNotif;
        notif_button.setOnClickListener(v -> ButtonClicked(2));

        ImageButton message_button = binding.buttonMessages;
        message_button.setOnClickListener(v -> ButtonClicked(3));

        ImageButton profile_button = binding.buttonProfile;
        profile_button.setOnClickListener(v-> ButtonClicked(4));

        backPack_button = binding.BackPack;
        backPack_button.setOnClickListener(v->ButtonClicked(0));


        return root;
    }
    public void ButtonClicked(int i)
    {
        PlaySounds.getInstance(this.getContext()).playSound(R.raw.button_knock);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        switch (i) {
            case 0:
                navController.navigate(R.id.action_to_Class_Selection);
                // TODO: Add navigation to your new content screen here
                // navController.navigate(R.id.action_Home_to_Dashboard);
                break;
            case 1:
                navController.navigate(R.id.toGames);
                break;
            case 2:
                navController.navigate(R.id.action_Home_to_Notifications);
                break;
            case 3:
                navController.navigate(R.id.action_Home_to_Messaging);
                break;
            case 4:
                navController.navigate(R.id.action_Home_to_Profile);
                break;

            default:
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}