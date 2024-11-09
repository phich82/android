package com.java.sample;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.java.sample.dto.User;

//public class SecondActivity extends AppCompatActivity {
public class SecondActivity extends Activity {

    Button buttonSecond, buttonSave;
    TextView textView, textName, textAge;
    EditText editTextName;

    private static final int RESULT_CODE_CONFIRM = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        buttonSecond = findViewById(R.id.buttonSecond);
        buttonSave = findViewById(R.id.buttonSave);

        textView = findViewById(R.id.textView);
        textName = findViewById(R.id.textViewName);
        textAge = findViewById(R.id.textViewAge);

        editTextName = findViewById(R.id.editTextName);


        // TODO: Receive data from intent (parent)
        Intent intentBefore = getIntent();
        try {
            Bundle bundle = (Bundle) intentBefore.getExtras();
            if (bundle != null) {
                textView.setText(bundle.getString("message"));
                User user = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    user = bundle.getSerializable("user", User.class);
                } else {
                    user = (User) bundle.getSerializable("user");
                }
                //User user = Util.getSerializable(bundle,"user", User.class);
                textName.setText(user.getName());
                textAge.setText("" + user.getAge());
            }
        } catch (Exception e) {
            Log.d("LifeCycle", e.getMessage());
        }

        // TODO: 1. Back to parent screen
        buttonSave.setOnClickListener(view -> {
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            // Back to parent screen with the attached data
            Bundle bundle = new Bundle();
            String name = editTextName.getText().toString();
            bundle.putString("name", name);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            // close current screen
            finish();
        });

        // TODO: 1. Back to parent screen
        buttonSecond.setOnClickListener(view -> {
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Log.d("LifeCycle ===> SecondActivity: ", "onStart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LifeCycle ===> SecondActivity: ", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LifeCycle ===> SecondActivity: ", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle ===> SecondActivity: ", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle ===> SecondActivity: ", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle ===> SecondActivity: ", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle ===> SecondActivity: ", "onDestroy");
    }
}