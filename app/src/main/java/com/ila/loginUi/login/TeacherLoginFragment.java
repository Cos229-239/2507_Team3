package com.ila.loginUi.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ila.R;
import com.ila.databinding.TeacherLoginScreenBinding;
import com.ila.playSounds.PlaySounds;
import com.ila.preferences.UserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class TeacherLoginFragment extends Fragment {

    private EditText emailInput;
    private EditText passwordInput;
    private Button enterButton;
    private UserManager userManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.teacher_login_screen, container, false);
        
        // Initialize UserManager
        userManager = UserManager.getInstance(requireContext());
        
        // Initialize views
        emailInput = root.findViewById(R.id.teacher_email_input);
        passwordInput = root.findViewById(R.id.teacher_password_input);
        enterButton = root.findViewById(R.id.teacher_enter_button);
        
        // Set click listener for Enter button
        enterButton.setOnClickListener(v -> handleLogin());
        
        return root;
    }
    
    private void handleLogin() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Get input values from the class fields (not local variables)
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        
        // Validate input
        if (email.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (password.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Basic email validation
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Show loading state
        enterButton.setEnabled(false);
        enterButton.setText("Logging in...");
        
        // Use Firebase Authentication
        userManager.loginTeacher(email, password, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Re-enable button
                enterButton.setEnabled(true);
                enterButton.setText("Enter");
                
                if (task.isSuccessful()) {
                    // Login successful
                    Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                    
                    // Navigate to teacher BackpackDash
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                    navController.navigate(R.id.action_to_TeacherDashboard);
                } else {
                    // Login failed
                    String errorMessage = "Login failed";
                    if (task.getException() != null) {
                        String exceptionMessage = task.getException().getMessage();
                        if (exceptionMessage != null) {
                            if (exceptionMessage.contains("password")) {
                                errorMessage = "Incorrect password";
                            } else if (exceptionMessage.contains("no user record")) {
                                errorMessage = "User not found. Please register first.";
                            } else if (exceptionMessage.contains("Invalid user type")) {
                                errorMessage = "This account is not a teacher account";
                            } else {
                                errorMessage = exceptionMessage;
                            }
                        }
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
