package com.java.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.sample.R;
import com.java.sample.adapter.FragmentStudentAdapter;
import com.java.sample.dto.Student;

import java.util.ArrayList;


public class StudentFragmentInfo extends Fragment {

    ArrayList<Student> students;
    FragmentStudentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_info, container, false);
        
        return view;
    }

    private void addStudentList() {
        students.add(new Student(1, "Nguyễn Văn A", 2000, "HCM"));
        students.add(new Student(2, "Nguyễn Văn B", 1990, "Cần Thơ"));
        students.add(new Student(3, "Nguyễn Văn C", 1980, "Hà Nội"));
        students.add(new Student(4, "Nguyễn Văn D", 2005, "Khánh Hòa"));
    }

}
