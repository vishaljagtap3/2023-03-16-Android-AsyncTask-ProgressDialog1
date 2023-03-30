package in.bitcode.progressdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupListeners();
    }

    private void setupListeners() {
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String localPath = Util.download("https://bitcode.in/file1.zip");

                String [] fileUrls = {
                        "https://bitcode.in/file1",
                        "https://bitcode.in/file2",
                        "https://bitcode.in/file3",
                };

                new DownloadThread()
                        .execute(fileUrls);
            }
        });
    }

    private void initViews() {
        btnDownload = findViewById(R.id.btnDownload);
    }

    class DownloadThread extends AsyncTask<String, Integer, Float> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("tag", "onPre: " + Thread.currentThread().getName());
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("BitCode");
            progressDialog.setMessage("Downloading...");
            progressDialog.setCancelable(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            btnDownload.setText("Download started");
        }

        @Override
        protected Float doInBackground(String [] input) {
            Log.e("tag", "doInBg: " + Thread.currentThread().getName());

            for(String url : input) {
                progressDialog.setMessage("Downloading -> " + url);
                for (int i = 0; i <= 100; i++) {
                    //Log.e("tag", "Download: " + i  +"%");
                    //btnDownload.setText(i+"%"); //can't touch views
                    Integer [] progress = new Integer[1];
                    progress[0] = i;
                    publishProgress(progress);

                    progressDialog.setProgress(i);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return 12.12f;
        }

        @Override
        protected void onPostExecute(Float res) {
            progressDialog.dismiss();
            Log.e("tag", "onPost: " + Thread.currentThread().getName());
            btnDownload.setText("Download finished " + res);
            super.onPostExecute(res);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            btnDownload.setText(values[0] + "%");
        }
    }
}