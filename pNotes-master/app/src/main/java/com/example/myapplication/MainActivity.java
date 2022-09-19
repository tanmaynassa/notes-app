package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    public static final String PREFERENCES_TODO = "TODO_List_Shared_Preferences";
    private static boolean flag = false;
    public static int item;
    public static String array = null;
    public static ArrayAdapter<String> arrayAdapter;
    public String arr[] =null;
    public Button add;

    public static List<String> arrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeFragment(new Red());
        setContentView(R.layout.activity_main);


        add = findViewById(R.id.add);
        ListView listView = findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, arrayList);
        if(listView != null){


            listView.setAdapter(arrayAdapter);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragment(new Edit());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(flag == false){
                    Intent intent = new Intent(MainActivity.this,Notes.class);
                    intent.putExtra("title", arrayList.get(position));
                    startActivity(intent);
                }
                flag = false;

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                flag = true;
                item = position;
                changeFragment(new Remove());
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {

        Edit edit = new Edit();
        if(Edit.on){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.addScreen, new Red());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            Edit.on=false;
        }else {
            finish();
        }
    }

    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.addScreen,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences(PREFERENCES_TODO,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.clear();
        for (int i = 0; i < arrayAdapter.getCount(); ++i){
            // This assumes you only have the list items in the SharedPreferences.
            editor.putString(String.valueOf(i), arrayAdapter.getItem(i));
        }
        editor.commit();
    }

   public void removeFile(String name) throws IOException {

    FileOutputStream file = openFileOutput(name,Context.MODE_PRIVATE);
    file.flush();
    file.close();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_TODO, MODE_PRIVATE);

        arrayAdapter.clear();

        for (int i = 0;; ++i){
            final String str = prefs.getString(String.valueOf(i), "");
            if (!str.equals("")){
                arrayAdapter.add(str);
            } else {
                break;
            }
        }

    }


}







