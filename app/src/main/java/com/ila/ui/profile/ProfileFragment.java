package com.ila.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ila.R;
import com.ila.databinding.FragmentProfileBinding;
import com.ila.playSounds.PlaySounds;
import com.ila.preferences.UserManager;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private UserManager userManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        // Initialize UserManager
        userManager = UserManager.getInstance(requireContext());

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProfile;
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Initialize profile information
        setupProfileInfo();
        
        // Set up button click listeners
        setupButtons();

        return root;
    }

    private void setupProfileInfo() {
        // Get user information from UserManager
        String username = userManager.getCurrentUsername();
        String userType = userManager.getCurrentUserType();

        // Update profile fields
        TextView profileName = binding.profileName;
        TextView profileUserType = binding.profileUserType;

        // Set username (for students) or instructor name (for teachers)
        if ("student".equals(userType)) {
            profileName.setText(username.isEmpty() ? "Student" : username);
        } else {
            profileName.setText("Instructor");
        }

        // Set user type with proper formatting
        String displayUserType = "student".equals(userType) ? "Student" : "Instructor";
        profileUserType.setText(displayUserType);
    }

    private void setupButtons() {
        Button changePasswordButton = binding.changePasswordButton;
        Button logoutButton = binding.buttonLogout;
        
        changePasswordButton.setOnClickListener(v -> handleChangePassword());
        logoutButton.setOnClickListener(v -> handleLogout());
    }

    private void handleChangePassword() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // TODO: Implement change password functionality
        Toast.makeText(requireContext(), "Change Password feature coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void handleLogout() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Clear stored credentials
        userManager.clearCredentials();
        
        // Show logout message
        Toast.makeText(requireContext(), getString(R.string.logoutSuccess), Toast.LENGTH_SHORT).show();
        
        // Navigate back to role selection screen
        androidx.navigation.NavController navController = androidx.navigation.Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_to_Identifier);
    }
}
