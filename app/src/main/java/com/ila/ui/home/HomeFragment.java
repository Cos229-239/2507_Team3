package com.ila.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.ila.ButtonSound.buttonSound;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ila.R;
import com.ila.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        ImageButton BriButton = binding.buttonCourse;
        BriButton.setOnClickListener(v -> ButtonClicked(0));

        ImageButton settings_button = binding.buttonSettings;
        settings_button.setOnClickListener(v -> ButtonClicked(1));

        ImageButton notif_button = binding.buttonNotif;
        notif_button.setOnClickListener(v -> ButtonClicked(2));

        ImageButton profile_button = binding.buttonProfile;
        profile_button.setOnClickListener(v-> ButtonClicked(3));

        return root;
    }
    public void ButtonClicked(int i)
    {
        buttonSound.playButtonSound(this.getContext());
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        switch (i) {
            case 0:
                navController.navigate(R.id.action_Home_to_Dashboard);
                break;
            case 1:
                navController.navigate(R.id.action_Home_to_Settings);
                break;
            case 2:
                navController.navigate(R.id.action_Home_to_Notifications);
                break;
            case 3:
                navController.navigate(R.id.action_Home_to_Profile);
            default:
                return;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}