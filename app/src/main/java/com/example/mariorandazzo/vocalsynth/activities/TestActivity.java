package com.example.mariorandazzo.vocalsynth.activities;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;

import com.DspFaust.DspFaust;
import com.example.mariorandazzo.vocalsynth.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TestActivity extends BaseActivity {

    private DspFaust dspFaust;

    private List<SeekBar> bars = new ArrayList<>();
    private List<EditText> editTexts = new ArrayList<>();
    private Switch onOffSwitch;

    private Locale currentLocale;


    private CompoundButton.OnCheckedChangeListener switchListener
            = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked)
                dspFaust.start();
            else
                dspFaust.stop();
        }
    };
    private SeekBar.OnSeekBarChangeListener barChangeListener
            = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int sliderIndex = bars.indexOf(seekBar);
            float paramValue = barValue2paramValue(sliderIndex, progress);

            dspFaust.setParamValue(sliderIndex, paramValue);
            editTexts
                    .get(sliderIndex)
                    .setText(String.format(currentLocale, "%.2f", paramValue));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        currentLocale = getResources().getConfiguration().locale;

        bars.add((SeekBar) findViewById(R.id.freq_bar));
        bars.add((SeekBar) findViewById(R.id.gain_bar));
        bars.add((SeekBar) findViewById(R.id.voice_type_bar));
        bars.add((SeekBar) findViewById(R.id.vowel_bar));
        bars.add((SeekBar) findViewById(R.id.vibrato_freq_bar));
        bars.add((SeekBar) findViewById(R.id.vibrato_gain_bar));

        editTexts.add((EditText) findViewById(R.id.freq_edit_text));
        editTexts.add((EditText) findViewById(R.id.gain_edit_text));
        editTexts.add((EditText) findViewById(R.id.voice_type_edit_text));
        editTexts.add((EditText) findViewById(R.id.vowel_edit_text));
        editTexts.add((EditText) findViewById(R.id.vibrato_freq_edit_text));
        editTexts.add((EditText) findViewById(R.id.vibrato_gain_edit_text));

        onOffSwitch = findViewById(R.id.on_off_switch);
        onOffSwitch.setOnCheckedChangeListener(switchListener);

        int samplingRate = Integer.parseInt(Objects.requireNonNull(preferences.getString(getString(R.string.settings_sampling_rate_key), "48000")));
        int blockSize = Integer.parseInt(Objects.requireNonNull(preferences.getString(getString(R.string.settings_block_size_key), "128")));

        dspFaust = new DspFaust(samplingRate, blockSize);

        for (int i = 0; i < bars.size(); i++) {
            SeekBar bar = bars.get(i);
            EditText text = editTexts.get(i);

            bar.setOnSeekBarChangeListener(barChangeListener);
            text.setText(
                    String.format(currentLocale, "%.2f", barValue2paramValue(i, bar.getProgress()))
            );
        }
    }

    private float barValue2paramValue(int i, int progress) {
        float decimalProgress = (float) progress / bars.get(i).getMax();
        return decimalProgress * dspFaust.getParamMax(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (onOffSwitch.isChecked())
            dspFaust.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (onOffSwitch.isChecked())
            dspFaust.stop();
    }
}
