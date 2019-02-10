package com.example.mariorandazzo.vocalsynth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.DspFaust.DspFaust;
import com.example.mariorandazzo.vocalsynth.R;
import com.example.mariorandazzo.vocalsynth.model.Sample;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ExperimentActivity extends BaseActivity {

    private int samplesCount = 1;
    private List<Sample> samples = new ArrayList<>();
    private String resultDirectoryName;
    private int samplesNumber;

    private DspFaust dspFaust;
    private int samplingRate;
    private int blockSize;
    private Random randGen;
    private int currentFrequency;
    private int currentGender;
    private float currentVowel;

    private ToggleButton audioToggle;
    private Button confirmButton;
    private TextView progressView;
    private RadioGroup vowelGroup;


    private CompoundButton.OnCheckedChangeListener audioCheckedChangeListener
            = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            confirmButton.setEnabled(isChecked);
            vowelGroup.setEnabled(isChecked);
            if (isChecked) dspFaust.start();
            else dspFaust.stop();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);

        samplesNumber = Integer.parseInt(Objects.requireNonNull(preferences.getString(getString(R.string.settings_samples_number_key), "15")));
        resultDirectoryName = preferences.getString(getString(R.string.settings_result_directory_key), "/sample");
        samplingRate = Integer.parseInt(Objects.requireNonNull(preferences.getString(getString(R.string.settings_sampling_rate_key), "48000")));
        blockSize = Integer.parseInt(Objects.requireNonNull(preferences.getString(getString(R.string.settings_block_size_key), "128")));


        dspFaust = new DspFaust(samplingRate, blockSize);
        randGen = new Random(new Date().getTime());

        audioToggle = findViewById(R.id.audio_toggle);
        audioToggle.setOnCheckedChangeListener(audioCheckedChangeListener);

        vowelGroup = findViewById(R.id.vowel_radio_group);

        progressView = findViewById(R.id.progress);
        confirmButton = findViewById(R.id.confirm_button);

        generateTestPoint();
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

        if (samplesCount > samplesNumber)
            finishExperiment();
        else
            goToNextStep();
    }

    private void goToNextStep() {
        samples.add(new Sample(currentFrequency, currentGender, currentVowel, getVowel(vowelGroup.getCheckedRadioButtonId())));
        generateTestPoint();
    }

    private int getVowel(int radioButtonId) {
        switch (radioButtonId) {
            case R.id.a_radio:
                return 0;
            case R.id.e_radio:
                return 1;
            case R.id.i_radio:
                return 2;
            case R.id.o_radio:
                return 3;
            case R.id.u_radio:
                return 4;
        }

        return -1;
    }

    private void finishExperiment() {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        File reusltFile = new File(
                getExternalFilesDir(resultDirectoryName),
                "sample_" + new Date().getTime() + ".csv"
        );

        try {
            fileWriter = new FileWriter(reusltFile);
            csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));

            for (Sample s : samples)
                csvFilePrinter.printRecord(s.getFrequency(), s.getGender(), s.getVowel(), s.getPredictedVowel());

            Intent finishIntent = new Intent(this, FinishActivity.class);
            finishIntent.putExtra(FinishActivity.FILEPATH_RESULT, reusltFile.getPath());
            startActivity(finishIntent);
        } catch (IOException e) {
            showTerminateDialog(getString(R.string.error), getString(R.string.io_error));
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
                if (csvFilePrinter != null) {
                    csvFilePrinter.close();
                }
            } catch (IOException e) {
                showTerminateDialog(getString(R.string.error), getString(R.string.stream_closing_error));
            }

        }
    }

    private void generateTestPoint() {
        progressView.setText(
                String.format(
                        getResources().getConfiguration().locale,
                        getString(R.string.progress_format), samplesCount, samplesNumber)
        );

        currentFrequency = randGen.nextInt((int) dspFaust.getParamMax(0) - 80) + 80;
        dspFaust.setParamValue(0, currentFrequency);

        currentGender = randGen.nextInt((int)dspFaust.getParamMax(2)+1);
        dspFaust.setParamValue(2, currentGender);

        currentVowel = randGen.nextFloat() * dspFaust.getParamMax(3);
        dspFaust.setParamValue(3, currentVowel);
    }

}
