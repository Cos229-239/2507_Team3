package com.ila.ui.progress;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.ila.R;
import com.ila.databinding.ProgressReportsScreenBinding;
import com.ila.preferences.ProgressReport;
import com.ila.preferences.ProgressReportManager;

import java.util.List;

public class ProgressReportsFragment extends Fragment {

    private ProgressReportsScreenBinding binding;
    private ProgressReportManager reportManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        
        binding = ProgressReportsScreenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        reportManager = ProgressReportManager.getInstance(requireContext());
        
        // Load and display progress reports
        loadProgressReports();

        return root;
    }

    private void loadProgressReports() {
        // Update statistics
        updateStatistics();
        
        // Get all student names
        List<String> studentNames = reportManager.getAllStudentNames();
        
        // Clear existing reports
        LinearLayout reportsContainer = binding.reportsContainer;
        reportsContainer.removeAllViews();
        
        if (studentNames.isEmpty()) {
            // Show no reports message
            TextView noReportsText = new TextView(requireContext());
            noReportsText.setText("No progress reports available yet.\nStudents need to complete lessons first.");
            noReportsText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            noReportsText.setTextColor(getResources().getColor(android.R.color.darker_gray));
            noReportsText.setGravity(android.view.Gravity.CENTER);
            noReportsText.setPadding(0, 50, 0, 0);
            reportsContainer.addView(noReportsText);
            return;
        }
        
        // Display reports for each student
        for (String studentName : studentNames) {
            addStudentReportsSection(studentName, reportsContainer);
        }
    }

    private void updateStatistics() {
        TextView totalStudentsText = binding.totalStudentsText;
        TextView totalLessonsText = binding.totalLessonsText;
        
        int totalStudents = reportManager.getTotalStudents();
        int totalLessons = reportManager.getTotalCompletedLessons();
        
        totalStudentsText.setText(String.valueOf(totalStudents));
        totalLessonsText.setText(String.valueOf(totalLessons));
    }

    private void addStudentReportsSection(String studentName, LinearLayout container) {
        // Get all reports for this student
        List<ProgressReport> studentReports = reportManager.getProgressReportsByStudent(studentName);
        
        // Create student header
        TextView studentHeader = new TextView(requireContext());
        studentHeader.setText(studentName);
        studentHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        studentHeader.setTypeface(null, android.graphics.Typeface.BOLD);
        studentHeader.setTextColor(getResources().getColor(R.color.teal_Light));
        studentHeader.setPadding(0, 30, 0, 15);
        container.addView(studentHeader);
        
        // Add each report for this student
        for (ProgressReport report : studentReports) {
            addReportCard(report, container);
        }
        
        // Add separator
        View separator = new View(requireContext());
        separator.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        separator.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 2));
        separator.setPadding(0, 20, 0, 20);
        container.addView(separator);
    }

    private void addReportCard(ProgressReport report, LinearLayout container) {
        // Create report card
        CardView reportCard = new CardView(requireContext());
        reportCard.setRadius(10f);
        reportCard.setCardElevation(5f);
        reportCard.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 
            LinearLayout.LayoutParams.WRAP_CONTENT));
        
        // Set card margins
        LinearLayout.LayoutParams cardParams = (LinearLayout.LayoutParams) reportCard.getLayoutParams();
        cardParams.setMargins(0, 10, 0, 10);
        reportCard.setLayoutParams(cardParams);
        
        // Create card content
        LinearLayout cardContent = new LinearLayout(requireContext());
        cardContent.setOrientation(LinearLayout.VERTICAL);
        cardContent.setPadding(20, 20, 20, 20);
        
        // Lesson title
        TextView lessonTitle = new TextView(requireContext());
        lessonTitle.setText(report.getLessonTitle());
        lessonTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        lessonTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        lessonTitle.setTextColor(getResources().getColor(android.R.color.black));
        lessonTitle.setPadding(0, 0, 0, 10);
        cardContent.addView(lessonTitle);
        
        // Performance details
        TextView performanceDetails = new TextView(requireContext());
        performanceDetails.setText(String.format("Score: %d/%d (%.1f%%) | Grade: %s", 
            report.getCorrectAnswers(), report.getTotalProblems(), 
            report.getPercentage(), report.getLetterGrade()));
        performanceDetails.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        performanceDetails.setTextColor(getResources().getColor(android.R.color.black));
        performanceDetails.setPadding(0, 0, 0, 5);
        cardContent.addView(performanceDetails);
        
        // Subject and grade level
        TextView subjectGrade = new TextView(requireContext());
        subjectGrade.setText(String.format("Subject: %s | Grade Level: %s", 
            report.getLessonSubject(), report.getGradeLevel()));
        subjectGrade.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        subjectGrade.setTextColor(getResources().getColor(android.R.color.darker_gray));
        subjectGrade.setPadding(0, 0, 0, 5);
        cardContent.addView(subjectGrade);
        
        // Completion date
        TextView completionDate = new TextView(requireContext());
        completionDate.setText("Completed: " + report.getFormattedDate());
        completionDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        completionDate.setTextColor(getResources().getColor(android.R.color.darker_gray));
        cardContent.addView(completionDate);
        
        reportCard.addView(cardContent);
        container.addView(reportCard);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
