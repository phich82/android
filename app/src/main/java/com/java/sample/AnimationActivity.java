package com.java.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimationActivity extends AppCompatActivity {

    ImageView imageAlphaAnimation, imageRotateAnimation, imageScaleAnimation,imageTranslateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        imageAlphaAnimation = findViewById(R.id.imageViewAlpha);
        imageRotateAnimation = findViewById(R.id.imageViewRotate);
        imageScaleAnimation = findViewById(R.id.imageViewScale);
        imageTranslateAnimation = findViewById(R.id.imageViewTranslate);

        Animation animationAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        Animation animationRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Animation animationScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        Animation animationTranslate = AnimationUtils.loadAnimation(this, R.anim.translate);

        // TODO: 1. Alpha animation
        imageAlphaAnimation.setOnClickListener(view -> {
            view.startAnimation(animationAlpha);
        });

        // TODO: 2. Alpha animation
        imageRotateAnimation.setOnClickListener(view -> {
            view.startAnimation(animationRotate);
        });
        // TODO: 3. Scale animation
        imageScaleAnimation.setOnClickListener(view -> {
            view.startAnimation(animationScale);
        });
        // TODO: 4. Translate animation
        imageTranslateAnimation.setOnClickListener(view -> {
            view.startAnimation(animationTranslate);
        });


    }
}