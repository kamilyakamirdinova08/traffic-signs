package com.example.traffic_signs.utils;// AboutActivity.java

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.traffic_signs.R;
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Получаем строку из ресурсов
        String aboutText = getResources().getString(R.string.about);

        // Находим TextView и устанавливаем в него текст
        TextView textViewAbout = findViewById(R.id.textViewAbout);
        textViewAbout.setText(aboutText);
    }
}
