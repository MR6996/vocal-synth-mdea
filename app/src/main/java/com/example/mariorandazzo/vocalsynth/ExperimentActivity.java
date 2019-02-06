package com.example.mariorandazzo.vocalsynth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.DspFaust.DspFaust;

public class ExperimentActivity extends AppCompatActivity {

    private int samplesCount = 0;
    private DspFaust dspFaust;
    private ToggleButton audioToggle;
    private Button confirmButton;


    private CompoundButton.OnCheckedChangeListener audioCheckedChangeListener
            = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            confirmButton.setEnabled(isChecked);
            if (isChecked) dspFaust.start();
            else dspFaust.stop();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);

        dspFaust = new DspFaust(ApplicationConfig.SR, ApplicationConfig.blockSize);

        audioToggle = findViewById(R.id.audio_toggle);
        audioToggle.setOnCheckedChangeListener(audioCheckedChangeListener);

        confirmButton = findViewById(R.id.confirm_button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (audioToggle.isChecked())
            dspFaust.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (audioToggle.isChecked())
            dspFaust.stop();
    }

    public void confirmResponse(View view) {
        audioToggle.setChecked(false);
        samplesCount++;

        if (samplesCount == ApplicationConfig.NUMBER_SAMPLE) {
            startActivity(new Intent(this, FinishActivity.class));
        }
    }


}
