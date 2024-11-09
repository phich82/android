package com.java.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.java.sample.dto.User;


public class MainActivity extends AppCompatActivity {
//public class MainActivity extends Activity {

    Button buttonMain,
            buttonEditName,
            buttonGoLogin,
            buttonGoAnimation,
            buttonRunBackGround,
            buttonAsyncTask,
            buttonShowImage,
            buttonDB,
            buttonGoWebservice;
    ImageView imageBrowser, imageMessage, imagePhone, imageCamera, imageViewCamera, imageViewLoadImage;
    TextView textViewReturnName, textViewBackGround, textViewAsyncTask;
    EditText editTextImageUrl;

    private static final int REQUEST_CODE_SAVE = 123;
    private static final int REQUEST_CODE_CAMERA_PICTURE = 124;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message message) {
            super.handleMessage(message);

            Bundle bundle = message.getData();
            String data = bundle.getString("MSG_KEY");

            TextView textView = findViewById(R.id.textViewBackGround);
            textView.setText(data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("LifeCycle ===> MainActivity: ", "onCreate");

        buttonMain = findViewById(R.id.buttonMain);
        imageBrowser = findViewById(R.id.imageViewBrowser);
        imageMessage = findViewById(R.id.imageViewMessage);
        imagePhone = findViewById(R.id.imageViewPhone);
        textViewReturnName = findViewById(R.id.textViewReturnResult);
        buttonEditName = findViewById(R.id.buttonEditName);
        imageCamera = findViewById(R.id.imageCamera);
        imageViewCamera = findViewById(R.id.imageViewCamera);


        buttonGoLogin = findViewById(R.id.buttonGoLogin);
        buttonGoAnimation = findViewById(R.id.buttonGoAnimation);
        buttonRunBackGround = findViewById(R.id.buttonRunBackGround);

        // Webservice
        buttonGoWebservice = findViewById(R.id.btnGoWebservice);

        // Database
        buttonDB = findViewById(R.id.buttonDB);

        // Async task
        textViewAsyncTask = findViewById(R.id.textViewAsyncTask);
        buttonAsyncTask = findViewById(R.id.buttonAsyncTask);

        // Load image
        editTextImageUrl = findViewById(R.id.editTextImageUrl);
        imageViewLoadImage = findViewById(R.id.imageViewLoadImage);
        buttonShowImage = findViewById(R.id.buttonShowImage);

        // TODO: Go webservice
        buttonGoWebservice.setOnClickListener(view -> {
            Intent intent = new Intent(this, WebserviceActivity.class);
            startActivity(intent);
        });

        // TODO: Database
        buttonDB.setOnClickListener(view -> {
            Intent intent = new Intent(this, DatabaseActivity.class);
            startActivity(intent);
        });

        // Show image
        buttonShowImage.setOnClickListener(view -> {
            String imageUrl = editTextImageUrl.getText().toString();
            if (imageUrl.trim().isEmpty()) {
                Toast.makeText(this, "Please enter a image url.", Toast.LENGTH_SHORT).show();
            } else {
                new LoadImage(imageViewLoadImage).execute(imageUrl);
            }
        });


        // TODO: Async Task
        buttonAsyncTask.setOnClickListener(view -> {
            new JobAsyncTask(textViewAsyncTask).execute();
        });

        // TODO: run background
        buttonRunBackGround.setOnClickListener(view -> runBackground(view));

        // TODO: 6. Navigate to Animation screen
        buttonGoAnimation.setOnClickListener(view -> {
            Intent intent = new Intent(this, AnimationActivity.class);
            startActivity(intent);
            // TODO: Intent animation
            overridePendingTransition(R.anim.enter, R.anim.exit);
        });

        // TODO: 5. Navigate to Login screen
        buttonGoLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, SharedPreferencesActivity.class);
            startActivity(intent);
        });

        // TODO: 4. Open camera
        imageViewCamera.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA_PICTURE);
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, REQUEST_CODE_CAMERA_PICTURE);
        });

        // TODO: 3. Send data from child to parent (implicit intent)
        buttonEditName.setOnClickListener(view -> {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SAVE);
        });


        // TODO: 2. Open chrome browser (implicit intent)
        imagePhone.setOnClickListener(view -> {
            // call phone
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:0901234567"));
            startActivity(intent);
        });
        imageMessage.setOnClickListener(view -> {
            // send message sms
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.putExtra("sms_body", "Hello...");
            intent.setData(Uri.parse("sms:0901234567"));
            startActivity(intent);
        });
        imageBrowser.setOnClickListener(view -> {
            // Open browser
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://khoapham.vn"));
            startActivity(intent);
        });

        // TODO: 1. Navigate & send data to other screen (explicit intent)
        buttonMain.setOnClickListener(view -> {
            Intent intent = new Intent(this, SecondActivity.class);

            // TODO: send data to other screen (activity)
            User user = new User("Nguyen Van A", 24);
            Bundle bundle = new Bundle();
            bundle.putString("message", "Hello World");
            bundle.putSerializable("user", user);

            intent.putExtras(bundle);

            // Navigate to other screen
            startActivity(intent);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        System.out.println("===> requestCode: " + requestCode);
        System.out.println("===> permissions: " + permissions);
        System.out.println("===> grantResults: " + grantResults[0]);
        for (String p: permissions) {
            System.out.println("===> permission: " + p);
        }
        // Check CAMERA request
        if (requestCode == REQUEST_CODE_CAMERA_PICTURE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_CAMERA_PICTURE);
        } else {
            // Not Allowed
            Toast.makeText(this, "Bạn không được phép sử dụng camera.", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: get data from camera & show it
        if (requestCode == REQUEST_CODE_CAMERA_PICTURE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageCamera.setImageBitmap(bitmap);
        }

        // TODO: get data from child screen
        if (requestCode == REQUEST_CODE_SAVE && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                textViewReturnName.setText(bundle.getString("name"));
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LifeCycle ===> MainActivity: ", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LifeCycle ===> MainActivity: ", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle ===> MainActivity: ", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle ===> MainActivity: ", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle ===> MainActivity: ", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle ===> MainActivity: ", "onDestroy");
    }

    public void runBackground(View view) {
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // TODO: Process background here
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bundle.putString("MSG_KEY", "This is a message from background.");
                message.setData(bundle);
                handler.sendMessage(message);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        TextView textView = findViewById(R.id.textViewBackGround);
        textView.setText("Button is clicked");
    }
}