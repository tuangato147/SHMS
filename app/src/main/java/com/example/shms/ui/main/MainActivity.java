package com.example.shms.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.NavigationUI;

import com.example.shms.R;
import com.example.shms.data.local.session.SessionManager;
import com.example.shms.ui.auth.login.LoginActivity;
import com.example.shms.ui.auth.register.RegisterActivity;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup navigation controller
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        // Setup navigation graph based on user role
        setupNavigationForRole();

        // Setup action bar
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.doctorMainFragment,
                R.id.staffMainFragment,
                R.id.patientMainFragment
        ).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void setupNavigationForRole() {
        String userRole = SharedPrefs.getUserRole(this);
        int graphResId;

        switch (userRole) {
            case "DOCTOR":
                graphResId = R.navigation.nav_doctor;
                break;
            case "STAFF":
                graphResId = R.navigation.nav_staff;
                break;
            case "PATIENT":
                graphResId = R.navigation.nav_patient;
                break;
            default:
                // Redirect to login if role is invalid
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return;
        }

        navController.setGraph(graphResId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
