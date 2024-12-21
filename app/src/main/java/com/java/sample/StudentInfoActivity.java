package com.java.sample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.java.sample.dto.Student;
import com.java.sample.fragment.StudentFragmentInfo;
import com.java.sample.util.Util;


public class StudentInfoActivity extends AppCompatActivity {

    TextView textViewName, textViewBirthYear, textViewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initUIElements();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Get student info
        Intent intentBefore = getIntent();
        Bundle bundle = intentBefore.getExtras();
        if (bundle != null) {
            try {
                Student student = Util.getSerializable(bundle, "student", Student.class);
                if (student != null) {
                    textViewName.setText(student.getFullName());
                    textViewBirthYear.setText("Birth year: " + student.getBirthYear());
                    textViewAddress.setText("Address: " + student.getAddress());

                    // Update student info using fragment
                    StudentFragmentInfo studentFragmentInfo = (StudentFragmentInfo) getSupportFragmentManager().findFragmentById(R.id.fcvStudentInfoActivity);
                    if (studentFragmentInfo != null) {
                        studentFragmentInfo.updateStudentInfo(student);
                    } else {
                        Toast.makeText(this, "StudentFragmentInfo is empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                System.out.println("error ===> " + e.getMessage());
            }
        }
    }

    private void initUIElements() {
        textViewName = findViewById(R.id.txtFullNameStudentInfo);
        textViewBirthYear = findViewById(R.id.txtBirthYearStudentInfo);
        textViewAddress = findViewById(R.id.txtAddressStudentInfo);
    }
}