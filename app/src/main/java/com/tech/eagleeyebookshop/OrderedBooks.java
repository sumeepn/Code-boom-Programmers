package com.tech.eagleeyebookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.tech.eagleeyebookshop.models.OrderDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderedBooks extends AppCompatActivity {

    ListView listView;
    List<OrderDetails> user;
    DatabaseReference ref;
    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_books);

        listView = (ListView) findViewById(R.id.orderlist);
        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("OrderedDeliveries");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    OrderDetails orderDetails = studentDatasnap.getValue(OrderDetails.class);
                    user.add(orderDetails);

                }

                MyAdapter adapter = new MyAdapter(OrderedBooks.this, R.layout.custom_delivery_ordered_deails, (ArrayList<OrderDetails>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static class ViewHolder {

        Button button1;
        Button button2;
        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        TextView COL5;
        TextView COL6;
        TextView COL7;
        TextView COL8;
    }

    class MyAdapter extends ArrayAdapter<OrderDetails> {
        LayoutInflater inflater;
        Context myContext;
        List<OrderDetails> user;


        public MyAdapter(Context context, int resource, ArrayList<OrderDetails> objects) {
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
                view = inflater.inflate(R.layout.custom_delivery_ordered_deails, null);

                holder.COL1 = (TextView) view.findViewById(R.id.bid);
                holder.COL2 = (TextView) view.findViewById(R.id.bname);
                holder.COL3 = (TextView) view.findViewById(R.id.bprice);
                holder.COL4 = (TextView) view.findViewById(R.id.bqty);
                holder.COL5 = (TextView) view.findViewById(R.id.buname);
                holder.COL6 = (TextView) view.findViewById(R.id.bunic);
                holder.COL7 = (TextView) view.findViewById(R.id.bucontact);
                holder.COL8 = (TextView) view.findViewById(R.id.buaddress);
                holder.button1 = (Button) view.findViewById(R.id.itemedit);
                holder.button2 = (Button) view.findViewById(R.id.itemdelete);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText(user.get(position).getBookId());
            holder.COL2.setText(user.get(position).getBookName());
            holder.COL3.setText(user.get(position).getPrice());
            holder.COL4.setText(user.get(position).getQty());
            holder.COL5.setText(user.get(position).getUserName());
            holder.COL6.setText(user.get(position).getNic());
            holder.COL7.setText(user.get(position).getContact());
            holder.COL8.setText(user.get(position).getAddress());
            System.out.println(holder);

            idd = user.get(position).getBookId();

            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    final String idd = user.get(position).getBookId();
                                    FirebaseDatabase.getInstance().getReference("OrderedDeliveries").child(idd).removeValue();
                                    //remove function not written
                                    Toast.makeText(myContext, "Item deleted successfully", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(R.layout.custom_update_orders, null);
                    dialogBuilder.setView(view1);

                    final TextView textView1 = (TextView) view1.findViewById(R.id.upid);
                    final TextView textView2 = (TextView) view1.findViewById(R.id.upname);
                    final TextView textView3 = (TextView) view1.findViewById(R.id.upprice);
                    final TextView textView4 = (TextView) view1.findViewById(R.id.upqty);
                    final EditText editText1 = (EditText) view1.findViewById(R.id.upusername);
                    final EditText editText2 = (EditText) view1.findViewById(R.id.upnic);
                    final EditText editText3 = (EditText) view1.findViewById(R.id.upcontat);
                    final EditText editText4 = (EditText) view1.findViewById(R.id.upaddress);
                    final EditText editText5 = (EditText) view1.findViewById(R.id.upqqty);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.bookupnow);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getBookId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("OrderedDeliveries").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = (String) snapshot.child("bookId").getValue();
                            String name = (String) snapshot.child("bookName").getValue();
                            String price = (String) snapshot.child("price").getValue();
                            String qty = (String) snapshot.child("qty").getValue();
                            String username = (String) snapshot.child("userName").getValue();
                            String userNic = (String) snapshot.child("nic").getValue();
                            String userContact = (String) snapshot.child("contact").getValue();
                            String userAddress = (String) snapshot.child("address").getValue();

                            textView1.setText(id);
                            textView2.setText(name);
                            textView3.setText(price);
                            textView4.setText(qty);
                            editText1.setText(username);
                            editText2.setText(userNic);
                            editText3.setText(userContact);
                            editText4.setText(userAddress);
                            editText5.setText(qty);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    buttonupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = editText1.getText().toString();
                            String nic = editText2.getText().toString();
                            String contact = editText3.getText().toString();
                            String address = editText4.getText().toString();
                            String qty = editText5.getText().toString();
                            Integer qtyy = Integer.valueOf(editText5.getText().toString());
                            Integer price = Integer.valueOf(textView3.getText().toString());

                            if (name.equals("")) {
                                editText1.setError("Name is required");
                            }else if (nic.isEmpty()) {
                                editText2.setError("NIC is required");
                            }else if (contact.isEmpty()) {
                                editText3.setError("Contact Number is required");
                            }else if (address.isEmpty()) {
                                editText4.setError("Address is required");
                            }else if (qty.isEmpty()) {
                                editText5.setError("Quantity is required");
                            } else {

                                HashMap map = new HashMap();
                                map.put("userName", name);
                                map.put("nic", nic);
                                map.put("contact", contact);
                                map.put("address", address);
                                map.put("qty", qty);

                                String finalval = String.valueOf(price*qtyy);
                                map.put("price",finalval);
                                reference.updateChildren(map);

                                Toast.makeText(OrderedBooks.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                                alertDialog.dismiss();
                            }
                        }
                    });
                }
            });

            return view;

        }
    }
}