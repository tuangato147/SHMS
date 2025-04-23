package com.example.shms.ui.activity;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.databinding.DataBindingUtil;

import com.example.shms.R;
import com.example.shms.databinding.DangnhapBinding;
import com.example.shms.ui.viewmodel.auth.LoginViewModel;

public class MainActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private DangnhapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize data binding
        binding = DataBindingUtil.setContentView(this, R.layout.dangnhap);

        // Initialize ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Set ViewModel cho binding
        binding.setViewModel(loginViewModel);
        binding.setLifecycleOwner(this);

        // Observe login state
        loginViewModel.getIsLoggedIn().observe(this, isLoggedIn -> {
            if (isLoggedIn) {
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe error messages
        loginViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}