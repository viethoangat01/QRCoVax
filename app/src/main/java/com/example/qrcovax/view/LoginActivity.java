package com.example.qrcovax.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qrcovax.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtPhoneNumber;
    ProgressBar pbSend;
    private static final String PHONENUMBER = "phonenumber";
    private static final String VERIFICATION_ID = "verificationId";

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
                    pbSend.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.INVISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+84" + edtPhoneNumber.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            LoginActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    pbSend.setVisibility(View.GONE);
                                    btnLogin.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    pbSend.setVisibility(View.GONE);
                                    btnLogin.setVisibility(View.VISIBLE);
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(verificationId, forceResendingToken);
                                    pbSend.setVisibility(View.GONE);
                                    btnLogin.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(getApplicationContext(), VerifyOtpActivity.class);
                                    intent.putExtra(PHONENUMBER, edtPhoneNumber.getText().toString());
                                    intent.putExtra(VERIFICATION_ID, verificationId);
                                    startActivity(intent);
                                }
                            }
                    );
                }
            }
        });
    }

    private void initView() {
        btnLogin = (Button) findViewById(R.id.button_login);
        edtPhoneNumber = (EditText) findViewById(R.id.edittext_login_phonenumber);
        pbSend = (ProgressBar) findViewById(R.id.progressbar_login);
    }
}
