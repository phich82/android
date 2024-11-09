package com.java.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.sample.R;
import com.java.sample.WebserviceActivity;
import com.java.sample.dto.Student;

import java.util.List;

public class StudentAdapter extends BaseAdapter {

    private WebserviceActivity context;
    private int layout;
    private List<Student> students;

    public StudentAdapter(WebserviceActivity context, int layout, List<Student> students) {
        this.context = context;
        this.layout = layout;
        this.students = students;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int i) {
        if (students.size() < 1) {
            return null;
        }
        return students.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtFullName = view.findViewById(R.id.txtFullNameStudent);
            holder.txtBirthYear = view.findViewById(R.id.txtBirthYearStudent);
            holder.txtAddress = view.findViewById(R.id.txtAddressStudent);
            holder.imgDelete = view.findViewById(R.id.imgDeleteStudent);
            holder.imgEdit = view.findViewById(R.id.imgEditStudent);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Student student = students.get(i);

        if (student != null) {
            holder.txtFullName.setText(student.getFullName());
            holder.txtBirthYear.setText(String.format("%s", student.getBirthYear()));
            holder.txtAddress.setText(student.getAddress());

            // Handle click events
            holder.imgEdit.setOnClickListener(v -> {

            });
            holder.imgDelete.setOnClickListener(v -> {

            });
        }

        return view;
    }

    private class ViewHolder {
        TextView txtFullName;
        TextView txtBirthYear;
        TextView txtAddress;
        ImageView imgDelete, imgEdit;
    }

}
