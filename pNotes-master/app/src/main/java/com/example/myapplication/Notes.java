package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Notes extends Activity {

    private String heading;
    private TextView edit ;
    private TextView title ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);
        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edit = findViewById(R.id.screen);
        title = findViewById(R.id.title);
        Intent intent = getIntent();
        heading = intent.getStringExtra("title");
        title.setText(heading);
        edit.setText(getTextFile());
    }

    @Override
    protected void onStop() {
        super.onStop();
        String content;
        content = edit.getText().toString();
        saveTextFile(content);
    }

    public String getTextFile(){
        FileInputStream fileInputStream = null;
        String fileData = null;
        try {
            fileInputStream = openFileInput(heading);
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();
            fileData = new String(buffer,"UTF-8");


        }catch (FileNotFoundException e){
            Log.e("file","couldn't find file");
            e.printStackTrace();
        }catch (IOException e) {
            Log.e("file", "error");
            e.printStackTrace();
        }finally {
            try {
                if(fileInputStream != null){
                    fileInputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return fileData;
    }

    public void saveTextFile(String content){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(heading, Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());

        }catch (FileNotFoundException e){
            Log.e("file","couldn't find file");

        }catch (IOException e) {
            Log.e("file", "io error");
            e.printStackTrace();
        }finally {
            try {
                if(fileOutputStream != null){
                    fileOutputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
