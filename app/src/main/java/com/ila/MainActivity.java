package com.ila;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.ila.databinding.ActivityMainBinding;
import com.ila.preferences.UserManager;



public class MainActivity extends AppCompatActivity {
    
    private UserManager userManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        

        

        
        // Initialize UserManager
        userManager = UserManager.getInstance(this);
        
        // Check if user is already logged in
        checkLoginStatus();
    }
    
    private void checkLoginStatus() {
        // Check if user is already logged in
        if (userManager.isLoggedIn()) {
            // User is logged in, navigate to appropriate screen based on user type
            String userType = userManager.getCurrentUserType();
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment_activity_main);
            
            if (navHostFragment != null) {
                NavController navController = navHostFragment.getNavController();
                
                if ("student".equals(userType)) {
                    // Navigate to Home screen for students
                    navController.navigate(R.id.navigation_home);
                } else if ("teacher".equals(userType)) {
                    // Navigate to Teacher Dashboard for teachers
                    navController.navigate(R.id.navigation_TeacherDashboard);
                }
            }
        }
        // If not logged in, stay on the role selection screen (default start destination)
        // User will need to select role and go through login process
    }
    

}