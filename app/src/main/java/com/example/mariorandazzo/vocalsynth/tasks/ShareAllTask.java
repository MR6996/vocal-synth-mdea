package com.example.mariorandazzo.vocalsynth.tasks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.mariorandazzo.vocalsynth.R;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

public class ShareAllTask extends AsyncTask<String, Void, Void> {


    private Context ctx;

    public ShareAllTask(Context ctx) {
        this.ctx = ctx;
    }


    @Override
    protected Void doInBackground(String... params) {
        File resultDirectory = ctx.getExternalFilesDir(params[0]);
        File resultZip = new File(ctx.getExternalFilesDir(params[0]) + ".zip");

        if (resultDirectory != null && resultDirectory.listFiles().length > 0) {
            ZipUtil.pack(resultDirectory, resultZip);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/zip");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(resultZip));
            ctx.startActivity(Intent.createChooser(shareIntent, ctx.getString(R.string.exportChooserTitle)));
        } else {
            Toast.makeText(ctx, ctx.getString(R.string.exportFailed), Toast.LENGTH_LONG).show();
        }

        return null;
    }
}
