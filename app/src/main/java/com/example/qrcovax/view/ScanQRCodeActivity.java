package com.example.qrcovax.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.qrcovax.R;
import com.example.qrcovax.model.RegisterShot;
import com.example.qrcovax.network.RestApiService;
import com.example.qrcovax.network.RetrofitInstance;
import com.example.qrcovax.utils.Constants;
import com.example.qrcovax.utils.Session;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission_group.CAMERA;

public class ScanQRCodeActivity extends AppCompatActivity {
    private ScannerLiveView scannerLiveView;

    private static final String CCCD = "cccd";
    private static final String USER_NAME = "name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r_code);
        initView();
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted...", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
            scannerLiveView.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
                @Override
                public void onScannerStarted(ScannerLiveView scanner) {
                    Toast.makeText(getApplicationContext(), "Đang quét...", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onScannerStopped(ScannerLiveView scanner) {
                    Toast.makeText(getApplicationContext(), "Dừng quét...", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onScannerError(Throwable err) {

                }

                @Override
                public void onCodeScanned(String data) {
                    String[] part = data.split(",");
                    String qr_id = part[0].trim();
                    String todanpho_id = part[1].trim();
                    String thoigianbatdau = part[2].trim();
                    String thoigianketthuc = part[2].trim();
                    if (qr_id.length() > 0) {
                        String cccd = Session.read(getApplicationContext(), CCCD, Constants.INIT_STRING);
                        String ten = Session.read(getApplicationContext(), USER_NAME, Constants.INIT_STRING);
                        RegisterShot registerShot = new RegisterShot(cccd, ten, qr_id, Long.parseLong(todanpho_id), Long.parseLong(thoigianbatdau), Long.parseLong(thoigianketthuc));
                        //Send infor to server
                        RestApiService apiService = RetrofitInstance.getApiService();
                        apiService.registerShotInfor(registerShot).enqueue(new Callback<RegisterShot>() {
                            @Override
                            public void onResponse(Call<RegisterShot> call, Response<RegisterShot> response) {
                                if (response.code() == 200) {
                                    Toast.makeText(getApplicationContext(), "Đăng ký tiêm thành công", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<RegisterShot> call, Throwable t) {

                            }
                        });
                        startActivity(new Intent(ScanQRCodeActivity.this, MainActivity.class));
                    }
                }
            });

        }
    }

    private void initView() {
        scannerLiveView = (ScannerLiveView) findViewById(R.id.scannerliveview_scanqrcode);
    }

    private boolean checkPermission() {
        int camer_permission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int vibrate_permission = ContextCompat.checkSelfPermission(getApplicationContext(), VIBRATE);
        return camer_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        int PERMISSION_CODE = 200;
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, VIBRATE}, PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            boolean camerAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean vibrationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (camerAccepted && vibrationAccepted) {
                Toast.makeText(this, "Permission Granted...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        scannerLiveView.stopScanner();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        decoder.setScanAreaPercent(0.8);
        scannerLiveView.setDecoder(decoder);
        scannerLiveView.startScanner();
    }
}
