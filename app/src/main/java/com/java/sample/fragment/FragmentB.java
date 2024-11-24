package com.java.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.sample.R;

import java.util.function.Function;


public class FragmentB extends Fragment {

    public TextView titleElement;
    public EditText nameElement;
    public Button btnElement;
    Function<String, Void> callback;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        initUIElements(view);
        return view;
    }

    public FragmentB setTitle(String title) {
        if (titleElement != null) {
            titleElement.setText(title);
        }
        return this;
    }

    public FragmentB setName(String name) {
        if (nameElement != null) {
            nameElement.setText(name);
        }
        return this;
    }

    public FragmentB onClick(Function<String, Void> callback) {
        this.callback = callback;
        return this;
    }

    private void initUIElements(View view) {
        titleElement = view.findViewById(R.id.lblTitleFragmentB);
        nameElement = view.findViewById(R.id.txtNameFragmentB);
        btnElement = view.findViewById(R.id.btnClickMeFragmentB);

        btnElement.setOnClickListener(v -> {
            if (callback != null) {
                callback.apply(nameElement.getText().toString());
            }
        });
    }
}
