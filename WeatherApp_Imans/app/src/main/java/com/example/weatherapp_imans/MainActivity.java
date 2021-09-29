package com.example.weatherapp_imans;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String City;
    String API="528d7d5984ce4a099109851b4bfb9b7a";
    ImageView search;
    EditText ecity;
    TextView city,temp,sunset,sunrise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        {
            ecity=(EditText)findViewById(R.id.thecity);
            search=(ImageView)findViewById(R.id.search);

            city=(TextView)findViewById(R.id.city);
            temp=(TextView) findViewById(R.id.temp);
            sunrise=(TextView) findViewById(R.id.sunrise);
            sunset=(TextView) findViewById(R.id.sunset);

            //search button code:

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    City=ecity.getText().toString();
                    new weatherT().execute();
                }
            });
        }
    }


     class weatherT extends AsyncTask<String,Void,String> {

        @Override protected void onPreExecute(){
            super.onPreExecute();
        }

         @Override
         protected String doInBackground(String... args) {

            String response= HttpRequest.excuteGet("https://api.weatherbit.io/v2.0/current?q="+City+"&units=metric&appid="+API);
             return response;
         }
         @Override
         protected void onPostExecute(String result){
            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject mainjson =jsonObj.getJSONObject("main");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);
                JSONObject sys =jsonObj.getJSONObject("sys");

                String cityname=jsonObj.getString("Name");
                String tempr=mainjson.getString("Temp");
                Long snrise=sys.getLong("Sunrise");
                String sunrise =new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(snrise*1000));
                Long snset=sys.getLong("Sunset");
                String sunset =new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(snset*1000));

                city.setText(cityname);
                temp.setText(tempr+ "℃");
                sunrise.setText(snrise);
                sunset.setText(snset);




            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Error:" + e.toString(), Toast.LENGTH_SHORT).show();
            }
         }
     }
}