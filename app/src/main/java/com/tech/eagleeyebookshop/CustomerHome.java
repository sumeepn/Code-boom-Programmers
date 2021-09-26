package com.tech.eagleeyebookshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CustomerHome extends AppCompatActivity {

    ImageView imageView1;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        imageView1=(ImageView)findViewById(R.id.customerprofile);
        imageView2=(ImageView)findViewById(R.id.customeracc);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this, CustomerProfile.class);
                startActivity(intent);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHome.this, CustomerAccountCard.class);
                startActivity(intent);
            }
        });
    }
}