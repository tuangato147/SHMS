package com.example.shms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private ImageView imgShowPassword;
    private Switch switchRemember;
    private Button btnLogin;
    private SessionManager sessionManager;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);

        // Khởi tạo SessionManager
        sessionManager = new SessionManager(this);

        // Ánh xạ các view
        edtUsername = findViewById(R.id.editTextText);
        edtPassword = findViewById(R.id.editTextText2);
        imgShowPassword = findViewById(R.id.imageView2);
        switchRemember = findViewById(R.id.switch1);
        btnLogin = findViewById(R.id.button2);

        // Kiểm tra và điền thông tin đã lưu
        if (sessionManager.isRememberMeEnabled()) {
            edtUsername.setText(sessionManager.getUsername());
            edtPassword.setText(sessionManager.getPassword());
            switchRemember.setChecked(true);
        }

        // Xử lý sự kiện ẩn/hiện mật khẩu
        imgShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        // Xử lý sự kiện đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Ẩn mật khẩu
            edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imgShowPassword.setImageResource(R.drawable.lock);
        } else {
            // Hiện mật khẩu
            edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imgShowPassword.setImageResource(R.drawable.unlock);
        }
        isPasswordVisible = !isPasswordVisible;
        edtPassword.setSelection(edtPassword.getText().length());
    }

    private void loginUser() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Log để debug
        Log.d("LoginActivity", "Attempting login with username: " + username);
        Log.d("LoginActivity", "Stored username: " + sessionManager.getUsername());
        Log.d("LoginActivity", "Stored password: " + sessionManager.getPassword());

        // Kiểm tra thông tin đăng nhập
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra thông tin đăng nhập với dữ liệu đã lưu
        if (username.equals(sessionManager.getUsername()) &&
                password.equals(sessionManager.getPassword())) {

            // Lưu trạng thái đăng nhập và remember me
            sessionManager.createLoginSession(username, password, switchRemember.isChecked());
            Log.d("LoginActivity", "Login successful for user: " + username);

            // Chuyển đến màn hình chính và xóa stack activity cũ
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Thông tin đăng nhập không chính xác", Toast.LENGTH_SHORT).show();
            Log.d("LoginActivity", "Login failed for user: " + username);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
