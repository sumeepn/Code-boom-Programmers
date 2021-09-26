package com.tech.eagleeyebookshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminHome extends AppCompatActivity {

    ImageView imageView1;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        imageView1=(ImageView)findViewById(R.id.book);
        imageView2=(ImageView)findViewById(R.id.delivery);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, ManageBooks.class);
                startActivity(intent);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, DeliveryManagemant.class);
                startActivity(intent);
            }
        });
    }
}