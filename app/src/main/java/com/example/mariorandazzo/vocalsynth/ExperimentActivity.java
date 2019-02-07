package com.example.mariorandazzo.vocalsynth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.DspFaust.DspFaust;
import com.example.mariorandazzo.vocalsynth.model.Sample;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExperimentActivity extends AppCompatActivity {


    private int samplesCount = 1;
    private List<Sample> samples = new ArrayList<>();

    private DspFaust dspFaust;
    private int currentGender;
    private float currentVowel;

    private ToggleButton audioToggle;
    private Button confirmButton;
    private TextView progressView;


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

        progressView = findViewById(R.id.progress);
        progressView.setText(
                String.format(
                        getResources().getConfiguration().locale,
                        "Test: %d/%d", samplesCount, ApplicationConfig.SAMPLES_NUMBER)
        );

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
        progressView.setText(
                String.format(
                        getResources().getConfiguration().locale,
                        "Test: %d/%d", samplesCount, ApplicationConfig.SAMPLES_NUMBER)
        );

        if (samplesCount > ApplicationConfig.SAMPLES_NUMBER)
            finishExperiment();
        else
            goToNextStep();
    }

    private void goToNextStep() {
        samples.add(new Sample(currentGender, currentVowel, 0));
    }

    private void finishExperiment() {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;

        try {
            fileWriter = new FileWriter(new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ApplicationConfig.FILE_NAME));
            csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));

            for (Sample s : samples)
                csvFilePrinter.printRecord(s.getR(), s.getTheta(), s.getVowel());

            startActivity(new Intent(this, FinishActivity.class));
        } catch (IOException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }

        }
    }

    protected void showDialog(String title, String message) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
