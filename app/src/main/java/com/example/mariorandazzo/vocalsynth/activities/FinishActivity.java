package com.example.mariorandazzo.vocalsynth.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.mariorandazzo.vocalsynth.R;
import com.example.mariorandazzo.vocalsynth.tasks.ShareTask;

import java.io.File;

public class FinishActivity extends BaseActivity {

    protected final static String FILEPATH_RESULT = "finish_activity_filepath_result";

    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Bundle extrasBundle = getIntent().getExtras();
        if (extrasBundle != null)
            resultUri = Uri.fromFile(new File(extrasBundle.getString(FILEPATH_RESULT)));
    }


    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
    }

    public void export(View view) {
        new ShareTask(this).execute(resultUri);
    }
}
