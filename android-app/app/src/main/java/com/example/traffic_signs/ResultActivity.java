package com.example.traffic_signs;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        Button openLinkButton = findViewById(R.id.buttonn);

        openLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://60a8-185-117-151-154.ngrok-free.app";
                // Создайте Intent с ACTION_VIEW и передайте в него URI для открытия в браузере
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                // Запустите браузер с заданным URL
                startActivity(intent);
            }
        });
    }
}
