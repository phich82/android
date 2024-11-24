package com.java.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.java.sample.R;

import java.util.function.Function;


public class FragA extends Fragment {

    public TextView titleElement;

    Function<String, Void> callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_a, container, false);
        initUIElements(view);

        // Get data from external
        Bundle bundle = getArguments();
        if (bundle != null) {
            setTitle(bundle.getString("test"));
        }

        return view;
    }

    public FragA setTitle(String title) {
        if (titleElement != null) {
            titleElement.setText(title);
        }
        return this;
    }

    public FragA onClick(Function<String, Void> callback) {
        this.callback = callback;
        return this;
    }

    private void initUIElements(View view) {
        titleElement = view.findViewById(R.id.lblTitleFragA);
    }

}