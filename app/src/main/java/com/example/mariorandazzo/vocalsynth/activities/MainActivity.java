package com.example.mariorandazzo.vocalsynth.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mariorandazzo.vocalsynth.R;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

public class MainActivity extends BaseActivity {

    private String resultDirectoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultDirectoryName = preferences.getString(getString(R.string.settings_result_directory_key), "/");
    }

    public void startExperiment(View view) {
        startActivity(new Intent(this, ExperimentActivity.class));
    }

    public void goToTest(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }

    public void shareResults(View view) {
        File resultDirectory = getExternalFilesDir(resultDirectoryName);
        File resultZip = new File(getExternalFilesDir(resultDirectoryName) + ".zip");

        if (resultDirectory != null && resultDirectory.listFiles().length > 0) {
            ZipUtil.pack(resultDirectory, resultZip);

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/zip");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(resultZip));
            startActivity(Intent.createChooser(shareIntent, getString(R.string.exportChooserTitle)));
        } else {
            Toast.makeText(this, getString(R.string.exportFailed), Toast.LENGTH_LONG).show();
        }
    }

    public void goToSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
