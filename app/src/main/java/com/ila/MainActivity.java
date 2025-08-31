package com.ila;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.ila.preferences.UserManager;
import com.ila.databinding.ActivityMainBinding;
import com.ila.R;


public class MainActivity extends AppCompatActivity {
    

    private UserManager userManager;
    private NavHostFragment navHostFragment;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        View view = binding.getRoot();
        ImageButton home_button = findViewById(R.id.home_button);
        home_button.setOnClickListener(v -> navigateHome());
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        // Initialize UserManager

        // Check if user is already logged in
    }
    private void navigateHome()
    {
        navController.navigate(R.id.action_to_Home);
    }
    private void checkLoginStatus() {
        // TEMPORARY: Bypass login for testing
        // TODO: Remove this bypass when Firebase is working
        // bypassLoginForTesting(); // Comment this out to enable role selection
        
        // Original login check (enabled for role selection)
        // Check if user is already logged in
        if (userManager.isLoggedIn()) {
            // User is logged in, navigate to appropriate screen based on user type
            String userType = userManager.getCurrentUserType();
                if ("student".equals(userType)) {
                    // Navigate to Home screen for students
                    navController.navigate(R.id.navigation_home);
                } else if ("teacher".equals(userType)) {
                    // Navigate to Teacher Dashboard for teachers
                    navController.navigate(R.id.navigation_home);

                }
            }
        }
        // If not logged in, stay on the role selection screen (default start destination)
        // User will need to select role and go through login process
    }