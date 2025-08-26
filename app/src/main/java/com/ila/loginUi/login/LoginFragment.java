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
import com.ila.preferences.UserManager;

public class LoginFragment extends Fragment {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button enterButton;
    private UserManager userManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.login_screen, container, false);
        
        // Initialize UserManager
        userManager = UserManager.getInstance(requireContext());
        
        // Initialize views
        usernameInput = root.findViewById(R.id.username_input);
        passwordInput = root.findViewById(R.id.password_input);
        enterButton = root.findViewById(R.id.enter_button);
        
        // Set click listener for Enter button
        enterButton.setOnClickListener(v -> handleLogin());
        
        return root;
    }
    
    private void handleLogin() {
        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        
        // Basic validation
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.loginError), Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Authenticate user
        if (userManager.authenticateUser(username, "", password, "student")) {
            // Save credentials locally
            userManager.saveUserCredentials(username, "", password, "student");
            
            // Navigate to Home screen
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_to_Home);
            
            // Show success message
            Toast.makeText(requireContext(), getString(R.string.loginSuccess, username), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), getString(R.string.invalidCredentials), Toast.LENGTH_SHORT).show();
        }
    }
}
