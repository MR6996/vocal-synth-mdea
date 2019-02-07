package com.example.mariorandazzo.vocalsynth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

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

    public void shareResults(View view) {
        File resultFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ApplicationConfig.FILE_NAME);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(resultFile));
        startActivity(Intent.createChooser(shareIntent, "Esporta..."));
    }
}
