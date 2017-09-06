package com.waqarahmed.android.pms_server;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class welcome_post extends AppCompatActivity {

    public static final String MY_WELCOME_PREF = "MY_WELCOME_PREF";
    public static final String API_KEY = "API_KEY";
    Intent intent;
    String se;
    Button scanButton;
    SharedPreferences sharedprefrences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_post);
        intent=new Intent(this,Show_units.class);
        sharedprefrences=getSharedPreferences(MY_WELCOME_PREF,MODE_PRIVATE);
        skipping();
        scanButton = (Button) findViewById(R.id.scan_btn);
    }

    public void skipping() {
        String check=sharedprefrences.getString(API_KEY,null);
        if(check!=null){
            startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * Parse the result into an instance of the IntentResult class we imported
         */
        IntentResult scanningIntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        /**
         * Check the scanningIntentResult object is it null or not, to proceed only if we have a valid result
         */
        if (scanningIntentResult != null) {
            /**
             * Retrieve the content and the format of the scan as strings value.
             */
            String scanContent = scanningIntentResult.getContents();
            /**
             * if condition after getting scanContent,
             * checking on them if there are consisting real data or not.
             */
            if(scanContent != null) {
                /**
                 * Now our program has the format and content of the scanned data,
                 * so you can do whatever you want with it.
                 */

                se = null;
                Pattern pattern = Pattern.compile("^(http[s]?)://([^:/\\s]+.*)/app\\?[readkey=]+=([^&]+)#myelectric");
                Matcher matcher = pattern.matcher(scanContent);
                if (matcher.matches() && matcher.groupCount() == 3){

                    se = matcher.group(3);
                    Toast.makeText(this,"Device Identified",Toast.LENGTH_SHORT).show();
                    saveData();

                }else {
                    Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT).show();
            }
        } else {
            /**
             * If scan data is not received
             * (for example, if the user cancels the scan by pressing the back button),
             * we can simply output a message.
             */
            Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveData() {
        editor=sharedprefrences.edit();
        editor.putString(API_KEY,se);
        editor.commit();
        startActivity(intent);

    }

    public void startScan(View view) {
        switch (view.getId()) {
            case R.id.scan_btn:
                /**
                 * create an instance of the IntentIntegrator class we imported,
                 * and then call on the initiateScan() method to start scanning
                 */
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;
        }
        Toast.makeText(this, "CLICKED", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        finish();
    }


}
