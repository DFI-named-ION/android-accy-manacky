package com.example.accymanacky;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText emailText;
    private EditText passwordText;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.email_text);
        passwordText = findViewById(R.id.password_text);

        Button log = findViewById(R.id.btn_login);

        log.setOnClickListener(view -> {
            createUser();
        });

        TextView reg = findViewById(R.id.btn_register);

        reg.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void createUser() {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailText.setError("Поле не може бути порожнім!");
        } else if (TextUtils.isEmpty(password)) {
            passwordText.setError("Поле не може бути порожнім!");
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Користувач успішно авторизований!", Toast.LENGTH_SHORT)
                            .show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Під час авторизації сталася помилка!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
            });
        }
    }
}
