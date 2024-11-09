package com.java.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SharedPreferencesActivity extends AppCompatActivity {

    Button buttonLogin;
    EditText editTextUser, editTextPassword;
    CheckBox checkBoxRemember;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        buttonLogin = findViewById(R.id.buttonLogin);
        editTextUser = findViewById(R.id.editTextUser);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRemember = findViewById(R.id.checkBoxRemember);

        // Get values from SharedPreferences
        sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);
        editTextUser.setText(sharedPreferences.getString("username", ""));
        editTextPassword.setText(sharedPreferences.getString("password", ""));
        checkBoxRemember.setChecked(sharedPreferences.getBoolean("checked", false));


        buttonLogin.setOnClickListener(view -> {
            String username = editTextUser.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            if (username.equals("admin") && password.equals("1234")) {
                // Check remember
                if (checkBoxRemember.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putBoolean("checked", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("username");
                    editor.remove("password");
                    editor.remove("checked");
                    editor.commit();
                }
                Toast.makeText(this, "Logged in successful.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Username or password is invalid.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}