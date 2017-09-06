package com.waqarahmed.android.pms_server;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    ProgressBar spinner;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent=new Intent(this,welcome_post.class);
        spinner= (ProgressBar) findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);
        spinner.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        maintask task=new maintask();
        task.execute();
    }

    public class  maintask extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] params) {
            try {

                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            spinner.setVisibility(View.GONE);
            startActivity(intent);

        }
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}
