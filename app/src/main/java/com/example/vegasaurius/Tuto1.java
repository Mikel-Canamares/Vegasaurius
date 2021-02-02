package com.example.vegasaurius;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class Tuto1 extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragment(new Step.Builder().setTitle("")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#00574B")) // int background color
                .setDrawable(R.drawable.screenshot_1) // int top drawable
                .setSummary("This is summary")
                .build());

        addFragment(new Step.Builder().setTitle("")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#00574B")) // int background color
                .setDrawable(R.drawable.screenshot_2) // int top drawable
                .setSummary("This is summary")
                .build());

        addFragment(new Step.Builder().setTitle("")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#00574B")) // int background color
                .setDrawable(R.drawable.screenshot_3) // int top drawable
                .setSummary("This is summary")
                .build());

        addFragment(new Step.Builder().setTitle("")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#00574B")) // int background color
                .setDrawable(R.drawable.screenshot_4) // int top drawable
                .setSummary("This is summary")
                .build());

        addFragment(new Step.Builder().setTitle("")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#00574B")) // int background color
                .setDrawable(R.drawable.screenshot_5) // int top drawable
                .setSummary("This is summary")
                .build());

        addFragment(new Step.Builder().setTitle("")
                .setContent("")
                .setBackgroundColor(Color.parseColor("#00574B")) // int background color
                .setDrawable(R.drawable.screenshot_6) // int top drawable
                .setSummary("")
                .build());

        setPrevText(""); // Previous button text
        setNextText(""); // Next button text
        setFinishText("Terminar"); // Finish button text
        setCancelText("Volver"); // Cancel button text

    }


    @Override
    public void currentFragmentPosition(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

