package com.example.mariorandazzo.vocalsynth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void startExperiment(View view) {
        startActivity(new Intent(this, ExperimentActivity.class));
    }

    public void goToTest(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }
}
