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
import com.java.sample.util.Util;
import com.java.sample.WebserviceActivity;
import com.java.sample.dto.ResponseStudent;
import com.java.sample.dto.Student;
import com.java.sample.http.Http;
import com.java.sample.util.Callback;

import org.json.JSONObject;


public class UpdateStudentActivity extends AppCompatActivity {

    private Button btnSaveEditStudent, btnCancelEditStudent;
    private EditText txtFullNameEditStudent, txtBirthYearEditStudent, txtAddressEditStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initUIElements();

        // Show student info
        Student student = getParamsActivity();
        if (student != null) {
            txtFullNameEditStudent.setText(student.getFullName());
            txtBirthYearEditStudent.setText(String.valueOf(student.getBirthYear()));
            txtAddressEditStudent.setText(student.getAddress());
        }

        // Cancel Button
        btnCancelEditStudent.setOnClickListener(view -> {
            startActivity(new Intent(this, WebserviceActivity.class));
        });

        // Save Button
        btnSaveEditStudent.setOnClickListener(view -> {
            updateStudent(student);
        });

    }

    private Student getParamsActivity() {
        Intent intent = getIntent();
        Bundle bundle = (Bundle) intent.getExtras();
        if (bundle != null) {
            return Util.getSerializable(bundle, "student", Student.class);
        }
        return null;
    }

    private void updateStudent(Student student) {
        try {
            String fullName = txtFullNameEditStudent.getText().toString();
            String birthYear = txtBirthYearEditStudent.getText().toString();
            String address = txtAddressEditStudent.getText().toString();

            // Validate
            if (fullName.trim().isEmpty()) {
                Toast.makeText(UpdateStudentActivity.this, "Full name must not be empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (birthYear.trim().isEmpty()) {
                Toast.makeText(UpdateStudentActivity.this, "Birth year must not be empty.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (address.trim().isEmpty()) {
                Toast.makeText(UpdateStudentActivity.this, "Address must not be empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            Http http = new Http();
            String url = "http://192.168.1.111:9000/api/v1/students/update.php";

            JSONObject requestBody = new JSONObject();
            requestBody.put("id", student.getId());
            requestBody.put("full_name", fullName);
            requestBody.put("birth_year", birthYear);
            requestBody.put("address", address);

            System.out.println("updateStudent ===> requestBody: " + requestBody);

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
                            Toast.makeText(UpdateStudentActivity.this, "A new student has already been created.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(UpdateStudentActivity.this, WebserviceActivity.class));
                        } else {
                            Toast.makeText(UpdateStudentActivity.this, responseStudent.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        System.out.println("===> error: " + e.getMessage());
                        e.printStackTrace();
                        Toast.makeText(UpdateStudentActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
        txtFullNameEditStudent = findViewById(R.id.txtFullNameEditStudent);
        txtBirthYearEditStudent = findViewById(R.id.txtBirthYearEditStudent);
        txtAddressEditStudent = findViewById(R.id.txtAddressEditStudent);

        btnSaveEditStudent = findViewById(R.id.btnSaveEditStudent);
        btnCancelEditStudent = findViewById(R.id.btnCancelEditStudent);
    }
}