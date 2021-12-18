package com.example.qrcovax.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qrcovax.R;
import com.example.qrcovax.utils.Constants;
import com.example.qrcovax.utils.Session;

public class RegisterProfileActivity extends AppCompatActivity {
    Button btnSave;
    EditText edtName, edtBirthday, edtID, edtEmail, edtCity, edtDistrict, edtWard, edtVillage;
    TextView txtPhoneNumber;

    //////
    private static final String PHONENUMBER = "phonenumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profile);
        checkSession();
        initView();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterProfileActivity.this, MainActivity.class));
            }
        });
        txtPhoneNumber.setText("0" + Session.read(getApplicationContext(), PHONENUMBER, Constants.INIT_STRING));


    }

    private void initView() {
        btnSave = (Button) findViewById(R.id.button_registerprofile_save);
        txtPhoneNumber = (TextView) findViewById(R.id.textview_registerprofile_phonenumber);
        edtName = (EditText) findViewById(R.id.edittext_registerprofile_name);
        edtBirthday = (EditText) findViewById(R.id.edittext_registerprofile_birthday);
        edtID = (EditText) findViewById(R.id.edittext_registerprofile_id);
        edtEmail = (EditText) findViewById(R.id.edittext_registerprofile_email);
        edtCity = (EditText) findViewById(R.id.edittext_registerprofile_city);
        edtDistrict = (EditText) findViewById(R.id.edittext_registerprofile_district);
        edtWard = (EditText) findViewById(R.id.edittext_registerprofile_ward);
        edtVillage = (EditText) findViewById(R.id.edittext_registerprofile_village);
    }

    private void checkSession() {
        //set default value to false
        //when user login first, will set false
        boolean session = Boolean.valueOf(Session.read(getApplicationContext(), Constants.SESSION_STATUS, "false"));
        if (!session) {
            //when user login first or logout value is false
            //Back to Login Activity
            startActivity(new Intent(RegisterProfileActivity.this, LoginActivity.class));
            finish();
        } else {
            //when user logged in, value: is true
            Log.d("Session", "sessionStatus: true");
        }
    }
}
