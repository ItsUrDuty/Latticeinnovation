package com.kartavya.latticeinnovation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private EditText editTextPhone, editTextName, editTextAdd1, editTextAdd2, editTextPin;
    private Button buttonSub,buttonCheck;
    private TextView textViewDob,textViewDis,textViewState;
    private RelativeLayout imageViewCalender;
    private String phone,name,add1,add2,pin,dateB,dist,state;
    private String getPhone="";
    private String gender;
    public RequestQueue requestQueue;
    public ProgressDialog loadingBar;
    final Calendar myCalendar = Calendar.getInstance();
    final  Calendar today = Calendar.getInstance();
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(getApplicationContext());

        editTextPhone = findViewById(R.id.bjvvsdsdyh);
        editTextName = findViewById(R.id.ssnjdsdsdsd);
        editTextAdd1 = findViewById(R.id.buguvyvybhvhcgc);
        editTextAdd2 = findViewById(R.id.cxcxcdvcdc);
        editTextPin = findViewById(R.id.bxvvvhvhhgc);

        buttonCheck = findViewById(R.id.xnsjcsbcjb);
        buttonSub = findViewById(R.id.submit_btn);

        textViewDob = findViewById(R.id.sxsncjsbcsjcvsvc);
        textViewDis = findViewById(R.id.xznxjzbcshcv);
        textViewState = findViewById(R.id.xscsjbcsjcvhs);

        spinner = findViewById(R.id.spinner_main2);

        imageViewCalender = findViewById(R.id.xnxcbxcxvcxxc);

        loadingBar = new ProgressDialog(this);
        loadingBar.setCanceledOnTouchOutside(false);

        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();

        getPhone = intent.getStringExtra("Phone");

        editTextPhone.setText(getPhone);



        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }
        };


        imageViewCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckForm();
            }
        });

        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckPin();
            }
        });

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


        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this,R.array.Spinner_items, android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);



        int age = today.get(Calendar.YEAR) - myCalendar.get(Calendar.YEAR);

        dateB = sdf.format(myCalendar.getTime())+"    -   "+age+" Years";

        textViewDob.setText(dateB);
    }

    private void CheckPin() {

        pin = editTextPin.getText().toString();

        if (TextUtils.isEmpty(pin))
        {
            editTextPin.setError("Pin code is required");
            editTextPin.requestFocus();
        }
        else
            {
                FetchData(pin);
            }

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

                textViewDis.setText("District : "+dist);
                textViewState.setText("State : "+state);

                loadingBar.dismiss();


            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }

        }, error -> {

            loadingBar.dismiss();
            //Toast.makeText(MainActivity.this, "Error occurred: try again!", Toast.LENGTH_SHORT).show();


        });


        requestQueue.add(request);


    }

    private void CheckForm() {

        name = editTextName.getText().toString();
        phone = editTextPhone.getText().toString().trim();
        add1 = editTextAdd1.getText().toString();
        add2 = editTextAdd2.getText().toString();
        pin = editTextPin.getText().toString();

        if (TextUtils.isEmpty(phone))
        {
            editTextPhone.setError("Number is required");
            editTextPhone.requestFocus();

        }
        else if (TextUtils.isEmpty(name))
        {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
        }
        else if (TextUtils.isEmpty(dateB))
        {
            Toast.makeText(MainActivity.this, "Please select your date of birth", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(add1))
        {
            editTextAdd1.setError("Address is required");
            editTextAdd1.requestFocus();
        }
        else if (TextUtils.isEmpty(pin))
        {
            editTextPin.setError("Pin code is required");
            editTextPin.requestFocus();
        }
        else if (TextUtils.isEmpty(dist))
        {
            Toast.makeText(MainActivity.this, "Please check your pin code for District and State", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setMessage("Saving..");
            loadingBar.show();
            SaveData();
        }





    }


    private void SaveData() {

        Paper.book().write("Phone",phone);
        Paper.book().write("Name",name);
        Paper.book().write("Gender",gender);
        Paper.book().write("DOB",dateB);
        Paper.book().write("Add1",add1);
        Paper.book().write("PinCode",pin);
        Paper.book().write("District",dist);
        Paper.book().write("State",state);

        if (!TextUtils.isEmpty(add2))
        {
            Paper.book().write("Add2",add2);

        }


        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(MainActivity.this, "Registration is done", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent);
                finish();

            }
        }, 1500);




    }
}