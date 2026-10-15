package com.example.traffic_signs.utils;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.traffic_signs.R;

public class Material extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        // Находим TextView
        TextView textViewAbout = findViewById(R.id.textViewAbout);

        // Получаем массив строк из ресурсов
        String[] materialsArray = getResources().getStringArray(R.array.materials_array);

        // Собираем строки в одну
        StringBuilder htmlBuilder = new StringBuilder();
        for (String material : materialsArray) {
            htmlBuilder.append(material).append("<br/>");
        }

        // Преобразуем HTML-строку в Spanned для отображения в TextView
        textViewAbout.setText(Html.fromHtml(htmlBuilder.toString()));

        // Делаем ссылки кликабельными
        textViewAbout.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
