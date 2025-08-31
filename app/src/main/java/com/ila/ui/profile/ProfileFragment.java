package com.ila.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
        String gradeLevel = userManager.getCurrentGradeLevel();

        // Update profile fields
        EditText profileName = binding.profileName;
        Spinner profileGradeLevel = binding.profileGradeLevel;
        TextView profileUserType = binding.profileUserType;

        // Set up grade level spinner
        setupGradeLevelSpinner(profileGradeLevel, gradeLevel);

        // Set username (for students) or instructor name (for teachers)
        if ("student".equals(userType)) {
            profileName.setText(username.isEmpty() ? "Student" : username);
        } else {
            profileName.setText("Instructor");
            profileName.setEnabled(false); // Teachers can't change their name
        }

        // Set grade level (only for students)
        if ("student".equals(userType)) {
            profileGradeLevel.setEnabled(true);
        } else {
            profileGradeLevel.setEnabled(false); // Teachers don't have grade levels
        }

        // Set user type with proper formatting
        String displayUserType = "student".equals(userType) ? "Student" : "Instructor";
        profileUserType.setText(displayUserType);
    }

    private void setupGradeLevelSpinner(Spinner spinner, String currentGradeLevel) {
        // Create adapter for grade levels
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.grade_levels,
            android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set current selection
        String[] gradeLevels = getResources().getStringArray(R.array.grade_levels);
        for (int i = 0; i < gradeLevels.length; i++) {
            if (gradeLevels[i].equals(currentGradeLevel)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void setupButtons() {
        Button saveProfileButton = binding.saveProfileButton;
        Button logoutButton = binding.buttonLogout;
        Button settingsButton = binding.settingsButton;

        // Set up button click listeners
        saveProfileButton.setOnClickListener(v -> handleSaveProfile());
        logoutButton.setOnClickListener(v -> handleLogout());
        settingsButton.setOnClickListener(v -> handleSettings());
    }

    private void handleSaveProfile() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Get current values from fields
        EditText profileName = binding.profileName;
        Spinner profileGradeLevel = binding.profileGradeLevel;
        
        String newName = profileName.getText().toString().trim();
        String newGradeLevel = profileGradeLevel.getSelectedItem().toString();
        
        // Validate input
        if (newName.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Save profile information
        userManager.updateProfileInfo(newName, newGradeLevel);
        
        // Show success message
        Toast.makeText(requireContext(), "Profile saved successfully!", Toast.LENGTH_SHORT).show();
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
        navController.navigate(R.id.action_Profile_to_Identifier);
    }
    
    private void handleSettings() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Navigate to Settings screen
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_Profile_to_Settings);
    }
}
