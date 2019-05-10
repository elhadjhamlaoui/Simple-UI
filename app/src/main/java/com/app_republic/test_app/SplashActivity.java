package com.app_republic.test_app;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        //setupWindowAnimations();

        RelativeLayout relativeLayout = findViewById(R.id.button);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);

                    View sharedView = findViewById(R.id.shared_view);
                    String transitionName = getString(R.string.shared_view);

                    ActivityOptions transitionActivityOptions = ActivityOptions.
                            makeSceneTransitionAnimation(SplashActivity.this, sharedView, transitionName);
                    startActivity(i, transitionActivityOptions.toBundle());

                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
    private void setupWindowAnimations() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.activity_explode);
            getWindow().setExitTransition(explode);
        }
    }
}
