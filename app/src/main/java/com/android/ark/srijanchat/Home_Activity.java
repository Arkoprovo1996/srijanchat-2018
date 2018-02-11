package com.android.ark.srijanchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;

public class Home_Activity extends AppCompatActivity {

    int a;
    Button login;
    TextView tv;
    EditText userName;
    EditText password;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        a = 5;
        login = findViewById(R.id.log_in);
        userName = findViewById(R.id.input);
        password = findViewById(R.id.pass);
        tv = findViewById(R.id.title);
        icon = findViewById(R.id.logo);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tv.getText().toString();
                String uname = userName.getText().toString();
                String pass = password.getText().toString();
                if(text.equals("Srijan Chat"))
                    tv.setText("Srijan 2.0 " + uname);
                else
                    tv.setText("Srijan Chat");
                if(uname.equals(""))
                {
                    Toast.makeText(Home_Activity.this, "Username can't be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                /*
                 *  Verification
                 *  Local File
                 */
                try {
                    FileReader fr = new FileReader("Passwords");
                    BufferedReader br = new BufferedReader(fr);

                    if (!pass.equals("srijan2018")) {
                        Toast.makeText(Home_Activity.this, "Wrong Password!", Toast.LENGTH_LONG).show();
                        return;
                    }


                    br.close();
                    fr.close();
                }
                catch(IOException e){

                }

                Intent intent = new Intent(Home_Activity.this, UserPage.class);
                intent.putExtra("uname", uname);
                intent.putExtra("pass", pass);
                Toast.makeText(Home_Activity.this, uname, 1500).show();
                startActivity(intent);
                finish();
            }
        });
    }
    boolean printBye()
    {
        System.out.println("Bye Bye!");
        return true;
    }
}
