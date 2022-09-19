package com.example.myapplication;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Edit extends Fragment implements View.OnClickListener {

    public static boolean on ;
    private static String data ;
    private EditText edit;
    private Button remove;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add,container,false);
        on = true;
        edit = v.findViewById(R.id.edit);




        edit.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 edit.getText().clear();
                 return false;
             }
         });
         Button add = v.findViewById(R.id.addEdit);
        add.setOnClickListener(this);



        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.addEdit:

                data = edit.getText().toString();
                MainActivity.arrayList.add(data);
                MainActivity.arrayAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.addScreen, new Red());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
        }

    }

}
