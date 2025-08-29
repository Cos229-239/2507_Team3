package com.ila.ui.problems;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ila.R;
import com.ila.databinding.ProblemScreenBinding;
import com.ila.playSounds.PlaySounds;

import java.util.ArrayList;
import java.util.List;

public class ProblemFragment extends Fragment {

    private ProblemScreenBinding binding;
    private String lessonTitle;
    private int currentProblemIndex = 0;
    private List<MathProblem> problems;
    private int correctAnswers = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        
        binding = ProblemScreenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get lesson information from arguments
        if (getArguments() != null) {
            lessonTitle = getArguments().getString("lessonTitle", "Math Lesson");
        } else {
            lessonTitle = "Math Lesson";
        }

        // Initialize problems based on lesson
        initializeProblems();
        
        // Set up the problem screen
        setupProblemScreen();

        return root;
    }

    private void initializeProblems() {
        problems = new ArrayList<>();
        
        switch (lessonTitle) {
            case "Lesson 1: Adding Within 10":
                problems.add(new MathProblem("3 + 2 = ___", 5));
                problems.add(new MathProblem("5 + 4 = ___", 9));
                problems.add(new MathProblem("7 + 1 = ___", 8));
                problems.add(new MathProblem("2 + 6 = ___", 8));
                problems.add(new MathProblem("4 + 3 = ___", 7));
                break;
                
            case "Lesson 2: Subtracting Within 10":
                problems.add(new MathProblem("8 - 3 = ___", 5));
                problems.add(new MathProblem("9 - 4 = ___", 5));
                problems.add(new MathProblem("7 - 2 = ___", 5));
                problems.add(new MathProblem("6 - 1 = ___", 5));
                problems.add(new MathProblem("5 - 3 = ___", 2));
                break;
                
            case "Lesson 3: Adding Within 20":
                problems.add(new MathProblem("8 + 7 = ___", 15));
                problems.add(new MathProblem("9 + 6 = ___", 15));
                problems.add(new MathProblem("12 + 5 = ___", 17));
                problems.add(new MathProblem("11 + 8 = ___", 19));
                problems.add(new MathProblem("15 + 4 = ___", 19));
                break;
                
            case "Lesson 4: Subtracting Within 20":
                problems.add(new MathProblem("15 - 7 = ___", 8));
                problems.add(new MathProblem("18 - 9 = ___", 9));
                problems.add(new MathProblem("14 - 6 = ___", 8));
                problems.add(new MathProblem("16 - 8 = ___", 8));
                problems.add(new MathProblem("13 - 5 = ___", 8));
                break;
                
            case "Lesson 5: Addition Word Problems":
                problems.add(new MathProblem("Tom has 4 apples. He gets 3 more. How many does he have now?", 7));
                problems.add(new MathProblem("There are 6 birds in a tree. 2 more fly in. How many birds are there?", 8));
                problems.add(new MathProblem("Sara has 5 stickers. Her friend gives her 4 more. How many stickers does Sara have?", 9));
                problems.add(new MathProblem("There are 7 kids on the playground. 3 more kids join. How many kids are playing?", 10));
                problems.add(new MathProblem("Mike has 8 toy cars. He buys 2 more. How many toy cars does Mike have?", 10));
                break;
                
            case "Lesson 6: Subtraction Word Problems":
                problems.add(new MathProblem("There are 9 cookies on a plate. 4 cookies are eaten. How many cookies are left?", 5));
                problems.add(new MathProblem("Jenny has 12 crayons. She gives 5 to her sister. How many crayons does Jenny have left?", 7));
                problems.add(new MathProblem("There are 15 balloons. 7 balloons pop. How many balloons are left?", 8));
                problems.add(new MathProblem("Sam has 18 marbles. He loses 6 marbles. How many marbles does Sam have now?", 12));
                problems.add(new MathProblem("There are 20 students in class. 8 students are absent. How many students are present?", 12));
                break;
        }
    }

    private void setupProblemScreen() {
        // Set lesson title
        TextView lessonTitleView = binding.lessonTitle;
        lessonTitleView.setText(lessonTitle);

        // Set up buttons
        Button previousButton = binding.previousButton;
        Button nextButton = binding.nextButton;

        System.out.println("DEBUG: Setting up buttons");
        System.out.println("DEBUG: previousButton found: " + (previousButton != null));
        System.out.println("DEBUG: nextButton found: " + (nextButton != null));

        previousButton.setOnClickListener(v -> handlePreviousClick());
        nextButton.setOnClickListener(v -> handleNextClick());

        System.out.println("DEBUG: Button click listeners set up");

        // Display first problem
        displayCurrentProblem();
    }

    private void displayCurrentProblem() {
        if (problems.isEmpty() || currentProblemIndex >= problems.size()) {
            return;
        }

        MathProblem currentProblem = problems.get(currentProblemIndex);
        
        // Update problem number display
        TextView problemNumberView = binding.problemNumber;
        problemNumberView.setText("Problem " + (currentProblemIndex + 1) + " of " + problems.size());

        // Update problem text
        TextView problemTextView = binding.problemText;
        problemTextView.setText(currentProblem.getProblemText());

        // Clear previous input and feedback
        EditText answerInput = binding.answerInput;
        answerInput.setText("");
        
        TextView feedbackMessage = binding.feedbackMessage;
        feedbackMessage.setText("");

        // Update button states
        Button previousButton = binding.previousButton;
        Button nextButton = binding.nextButton;
        
        previousButton.setEnabled(currentProblemIndex > 0);
        // Always enable the next/finish button - it should be clickable
        nextButton.setEnabled(true);
        
        if (currentProblemIndex == problems.size() - 1) {
            nextButton.setText("Finish");
            System.out.println("DEBUG: Last problem reached, button text set to 'Finish'");
        } else {
            nextButton.setText("Next");
            System.out.println("DEBUG: Problem " + (currentProblemIndex + 1) + ", button text set to 'Next'");
        }
        
        System.out.println("DEBUG: Button states - Previous: " + previousButton.isEnabled() + ", Next/Finish: " + nextButton.isEnabled());
        System.out.println("DEBUG: Current problem index: " + currentProblemIndex + ", Total problems: " + problems.size());
    }

    private void handlePreviousClick() {
        if (currentProblemIndex > 0) {
            currentProblemIndex--;
            displayCurrentProblem();
            PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        }
    }

    private void handleNextClick() {
        System.out.println("DEBUG: handleNextClick called");
        System.out.println("DEBUG: Current problem index: " + currentProblemIndex);
        System.out.println("DEBUG: Total problems: " + problems.size());
        
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // First check the current answer
        if (!checkCurrentAnswer()) {
            // If answer is wrong, don't proceed - user needs to try again
            return;
        }
        
        // Answer is correct, proceed to next problem or finish
        if (currentProblemIndex < problems.size() - 1) {
            System.out.println("DEBUG: Moving to next problem");
            currentProblemIndex++;
            displayCurrentProblem();
        } else {
            System.out.println("DEBUG: Last problem reached, calling showFinalResults");
            // Show final results
            showFinalResults();
        }
    }

    private boolean checkCurrentAnswer() {
        EditText answerInput = binding.answerInput;
        String userAnswer = answerInput.getText().toString().trim();
        
        if (TextUtils.isEmpty(userAnswer)) {
            Toast.makeText(requireContext(), "Please enter an answer", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int userAnswerInt = Integer.parseInt(userAnswer);
            MathProblem currentProblem = problems.get(currentProblemIndex);
            
            TextView feedbackMessage = binding.feedbackMessage;
            
            if (userAnswerInt == currentProblem.getCorrectAnswer()) {
                feedbackMessage.setText("Correct! Great job! 🎉");
                feedbackMessage.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                if (!currentProblem.isAnswered()) {
                    correctAnswers++;
                    currentProblem.setAnswered(true);
                }
                return true;
            } else {
                feedbackMessage.setText("Try again! The correct answer is " + currentProblem.getCorrectAnswer());
                feedbackMessage.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                return false;
            }
            
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void showFinalResults() {
        int totalProblems = problems.size();
        double percentage = (double) correctAnswers / totalProblems * 100;
        
        // Add logging to debug navigation
        System.out.println("DEBUG: showFinalResults called");
        System.out.println("DEBUG: correctAnswers = " + correctAnswers);
        System.out.println("DEBUG: totalProblems = " + totalProblems);
        System.out.println("DEBUG: lessonTitle = " + lessonTitle);
        
        // Navigate to results screen with score information
        Bundle args = new Bundle();
        args.putString("lessonTitle", lessonTitle);
        args.putInt("correctAnswers", correctAnswers);
        args.putInt("totalProblems", totalProblems);
        
        System.out.println("DEBUG: About to navigate to Results");
        
        try {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            System.out.println("DEBUG: NavController found, navigating to action_Problems_to_Results");
            navController.navigate(R.id.action_Problems_to_Results, args);
            System.out.println("DEBUG: Navigation completed");
        } catch (Exception e) {
            System.out.println("DEBUG: Navigation error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Inner class to represent a math problem
    private static class MathProblem {
        private String problemText;
        private int correctAnswer;
        private boolean answered;

        public MathProblem(String problemText, int correctAnswer) {
            this.problemText = problemText;
            this.correctAnswer = correctAnswer;
            this.answered = false;
        }

        public String getProblemText() { return problemText; }
        public int getCorrectAnswer() { return correctAnswer; }
        public boolean isAnswered() { return answered; }
        public void setAnswered(boolean answered) { this.answered = answered; }
    }
}
