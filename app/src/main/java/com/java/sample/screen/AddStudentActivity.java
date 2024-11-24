package com.java.sample.screen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.java.sample.R;
import com.java.sample.WebserviceActivity;
import com.java.sample.dto.ResponseStudent;
import com.java.sample.http.Http;
import com.java.sample.util.Callback;

import org.json.JSONObject;


public class AddStudentActivity extends AppCompatActivity {

    private Button btnSaveAddStudent, btnCancelAddStudent;
    private EditText txtFullNameAddStudent, txtBirthYearAddStudent, txtAddressAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initUIElements();

        // Cancel Button
        btnCancelAddStudent.setOnClickListener(view -> {
            startActivity(new Intent(this, WebserviceActivity.class));
        });

        // Save Button
        btnSaveAddStudent.setOnClickListener(view -> {
            createStudent();
        });
    }

    private void createStudent() {
        Http http = new Http();
        String url = "http://192.168.1.111:9000/api/v1/students/insert.php";
        JSONObject requestBody = new  JSONObject();
        try {
            String fullName = txtFullNameAddStudent.getText().toString();
            String birthYear = txtBirthYearAddStudent.getText().toString();
            String address = txtAddressAddStudent.getText().toString();

            // Validate
            if (fullName.trim().isEmpty()) {
                Toast.makeText(AddStudentActivity.this, "Full name must not be empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (birthYear.trim().isEmpty()) {
                Toast.makeText(AddStudentActivity.this, "Birth year must not be empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (address.trim().isEmpty()) {
                Toast.makeText(AddStudentActivity.this, "Address must not be empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            requestBody.put("full_name", fullName);
            requestBody.put("birth_year", birthYear);
            requestBody.put("address", address);

            System.out.println("===> requestBody: " + requestBody);

            http.request(this, url, Request.Method.POST, requestBody, new Callback<Boolean, JSONObject>() {
                @Override
                public Void call() {
                    System.out.println("===> success (callback): " + this.success);
                    if (!this.success) {
                        return null;
                    }
                    try {
                        ResponseStudent responseStudent = ResponseStudent.from(this.data);
                        if (responseStudent.isSuccess()) {
                            Toast.makeText(AddStudentActivity.this, "A new student has already been created.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AddStudentActivity.this, WebserviceActivity.class));
                        } else {
                            Toast.makeText(AddStudentActivity.this, responseStudent.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        System.out.println("===> error: " + e.getMessage());
                        e.printStackTrace();
                        Toast.makeText(AddStudentActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            System.out.println("===> error (addStudent): " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initUIElements() {
        txtFullNameAddStudent = findViewById(R.id.txtFullNameAddStudent);
        txtBirthYearAddStudent = findViewById(R.id.txtBirthYearAddStudent);
        txtAddressAddStudent = findViewById(R.id.txtAddressAddStudent);

        btnSaveAddStudent = findViewById(R.id.btnSaveAddStudent);
        btnCancelAddStudent = findViewById(R.id.btnCancelAddStudent);
    }
}