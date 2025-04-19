package com.example.shms.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shms.R;
import com.example.shms.data.local.session.SessionManager;
import com.example.shms.ui.auth.login.LoginActivity;
import com.example.shms.ui.auth.register.RegisterActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvLogin;
    private Button btnEmailPhone;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);

        // Khởi tạo SessionManager
        sessionManager = new SessionManager(this);

        // Kiểm tra nếu đã đăng nhập thì chuyển thẳng vào MainMenuActivity
        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(MainActivity.this, MainMenuActivity.class));
            finish();
            return;
        }

        // Ánh xạ các view
        tvLogin = findViewById(R.id.textView6);
        btnEmailPhone = findViewById(R.id.button3);

        // Sửa lại phần này - Click vào textView6 sẽ chuyển sang RegisterActivity
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                Log.d("MainActivity", "Navigating to RegisterActivity");
            }
        });

        // Click vào button "I'll use email or phone" sẽ chuyển sang LoginActivity
        btnEmailPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                Log.d("MainActivity", "Navigating to LoginActivity");
            }
        });
    }
}
