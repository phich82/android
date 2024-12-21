package com.java.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.java.sample.R;
import com.java.sample.adapter.FragmentStudentAdapter;
import com.java.sample.contract.TransformStudent;
import com.java.sample.dto.Student;

import java.util.ArrayList;


public class StudentFragmentList extends ListFragment {

    ArrayList<Student> students;
    FragmentStudentAdapter adapter;
    TransformStudent transformStudent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        transformStudent = (TransformStudent) getActivity();
        students = new ArrayList<>();
        addStudentList();
        adapter = new FragmentStudentAdapter(getActivity(), R.layout.student_item, students);
        setListAdapter(adapter);
        return inflater.inflate(R.layout.fragment_student_list, container, false);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        transformStudent.transform(students.get(position));
    }

    private void addStudentList() {
        students.add(new Student(1, "Nguyễn Văn A", 2000, "HCM"));
        students.add(new Student(2, "Nguyễn Văn B", 1990, "Cần Thơ"));
        students.add(new Student(3, "Nguyễn Văn C", 1980, "Hà Nội"));
        students.add(new Student(4, "Nguyễn Văn D", 2005, "Khánh Hòa"));
    }

}
