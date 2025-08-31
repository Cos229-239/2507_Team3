package com.ila.ui.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.NavOptions;
import androidx.navigation.NavArgs;

import com.ila.R;
import com.ila.databinding.FragmentClassesBinding;
import com.ila.playSounds.PlaySounds;

public class ClassesFragment extends Fragment {

    private FragmentClassesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ClassesViewModel classesViewModel =
                new ViewModelProvider(this).get(ClassesViewModel.class);

        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up subject buttons
        setupSubjectButtons();

        return root;
    }

    private void setupSubjectButtons() {
        Button mathButton = binding.mathButton;
        Button readingButton = binding.readingButton;
        Button scienceButton = binding.scienceButton;
        Button socialStudiesButton = binding.socialStudiesButton;

        // Set up click listeners
        mathButton.setOnClickListener(v -> handleMathSubject());
        readingButton.setOnClickListener(v -> handleReadingSubject());
        scienceButton.setOnClickListener(v -> handleScienceSubject());
        socialStudiesButton.setOnClickListener(v -> handleSocialStudiesSubject());
    }

    private void handleMathSubject() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Navigate to Math lessons screen
        navigateToLessons("Math");
    }

    private void handleReadingSubject() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Navigate to Reading lessons screen
        navigateToLessons("Reading");
    }

    private void handleScienceSubject() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Navigate to Science lessons screen
        navigateToLessons("Science");
    }

    private void handleSocialStudiesSubject() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Navigate to Social Studies lessons screen
        navigateToLessons("Social Studies");
    }

    private void navigateToLessons(String subject) {
        // Create bundle with subject information
        Bundle args = new Bundle();
        args.putString("subject", subject);
        
        // Navigate to lessons screen with subject argument
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_to_Lessons, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}