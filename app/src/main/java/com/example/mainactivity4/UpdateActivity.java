package com.example.mainactivity4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

public class UpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper dbHelper;

    Button deleteBtn, updateBtn, backButton;

    EditText passwordField, usernameField, dateOfBirth, regNumber, fullName;

    Spinner status, gender, school, course, county;

    DatePickerDialog.OnDateSetListener datePicker;

    String [] listOfGenders = {"Male", "Female", "Prefer Not to Say"};
    String [] listOfStatus = {"Single", "Married", "Divorced"};
    String [] listOfSchool = {"School Of Information Science & Technology", "School Of Education", "School Of Law", "School Of Medicine", "School Of Agriculture"};
    String [] listOfCounties = {"Mombasa", "Nairobi", "Kisii", "Kericho", "Bomet", "Kisumu", "Kakamega"};
    String [] listOfCourses = {"Bsc in Information Technology", "Bsc in Law", "Bsc in Criminology", "Bsc in Computer Science", "Bsc in Medicine", "Bsc in Software Engineering"};

    ArrayAdapter countyAdapter, genderAdapter, schoolAdapter, courseAdapter, statusAdapter;
    String selectedCounty, selectedGender, selectedSchool, selectedCourse, selectedStatus;

    final Calendar myCalendar= Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        dbHelper = new DatabaseHelper(UpdateActivity.this);

        backButton = findViewById(R.id.backbtn);
        updateBtn = findViewById(R.id.updatebtn);

        passwordField = findViewById(R.id.password1);
        usernameField = findViewById(R.id.username1);
        dateOfBirth = findViewById(R.id.dob);
        regNumber = findViewById(R.id.regno);
        fullName = findViewById(R.id.name);

        status = findViewById(R.id.status1);
        gender = findViewById(R.id.gender1);
        school = findViewById(R.id.school1);
        course = findViewById(R.id.course1);
        county = findViewById(R.id.county1);

        //Spinner Declaration
        gender.setOnItemSelectedListener(this);
        county.setOnItemSelectedListener(this);
        status.setOnItemSelectedListener(this);
        school.setOnItemSelectedListener(this);
        course.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the list
        countyAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, listOfCounties);
        genderAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, listOfGenders);
        statusAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, listOfStatus);
        courseAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, listOfCourses);
        schoolAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, listOfSchool);

        countyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        gender.setAdapter(genderAdapter);
        status.setAdapter(statusAdapter);
        county.setAdapter(countyAdapter);
        course.setAdapter(courseAdapter);
        school.setAdapter(schoolAdapter);

        dbHelper = new DatabaseHelper(UpdateActivity.this);

        ShowDetails();
        GoBack();
        ShowDate();
        ShowDatePicker();
        Update();
    }
    void ShowDetails()
    {
        try {
            Intent dashboard = getIntent();
            usernameField.setText(dashboard.getStringExtra("username"));

            Cursor rs = dbHelper.getUserDetails(usernameField.getText().toString());

            if (rs.moveToFirst()) {
                //looping through all the records
                do {
                    fullName.setText(rs.getString(0));
                    regNumber.setText(rs.getString(1));
                    dateOfBirth.setText(rs.getString(2));
                    selectedSchool = rs.getString(3);
                    selectedCourse = rs.getString(4);
                    selectedGender = rs.getString(5);
                    selectedStatus = rs.getString(6);
                    selectedCounty = rs.getString(7);
                    passwordField.setText(rs.getString(9));
                } while (rs.moveToNext());
            }
        }
        catch(Exception e)
        {

        }
    }
    void Update()
    {
        //go back to the dashboard and show updated values
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //can't leave the username and password empty when updating
                if(usernameField.getText().toString().isEmpty() || passwordField.getText().toString().isEmpty())
                {
                    Toast.makeText(UpdateActivity.this, "USERNAME OR PASSWORD CANNOT BE NULL!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //show successful message
                    Toast.makeText(UpdateActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();

                    //update to database
                    dbHelper.UpdateDetails(fullName.getText().toString(), regNumber.getText().toString(), dateOfBirth.getText().toString(),
                            school.getSelectedItem().toString(), course.getSelectedItem().toString(), gender.getSelectedItem().toString(),
                            status.getSelectedItem().toString(), county.getSelectedItem().toString(), usernameField.getText().toString(),
                            passwordField.getText().toString());

                    //pass values to the dashboard and start activity
                    Intent dashboardIntent = new Intent(getBaseContext(), DetailsActivity.class);
                    //pass the same data
                    dashboardIntent.putExtra("username", usernameField.getText().toString());
                    startActivity(dashboardIntent);
                }
            }
        });
    }
    void GoBack()
    {
        //go back to dashboard
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboardIntent = new Intent(getBaseContext(), DetailsActivity.class);
                //pass the same data
                dashboardIntent.putExtra("username", usernameField.getText().toString());
                startActivity(dashboardIntent);
            }
        });
    }

    public void ShowDatePicker()
    {
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(UpdateActivity.this, datePicker, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    void ShowDate()
    {
        try {
            datePicker = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH,month);
                    myCalendar.set(Calendar.DAY_OF_MONTH,day);
                    String myFormat="dd/MM/yy";
                    SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.UK);

                    //set date in edit text - date
                    dateOfBirth.setText(dateFormat.format(myCalendar.getTime()));
                }
            };
        }
        catch (Exception e)
        {

        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            switch (adapterView.getId())
            {
                case R.id.county1:
                    selectedCounty = listOfCounties[i];
                case R.id.gender1:
                    selectedGender = listOfGenders[i];
                case R.id.status1:
                    selectedStatus = listOfStatus[i];
                case R.id.course1:
                    selectedCourse = listOfCourses[i];
                case R.id.school1:
                    selectedSchool = listOfSchool[i];
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