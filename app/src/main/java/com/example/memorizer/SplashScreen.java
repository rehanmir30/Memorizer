package com.example.memorizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        logo=findViewById(R.id.logo);
        slogan=findViewById(R.id.slogan);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(3000);

        logo.startAnimation(fadeIn);
        slogan.startAnimation(fadeIn);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashScreen.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
        },4000);
    }
}