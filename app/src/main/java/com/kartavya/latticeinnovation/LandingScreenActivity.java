package com.kartavya.latticeinnovation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.paperdb.Paper;

public class LandingScreenActivity extends AppCompatActivity {

    private EditText phoneEt;
    private Button subBtn;
    private String phoneStr;
    private TextView textView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);
        Paper.init(getApplicationContext());

        phoneEt = findViewById(R.id.bjbhvhvvvyh);
        subBtn = findViewById(R.id.submit_btbbhvycn);
        textView = findViewById(R.id.sxmnsjcsbchscv);
        linearLayout = findViewById(R.id.bhvgcfxfdz);

        if (Paper.book().exist("Phone"))
        {
            linearLayout.setVisibility(View.INVISIBLE);
            subBtn.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);

            final Handler handler2 = new Handler(Looper.getMainLooper());
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(LandingScreenActivity.this, WeatherActivity.class);
                    startActivity(intent);
                    finish();

                }
            }, 1000);

        }

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneStr = phoneEt.getText().toString();

                if (TextUtils.isEmpty(phoneStr))
                {
                    phoneEt.setError("Number is required");
                    phoneEt.requestFocus();
                }
                else
                    {
                        Intent intent = new Intent(LandingScreenActivity.this, MainActivity.class);
                        intent.putExtra("Phone",phoneStr);
                        startActivity(intent);
                        finish();
                    }
            }
        });
    }
}