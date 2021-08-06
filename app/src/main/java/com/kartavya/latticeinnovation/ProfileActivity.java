package com.kartavya.latticeinnovation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.paperdb.Paper;

public class ProfileActivity extends AppCompatActivity {

    private TextView name,phone,gender,dob,add1,add2,pin,dist,state;
    private ImageView backBtn;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Paper.init(getApplicationContext());

        name = findViewById(R.id.njvhcgcjkvcvcp);
        phone = findViewById(R.id.byhxcfyjbcycp);
        gender = findViewById(R.id.njvbjvhchcbhvgcp);
        dob = findViewById(R.id.njbjvbhjvhcgcp);
        add1 = findViewById(R.id.njknjbjvhvvhcgcp);
        add2 = findViewById(R.id.njbhvbhvhvhcgcp);
        pin = findViewById(R.id.njvbhvhvhcbhjgcpjb);
        dist = findViewById(R.id.njvbhvhvbhchjbgc);
        state = findViewById(R.id.njbvbhhvvbhvhvhcgc);

        backBtn = findViewById(R.id.hgfvfrg);

        linearLayout = findViewById(R.id.xnsjcsbcjscv);

        name.setText(Paper.book().read("Name"));
        phone.setText(Paper.book().read("Phone"));
        gender.setText(Paper.book().read("Gender"));
        dob.setText(Paper.book().read("DOB"));
        add1.setText(Paper.book().read("Add1"));
        pin.setText(Paper.book().read("PinCode"));
        dist.setText(Paper.book().read("District"));
        state.setText(Paper.book().read("State"));

        if (Paper.book().exist("Add2"))
        {
            linearLayout.setVisibility(View.VISIBLE);
            add2.setText(Paper.book().read("Add2"));
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }
}