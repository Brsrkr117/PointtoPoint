package com.example.pointtopoint;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import timber.log.Timber;



public class RiderSecondActivity extends AppCompatActivity {
    private static final String TAG = "here";
    private FirebaseAuth firebaseAuth;
    private Button signout;
    private Button Viewprofile;
    private Button Vieworders;
    private EditText radius;
    private String UserID;
    private FirebaseFirestore db;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private String currentlat;
    private String currentlong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_second);

        firebaseAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        UserID=firebaseAuth.getCurrentUser().getUid();
        signout=(Button)findViewById(R.id.buttonRsignout);
        Viewprofile=(Button) findViewById(R.id.btnviewprof);
        Vieworders=(Button) findViewById(R.id.btnViewOrder);
        radius=(EditText) findViewById(R.id.etradius);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationProviderClient.getLastLocation();
        getLocationPermission();
        getDeviceLocation();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        Viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RiderSecondActivity.this, RiderProfileViewActivity.class));
            }
        });

        Vieworders.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String rad=radius.getText().toString();
                if(rad.isEmpty()){
                    Toast.makeText(RiderSecondActivity.this, "Enter radius", Toast.LENGTH_SHORT).show();
                }
                else{
                    currentlat = Double.toString(mLastKnownLocation.getLatitude());
                    currentlong = Double.toString(mLastKnownLocation.getLongitude());

                    db=FirebaseFirestore.getInstance();

                    Map<String, Object> usermap = new HashMap<>();
                    usermap.putIfAbsent("radius",rad);

                    db.collection("riders").document(UserID).update(usermap);

                    Bundle bundle = new Bundle();
                    bundle.putString("radius",rad);

                    Intent intent = new Intent(getApplicationContext(), RiderViewOrderActivity.class);
                    intent.putExtras(bundle);
                    finish();
                    startActivity(intent);
                }

            }
        });

    }
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(RiderSecondActivity.this, LoginActivity.class));
    }
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.getResult();
                        if (mLastKnownLocation != null) {
                            Toast.makeText(RiderSecondActivity.this,"Location Detected",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                    }
                });
            }
        } catch (SecurityException e)  {
            Timber.e(Objects.requireNonNull(e.getMessage()));
        }
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                finish();
                startActivity(new Intent(RiderSecondActivity.this, RiderProfileViewActivity.class));
                break;
            case R.id.nav_logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(RiderSecondActivity.this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);

    }



}
