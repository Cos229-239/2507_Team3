package com.ila.preferences;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

public class ProgressReport {
    private String studentName;
    private String lessonTitle;
    private int correctAnswers;
    private int totalProblems;
    private double percentage;
    private String letterGrade;
    private String lessonSubject;
    private String gradeLevel;
    private Date completionDate;
    private String reportId;

    public ProgressReport(String studentName, String lessonTitle, int correctAnswers, 
                        int totalProblems, String lessonSubject, String gradeLevel) {
        this.studentName = studentName;
        this.lessonTitle = lessonTitle;
        this.correctAnswers = correctAnswers;
        this.totalProblems = totalProblems;
        this.lessonSubject = lessonSubject;
        this.gradeLevel = gradeLevel;
        this.percentage = (double) correctAnswers / totalProblems * 100;
        this.letterGrade = calculateLetterGrade(this.percentage);
        this.completionDate = new Date();
        this.reportId = generateReportId();
    }

    private String calculateLetterGrade(double percentage) {
        if (percentage >= 97) return "A+";
        else if (percentage >= 93) return "A";
        else if (percentage >= 90) return "A-";
        else if (percentage >= 87) return "B+";
        else if (percentage >= 83) return "B";
        else if (percentage >= 80) return "B-";
        else if (percentage >= 77) return "C+";
        else if (percentage >= 73) return "C";
        else if (percentage >= 70) return "C-";
        else if (percentage >= 67) return "D+";
        else if (percentage >= 63) return "D";
        else if (percentage >= 60) return "D-";
        else return "F";
    }

    private String generateReportId() {
        return studentName + "_" + lessonTitle.replace(" ", "_") + "_" + System.currentTimeMillis();
    }

    // JSON serialization
    public JSONObject toJson() {
        try {
            JSONObject json = new JSONObject();
            json.put("studentName", studentName);
            json.put("lessonTitle", lessonTitle);
            json.put("correctAnswers", correctAnswers);
            json.put("totalProblems", totalProblems);
            json.put("percentage", percentage);
            json.put("letterGrade", letterGrade);
            json.put("lessonSubject", lessonSubject);
            json.put("gradeLevel", gradeLevel);
            json.put("completionDate", completionDate.getTime());
            json.put("reportId", reportId);
            return json;
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    // JSON deserialization
    public static ProgressReport fromJson(JSONObject json) {
        try {
            String studentName = json.getString("studentName");
            String lessonTitle = json.getString("lessonTitle");
            int correctAnswers = json.getInt("correctAnswers");
            int totalProblems = json.getInt("totalProblems");
            String lessonSubject = json.getString("lessonSubject");
            String gradeLevel = json.getString("gradeLevel");
            
            ProgressReport report = new ProgressReport(studentName, lessonTitle, correctAnswers, 
                                                    totalProblems, lessonSubject, gradeLevel);
            
            // Set additional fields that aren't in constructor
            report.percentage = json.getDouble("percentage");
            report.letterGrade = json.getString("letterGrade");
            report.completionDate = new Date(json.getLong("completionDate"));
            report.reportId = json.getString("reportId");
            
            return report;
        } catch (JSONException e) {
            return null;
        }
    }

    // Getters
    public String getStudentName() { return studentName; }
    public String getLessonTitle() { return lessonTitle; }
    public int getCorrectAnswers() { return correctAnswers; }
    public int getTotalProblems() { return totalProblems; }
    public double getPercentage() { return percentage; }
    public String getLetterGrade() { return letterGrade; }
    public String getLessonSubject() { return lessonSubject; }
    public String getGradeLevel() { return gradeLevel; }
    public Date getCompletionDate() { return completionDate; }
    public String getReportId() { return reportId; }

    // Format date for display
    public String getFormattedDate() {
        return String.format("%d/%d/%d", 
            completionDate.getMonth() + 1, 
            completionDate.getDate(), 
            completionDate.getYear() + 1900);
    }

    // Get performance summary
    public String getPerformanceSummary() {
        return String.format("%s - %s (%d/%d, %.1f%%, Grade: %s)", 
            lessonSubject, lessonTitle, correctAnswers, totalProblems, percentage, letterGrade);
    }
}
