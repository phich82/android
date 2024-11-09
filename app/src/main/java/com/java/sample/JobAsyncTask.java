package com.java.sample;


import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

public class JobAsyncTask extends AsyncTask<Void, String, String> {

    private TextView textView;

    public JobAsyncTask(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.textView.setText("Starting...\n");
    }
    @Override
    protected String doInBackground(Void... voids) {
        for (int i=1; i <= 5; i++) {
            publishProgress("Done: " + i);
        }
        return "Finished\n";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        this.textView.append(s);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        this.textView.append(values[0]);
    }
}
