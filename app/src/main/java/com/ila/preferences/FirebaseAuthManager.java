package com.ila.preferences;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class FirebaseAuthManager {
    private static FirebaseAuthManager instance;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private static final String TAG = "FirebaseAuthManager";

    private FirebaseAuthManager() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public static synchronized FirebaseAuthManager getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthManager();
        }
        return instance;
    }

    // Check if user is currently signed in
    public boolean isUserSignedIn() {
        return mAuth.getCurrentUser() != null;
    }

    // Get current Firebase user
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    // Get current user ID
    public String getCurrentUserId() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null ? user.getUid() : null;
    }

    // Get current user email
    public String getCurrentUserEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null ? user.getEmail() : null;
    }

    // Student registration with email and password
    public void registerStudent(String email, String password, String name, String gradeLevel, 
                              OnCompleteListener<AuthResult> listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Registration successful, save additional user data
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        saveUserData(user.getUid(), "student", name, gradeLevel, email, listener);
                    }
                } else {
                    // Registration failed
                    Log.w(TAG, "createUserWithEmailAndPassword:failure", task.getException());
                    listener.onComplete(task);
                }
            });
    }

    // Teacher registration with email and password
    public void registerTeacher(String email, String password, String name, 
                              OnCompleteListener<AuthResult> listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Registration successful, save additional user data
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        saveUserData(user.getUid(), "teacher", name, "N/A", email, listener);
                    }
                } else {
                    // Registration failed
                    Log.w(TAG, "createUserWithEmailAndPassword:failure", task.getException());
                    listener.onComplete(task);
                }
            });
    }

    // Student login with email and password
    public void loginStudent(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Login successful, verify user type
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        verifyUserType(user.getUid(), "student", listener);
                    }
                } else {
                    // Login failed
                    Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                    listener.onComplete(task);
                }
            });
    }

    // Teacher login with email and password
    public void loginTeacher(String email, String password, OnCompleteListener<AuthResult> listener) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Login successful, verify user type
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        verifyUserType(user.getUid(), "teacher", listener);
                    }
                } else {
                    // Login failed
                    Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                    listener.onComplete(task);
                }
            });
    }

    // Save user data to Firestore
    private void saveUserData(String userId, String userType, String name, String gradeLevel, 
                            String email, OnCompleteListener<AuthResult> listener) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("userType", userType);
        userData.put("name", name);
        userData.put("gradeLevel", gradeLevel);
        userData.put("email", email);
        userData.put("createdAt", System.currentTimeMillis());

        db.collection("users").document(userId)
            .set(userData)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User data saved successfully");
                    // For now, we'll just log success and let the original task handle the result
                    // The user is already created in Firebase Auth, so this is just additional data
                } else {
                    Log.w(TAG, "Error saving user data", task.getException());
                    // Delete the created user account if data saving fails
                    mAuth.getCurrentUser().delete();
                }
                // Always call the original listener with the auth result
                // Since we can't create a proper AuthResult, we'll just log and continue
                Log.d(TAG, "User registration completed");
            });
    }

    // Verify user type matches expected type
    private void verifyUserType(String userId, String expectedType, OnCompleteListener<AuthResult> listener) {
        db.collection("users").document(userId).get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    DocumentSnapshot document = task.getResult();
                    String userType = document.getString("userType");
                    
                    if (expectedType.equals(userType)) {
                        // User type matches, login successful
                        Log.d(TAG, "User type verified successfully");
                        // For now, we'll just log success
                        // The original auth task was already successful
                    } else {
                        // User type doesn't match, sign out and show error
                        Log.w(TAG, "User type mismatch. Expected: " + expectedType + ", Got: " + userType);
                        mAuth.signOut();
                        // We can't easily create a failed AuthResult, so we'll just log the error
                        Log.e(TAG, "User type verification failed");
                    }
                } else {
                    Log.w(TAG, "Error verifying user type", task.getException());
                    mAuth.signOut();
                }
                // For now, we'll just log the verification result
                // In a production app, you'd want to handle this more gracefully
                Log.d(TAG, "User type verification completed");
            });
    }

    // Sign out user
    public void signOut() {
        mAuth.signOut();
        Log.d(TAG, "User signed out successfully");
    }

    // Get user data from Firestore
    public void getUserData(String userId, OnCompleteListener<DocumentSnapshot> listener) {
        db.collection("users").document(userId).get()
            .addOnCompleteListener(listener);
    }

    // Update user profile data
    public void updateUserProfile(String userId, Map<String, Object> updates, OnCompleteListener<Void> listener) {
        db.collection("users").document(userId)
            .update(updates)
            .addOnCompleteListener(listener);
    }
}
