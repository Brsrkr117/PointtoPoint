package com.example.pointtopoint;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;



public class RiderSecondActivity extends AppCompatActivity {
    private static final String TAG = "here";
    private FirebaseAuth firebaseAuth;
    //private Button signout;
    //private Button Viewprofile;
    private Button Vieworders;
    private EditText radius;
    private String UserID;
    private FirebaseFirestore db;
    private TextView locationhead,lat,lng;

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
        //signout=(Button)findViewById(R.id.buttonRsignout);
        //Viewprofile=(Button) findViewById(R.id.btnviewprof);
        Vieworders=(Button) findViewById(R.id.btnViewOrder);
        radius=(EditText) findViewById(R.id.etradius);
        locationhead=findViewById(R.id.loc_head);

        lat=findViewById(R.id.lat);
        lng=findViewById(R.id.lng);



        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationProviderClient.getLastLocation();
        getLocationPermission();
        getDeviceLocation();

        //Toast.makeText(RiderSecondActivity.this,currentlat, Toast.LENGTH_SHORT).show();
        currentlat = lat.getText().toString();
        currentlong = lng.getText().toString();

        //Toast.makeText(RiderSecondActivity.this, currentlat, Toast.LENGTH_LONG).show();

        /*signout.setOnClickListener(new View.OnClickListener() {
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
        });*/

        Vieworders.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String rad=radius.getText().toString();
                String currentlat1 = lat.getText().toString();
                String currentlong1 = lng.getText().toString();

                if(rad.isEmpty()){
                    Toast.makeText(RiderSecondActivity.this, "Enter radius", Toast.LENGTH_SHORT).show();
                }
                else{

                    db=FirebaseFirestore.getInstance();

                    Map<String, Object> usermap = new HashMap<>();
                    usermap.putIfAbsent("radius",rad);

                    db.collection("riders").document(UserID).update(usermap);



                    Bundle bundle = new Bundle();
                    bundle.putString("radius",rad);
                    bundle.putString("currentlat",currentlat1);
                    bundle.putString("currentlong",currentlong1);

                    //Toast.makeText(RiderSecondActivity.this,currentlat1, Toast.LENGTH_LONG).show();

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
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(this,location ->{
            if(location!=null){
                /*currentlat = Double.toString(location.getLatitude());
                currentlong = Double.toString(location.getLongitude());*/
                lng.setText(Double.toString(location.getLongitude()));
                lat.setText(Double.toString(location.getLatitude()));
                Toast.makeText(RiderSecondActivity.this,"Location Detected",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(RiderSecondActivity.this,"Location null",Toast.LENGTH_SHORT).show();
        } );
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
