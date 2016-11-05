package com.sumon.studymate;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private ImageView ivLogo;
    private TextView qouteTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        qouteTV = (TextView) findViewById(R.id.qouteTV);
        qouteTV.setVisibility(View.GONE);

        CustomAnimation.zoomIn(ivLogo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                qouteTV.startAnimation(CustomAnimation.blink(1500));
                qouteTV.setVisibility(View.VISIBLE);
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LauncherActivity.class));
                finish();
            }
        }, 3000);


    }
}
