package com.example.mainactivity4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

public class DetailsActivity extends AppCompatActivity {
    Button signOutBtn, deleteBtn;

    DatabaseHelper dbHelper;

    ListView userDetailsList;
    SearchView search;

    UserAdapter userAdapter;

    String titleDetails[] = {"Name", "RegNo", "DOB", "School", "Course", "Gender", "Status", "County", "Username", "Password"};
    String allDetails[] = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        userDetailsList = findViewById(R.id.listView);
        search = findViewById(R.id.searchView);

        signOutBtn = findViewById(R.id.signOut12);
        deleteBtn = findViewById(R.id.delete);

        dbHelper = new DatabaseHelper(DetailsActivity.this);

        userAdapter = new UserAdapter(DetailsActivity.this, titleDetails, allDetails);

        userDetailsList.setAdapter(userAdapter);

        DisplayDetails();
        CheckItemSelected();
        //SearchDetails();
        Delete();
        LogOut();
    }
    public void DisplayDetails()
    {
        try {
            Intent dashboard = getIntent();
            allDetails[8] = dashboard.getStringExtra("username");

            Cursor rs = dbHelper.getUserDetails(allDetails[8]);

            if (rs.moveToFirst()) {
                //looping through all the records
                do {
                    allDetails[0] = rs.getString(0);
                    allDetails[1] = rs.getString(1);
                    allDetails[2] = rs.getString(2);
                    allDetails[3] = rs.getString(3);
                    allDetails[4] = rs.getString(4);
                    allDetails[5] = rs.getString(5);
                    allDetails[6] = rs.getString(6);
                    allDetails[7] = rs.getString(7);
                    allDetails[9] = rs.getString(9);
                } while (rs.moveToNext());
            }

        }
        catch(Exception e)
        {

        }
    }
    /*void SearchDetails()
    {
        //search the item name
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                DetailsActivity.this.arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DetailsActivity.this.arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }*/
    void CheckItemSelected()
    {
        userDetailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                try {
                    Intent updateIntent = new Intent(getBaseContext(), UpdateActivity.class);
                    //store values to be displayed in the next activity
                    updateIntent.putExtra("username", allDetails[8]);
                    startActivity(updateIntent);
                }
                catch (Exception e)
                {

                }
            }
        });
    }
    void Delete()
    {
        //delete records and go back to welcome page
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete all records

                Intent welcomeIntent = new Intent(getBaseContext(), WelcomeActivity.class);
                dbHelper.DeleteDetails(allDetails[8]);
                startActivity(welcomeIntent);
            }
        });
    }
    void LogOut()
    {
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = new Intent(getBaseContext(), WelcomeActivity.class);
                startActivity(signInIntent);
            }
        });
    }
}