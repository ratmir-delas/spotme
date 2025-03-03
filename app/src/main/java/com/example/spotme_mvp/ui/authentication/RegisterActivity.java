package com.example.spotme_mvp.ui.authentication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText, phoneEditText;
    private Button registerButton;
    private TextView loginTextView;

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);

        // Inicializar BD
        AppDatabase db = AppDatabase.getInstance(this);
        userDao = db.userDao();

        // Inicializar os elementos da UI
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        phoneEditText = findViewById(R.id.phoneNumEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginTextView = findViewById(R.id.loginText);


        // Ação do botão de registo
        registerButton.setOnClickListener(v -> registerUser());

        // Ir para a página de login
        loginTextView.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
            showToast("Todos os campos devem ser preenchidos!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showToast("As senhas não coincidem!");
            return;
        }

        // Verifica se o utilizador já existe e regista-o
        new RegisterUserTask().execute(new User(username, password, email, phone));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class RegisterUserTask extends AsyncTask<User, Void, String> {
        @Override
        protected String doInBackground(User... users) {
            User existingUser = userDao.login(users[0].getEmail(), users[0].getPassword());
            if (existingUser != null) {
                return "Email já registado!";
            }
            userDao.insert(users[0]);

            // Configurar o UserSession com o ID do novo usuário
            User newUser = userDao.getUserByEmail(users[0].getEmail());
            if (newUser != null) {
                // Após inserir, configuramos a sessão
                UserSession.getInstance(getApplicationContext()).setUser(newUser);
            }
            return "Conta registada com sucesso!";
        }

        @Override
        protected void onPostExecute(String result) {
            showToast(result);
            if (result.equals("Conta registada com sucesso!")) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        }
    }
}
