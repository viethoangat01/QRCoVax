package com.example.qrcovax.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qrcovax.R;
import com.example.qrcovax.model.User;
import com.example.qrcovax.network.RestApiService;
import com.example.qrcovax.network.RetrofitInstance;
import com.example.qrcovax.utils.Constants;
import com.example.qrcovax.utils.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterProfileActivity extends AppCompatActivity {
    Button btnSave;
    EditText edtName, edtBirthday, edtID, edtEmail, edtDistrict, edtWard, edtVillage;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale, rbOther;
    TextView txtPhoneNumber;

    String gender = "";

    //////
    private static final String PHONENUMBER = "phonenumber";
    private static final String USER_UID = "userUID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profile);
        checkSession();
        initView();
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radiobutton_registerprofile_male:
                        gender = getString(R.string.registerprofile_male);
                        break;
                    case R.id.radiobutton_registerprofile_female:
                        gender = getString(R.string.registerprofile_female);
                        break;
                    case R.id.radiobutton_registerprofile_other:
                        gender = getString(R.string.registerprofile_other);
                        break;
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName.getText().toString().trim().isEmpty()
                        || edtBirthday.getText().toString().trim().isEmpty()
                        || edtID.getText().toString().trim().isEmpty()
                        || edtDistrict.getText().toString().trim().isEmpty()
                        || edtWard.getText().toString().trim().isEmpty()
                        || edtVillage.getText().toString().trim().isEmpty()
                        || gender.length() == 0) {
                    Toast.makeText(RegisterProfileActivity.this, "Thông tin không được để trống!", Toast.LENGTH_SHORT).show();
                } else {
                    String id = Session.read(getApplicationContext(), USER_UID, Constants.INIT_STRING);
                    User user = new User(id, edtID.getText().toString().trim(), edtName.getText().toString().trim(), edtBirthday.getText().toString().trim(), gender, edtEmail.getText().toString().trim(),
                            Session.read(getApplicationContext(), PHONENUMBER, Constants.INIT_STRING).trim(),
                            edtDistrict.getText().toString().trim(), edtWard.getText().toString().trim(), edtVillage.getText().toString().trim());
                    //Send infor to server
                    RestApiService apiService = RetrofitInstance.getApiService();
                    apiService.updateInformation(user).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("Update user", "onFailure: " + t.getMessage());
                        }
                    });
                    startActivity(new Intent(RegisterProfileActivity.this, MainActivity.class));
                }
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
        edtDistrict = (EditText) findViewById(R.id.edittext_registerprofile_district);
        edtWard = (EditText) findViewById(R.id.edittext_registerprofile_ward);
        edtVillage = (EditText) findViewById(R.id.edittext_registerprofile_village);
        rgGender = (RadioGroup) findViewById(R.id.radiogroup_registerprofile);
        rbMale = (RadioButton) findViewById(R.id.radiobutton_registerprofile_male);
        rbFemale = (RadioButton) findViewById(R.id.radiobutton_registerprofile_female);
        rbOther = (RadioButton) findViewById(R.id.radiobutton_registerprofile_other);
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
