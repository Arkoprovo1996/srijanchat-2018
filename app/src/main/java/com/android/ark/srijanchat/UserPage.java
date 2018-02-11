package com.android.ark.srijanchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserPage extends AppCompatActivity {

    private ListView msgs;
    ArrayList<String> list = new ArrayList<>();   // memory

    FirebaseDatabase database;
    DatabaseReference myRef;
    String uname, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        uname = intent.getStringExtra("uname");
        pass = intent.getStringExtra("pass");
        long secKey = numerate(pass);
        setTitle(uname);
//        Toast.makeText(this, Long.toString(secKey), Toast.LENGTH_LONG).show();

        msgs = findViewById(R.id.messages);

        setContentView(R.layout.activity_user_page);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);   // Mediator / ArrayAdaptor
        final ListView msgs = findViewById(R.id.messages);
        msgs.setAdapter(adapter);   // Apply the Adapter

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        ImageView btn = findViewById(R.id.send_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt = findViewById(R.id.messagebox);
                if(txt.getText().toString().equals(""))
                {
                    Toast.makeText(UserPage.this, "Empty Message", Toast.LENGTH_SHORT).show();
                    return;
                }

                myRef.push().setValue(uname + ":" + txt.getText().toString());

                txt.setText("");
                adapter.notifyDataSetChanged(); // Notify the view that the data is changed & needs to be updated
            }
        });

        // Read from the database

        if(myRef == null)
            return;

        myRef.push().setValue(uname + " has joined!");
        adapter.notifyDataSetChanged();

/*        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                Object value = dataSnapshot.getValue();
//                Log.d("DEBUG", "Value is: " + value.toString());
                list.clear();
                for(DataSnapshot shot: dataSnapshot.getChildren())
                {
                    list.add(shot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DEBUG", "Failed to read value.", error.toException());
            }
        });*/

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    String[] msgs = dataSnapshot.getValue().toString().split(":");

                    String user = msgs[0], msg = msgs[1];
                    if (user.equals(uname)) {
                        list.add("\u27a1\t\t\t\t\t\t\t\t\t\t\t\t\t" + msg);

                        adapter.notifyDataSetChanged();
                    } else {
                        list.add("\u270f\t\t\t" + user + " :\t" + msg);
                        adapter.notifyDataSetChanged();
                    }
                }
                catch (Exception e) {
                    String msg = dataSnapshot.getValue().toString();
                    list.add(msg);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //list.add("Hi");
    }
    public long numerate(String pass)
    {
        int i = pass.length(), base=128;
        long sKey = 0;
        while(i-- != 0)
            sKey = base * sKey + (long)pass.charAt(i);
        return sKey;
    }
}
