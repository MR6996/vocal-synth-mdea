package com.example.mariorandazzo.vocalsynth.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.mariorandazzo.vocalsynth.ApplicationConfig;
import com.example.mariorandazzo.vocalsynth.R;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

public class MainActivity extends BaseActivity {

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
        File resultDirectory = getExternalFilesDir(ApplicationConfig.RESULT_DIR);
        File resultZip = new File(getExternalFilesDir(ApplicationConfig.RESULT_DIR) + ".zip");
        ZipUtil.pack(resultDirectory, resultZip);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/zip");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(resultZip));
        startActivity(Intent.createChooser(shareIntent, "Esporta..."));
    }
}
