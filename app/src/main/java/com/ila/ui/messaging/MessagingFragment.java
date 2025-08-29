package com.ila.ui.messaging;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ila.R;
import com.ila.databinding.MessagingScreenBinding;
import com.ila.playSounds.PlaySounds;
import com.ila.preferences.UserManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessagingFragment extends Fragment {

    private MessagingScreenBinding binding;
    private UserManager userManager;
    private StringBuilder messageHistory;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        
        // Initialize UserManager
        userManager = UserManager.getInstance(requireContext());
        
        binding = MessagingScreenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize message history
        messageHistory = new StringBuilder();
        
        // Set up messaging functionality
        setupMessaging();

        return root;
    }

    private void setupMessaging() {
        Button sendButton = binding.sendButton;
        EditText messageInput = binding.messageInput;
        TextView messageList = binding.messageList;

        // Set up send button click listener
        sendButton.setOnClickListener(v -> handleSendMessage(messageInput, messageList));
        
        // Show welcome message
        showWelcomeMessage(messageList);
    }

    private void handleSendMessage(EditText messageInput, TextView messageList) {
        String message = messageInput.getText().toString().trim();
        
        if (message.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a message", Toast.LENGTH_SHORT).show();
            return;
        }

        // Play button sound
        PlaySounds.getInstance(requireContext()).playSound(R.raw.button_knock);
        
        // Get current timestamp
        String timestamp = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        
        // Get user info
        String userType = userManager.getCurrentUserType();
        String userName = userType.equals("student") ? 
            (userManager.getCurrentUsername().isEmpty() ? "Student" : userManager.getCurrentUsername()) :
            "Instructor";
        
        // Format the message
        String formattedMessage = String.format("[%s] %s: %s\n", timestamp, userName, message);
        
        // Add to message history
        messageHistory.append(formattedMessage);
        
        // Update the message list
        messageList.setText(messageHistory.toString());
        
        // Clear input field
        messageInput.setText("");
        
        // Show success message
        Toast.makeText(requireContext(), "Message sent!", Toast.LENGTH_SHORT).show();
    }

    private void showWelcomeMessage(TextView messageList) {
        String userType = userManager.getCurrentUserType();
        String welcomeMessage;
        
        if (userType.equals("student")) {
            welcomeMessage = "Welcome to messaging! You can send messages to your instructors here.\n\n";
        } else {
            welcomeMessage = "Welcome to messaging! You can send messages to your students here.\n\n";
        }
        
        messageHistory.append(welcomeMessage);
        messageList.setText(messageHistory.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
