package com.example.mariorandazzo.vocalsynth.tasks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.mariorandazzo.vocalsynth.R;

public class ShareTask extends AsyncTask<Uri, Void, Void> {

    private Context ctx;

    public ShareTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected Void doInBackground(Uri... params) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_STREAM, params[0]);
        ctx.startActivity(Intent.createChooser(shareIntent, ctx.getString(R.string.exportChooserTitle)));
        return null;
    }
}
