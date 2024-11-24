package com.java.sample;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.reflect.TypeToken;
import com.java.sample.adapter.StudentAdapter;
import com.java.sample.dto.ResponseStudent;
import com.java.sample.dto.Student;
import com.java.sample.http.Http;
import com.java.sample.screen.AddStudentActivity;
import com.java.sample.screen.UpdateStudentActivity;
import com.java.sample.util.Callback;
import com.java.sample.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WebserviceActivity extends AppCompatActivity {

    Button btnGoBack;
    ListView listViewStudent;
    StudentAdapter studentAdapter;
    List<Student> students;

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initUIElements();

        // GoBack Button
        btnGoBack.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        // Show student list
        students = new ArrayList<>();
        studentAdapter = new StudentAdapter(this, R.layout.student_item, students);
        listViewStudent.setAdapter(studentAdapter);

        // Load data from API
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu_student, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnAddStudentMenu) {
            startActivity(new Intent(this, AddStudentActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEditStudent(Student student) {
        Intent intent = new Intent(this, UpdateStudentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("student", student);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onDeleteStudent(Student student) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure to delete student with name (" + student.getFullName() + ")?");
        dialog.setPositiveButton("Yes", (dialog1, which) -> {
            try {
                http = new Http();
                String url = "http://192.168.1.111:9000/api/v1/students/delete.php";
                JSONObject requestBody = new JSONObject();
                requestBody.put("id", student.getId());
                http.request(this, url, Request.Method.POST, requestBody, new Callback<Boolean, JSONObject>() {
                    @Override
                    public Void call() {
                        System.out.println("onDeleteStudent ===> success (callback): " + this.success);
                        if (!this.success) {
                            return null;
                        }
                        try {
                            ResponseStudent responseStudent = ResponseStudent.from(this.data);
                            if (responseStudent.isSuccess()) {
                                loadData();
                                dialog1.dismiss();
                                Toast.makeText(WebserviceActivity.this, "A student already deleted.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(WebserviceActivity.this, responseStudent.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            System.out.println("onDeleteStudent ===> error: " + e.getMessage());
                            Toast.makeText(WebserviceActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return null;
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        dialog.setNegativeButton("No", (dialog1, which) -> {
            dialog1.dismiss();
        });
        dialog.show();
    }

    private void loadData() {
        if (students == null) {
            students = new ArrayList<>();
        }
        http = new Http();
        String url = "http://192.168.1.111:9000/api/v1/students/list.php";

        http.request(this, url, Request.Method.POST, new Callback<Boolean, JSONObject>() {
            @Override
            public Void call() {
                System.out.println("loadData ===> success (callback): " + this.success);
                if (!this.success) {
                    return null;
                }
                try {
                    ResponseStudent responseStudent = ResponseStudent.from(this.data);
                    if (responseStudent.isSuccess()) {
                        JSONArray dataResponse = (JSONArray) responseStudent.getData();
                        // Clear old data
                        students.clear();
                        // Load new data
                        students.addAll(Util.fromJson(dataResponse.toString(),
                                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES,
                                new TypeToken<List<Student>>(){}));
                    }
                    studentAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    System.out.println("===> error: " + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    private void initUIElements() {
        btnGoBack = findViewById(R.id.btnGoBack);
        listViewStudent = findViewById(R.id.listViewStudent);
    }

}