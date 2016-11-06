package com.sumon.studymate;

import android.content.Intent;
import android.os.Build;
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
                Intent intent = new Intent(SplashActivity.this, LauncherActivity.class);
                intent.putExtra("count", 1);
                startActivity(intent);
                finish();
            }
        }, 3000);


    }

    public void fullScreenCall() {
        if (Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

}
