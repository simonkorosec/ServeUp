package serve.serveup.views.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import serve.serveup.utils.DialogPassableMethod;
import serve.serveup.utils.Utils;

import static android.Manifest.permission.CAMERA;

public class QRcodeScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private int idNarocila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        int currentApiVersion = Build.VERSION.SDK_INT;
        if(currentApiVersion >=  Build.VERSION_CODES.M) {
            if(!permissionIsGranted())
                requestPermission();
        }

        if(getIntent() != null) {
            idNarocila = getIntent().getIntExtra("id_narocilo", -1);
        }

    }

    @Override
    public void handleResult(Result result) {

        final String myResult = result.getText();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Skeniranje uspešno")
                .setMessage("Koda naročila: " + myResult)
                .setNegativeButton("Potrdi naročilo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(QRcodeScannerActivity.this, ProcessingQRCodeConfirmationActivity.class);
                        myIntent.putExtra("qrcode", myResult);
                        myIntent.putExtra("scan_id_narocilo", idNarocila);
                        startActivity(myIntent);
                        finish();
                    }
                })
                .setPositiveButton("Ponovno skeniraj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scannerView.resumeCameraPreview(QRcodeScannerActivity.this);
                    }
                })
                .create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0) {
                boolean cameraAccepted = (grantResults[0] == PackageManager.PERMISSION_GRANTED);
                if(!cameraAccepted) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(CAMERA)) {

                            Utils.createDialog(QRcodeScannerActivity.this, "Dovoljenja",
                                    "Potrditi morate dovoljenja za nadaljevanje", "OK",
                                    "CANCEL", new DialogPassableMethod() {
                                        @Override
                                        public void executeMethod() {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{CAMERA},
                                                        REQUEST_CAMERA);
                                            }
                                        }
                                    });
                        }
                    }
                }
            }
        }
    }

    private boolean permissionIsGranted() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (permissionIsGranted() && (idNarocila != 0 || idNarocila != -1)) {
                if(scannerView == null) {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
            else
                requestPermission();
        }
    }
}
