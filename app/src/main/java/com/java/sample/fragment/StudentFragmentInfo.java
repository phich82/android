package com.java.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.sample.R;
import com.java.sample.dto.Student;



public class StudentFragmentInfo extends Fragment {

    TextView nameTextView, birthYearTextView, addressTextView;
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_info, container, false);

        initUIElements();

        return view;
    }

    public void updateStudentInfo(Student student) {
        if (student != null) {
            setName(student.getFullName());
            setBirthYear(student.getBirthYear());
            setAddress(student.getAddress());
        } else {
            Toast.makeText(getActivity(), "Student not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void setName(String name) {
        nameTextView.setText(name);
    }

    public void setBirthYear(Integer birthYear) {
        birthYearTextView.setText("Birth year: " + birthYear);
    }

    public void setAddress(String address) {
        addressTextView.setText("Address: " + address);
    }

    private void initUIElements() {
        nameTextView = view.findViewById(R.id.txtFullName);
        birthYearTextView = view.findViewById(R.id.txtBirthYear);
        addressTextView = view.findViewById(R.id.txtAddress);
    }

}
