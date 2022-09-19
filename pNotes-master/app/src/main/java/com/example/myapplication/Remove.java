package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Remove extends Fragment implements View.OnClickListener{

    String name;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {




        View v = inflater.inflate(R.layout.remove, container, false);

        Button cancel = v.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        Button remove = v.findViewById(R.id.remove);
        remove.setOnClickListener(this);

        return v;
    }




    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        name = MainActivity.arrayList.get(MainActivity.item);


        switch (v.getId()) {
           case R.id.cancel:

               fragmentTransaction.replace(R.id.addScreen, new Red());
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit();
               break;

           case R.id.remove:

               Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();

               try {
                   ((MainActivity) getActivity()).removeFile(name);
               } catch (IOException e) {
                   e.printStackTrace();
               }
               MainActivity.arrayList.remove(MainActivity.item);

               MainActivity.arrayAdapter.notifyDataSetChanged();
               fragmentTransaction.replace(R.id.addScreen, new Red());
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit();
               break;
       }


    }
}
