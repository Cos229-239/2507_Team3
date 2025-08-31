package com.ila.ui.lessons;

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
import androidx.navigation.NavOptions;
import androidx.navigation.NavArgs;

import com.ila.R;
import com.ila.databinding.LessonsScreenBinding;
import com.ila.playSounds.PlaySounds;

public class LessonsFragment extends Fragment {

    private LessonsScreenBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        
        binding = LessonsScreenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up lesson buttons
        setupLessonButtons();

        return root;
    }

    private void setupLessonButtons() {
        // Lesson 1: Adding Within 10
        Button lesson1Button = binding.lesson1Button;
        lesson1Button.setOnClickListener(v -> handleLesson1Click());

        // Lesson 2: Subtracting Within 10
        Button lesson2Button = binding.lesson2Button;
        lesson2Button.setOnClickListener(v -> handleLesson2Click());

        // Lesson 3: Adding Within 20
        Button lesson3Button = binding.lesson3Button;
        lesson3Button.setOnClickListener(v -> handleLesson3Click());

        // Lesson 4: Subtracting Within 20
        Button lesson4Button = binding.lesson4Button;
        lesson4Button.setOnClickListener(v -> handleLesson4Click());

        // Lesson 5: Addition Word Problems
        Button lesson5Button = binding.lesson5Button;
        lesson5Button.setOnClickListener(v -> handleLesson5Click());

        // Lesson 6: Subtraction Word Problems
        Button lesson6Button = binding.lesson6Button;
        lesson6Button.setOnClickListener(v -> handleLesson6Click());
    }

    private void handleLesson1Click() {
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        navigateToProblems("Lesson 1: Adding Within 10");
    }

    private void handleLesson2Click() {
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        navigateToProblems("Lesson 2: Subtracting Within 10");
    }

    private void handleLesson3Click() {
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        navigateToProblems("Lesson 3: Adding Within 20");
    }

    private void handleLesson4Click() {
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        navigateToProblems("Lesson 4: Subtracting Within 20");
    }

    private void handleLesson5Click() {
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        navigateToProblems("Lesson 5: Addition Word Problems");
    }

    private void handleLesson6Click() {
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        navigateToProblems("Lesson 6: Subtraction Word Problems");
    }

    private void navigateToProblems(String lessonTitle) {
        // Create bundle with lesson information
        Bundle args = new Bundle();
        args.putString("lessonTitle", lessonTitle);
        
        // Navigate to problems screen with lesson argument
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_Lessons_to_Problems, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
