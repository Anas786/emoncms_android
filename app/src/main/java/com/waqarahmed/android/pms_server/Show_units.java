package com.waqarahmed.android.pms_server;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

public class Show_units extends AppCompatActivity {


    public static final String UNIT = "UNIT";
    public static final String UPDATE = "UPDATE";
    public static final String AMOUNT = "AMOUNT";
    public static final String BILL = "BILL";
    ProgressBar spinner;
    Button button;
    String Api_Key;
    String JSON;
    String id,node,value="0.0",name;
    Intent intent,intent1;
    JSONArray json_Array;
    SharedPreferences sharedprefrences;
    SharedPreferences.Editor editor;
    TextView energy_units;
    TextView time_update;
    TextView amount;
    TextView bill;
    String currentDateTimeString;
    Bill_Calulator calulator;
    double unit=0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_units);
        energy_units= (TextView) findViewById(R.id.energy_units);
        time_update= (TextView) findViewById(R.id.update);
        amount= (TextView) findViewById(R.id.amount);
        bill= (TextView) findViewById(R.id.bill);
        calulator=new Bill_Calulator();
        sharedprefrences=getSharedPreferences(welcome_post.MY_WELCOME_PREF,MODE_PRIVATE);
        spinner= (ProgressBar) findViewById(R.id.spinner);
        spinner.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        spinner.setVisibility(View.GONE);
        settingViews();
    }

    public void refreshButton(View view) {
        Api_Key=sharedprefrences.getString(welcome_post.API_KEY," ");
        RetrieveFeedTask rf = new RetrieveFeedTask();
        rf.execute();
    }

    public void parsingJSON() {
        try {
            json_Array = new JSONArray(JSON);
            int count = 0;

            while(count<json_Array.length()){
                JSONObject JO = json_Array.getJSONObject(count);
                id = JO.getString("id");
                node = JO.getString("tag");
                value = JO.getString("value");
                name=JO.getString("name");
                if(node.equals("Node 1")&&name.equals("Energy")){
                    //Log.d("LOGTAG",id+" "+node+" "+name+" "+value+"\n");
                    savingData();
                    settingViews();
                    break;
                }
                count++;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void settingViews() {

        String myunit=sharedprefrences.getString(UNIT,"0.0");
        energy_units.setText(String.valueOf(unit));
        time_update.setText(sharedprefrences.getString(UPDATE,"No update received"));
        amount.setText(sharedprefrences.getString(AMOUNT,"0")+" PKR");
        bill.setText(sharedprefrences.getString(BILL,"0")+" PKR");

    }

    public String getTime(){
        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return currentDateTimeString;
    }

    public void savingData() {
        editor=sharedprefrences.edit();
        unit = Double.parseDouble(value);
        editor.putString(UNIT, String.valueOf(unit));
        editor.putString(UPDATE,getTime());
        editor.putString(AMOUNT,calulator.getAmount(value));
        editor.putString(BILL,calulator.getBill(calulator.getAmount(value)));
        editor.commit();
    }

    public void changeQR(View view) {
        intent=new Intent(this,welcome_post.class);
        startActivity(intent);
    }

    public class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {

            String API_URL = "http://www.corbitweb.com/emoncms/feed/list.json";
            String API_KEY = Api_Key;
            String Json_String;
            try {
                URL url = new URL(API_URL +"&apikey="+API_KEY);
                Log.i("URL",url.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((Json_String= bufferedReader.readLine())!=null){
                    stringBuilder.append(Json_String+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;

        }

        public void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            spinner.setVisibility(View.GONE);
            Log.i("INFO", response);
            JSON = response;
            parsingJSON();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


}
