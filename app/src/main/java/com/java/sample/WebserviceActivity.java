package com.java.sample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.java.sample.adapter.StudentAdapter;
import com.java.sample.dto.Student;
import com.java.sample.http.Http;
import com.java.sample.util.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class WebserviceActivity extends AppCompatActivity {

    Button btnGoBack;
    ListView listViewStudent;
    StudentAdapter studentAdapter;
    ArrayList<Student> students;

    Http http;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_webservice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initUIElements();

        btnGoBack.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        students = new ArrayList<>();
        studentAdapter = new StudentAdapter(this, R.layout.student_item, students);
        listViewStudent.setAdapter(studentAdapter);

        http = new Http();
        String url = "http://192.168.1.111:9000/index.php";

        http.request(url, this, new Callback<Boolean, JSONArray>() {
            @Override
            public Void call() {
                System.out.println("===> success (callback): " + this.success);
                System.out.println("===> result (callback): " + this.data);
                if (success) {
                    for (int i=0; i < this.data.length(); i++) {
                        try {
                            JSONObject obj = this.data.getJSONObject(i);
                            students.add(new Student(
                                    obj.getInt("id"),
                                    obj.getString("full_name"),
                                    obj.getInt("birth_year"),
                                    obj.getString("address")
                            ));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("===> students: " + students);
                    studentAdapter.notifyDataSetChanged();
                }
                return null;
            }
        });

//        http.request(url, this, (success, response) -> {
//            System.out.println("===> success: " + success);
//            System.out.println("===> response: " + response);
//            return null;
//        });


    }

    private void initUIElements() {
        btnGoBack = findViewById(R.id.btnGoBack);
        listViewStudent = findViewById(R.id.listViewStudent);
    }
}