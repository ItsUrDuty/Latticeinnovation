package com.kartavya.latticeinnovation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import io.paperdb.Paper;

public class WeatherActivity extends AppCompatActivity {

    private TextView tempC,tempF,land,lati,humidity,cloud,district,stateEt;
    private EditText editTextPin;
    private Button buttonSub,buttonCheck;
    private LinearLayout linearLayout;
    private String pin,dist,state;
    public RequestQueue requestQueue,requestQueue2;
    public ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        linearLayout = findViewById(R.id.sdmssbfjdfbjf);



        tempC = findViewById(R.id.njvhcgcvcvc);
        tempF = findViewById(R.id.byhxcfycyc);
        land = findViewById(R.id.njvbjvhchcgc);
        lati = findViewById(R.id.njbjvjvhcgc);
        humidity = findViewById(R.id.njknjbjvhcgc);
        cloud = findViewById(R.id.njvbhvhvhcgc);
        district = findViewById(R.id.xznxjbjjvvzbcshcv);
        stateEt = findViewById(R.id.xscsjbcsjcbjvvhs);

        editTextPin = findViewById(R.id.vhchcgxfxfz);

        buttonSub = findViewById(R.id.submibjvt_btn);

        buttonCheck = findViewById(R.id.bjvvfhycgxfxfr);

        loadingBar = new ProgressDialog(this);
        loadingBar.setCanceledOnTouchOutside(false);

        requestQueue = Volley.newRequestQueue(this);

        requestQueue2 = Volley.newRequestQueue(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        editTextPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(editTextPin.getText().toString()))
                {
                    buttonCheck.setBackgroundColor(Color.BLACK);
                    buttonCheck.setTextColor(Color.WHITE);
                }
                else
                {
                    buttonCheck.setBackgroundColor(Color.parseColor("#CCCCCC"));
                    buttonCheck.setTextColor(Color.BLACK);
                }

            }
        });


        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin = editTextPin.getText().toString();

                if (TextUtils.isEmpty(pin))
                {
                    editTextPin.setError("Pin code is required");
                    editTextPin.requestFocus();
                }
                else
                {
                    linearLayout.setVisibility(View.INVISIBLE);
                    buttonSub.setVisibility(View.INVISIBLE);
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), 0);
                    FetchData(pin);
                }
            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckWeather(dist);
            }
        });









    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.myprofile)
        {
            Intent intent = new Intent(WeatherActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.logout)
        {
            loadingBar.setTitle("Logout..");
            loadingBar.show();

            Paper.book().delete("Name");
            Paper.book().delete("Phone");
            Paper.book().delete("Gender");
            Paper.book().delete("DOB");
            Paper.book().delete("Add1");
            Paper.book().delete("PinCode");
            Paper.book().delete("District");
            Paper.book().delete("State");

            if (Paper.book().exist("Add2"))
            {
                Paper.book().delete("Add2");
            }

            final Handler handler3 = new Handler(Looper.getMainLooper());
            handler3.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(WeatherActivity.this, LandingScreenActivity.class);
                    startActivity(intent);
                    finish();
                    loadingBar.dismiss();

                }
            }, 1500);

        }
        return super.onOptionsItemSelected(item);
    }

    private void CheckWeather(String dist) {
        loadingBar.setMessage("Searching..");
        loadingBar.show();

        String url = "https://api.weatherapi.com/v1/current.json?key=35c9f92ac5bf4df0811144140212307&q="+dist;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {

                    try {


                        JSONObject location = response.getJSONObject("location");

                        String lat = location.getString("lat");
                        String lon = location.getString("lon");

                        lati.setText(lon);
                        land.setText(lat);

                        JSONObject current = response.getJSONObject("current");

                        String tepC = current.getString("temp_c");
                        String tepF = current.getString("temp_f");

                        String hum = current.getString("humidity");
                        String clo = current.getString("cloud");

                        tempC.setText(tepC+" ℃");
                        tempF.setText(tepF+" ℉");
                        humidity.setText(hum);
                        cloud.setText(clo);

                        linearLayout.setVisibility(View.VISIBLE);
                        loadingBar.dismiss();











                    } catch (JSONException e) {
                        Toast.makeText(WeatherActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }


                }, error -> {

            loadingBar.dismiss();


        });

        requestQueue2.add(request);


    }

    private void FetchData(String pin) {

        loadingBar.setMessage("Checking..");
        loadingBar.show();

        String url = "https://api.postalpincode.in/pincode/"+pin;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {

            try {

                JSONObject jsonObject =  response.optJSONObject(0);
                JSONArray jsonArray = jsonObject.getJSONArray("PostOffice");
                JSONObject hit =  jsonArray.getJSONObject(0);
                dist = hit.getString("District");
                state = hit.getString("State");

                district.setText("District : "+dist);
                stateEt.setText("State : "+state);

                loadingBar.dismiss();
                buttonSub.setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                Toast.makeText(WeatherActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }

        }, error -> {

            loadingBar.dismiss();
            //Toast.makeText(MainActivity.this, "Error occurred: try again!", Toast.LENGTH_SHORT).show();


        });


        requestQueue.add(request);

    }
}