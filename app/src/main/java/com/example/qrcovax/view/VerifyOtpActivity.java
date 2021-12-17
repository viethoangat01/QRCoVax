package com.example.qrcovax.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qrcovax.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOtpActivity extends AppCompatActivity {
    private Button btnVerify;
    private EditText edtCode1, edtCode2, edtCode3, edtCode4, edtCode5, edtCode6;
    private ProgressBar pbLoad;
    private TextView tvPhoneNumber, tvResend;
    private static final String PHONENUMBER = "phonenumber";
    private static final String VERIFICATION_ID = "verificationId";
    private static final String FORMAT_PHONE = "(+84) %s";

    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        initView();
        verificationId = getIntent().getStringExtra(VERIFICATION_ID);
        tvPhoneNumber.setText(String.format(
                FORMAT_PHONE, getIntent().getStringExtra(PHONENUMBER)
        ));
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtCode1.getText().toString().trim().isEmpty()
                        || edtCode2.getText().toString().trim().isEmpty()
                        || edtCode3.getText().toString().trim().isEmpty()
                        || edtCode4.getText().toString().trim().isEmpty()
                        || edtCode5.getText().toString().trim().isEmpty()
                        || edtCode6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(VerifyOtpActivity.this, R.string.verifyotp_entervalidcode, Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = edtCode1.getText().toString() +
                        edtCode2.getText().toString() +
                        edtCode3.getText().toString() +
                        edtCode4.getText().toString() +
                        edtCode5.getText().toString() +
                        edtCode6.getText().toString();
                String verificationId = getIntent().getStringExtra(VERIFICATION_ID);
                if (verificationId != null) {
                    pbLoad.setVisibility(View.VISIBLE);
                    btnVerify.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId, code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pbLoad.setVisibility(View.GONE);
                                    btnVerify.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(VerifyOtpActivity.this, R.string.verifyotp_codeinvalid, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+84" + getIntent().getStringExtra(PHONENUMBER),
                        60,
                        TimeUnit.SECONDS,
                        VerifyOtpActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(newVerificationId, forceResendingToken);
                                verificationId = newVerificationId;
                                Toast.makeText(VerifyOtpActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
        setupOTPInputs();
    }

    private void initView() {
        btnVerify = (Button) findViewById(R.id.button_verifyotp_verify);
        edtCode1 = (EditText) findViewById(R.id.edittext_verifyotp_1);
        edtCode2 = (EditText) findViewById(R.id.edittext_verifyotp_2);
        edtCode3 = (EditText) findViewById(R.id.edittext_verifyotp_3);
        edtCode4 = (EditText) findViewById(R.id.edittext_verifyotp_4);
        edtCode5 = (EditText) findViewById(R.id.edittext_verifyotp_5);
        edtCode6 = (EditText) findViewById(R.id.edittext_verifyotp_6);
        pbLoad = (ProgressBar) findViewById(R.id.progressbar_verifyotp);
        tvPhoneNumber = (TextView) findViewById(R.id.textview_verifyotp_phonenumber);
        tvResend = (TextView) findViewById(R.id.textview_verifyotp_resend);
    }

    private void setupOTPInputs() {
        edtCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtCode2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtCode3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtCode4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtCode5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    edtCode6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
