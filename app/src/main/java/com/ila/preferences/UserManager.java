package com.ila.preferences;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;
import androidx.annotation.NonNull;

public class UserManager {
    private static UserManager instance;
    private PreferencesDataCarriage preferences;
    private FirebaseAuthManager firebaseAuth;
    private static final String TAG = "UserManager";

    // Keys for local preferences (fallback)
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_USER_TYPE = "user_type";
    private static final String KEY_GRADE_LEVEL = "grade_level";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private UserManager(Context context) {
        preferences = new PreferencesDataCarriage(context, "user_prefs");
        firebaseAuth = FirebaseAuthManager.getInstance();
    }

    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context);
        }
        return instance;
    }

    // Check if user is logged in (Firebase or local)
    public boolean isLoggedIn() {
        // First check Firebase
        if (firebaseAuth.isUserSignedIn()) {
            return true;
        }
        // Fallback to local storage
        return preferences.getBool(KEY_IS_LOGGED_IN, false);
    }

    // Get current username (Firebase or local)
    public String getCurrentUsername() {
        // First try to get from Firebase
        if (firebaseAuth.isUserSignedIn()) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null && user.getEmail() != null) {
                // Extract username from email (everything before @)
                String email = user.getEmail();
                return email.substring(0, email.indexOf('@'));
            }
        }
        // Fallback to local storage
        return preferences.getString(KEY_USERNAME, "");
    }

    // Get current user type (Firebase or local)
    public String getCurrentUserType() {
        // First try to get from Firebase
        if (firebaseAuth.isUserSignedIn()) {
            String userId = firebaseAuth.getCurrentUserId();
            if (userId != null) {
                // This will be handled asynchronously, for now return local
                // TODO: Implement proper Firebase user type retrieval
            }
        }
        // Fallback to local storage
        return preferences.getString(KEY_USER_TYPE, "");
    }

    // Get current grade level (Firebase or local)
    public String getCurrentGradeLevel() {
        // First try to get from Firebase
        if (firebaseAuth.isUserSignedIn()) {
            String userId = firebaseAuth.getCurrentUserId();
            if (userId != null) {
                // This will be handled asynchronously, for now return local
                // TODO: Implement proper Firebase grade level retrieval
            }
        }
        // Fallback to local storage
        return preferences.getString(KEY_GRADE_LEVEL, "1st Grade");
    }

    // Firebase Authentication Methods

    // Student registration
    public void registerStudent(String email, String password, String name, String gradeLevel, 
                              OnCompleteListener<AuthResult> listener) {
        firebaseAuth.registerStudent(email, password, name, gradeLevel, listener);
    }

    // Teacher registration
    public void registerTeacher(String email, String password, String name, 
                              OnCompleteListener<AuthResult> listener) {
        firebaseAuth.registerTeacher(email, password, name, listener);
    }

    // Student login
    public void loginStudent(String email, String password, OnCompleteListener<AuthResult> listener) {
        firebaseAuth.loginStudent(email, password, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Login successful, also save to local storage as backup
                    String username = email.substring(0, email.indexOf('@'));
                    saveUserCredentials(username, password, "student", "1st Grade");
                }
                // Pass the result to the original listener
                listener.onComplete(task);
            }
        });
    }

    // Teacher login
    public void loginTeacher(String email, String password, OnCompleteListener<AuthResult> listener) {
        firebaseAuth.loginTeacher(email, password, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Login successful, also save to local storage as backup
                    saveUserCredentials("Instructor", password, "teacher", "N/A");
                }
                // Pass the result to the original listener
                listener.onComplete(task);
            }
        });
    }

    // Sign out
    public void signOut() {
        // Sign out from Firebase
        firebaseAuth.signOut();
        
        // Clear local storage as well
        clearCredentials();
        
        Log.d(TAG, "User signed out from both Firebase and local storage");
    }

    // Update profile information
    public void updateProfileInfo(String name, String gradeLevel) {
        // Update Firebase if user is signed in
        if (firebaseAuth.isUserSignedIn()) {
            String userId = firebaseAuth.getCurrentUserId();
            if (userId != null) {
                Map<String, Object> updates = new HashMap<>();
                updates.put("name", name);
                updates.put("gradeLevel", gradeLevel);
                
                firebaseAuth.updateUserProfile(userId, updates, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Profile updated in Firebase successfully");
                        } else {
                            Log.w(TAG, "Error updating profile in Firebase", task.getException());
                        }
                    }
                });
            }
        }
        
        // Also update local storage as backup
        preferences.saveString(KEY_USERNAME, name);
        preferences.saveString(KEY_GRADE_LEVEL, gradeLevel);
    }

    // Legacy local storage methods (for backward compatibility)

    // Save user credentials locally (fallback)
    public void saveUserCredentials(String username, String password, String userType, String gradeLevel) {
        preferences.saveString(KEY_USERNAME, username);
        preferences.saveString(KEY_PASSWORD, password);
        preferences.saveString(KEY_USER_TYPE, userType);
        preferences.saveString(KEY_GRADE_LEVEL, gradeLevel);
        preferences.saveBool(KEY_IS_LOGGED_IN, true);
    }

    // Clear all stored credentials
    public void clearCredentials() {
        preferences.saveString(KEY_USERNAME, "");
        preferences.saveString(KEY_PASSWORD, "");
        preferences.saveString(KEY_USER_TYPE, "");
        preferences.saveString(KEY_GRADE_LEVEL, "");
        preferences.saveBool(KEY_IS_LOGGED_IN, false);
    }

    // Get stored password (for local fallback)
    public String getStoredPassword() {
        return preferences.getString(KEY_PASSWORD, "");
    }

    // Check if using Firebase or local storage
    public boolean isUsingFirebase() {
        return firebaseAuth.isUserSignedIn();
    }

    // Get Firebase user ID
    public String getFirebaseUserId() {
        return firebaseAuth.getCurrentUserId();
    }

    // Get Firebase user email
    public String getFirebaseUserEmail() {
        return firebaseAuth.getCurrentUserEmail();
    }
}
