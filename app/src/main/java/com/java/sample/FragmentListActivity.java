package com.java.sample;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.java.sample.contract.TransformStudent;
import com.java.sample.dto.Student;
import com.java.sample.fragment.StudentFragmentInfo;


public class FragmentListActivity extends AppCompatActivity implements TransformStudent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragment_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void transform(Student student) {
        StudentFragmentInfo studentFragmentInfo = (StudentFragmentInfo) getSupportFragmentManager().findFragmentById(R.id.fcvStudentInfo);
        // Screen with horizontal orientation

        Configuration configuration = getResources().getConfiguration();
        // Check if orientation is landscape
        if (studentFragmentInfo != null && configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            studentFragmentInfo.updateStudentInfo(student);
        } else {
            Intent intent = new Intent(this, StudentInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("student", student);
            intent.putExtras(bundle);
            // Navigate to student info screen
            startActivity(intent);
        }
    }
}