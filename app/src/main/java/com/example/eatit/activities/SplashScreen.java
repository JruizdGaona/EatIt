package com.example.eatit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.example.eatit.R;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        TextView txt = findViewById(R.id.text_app);
        TextView txtCharge = findViewById(R.id.text_cargando);

        TranslateAnimation an = new TranslateAnimation(00.0f, 0.0f, 1600.0f, 0.0f);
        TranslateAnimation an2 = new TranslateAnimation(00.0f, 0.0f, 0f, 50.0f);

        an.setDuration(2000);
        txt.startAnimation(an);

        an2.setDuration(1000);
        an2.setRepeatCount(Animation.INFINITE);
        an2.setRepeatMode(Animation.REVERSE);
        txtCharge.startAnimation(an2);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            finish();
        }, 2000);
    }
}
