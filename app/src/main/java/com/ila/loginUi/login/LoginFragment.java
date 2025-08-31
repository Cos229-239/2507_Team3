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

import com.ila.R;
import com.ila.playSounds.PlaySounds;

public class LoginFragment extends Fragment {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button enterButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.login_screen, container, false);

        // Initialize UserManager

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

        // Get input values from the class fields (not local variables)
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Validate input
        if (username.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }


        // Convert username to email format for Firebase
        String email = username + "@gmail.com";
    }
}
