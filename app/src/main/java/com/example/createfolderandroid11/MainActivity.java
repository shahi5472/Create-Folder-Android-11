package com.example.createfolderandroid11;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_STORAGE_PERMISSION = 100;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_STORAGE_PERMISSION) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Intent intent = new Intent();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//                }
//                Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
//                intent.setData(uri);
//                startActivity(intent);
//            }
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView viewPath = findViewById(R.id.viewPath);
        Button createFolder = findViewById(R.id.createFolder);
        createFolder.setOnClickListener(view -> {

        });

        //For public folder
        findViewById(R.id.savePublic).setOnClickListener(view -> {
            // Requesting Permission to access External Storage
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);

            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            File file = new File(folder, "geeksData.txt");
            writeTextData(file, "Public Test data :: " + System.currentTimeMillis());
        });

        //For private folder
        findViewById(R.id.savePrivate).setOnClickListener(view -> {
            File folder = getExternalFilesDir("GeeksForGeeks");

            File file = new File(folder, "gfg.txt");
            writeTextData(file, "Private Test data :: " + System.currentTimeMillis());
        });
    }

    private void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            Toast.makeText(this, "Done" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    void oldMethod(TextView viewPath) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
            return;
        } else {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        Intent intent = new Intent();
//                        intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//                        Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
//                        intent.setData(uri);
//                        startActivity(intent);
//                    }
            try {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFolder/Backup/";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                viewPath.setText("File Path " + file.getPath());
            } catch (Exception e) {
                viewPath.setText("File Path " + e.getMessage());
            }

            Toast.makeText(getApplicationContext(), "Create Folder", Toast.LENGTH_SHORT).show();
        }
    }
}