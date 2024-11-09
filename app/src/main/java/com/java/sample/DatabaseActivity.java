package com.java.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWindow;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.java.sample.adapter.ImageStoreAdapter;
import com.java.sample.adapter.WorkingAdapter;
import com.java.sample.db.DB;
import com.java.sample.dto.ImageStorage;
import com.java.sample.dto.Working;
import com.java.sample.screen.AddImageActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DatabaseActivity extends AppCompatActivity {

    DB db;

    WorkingAdapter workingAdapter;
    ListView listViewWorking;
    List<Working> workings;

    ImageStoreAdapter imageStoreAdapter;
    ListView listViewImageStorage;
    List<ImageStorage> imageStorages;

    Button btnAddImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Go to Add Image screen
        btnAddImage = findViewById(R.id.btnGoAddImageScreen);
        btnAddImage.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddImageActivity.class);
            startActivity(intent);
        });

        // 1. Show Job List
        listViewWorking = findViewById(R.id.listViewWorking);
        workings = new ArrayList<>();
        workingAdapter = new WorkingAdapter(this, R.layout.working_item, workings);
        listViewWorking.setAdapter(workingAdapter);


        // 1. Create & connect to database
        db = new DB(this, "db.sqlite", null, 1);

        // 2. Create new table
        //db.execute(Util.readSql(this, R.raw.working));

        // 3. Seed some data
        //db.execute(Util.readSql(this, R.raw.seed_working));

        // 4. Select data
        loadDataJob();

        // 2. Show Image Storage
        listViewImageStorage = findViewById(R.id.listViewImageStorage);
        imageStorages = new ArrayList<>();
        imageStoreAdapter = new ImageStoreAdapter(this, R.layout.image_storage_item, imageStorages);
        listViewImageStorage.setAdapter(imageStoreAdapter);
        //db.execute("DELETE FROM image_storage");
        loadDataImageStorage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.buttonMenuAdd) {
            showDialogAddJob();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialogDeleteJob(Integer id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        // Check exists of job
        Working working = findWorking(id);
        if (working == null) {
            //Toast.makeText(this, "Not found job with id " + id, Toast.LENGTH_SHORT).show();
            dialog.setMessage("Not found job with id " + id);
            dialog.show();
            return;
        }

        System.out.println("Delete job with id: ===> " + working.getId());

        dialog.setMessage("Are you sure to delete job (" + working.getName() + ")?");
        dialog.setPositiveButton("Yes", (dialog1, which) -> {
            String sql = String.format("DELETE FROM working WHERE id = %d", id);
            db.execute(sql);
            loadDataJob();
            dialog1.dismiss();
            Toast.makeText(this, "A job already deleted.", Toast.LENGTH_SHORT).show();
        });
        dialog.setNegativeButton("No", (dialog1, which) -> {
            dialog1.dismiss();
        });
        dialog.show();

//        if (working == null) {
//            Toast.makeText(this, "Not found job with id " + id, Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_delete_working);
//
//        Button buttonDeleteJob = dialog.findViewById(R.id.buttonYesJob);
//        Button buttonCancelJob = dialog.findViewById(R.id.buttonNoJob);
//
//        // Close dialog
//        buttonCancelJob.setOnClickListener(view -> {
//            dialog.dismiss();
//        });
//
//        buttonDeleteJob.setOnClickListener(view -> {
//            String sql = String.format("DELETE FROM working WHERE id = %d", id);
//            db.execute(sql);
//            dialog.dismiss();
//            loadData();
//            Toast.makeText(this, "A job already deleted.", Toast.LENGTH_SHORT).show();
//        });
//
//        dialog.show();
    }

    public void showDialogEditJob(Integer id) {
        Working working = findWorking(id);
        if (working == null) {
            Toast.makeText(this, "Job not found with id " + id, Toast.LENGTH_SHORT).show();
            return;
        }

        System.out.println("Edit job with id: ===> " + working.getId());

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_working);

        EditText editTextEditJob = dialog.findViewById(R.id.editTextEditJob);
        Button buttonSaveJob = dialog.findViewById(R.id.buttonSaveJob);
        Button buttonCancelJob = dialog.findViewById(R.id.buttonCancelJob);

        // Display the selected job
        editTextEditJob.setText(working.getName());

        // Close dialog
        buttonCancelJob.setOnClickListener(view -> {
            dialog.dismiss();
        });

        // Update job
        buttonSaveJob.setOnClickListener(view -> {
            String job = editTextEditJob.getText().toString().trim();
            if (job.equals("")) {
                Toast.makeText(this, "Please enter a job!", Toast.LENGTH_SHORT).show();
            } else {
                if (!job.equals(working.getName())) {
                    String sql = String.format("UPDATE working SET name='%s' WHERE id = %d", job, id);
                    db.execute(sql);
                    dialog.dismiss();
                    loadDataJob();
                    Toast.makeText(this, "Job already updated.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void showDialogAddJob() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_working);

        EditText editTextAddJob = dialog.findViewById(R.id.editTextJob);
        Button buttonAddJob = dialog.findViewById(R.id.buttonAddJob);
        Button buttonCancelJob = dialog.findViewById(R.id.buttonCancelJob);

        // Close dialog
        buttonCancelJob.setOnClickListener(view -> {
            dialog.dismiss();
        });

        // Add new job
        buttonAddJob.setOnClickListener(view -> {
            String job = editTextAddJob.getText().toString().trim();
            if (job.equals("")) {
                Toast.makeText(this, "Please enter a job!", Toast.LENGTH_SHORT).show();
            } else {
                String sql = String.format("INSERT INTO working(name) VALUES('%s')", job);
                db.execute(sql);
                Toast.makeText(this, "A job has already been added!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                loadDataJob();
            }
        });

        dialog.show();
    }

    public void showDialogDeleteImageStorage(Integer id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        // Check exists of job
        ImageStorage imageStorage = findImageStorage(id);
        if (imageStorage == null) {
            dialog.setMessage("Not found image storage with id " + id);
            dialog.show();
            return;
        }

        System.out.println("Delete image storage with id: ===> " + imageStorage.getId());

        dialog.setMessage("Are you sure to delete image storage (" + imageStorage.getName() + ")?");
        dialog.setPositiveButton("Yes", (dialog1, which) -> {
            String sql = String.format("DELETE FROM image_storage WHERE id = %d", id);
            db.execute(sql);
            loadDataImageStorage();
            dialog1.dismiss();
            Toast.makeText(this, "An image storage already deleted.", Toast.LENGTH_SHORT).show();
        });
        dialog.setNegativeButton("No", (dialog1, which) -> {
            dialog1.dismiss();
        });
        dialog.show();
    }

    private void loadDataImageStorage() {
        // Increase the storage size for blob column in sqlite
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); // 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (imageStorages == null) {
                imageStorages = new ArrayList<>();
            }
            imageStorages.clear();
            Cursor cursor = db.select("SELECT * FROM image_storage");
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                byte[] image = cursor.getBlob(3);
                imageStorages.add(new ImageStorage(id, name, description, image));
            }
            imageStoreAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            System.out.println("loadDataImageStorage ===> error: " + e.getMessage());
        }
    }

    private void loadDataJob() {
        if (workings == null) {
            workings = new ArrayList<>();
        }
        Cursor cursor = db.select("SELECT * FROM working");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            workings.add(new Working(id, name));
        }
        workingAdapter.notifyDataSetChanged();
    }

    private Working findWorking(Integer id) {
        Cursor cursor = db.select("SELECT * FROM working WHERE id = " + id);
        if (cursor == null) {
            return null;
        }
        Working working = new Working();
        while (cursor.moveToNext()) {
            working.setId(id);
            working.setName(cursor.getString(1));
        }
        return working;
    }

    private ImageStorage findImageStorage(Integer id) {
        Cursor cursor = db.select("SELECT * FROM image_storage WHERE id = " + id);
        if (cursor == null) {
            return null;
        }
        ImageStorage imageStorage = new ImageStorage();
        while (cursor.moveToNext()) {
            imageStorage.setId(id);
            imageStorage.setName(cursor.getString(1));
            imageStorage.setDescription(cursor.getString(2));
            imageStorage.setImage(cursor.getBlob(3));
        }
        return imageStorage;
    }
}