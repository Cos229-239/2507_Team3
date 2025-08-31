package com.ila.preferences;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ProgressReportManager {
    private static ProgressReportManager instance;
    private final PreferencesDataCarriage preferences;
    private static final String KEY_PROGRESS_REPORTS = "progress_reports";

    private ProgressReportManager(Context context) {
        preferences = new PreferencesDataCarriage(context, "progress_reports_prefs");
    }

    public static synchronized ProgressReportManager getInstance(Context context) {
        if (instance == null) {
            instance = new ProgressReportManager(context);
        }
        return instance;
    }

    public void saveProgressReport(ProgressReport report) {
        List<ProgressReport> reports = getAllProgressReports();
        reports.add(report);
        saveAllProgressReports(reports);
    }

    public List<ProgressReport> getAllProgressReports() {
        String json = preferences.getString(KEY_PROGRESS_REPORTS, "[]");
        List<ProgressReport> reports = new ArrayList<>();
        
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ProgressReport report = ProgressReport.fromJson(jsonObject);
                if (report != null) {
                    reports.add(report);
                }
            }
        } catch (JSONException e) {
            // If JSON parsing fails, return empty list
        }
        
        return reports;
    }

    public List<ProgressReport> getProgressReportsByStudent(String studentName) {
        List<ProgressReport> allReports = getAllProgressReports();
        List<ProgressReport> studentReports = new ArrayList<>();
        
        for (ProgressReport report : allReports) {
            if (report.getStudentName().equals(studentName)) {
                studentReports.add(report);
            }
        }
        
        return studentReports;
    }

    public List<ProgressReport> getProgressReportsBySubject(String subject) {
        List<ProgressReport> allReports = getAllProgressReports();
        List<ProgressReport> subjectReports = new ArrayList<>();
        
        for (ProgressReport report : allReports) {
            if (report.getLessonSubject().equals(subject)) {
                subjectReports.add(report);
            }
        }
        
        return subjectReports;
    }

    public List<ProgressReport> getProgressReportsByGradeLevel(String gradeLevel) {
        List<ProgressReport> allReports = getAllProgressReports();
        List<ProgressReport> gradeReports = new ArrayList<>();
        
        for (ProgressReport report : allReports) {
            if (report.getGradeLevel().equals(gradeLevel)) {
                gradeReports.add(report);
            }
        }
        
        return gradeReports;
    }

    public List<String> getAllStudentNames() {
        List<ProgressReport> allReports = getAllProgressReports();
        List<String> studentNames = new ArrayList<>();
        
        for (ProgressReport report : allReports) {
            if (!studentNames.contains(report.getStudentName())) {
                studentNames.add(report.getStudentName());
            }
        }
        
        return studentNames;
    }

    public List<String> getAllSubjects() {
        List<ProgressReport> allReports = getAllProgressReports();
        List<String> subjects = new ArrayList<>();
        
        for (ProgressReport report : allReports) {
            if (!subjects.contains(report.getLessonSubject())) {
                subjects.add(report.getLessonSubject());
            }
        }
        
        return subjects;
    }

    public List<String> getAllGradeLevels() {
        List<ProgressReport> allReports = getAllProgressReports();
        List<String> gradeLevels = new ArrayList<>();
        
        for (ProgressReport report : allReports) {
            if (!gradeLevels.contains(report.getGradeLevel())) {
                gradeLevels.add(report.getGradeLevel());
            }
        }
        
        return gradeLevels;
    }

    public void clearAllProgressReports() {
        preferences.saveString(KEY_PROGRESS_REPORTS, "[]");
    }

    public void deleteProgressReport(String reportId) {
        List<ProgressReport> reports = getAllProgressReports();
        reports.removeIf(report -> report.getReportId().equals(reportId));
        saveAllProgressReports(reports);
    }

    private void saveAllProgressReports(List<ProgressReport> reports) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (ProgressReport report : reports) {
                jsonArray.put(report.toJson());
            }
            preferences.saveString(KEY_PROGRESS_REPORTS, jsonArray.toString());
        } catch (Exception e) {
            // If JSON serialization fails, save empty array
            preferences.saveString(KEY_PROGRESS_REPORTS, "[]");
        }
    }

    // Get statistics for BackpackDash
    public int getTotalCompletedLessons() {
        return getAllProgressReports().size();
    }

    public int getTotalStudents() {
        return getAllStudentNames().size();
    }

    public double getAverageScore() {
        List<ProgressReport> reports = getAllProgressReports();
        if (reports.isEmpty()) return 0.0;
        
        double totalPercentage = 0;
        for (ProgressReport report : reports) {
            totalPercentage += report.getPercentage();
        }
        
        return totalPercentage / reports.size();
    }
}
