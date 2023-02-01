package com.example.mainactivity4;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;

    EditText Username,password;
    Button login,signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Username=(EditText)findViewById(R.id.Name);
        password=(EditText) findViewById(R.id.EnterPassword);
        login=(Button) findViewById(R.id.login);
        signup=(Button) findViewById(R.id.signup);

        dbHelper = new DatabaseHelper(WelcomeActivity.this);

        //login activity
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor rs = dbHelper.getData(Username.getText().toString(), password.getText().toString());

                if(rs.moveToFirst())
                {
                    do
                    {
                        Toast.makeText(WelcomeActivity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(WelcomeActivity.this,DetailsActivity.class);
                        intent.putExtra("username", Username.getText().toString());
                        startActivity(intent);
                    }
                    while(rs.moveToNext());
                }
                else
                {
                    Toast.makeText(WelcomeActivity.this, "Invalid Details!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //signup activity
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}