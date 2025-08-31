package com.ila.ui.BackpackDash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.ila.R;
import com.ila.databinding.TeacherDashboardScreenBinding;
import com.ila.playSounds.PlaySounds;

public class TeacherDashboardFragment extends Fragment {

    private TeacherDashboardScreenBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        

        
        binding = TeacherDashboardScreenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up button click listeners
        setupButtons();

        return root;
    }

    private void setupButtons() {
        Button manageClassesButton = binding.manageClassesButton;
        Button viewStudentsButton = binding.viewStudentsButton;
        Button uploadContentButton = binding.uploadContentButton;
        Button progressReportsButton = binding.progressReportsButton;
        Button messagesButton = binding.messagesButton;
        Button dashboardSettingsButton = binding.dashboardSettingsButton;

        manageClassesButton.setOnClickListener(v -> handleManageClasses());
        viewStudentsButton.setOnClickListener(v -> handleViewStudents());
        uploadContentButton.setOnClickListener(v -> handleUploadContent());
        progressReportsButton.setOnClickListener(v -> handleProgressReportsClick());
        messagesButton.setOnClickListener(v -> handleMessages());
        dashboardSettingsButton.setOnClickListener(v -> handleSettings());
    }

    private void handleManageClasses() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // TODO: Implement manage classes functionality
        Toast.makeText(requireContext(), "Manage Classes feature coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void handleViewStudents() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // TODO: Implement view students functionality
        Toast.makeText(requireContext(), "View Students feature coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void handleUploadContent() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // TODO: Implement upload content functionality
        Toast.makeText(requireContext(), "Upload Content feature coming soon!", Toast.LENGTH_SHORT).show();
    }


    
    private void handleMessages() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Navigate to Messaging screen
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_to_Messaging);
    }

    private void handleSettings() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Navigate to Settings screen
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_Home_to_Settings);
    }

    private void handleProgressReportsClick() {
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Navigate to progress reports screen
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
