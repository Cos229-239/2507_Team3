package com.ila.ui.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ila.R;
import com.ila.databinding.ResultsScreenBinding;
import com.ila.playSounds.PlaySounds;
import com.ila.preferences.ProgressReport;
import com.ila.preferences.ProgressReportManager;
import com.ila.preferences.UserManager;

public class ResultsFragment extends Fragment {

    private ResultsScreenBinding binding;
    private String lessonTitle;
    private int correctAnswers;
    private int totalProblems;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        
        System.out.println("DEBUG: ResultsFragment onCreateView called");
        
        binding = ResultsScreenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get results information from arguments
        if (getArguments() != null) {
            lessonTitle = getArguments().getString("lessonTitle", "Math Lesson");
            correctAnswers = getArguments().getInt("correctAnswers", 0);
            totalProblems = getArguments().getInt("totalProblems", 5);
            
            System.out.println("DEBUG: ResultsFragment received arguments:");
            System.out.println("DEBUG: lessonTitle = " + lessonTitle);
            System.out.println("DEBUG: correctAnswers = " + correctAnswers);
            System.out.println("DEBUG: totalProblems = " + totalProblems);
        } else {
            lessonTitle = "Math Lesson";
            correctAnswers = 0;
            totalProblems = 5;
            System.out.println("DEBUG: ResultsFragment no arguments received, using defaults");
        }

        // Set up the results screen
        setupResultsScreen();
        System.out.println("DEBUG: ResultsFragment setup completed");

        return root;
    }

    private void setupResultsScreen() {
        // Set lesson title
        TextView lessonTitleView = binding.lessonTitle;
        lessonTitleView.setText(lessonTitle);

        // Set score display
        TextView scoreText = binding.scoreText;
        scoreText.setText(correctAnswers + " out of " + totalProblems);

        // Set correct vs incorrect display
        TextView correctIncorrectText = binding.correctIncorrectText;
        int incorrectAnswers = totalProblems - correctAnswers;
        correctIncorrectText.setText("Correct: " + correctAnswers + " | Incorrect: " + incorrectAnswers);

        // Set percentage display
        TextView percentageText = binding.percentageText;
        double percentage = (double) correctAnswers / totalProblems * 100;
        percentageText.setText(String.format("%.1f%%", percentage));

        // Set letter grade display
        TextView letterGradeText = binding.letterGradeText;
        String letterGrade = getLetterGrade(percentage);
        letterGradeText.setText("Grade: " + letterGrade);

        // Set congratulatory message based on score
        TextView congratsMessage = binding.congratsMessage;
        String message = getCongratulatoryMessage(percentage);
        congratsMessage.setText(message);

        // Automatically send progress report to teacher
        sendProgressReportToTeacher();

        // Set up buttons
        Button tryAgainButton = binding.tryAgainButton;
        Button backToLessonsButton = binding.backToLessonsButton;

        tryAgainButton.setOnClickListener(v -> handleTryAgainClick());
        backToLessonsButton.setOnClickListener(v -> handleBackToLessonsClick());
    }

    private void sendProgressReportToTeacher() {
        try {
            UserManager userManager = UserManager.getInstance(requireContext());
            ProgressReportManager reportManager = ProgressReportManager.getInstance(requireContext());
            
            String studentName = userManager.getCurrentUsername();
            String gradeLevel = userManager.getCurrentUserType().equals("student") ? 
                userManager.getCurrentGradeLevel() : "N/A";
            
            // Determine lesson subject from lesson title
            String lessonSubject = "Math"; // Default to Math for now
            if (lessonTitle.contains("Reading")) lessonSubject = "Reading";
            else if (lessonTitle.contains("Science")) lessonSubject = "Science";
            else if (lessonTitle.contains("Social Studies")) lessonSubject = "Social Studies";
            
            // Create and save progress report
            ProgressReport report = new ProgressReport(
                studentName, lessonTitle, correctAnswers, totalProblems, lessonSubject, gradeLevel
            );
            
            reportManager.saveProgressReport(report);
            
            System.out.println("DEBUG: Progress report sent to teacher for " + studentName);
            System.out.println("DEBUG: Report details: " + report.getPerformanceSummary());
            
        } catch (Exception e) {
            System.out.println("DEBUG: Error sending progress report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getCongratulatoryMessage(double percentage) {
        if (percentage == 100) {
            return "Perfect score! Outstanding! 🌟";
        } else if (percentage >= 97) {
            return "Excellent! Almost perfect! 🌟";
        } else if (percentage >= 93) {
            return "Outstanding work! You're amazing! 🌟";
        } else if (percentage >= 90) {
            return "Great job! You're doing fantastic! 🎉";
        } else if (percentage >= 87) {
            return "Very good work! Keep it up! 🎉";
        } else if (percentage >= 83) {
            return "Good job! You're learning well! 👍";
        } else if (percentage >= 80) {
            return "Well done! You're making progress! 👍";
        } else if (percentage >= 77) {
            return "Good effort! Keep practicing! 💪";
        } else if (percentage >= 73) {
            return "Not bad! More practice will help! 💪";
        } else if (percentage >= 70) {
            return "You're getting there! Keep trying! 💪";
        } else if (percentage >= 67) {
            return "Keep practicing! You can improve! 💪";
        } else if (percentage >= 63) {
            return "Don't give up! Practice makes perfect! 💪";
        } else if (percentage >= 60) {
            return "You're close! Keep working hard! 💪";
        } else {
            return "Keep trying! Every mistake is a learning opportunity! 💪";
        }
    }

    private String getLetterGrade(double percentage) {
        if (percentage >= 97) {
            return "A+";
        } else if (percentage >= 93) {
            return "A";
        } else if (percentage >= 90) {
            return "A-";
        } else if (percentage >= 87) {
            return "B+";
        } else if (percentage >= 83) {
            return "B";
        } else if (percentage >= 80) {
            return "B-";
        } else if (percentage >= 77) {
            return "C+";
        } else if (percentage >= 73) {
            return "C";
        } else if (percentage >= 70) {
            return "C-";
        } else if (percentage >= 67) {
            return "D+";
        } else if (percentage >= 63) {
            return "D";
        } else if (percentage >= 60) {
            return "D-";
        } else {
            return "F";
        }
    }

    private void handleTryAgainClick() {
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Navigate back to the same lesson problems
        Bundle args = new Bundle();
        args.putString("lessonTitle", lessonTitle);
        
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.action_Results_to_Problems, args);
    }

    private void handleBackToLessonsClick() {
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Navigate back to lessons screen
        requireActivity().onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
