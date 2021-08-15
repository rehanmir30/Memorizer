package com.example.memorizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        aSwitch = findViewById(R.id.nightmode);
        aSwitch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       if (aSwitch.isChecked()) {

                           AppCompatDelegate
                                   .setDefaultNightMode(
                                           AppCompatDelegate
                                                   .MODE_NIGHT_YES);
                           Toast.makeText(getApplicationContext(),"Night mode enabled",Toast.LENGTH_SHORT).show();
                       }
                       else {
                           AppCompatDelegate
                                   .setDefaultNightMode(
                                           AppCompatDelegate
                                                   .MODE_NIGHT_NO);
                           Toast.makeText(getApplicationContext(),"Night mode disabled",Toast.LENGTH_SHORT).show();
                       }

                    }
                });
    }
}