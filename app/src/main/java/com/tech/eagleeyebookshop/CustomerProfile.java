package com.tech.eagleeyebookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CustomerProfile extends AppCompatActivity {

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    ImageButton imageButton;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        textView1=(TextView)findViewById(R.id.pUname);
        textView2=(TextView)findViewById(R.id.pRemail);
        textView3=(TextView)findViewById(R.id.pAge);
        textView4=(TextView)findViewById(R.id.pcontct);
        textView5=(TextView)findViewById(R.id.ppassword);
        imageButton = (ImageButton)findViewById(R.id.pimageButton);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String userid = user.getUid();

        myRef = FirebaseDatabase.getInstance().getReference().child("User").child(userid).getRef();
        System.out.println(userid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String age = snapshot.child("age").getValue().toString();
                String phone = snapshot.child("phone").getValue().toString();
                String password = snapshot.child("password").getValue().toString();

                textView1.setText(name);
                textView2.setText(email);
                textView3.setText(age);
                textView4.setText(phone);
                textView5.setText(password);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CustomerProfile.this);

                View view1 = LayoutInflater.from(CustomerProfile.this).inflate(R.layout.update_profile,null);
                dialogBuilder.setView(view1);

                final EditText editText1 = (EditText)view1.findViewById(R.id.uUname);
                final TextView textView2 = (TextView) view1.findViewById(R.id.uRemail);
                final EditText editText3 = (EditText)view1.findViewById(R.id.uAge);
                final EditText editText4 = (EditText)view1.findViewById(R.id.ucontct);
                final TextView textViewp = (TextView) view1.findViewById(R.id.upassword);
                Button button = (Button)view1.findViewById(R.id.updatep);

                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CustomerProfile.this, "You Can't update your Email", Toast.LENGTH_SHORT).show();
                    }
                });

                textViewp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CustomerProfile.this, "You Can't update your Password", Toast.LENGTH_SHORT).show();
                    }
                });

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String Name = (String) snapshot.child("name").getValue();
                        String Email = (String) snapshot.child("email").getValue();
                        String Age = (String) snapshot.child("age").getValue();
                        String Contact = (String) snapshot.child("phone").getValue();
                        String Password = (String) snapshot.child("password").getValue();

                        editText1.setText(Name);
                        textView2.setText(Email);
                        editText3.setText(Age);
                        editText4.setText(Contact);
                        textViewp.setText(Password);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Name = editText1.getText().toString();
                        String Age = editText3.getText().toString();
                        String Contact = editText4.getText().toString();

                        if(Name.isEmpty()) {
                            editText1.setError("Name is required");
                        }else if(Age.isEmpty()){
                            editText3.setError("Age is required");
                        }else if(Contact.isEmpty()){
                            editText4.setError("Contact Number is required");
                        }else {

                            HashMap map = new HashMap();
                            map.put("name", Name);
                            map.put("age",Age);
                            map.put("phone",Contact);
                            myRef.updateChildren(map);

                            Toast.makeText(CustomerProfile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();

                            alertDialog.dismiss();
                        }
                    }
                });

            }
        });

    }

}
