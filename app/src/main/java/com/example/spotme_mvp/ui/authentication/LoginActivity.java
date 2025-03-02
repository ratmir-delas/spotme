package com.example.spotme_mvp.ui.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotme_mvp.MainActivity;
import com.example.spotme_mvp.R;
import com.example.spotme_mvp.database.AppDatabase;
import com.example.spotme_mvp.database.UserDao;
import com.example.spotme_mvp.entities.User;
import com.example.spotme_mvp.utils.UserSession;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView signUpButton;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signupText);

        // Inicializar a base de dados e o DAO
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        userDao = db.userDao();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    authenticateUser(email, password);
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void authenticateUser(String email, String password) {
        AsyncTask.execute(() -> {
            User user = userDao.getUserByEmail(email);

            if (user == null) {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show());
                return;
            }

            if (user.getPassword().equals(password)) {

                UserSession.getInstance(getApplicationContext()).setUserId(user.getId());
                UserSession.getInstance(getApplicationContext()).setUserName(user.getUsername());
                UserSession.getInstance(getApplicationContext()).setUserEmail(user.getEmail());

                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                });
            } else {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show());
            }
        });
    }


}