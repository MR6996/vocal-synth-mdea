package com.example.mariorandazzo.vocalsynth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.example.mariorandazzo.vocalsynth.R;
import com.example.mariorandazzo.vocalsynth.tasks.ShareAllTask;

public class MainActivity extends BaseActivity {

    protected static final String GENDER_EXTRA = "com.randazzo.mario.gender.extra.key";

    private String resultDirectoryName;

    private RadioGroup genderRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultDirectoryName = preferences.getString(getString(R.string.settings_result_directory_key), "/samples");

        genderRadioGroup = findViewById(R.id.gender_radio_group);
    }

    public void startExperiment(View view) {
        Intent experimentIntent = new Intent(this, ExperimentActivity.class);
        experimentIntent.putExtra(GENDER_EXTRA, genderRadioGroup.getCheckedRadioButtonId() == R.id.m_radio);
        startActivity(experimentIntent);
    }

    public void goToTest(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }

    public void shareResults(View view) {
        new ShareAllTask(this).execute(resultDirectoryName);
    }

    public void goToSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
