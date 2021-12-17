package com.example.qrcovax.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qrcovax.R;

public class LoginActivity extends AppCompatActivity {
    EditText edtPhoneNumber;
    Button btnLogin;
    private static final String PHONENUMBER = "phonenumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPhoneNumber.getText().toString().trim().isEmpty()) {
                    edtPhoneNumber.setError(getString(R.string.login_errorinput));
                } else {
                    Intent intent = new Intent(getApplicationContext(), VerifyOtpActivity.class);
                    intent.putExtra(PHONENUMBER, edtPhoneNumber.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    private void initView() {
        btnLogin = (Button) findViewById(R.id.button_login);
        edtPhoneNumber = (EditText) findViewById(R.id.edittext_login_phonenumber);
    }
}
