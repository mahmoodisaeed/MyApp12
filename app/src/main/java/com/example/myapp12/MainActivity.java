package com.example.myapp12;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.myapp12.pray.PrayTimesClass;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView txt11 = findViewById(R.id.txt1);
        /*
        new Thread(new Runnable() {
            public void run() {
//get from webservice
                runOnUiThread(new Runnable() {
                    public void run() {
                        AppCompatButton btn1 = findViewById(R.id.btn1);
                        btn1.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        TextView txt11 = findViewById(R.id.txt1);
                                                        int i = 10;
                                                        while (i-- > 0) {
                                                            SystemClock.sleep(1000);
                                                            txt11.setText(Integer.toString(i));
                                                            //Toast.makeText(MainActivity.this, Integer.toString(i), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                        );
                        }
                });
            }
        }).start();
*/


        //String url = "http://api.aladhan.com/v1/timingsByCity?city=Tehran&country=Iran&method=8";
        String url = "http://api.aladhan.com/v1/timingsByCity?city=Karaj&country=Iran&method=8";
        // http://api.openweathermap.org/data/2.5/weather?q=Canberra&units=metric
        AsyncHttpClient client = new AsyncHttpClient();

        //client.get(url, new JsonHttpResponseHandler()
        //{
        //@Override
        //public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        //Log.d("levels", "onSuccess");
        //}
        //@Override
        //public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        //Log.d("levels","onFailure");
        //}
        //}
        //);
        //}
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("levels","onSuccess");

                Gson gson = new Gson();
                PrayTimesClass times =  gson.fromJson(response.toString(), PrayTimesClass.class);
                Log.d("levels","before get data");

                String maqrib = times.getData().getTimings().getMaghrib();
                Log.d("levels",maqrib);
                Toast.makeText(MainActivity.this, maqrib, Toast.LENGTH_SHORT).show();
                //super.onSuccess(statusCode, headers, response);

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("levels","m1");
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("levels","m2");
            }
        });
    }
}
