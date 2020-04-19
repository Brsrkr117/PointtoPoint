package com.example.pointtopoint;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RiderViewOrderActivity extends AppCompatActivity {

    private static final String TAG = "here displaying";
    private RecyclerView mOrderlist;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private OrderListAdapter ordersListAdapter;
    private List<orders> ordersList;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private double currentLat;
    private double currentLng;
    float distance;

    private String UserID;
    private String rad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_view_order);
        mOrderlist=(RecyclerView)findViewById(R.id.Order_list);

        firebaseAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        UserID=firebaseAuth.getCurrentUser().getUid();

        Bundle b = getIntent().getExtras();
        rad = b.getString("radius");

        Toast.makeText(RiderViewOrderActivity.this,"Your radius is " + rad +"km", Toast.LENGTH_LONG).show();

        db=FirebaseFirestore.getInstance();
        ordersList=new ArrayList<>();
        ordersListAdapter= new OrderListAdapter(ordersList,this);

        mOrderlist=(RecyclerView)findViewById(R.id.Order_list);
        mOrderlist.setHasFixedSize(true);
        mOrderlist.setLayoutManager(new LinearLayoutManager(this));
        mOrderlist.setAdapter(ordersListAdapter);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationProviderClient.getLastLocation();

        db=FirebaseFirestore.getInstance();
        db.collection("orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.d(TAG,"Error"+e.getMessage());
                }

                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType()==DocumentChange.Type.ADDED){
                       /* String name1=doc.getDocument().getString("Order id");
                        Log.d(TAG,"Order ID "+ name1);*/
                        orders orders =doc.getDocument().toObject(orders.class);
                        String tempordertype=orders.getOrdertype();
                        String temporderstatus=orders.getOrderstatus();

                        /*String pickuplat=orders.getPickuplat();
                        String pickuplong=orders.getPickuplong();
                        String droplat=orders.getDroplat();
                        String droplong=orders.getDroplong();

                        String pickupaddr=orders.getPickaddr();
                        String dropaddr=orders.getDropaddr();*/

                        //conditional recycler view

                        if(temporderstatus.equals("pending")){
                            ordersList.add(orders);
                            ordersListAdapter.notifyDataSetChanged();
                        }


                    }
                }
            }
        });



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
                            currentLat = mLastKnownLocation.getLatitude();
                            currentLng = mLastKnownLocation.getLatitude();
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", Objects.requireNonNull(e.getMessage()));
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

    /**
     * Handles the result of the request for location permissions.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
    }
}
