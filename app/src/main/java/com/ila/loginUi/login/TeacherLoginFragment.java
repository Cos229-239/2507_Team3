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
import com.ila.playSounds.PlaySounds;

public class TeacherLoginFragment extends Fragment {

    private EditText emailInput;
    private EditText passwordInput;
    private Button enterButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.teacher_login_screen, container, false);
        
        // Initialize UserManager

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
        

        // TEMPORARY: Bypass Firebase for testing
        // TODO: Re-enable Firebase authentication when ready


        // Simulate successful login
        Toast.makeText(requireContext(), "Login successful! (Firebase bypassed)", Toast.LENGTH_SHORT).show();

        // Navigate to teacher dashboard
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
    }
}
