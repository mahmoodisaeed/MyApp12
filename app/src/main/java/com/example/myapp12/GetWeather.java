package com.example.myapp12;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp12.weather.WeatherClass;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.InputStream;

import cz.msebera.android.httpclient.Header;

public class GetWeather extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_weather);

        AppCompatButton btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshCity();
            }
        });

        String url = "https://api.openweathermap.org/data/2.5/forecast?q=Tehran&appid=6752653580d6f59f699aa7ccbda4e19c";



        /* -------
        SSLSocketFactory sf = new SSLSocketFactory(
                SSLContext.getInstance("TLS"),
                SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme sch = new Scheme("https", 443, sf);
        httpclient.getConnectionManager().getSchemeRegistry().register(sch);

        HttpGet httpget = new HttpGet("https://host/");
        */

        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("levels","11");
        client.get(url, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("levels","12");

                Gson gson = new Gson();
                WeatherClass weather = gson.fromJson(response.toString(),WeatherClass.class);
                String dt_txt = weather.getList().get(0).getDtTxt();
                Log.d("levels weather",dt_txt);
                ShowADay(weather,0, "txtDay1", "img1");
                ShowADay(weather,1, "txtDay2", "img2");
                ShowADay(weather,2, "txtDay3", "img3");
                ShowADay(weather,3, "txtDay4", "img4");
                ShowADay(weather,4, "txtDay5", "img5");
/*
                ((TextView)findViewById(R.id.txtDay1)).setText(
                        "" + weather.getList().get(0).getWeather().get(0).getDescription()
                                + ", " + "" + String.format("%.0f",weather.getList().get(0).getMain().getTemp()-273.15) + "̊C");
                new DownloadImageFromInternet((ImageView) findViewById(R.id.img1)).execute("http://openweathermap.org/img/w/10d.png");
*/
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("levels","m1");
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //Log.d("levels",errorResponse.toString());
            }
        });
    }

    private void RefreshCity() {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="
            + ((EditText)findViewById(R.id.edtCityName)).getText()
            + "&appid=6752653580d6f59f699aa7ccbda4e19c";
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("levels","11");
        client.get(url, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("levels","12");

                Gson gson = new Gson();
                WeatherClass weather = gson.fromJson(response.toString(),WeatherClass.class);
                String dt_txt = weather.getList().get(0).getDtTxt();
                Log.d("levels weather",dt_txt);
                ShowADay(weather,0, "txtDay1", "img1");
                ShowADay(weather,1, "txtDay2", "img2");
                ShowADay(weather,2, "txtDay3", "img3");
                ShowADay(weather,3, "txtDay4", "img4");
                ShowADay(weather,4, "txtDay5", "img5");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("levels","m1");
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //Log.d("levels",errorResponse.toString());
            }
        });

    }

    private void ShowADay(WeatherClass weather, int i, String txtDayId, String imgId)
    {
        int resId = getResources().getIdentifier(txtDayId, "id", getPackageName());
        TextView txtDay0 = (TextView)findViewById(resId);
        txtDay0.setText(
                txtDay0.getText().subSequence(0,12)
                        + " " + weather.getList().get(i).getWeather().get(0).getDescription()
                        + ", " + String.format("%.0f",weather.getList().get(i).getMain().getTemp()-273.15)
                        + "̊C"
        );
        resId = getResources().getIdentifier(imgId, "id", getPackageName());
        ImageView img0 = (ImageView)findViewById(resId);
        new DownloadImageFromInternet(img0).execute("http://openweathermap.org/img/w/"
                + weather.getList().get(i).getWeather().get(0).getIcon() +
                ".png");
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

}