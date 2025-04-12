package com.example.shms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword, edtConfirmPassword;
    private Switch switchPolicy;
    private Button btnSignUp;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangki);

        // Khởi tạo SessionManager
        sessionManager = new SessionManager(this);

        // Ánh xạ các view
        edtUsername = findViewById(R.id.editTextText3);
        edtPassword = findViewById(R.id.editTextText33);
        edtConfirmPassword = findViewById(R.id.editTextText32);
        switchPolicy = findViewById(R.id.switch1);
        btnSignUp = findViewById(R.id.button4);

        // Xử lý sự kiện bật/tắt switch chính sách
        switchPolicy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showPolicyDialog();
            }
        });

        // Xử lý sự kiện đăng ký
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void showPolicyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chính sách bảo mật")
                .setMessage("1. Thông tin cá nhân của bạn sẽ được bảo mật\n\n" +
                        "2. Chúng tôi cam kết không chia sẻ thông tin với bên thứ ba\n\n" +
                        "3. Mọi thông tin sức khỏe được mã hóa và lưu trữ an toàn\n\n" +
                        "4. Bạn có quyền yêu cầu xóa thông tin cá nhân\n\n" +
                        "5. Chúng tôi sử dụng thông tin để cải thiện dịch vụ")
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setNegativeButton("Không đồng ý", (dialog, which) -> {
                    switchPolicy.setChecked(false);
                    dialog.dismiss();
                });
        builder.create().show();
    }

    private void registerUser() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        // Kiểm tra các trường dữ liệu
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!switchPolicy.isChecked()) {
            Toast.makeText(this, "Vui lòng đồng ý với chính sách bảo mật", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lưu thông tin đăng ký
        sessionManager.saveCredentials(username, password);
        Log.d("Register", "New user registered: " + username);

        // Chuyển đến màn hình đăng nhập
        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
}
