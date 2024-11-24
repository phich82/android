package com.java.sample.screen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.java.sample.DatabaseActivity;
import com.java.sample.R;
import com.java.sample.db.DB;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddImageActivity extends AppCompatActivity {

    DB db;

    private EditText txtNameAddImage, txtDescriptionAddImage;
    private ImageView imgCameraAddImage, imgUploadAddImage, imgPreviewAddImage;
    private Button btnSaveAddImage, btnCancelAddImage;

    private final int REQUEST_CODE_CAMERA = 100;
    private final int REQUEST_CODE_FOLDER = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        // 1. Create & connect to database
        db = new DB(this, "db.sqlite", null, 1);

        // 2. Create new table
        //db.execute(Util.readSql(this, R.raw.image_storage));

        loadUiElements();

        // Camera button
        imgCameraAddImage.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_FOLDER);
        });

        // Load image button from local storage
        imgUploadAddImage.setOnClickListener(view -> {
            // Android 13+: use READ_MEDIA_IMAGES
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_CODE_FOLDER);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_FOLDER);
            }
        });

        // Save button
        btnSaveAddImage.setOnClickListener(view -> {
            String sql = "INSERT INTO image_storage(name, description, image) VALUES (?, ?, ?)";
            String name = txtNameAddImage.getText().toString().trim();
            String description = txtDescriptionAddImage.getText().toString().trim();
            // Convert bitmap to bytes
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgPreviewAddImage.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
            byte[] image = byteArray.toByteArray();

            Object[] bindings = new Object[]{name, description, image};
            db.insertBlob(sql, bindings);
            Toast.makeText(this, "New image has already been inserted.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, DatabaseActivity.class));
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            // Camera permission
            case REQUEST_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent1, REQUEST_CODE_CAMERA);
                } else {
                    Toast.makeText(this, "You do not allow to access camera!", Toast.LENGTH_SHORT).show();
                }
                break;
                // External Storage permission
            case REQUEST_CODE_FOLDER:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent2 = new Intent(Intent.ACTION_PICK);
                    intent2.setType("image/*");
                    startActivityForResult(intent2, REQUEST_CODE_FOLDER);
                } else {
                    Toast.makeText(this, "You do not allow to read the external storage!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Image from camera
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgPreviewAddImage.setImageBitmap(bitmap);
        }
        // Image from local storage
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgPreviewAddImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadUiElements() {
        txtNameAddImage = findViewById(R.id.txtNameAddImage);
        txtDescriptionAddImage = findViewById(R.id.txtDescriptionAddImage);
        imgCameraAddImage = findViewById(R.id.imgCameraAddImage);
        imgUploadAddImage = findViewById(R.id.imgUploadAddImage);
        imgPreviewAddImage = findViewById(R.id.imgPreviewAddImage);
        btnSaveAddImage = findViewById(R.id.btnSaveAddImage);
        btnCancelAddImage = findViewById(R.id.btnCancelAddImage);
    }
}