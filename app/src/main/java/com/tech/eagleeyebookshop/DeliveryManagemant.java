package com.tech.eagleeyebookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech.eagleeyebookshop.models.Books;
import com.tech.eagleeyebookshop.models.OrderDetails;

import java.util.ArrayList;
import java.util.List;

public class DeliveryManagemant extends AppCompatActivity {

    Button button;
    ListView listView;
    List<Books> user;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_managemant);

        listView = (ListView) findViewById(R.id.listviewdel);
        button = (Button) findViewById(R.id.gotodelItms);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryManagemant.this, OrderedBooks.class);
                startActivity(intent);
            }
        });

        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Books");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    Books books = studentDatasnap.getValue(Books.class);
                    user.add(books);
                }

                MyAdapter adapter = new MyAdapter(DeliveryManagemant.this, R.layout.custom_delivery_items, (ArrayList<Books>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static class ViewHolder {

        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        Button button;
    }

    class MyAdapter extends ArrayAdapter<Books> {
        LayoutInflater inflater;
        Context myContext;
        List<Books> user;


        public MyAdapter(Context context, int resource, ArrayList<Books> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_delivery_items, null);

                holder.COL1 = (TextView) view.findViewById(R.id.bookid);
                holder.COL2 = (TextView) view.findViewById(R.id.bookname);
                holder.COL3 = (TextView) view.findViewById(R.id.bookauthor);
                holder.COL4 = (TextView) view.findViewById(R.id.bookprice);
                holder.button = (Button) view.findViewById(R.id.deliver);

                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText(user.get(position).getId());
            holder.COL2.setText(user.get(position).getName());
            holder.COL3.setText(user.get(position).getAuthor());
            holder.COL4.setText(user.get(position).getPrice());

            System.out.println(holder);

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view = inflater.inflate(R.layout.custom_insert_deliverbook, null);
                    dialogBuilder.setView(view);

                    final TextView textView1 = (TextView) view.findViewById(R.id.bookID);
                    final TextView textView2 = (TextView) view.findViewById(R.id.bookNaame);
                    final TextView textView3 = (TextView) view.findViewById(R.id.bookaut);
                    final TextView textView4 = (TextView) view.findViewById(R.id.bookpri);
                    final EditText editText1 = (EditText) view.findViewById(R.id.bookcuname);
                    final EditText editText2 = (EditText) view.findViewById(R.id.bookcunic);
                    final EditText editText3 = (EditText) view.findViewById(R.id.bookcucontat);
                    final EditText editText4 = (EditText) view.findViewById(R.id.bookcuaddress);
                    final EditText editText5 = (EditText) view.findViewById(R.id.bookcuqty);
                    final Button buttonAdd = (Button) view.findViewById(R.id.bookunow);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Books").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = (String) snapshot.child("id").getValue();
                            String name = (String) snapshot.child("name").getValue();
                            String price = (String) snapshot.child("price").getValue();
                            String author = (String) snapshot.child("author").getValue();

                            textView1.setText(id);
                            textView2.setText(name);
                            textView3.setText(author);
                            textView4.setText(price);

                            buttonAdd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("OrderedDeliveries");

                                    final String username = editText1.getText().toString();
                                    final String nic = editText2.getText().toString();
                                    final String contact = editText3.getText().toString();
                                    final String address = editText4.getText().toString();
                                    final String qty = editText5.getText().toString();
                                    Integer qtyval = Integer.valueOf(editText5.getText().toString());
                                    String id = textView1.getText().toString();
                                    String name = textView2.getText().toString();
                                    Integer price = Integer.valueOf(textView4.getText().toString());

                                    Integer tax = (price*2) / 100 ;
                                    Integer total = price+tax;
                                    String finalval = String.valueOf(total * qtyval);

                                    if (username.isEmpty()) {
                                        editText1.setError("Name is required");
                                    }else if (nic.isEmpty()) {
                                        editText2.setError("NIC is required");
                                    }else if (contact.isEmpty()) {
                                        editText3.setError("Contact Number is required");
                                    }else if (address.isEmpty()) {
                                        editText4.setError("Address is required");
                                    }
                                    else if (qty.isEmpty()) {
                                        editText5.setError("Quantity is required");
                                    }else {

                                        OrderDetails orderDetails = new OrderDetails(id,name,finalval,qty,username,nic,contact,address);
                                        reference.child(id).setValue(orderDetails);

                                        Toast.makeText(DeliveryManagemant.this, "Successfully added", Toast.LENGTH_SHORT).show();

                                        alertDialog.dismiss();
                                    }

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });

                }

            });

            return view;

        }
    }

}