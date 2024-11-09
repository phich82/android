package com.java.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.java.sample.DatabaseActivity;
import com.java.sample.MainActivity;
import com.java.sample.R;
import com.java.sample.dto.Working;

import java.util.List;

public class WorkingAdapter extends BaseAdapter {

    private DatabaseActivity context;
    private int layout;
    private List<Working> workings;

    public WorkingAdapter(DatabaseActivity context, int layout, List<Working> workings) {
        this.context = context;
        this.layout = layout;
        this.workings = workings;
    }

    @Override
    public int getCount() {
        return workings.size();
    }

    @Override
    public Object getItem(int i) {
        if (workings.size() < 1) {
            return null;
        }
        return workings.get(i);
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
            holder.txtName = view.findViewById(R.id.textViewName);
            holder.imgDelete = view.findViewById(R.id.imageViewDelete);
            holder.imgEdit = view.findViewById(R.id.imageViewEdit);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Working working = workings.get(i);
        holder.txtName.setText(working.getName());

        // Handle click events
        holder.imgEdit.setOnClickListener(v -> context.showDialogEditJob(working.getId()));
        holder.imgDelete.setOnClickListener(v -> context.showDialogDeleteJob(working.getId()));

        return view;
    }

    private class ViewHolder {
        TextView txtName;
        ImageView imgDelete, imgEdit;
    }

}
