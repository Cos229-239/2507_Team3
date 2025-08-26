package com.ila.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ila.R;
import com.ila.databinding.FragmentSettingsBinding;
import com.ila.playSounds.PlaySounds;
import com.ila.settings.SettingsHandler;

public class SettingsFragment extends Fragment{

    private FragmentSettingsBinding binding;
    private SettingsViewModel settingsViewModel;
    private SettingsHandler settingsHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        settingsHandler = new SettingsHandler(this.getContext());

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        // Set up dark mode button
        Button darkModeButton = binding.darkModeButton;
        darkModeButton.setOnClickListener(v -> handleDarkModeToggle());
        
        // Set up change password button
        Button changePasswordButton = binding.changePasswordButton;
        changePasswordButton.setOnClickListener(v -> handleChangePassword());

        return root;
    }

    private void handleDarkModeToggle() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Toggle dark mode
        settingsHandler.setNightMode();
        
        // Show feedback message
        Toast.makeText(requireContext(), "Dark mode toggled!", Toast.LENGTH_SHORT).show();
    }

    private void handleChangePassword() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // TODO: Implement change password functionality
        Toast.makeText(requireContext(), "Change Password feature coming soon!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}