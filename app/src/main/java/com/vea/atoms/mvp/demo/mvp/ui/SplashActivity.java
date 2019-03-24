package com.vea.atoms.mvp.demo.mvp.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.vea.atoms.mvp.commonsdk.core.RouterHub;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance().build(RouterHub.APP_MAIN_ACTIVITY).navigation();
                finish();
            }
        }, 1000);
    }
}
