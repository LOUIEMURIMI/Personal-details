package com.example.mainactivity4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText Username, displayText, Regno, Date, Password;
    Button SUBMIT,BACK;
    Spinner Gender, Status,County,School,Course;

    String[] genders = {"male", "female", "prefer not to say"};
    String[] counties = {"Nairobi", "Mombasa", "Kakamega", "Kisumu", "Nakuru"};
    String[] Statuses = {"married", "single", "divorced"};
    String[] Schools={"SIST","SPAS","SASS","SOBE"};
    String[] Courses={"Bsc in Computer science","Bsc in Applied Statistics","Bsc in Agriculture","Bsc in Education"};
    DatabaseHelper dbHelper;

    String str;

    String selectedcounties,selectedgenders,selectedStatuses, selectedCourse, selectedSchools;
    ArrayAdapter genderAdapter, countyAdapter, statusAdapter,CourseAdapter,SchoolAdapter;

    DatePickerDialog.OnDateSetListener dateDialog;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign ID's
        Username = (EditText) findViewById(R.id.displayUsername);
        displayText = (EditText) findViewById(R.id.displayText);
        Regno = (EditText) findViewById(R.id.displayRegno);
        School = (Spinner) findViewById(R.id.displaySchool);
        Course = (Spinner) findViewById(R.id.displayCourse);
        Date = (EditText) findViewById(R.id.displayDate);
        Password = (EditText) findViewById(R.id.displayPassword);
        Gender = (Spinner) findViewById(R.id.displayGender);
        County = (Spinner) findViewById(R.id.displayCounty);
        Status = (Spinner) findViewById(R.id.Status);
        SUBMIT=(Button) findViewById(R.id.SUBMIT);
        BACK=(Button) findViewById(R.id.Back);
        //Adapters -spinners
        Gender.setOnItemSelectedListener(this);
        County.setOnItemSelectedListener(this);
        Status.setOnItemSelectedListener(this);

        genderAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, genders);
        countyAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, counties);
        statusAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, Statuses);
        SchoolAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, Schools);
        CourseAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, Courses);

        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countyAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        statusAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        SchoolAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        CourseAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);



        // Set the ArrayAdapter (ad) data on the
        //Spinner which binds data to spinner
        Gender.setAdapter(genderAdapter);
        County.setAdapter(countyAdapter);
        Status.setAdapter(statusAdapter);
        School.setAdapter(SchoolAdapter);
        Course.setAdapter(CourseAdapter);


        dbHelper = new DatabaseHelper(MainActivity.this);
        ShowDateDetails();
        showDate();
        submitDetails();

        BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboardIntent = new Intent(getBaseContext(), WelcomeActivity.class);
                startActivity(dashboardIntent);
            }
        });
    }

    public void submitDetails() {

        SUBMIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=displayText.getText().toString();
                String registrationnumber=Regno.getText().toString();
                String dateofbirth=Date.getText().toString();
                String username=Username.getText().toString();
                String password=Password.getText().toString();

                /*if ( TextUtils.isEmpty(Name)||
                        TextUtils.isEmpty(registrationnumber)||
                        TextUtils.isEmpty(dateofbirth)||
                        TextUtils.isEmpty(username)||
                        TextUtils.isEmpty(password)||
                        TextUtils.isEmpty(selectedSchools)||
                        TextUtils.isEmpty(selectedCourse)||
                        TextUtils.isEmpty(selectedgenders)||
                        TextUtils.isEmpty(selectedcounties)||
                        TextUtils.isEmpty(selectedStatuses))
                {
                    Toast.makeText(MainActivity.this, "Fill all Details!", Toast.LENGTH_SHORT).show();
                }*/
                if(dbHelper.CheckLoginDetails(Username.getText().toString(), Password.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Username exists! Try Another Username!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //add user details here
                    dbHelper.addUserDetails(displayText.getText().toString(), Regno.getText().toString(),
                            Date.getText().toString(), selectedSchools, selectedCourse, selectedgenders, selectedStatuses,
                            selectedcounties, Username.getText().toString(), Password.getText().toString());

                    Toast.makeText(MainActivity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                    Intent dashboardIntent = new Intent(getBaseContext(), DetailsActivity.class);
                    dashboardIntent.putExtra("username", Username.getText().toString());
                    startActivity(dashboardIntent);
                }
            }
        });
    }

    void ShowDateDetails()
    {
        dateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "dd/MM/yy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.UK);
                Date.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

    }

    void showDate()
    {
     Date.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             new DatePickerDialog(MainActivity.this,dateDialog,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
         }
     });
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View args1, int position, long id) {
        try {
            switch (arg0.getId())
            {
                case R.id.County:
                    Toast.makeText(getApplicationContext(),counties[position],Toast.LENGTH_LONG).show();
                    selectedcounties = counties[position];
                case R.id.Gender:
                    Toast.makeText(getApplicationContext(),genders[position],Toast.LENGTH_LONG).show();
                    selectedgenders = genders[position];
                case R.id.Status:
                    Toast.makeText(getApplicationContext(),Statuses[position],Toast.LENGTH_LONG).show();
                    selectedStatuses = Statuses[position];
                case R.id.EnterSchool:
                    Toast.makeText(getApplicationContext(),Schools[position],Toast.LENGTH_LONG).show();
                    selectedSchools = Schools[position];
                case R.id.EnterCourse:
                    Toast.makeText(getApplicationContext(),Courses[position],Toast.LENGTH_LONG).show();
                    selectedCourse = Courses[position];
            }
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
