package com.example.android.nearprototype;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    GoogleApiClient client;
    Location userLocation;
    String TAG = "NEAR: ";

    FirebaseDatabase storyList = FirebaseDatabase.getInstance();
    DatabaseReference currentStory = FirebaseDatabase.getInstance().getReference(UUID.randomUUID().toString());

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupLocation();
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        userLocation = new Location(LocationServices.FusedLocationApi.getLastLocation(client));

         ((TextView) findViewById(R.id.textView)).
                 setText(String.format("( %s: %f, %s: %f )",
                         "Lat", userLocation.getLatitude(),
                         "Lng", userLocation.getLongitude()));
        
        currentStory.child("Location").child("Latitude").setValue(userLocation.getLatitude());
        currentStory.child("Location").child("Longitude").setValue(userLocation.getLongitude());
        currentStory.child("Gembag").child("Diamonds").setValue(100);
        currentStory.child("Gembag").child("Emeralds").setValue(0);
        currentStory.child("Gembag").child("Rubies").setValue(0);
        currentStory.child("Gembag").child("Sapphires").setValue(1);
        currentStory.child("userStory").child("Description").setValue("storyDescription");
        currentStory.child("userStory").child("Filename").setValue("storyFilename_ID.jpg");
        currentStory.child("userStory").child("Description").setValue("storyTitle");
    
    }

    @Override
    public void onConnectionFailed(ConnectionResult result)
    {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause)
    {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        client.connect();
    }

    // connect to Google API for LocationServices
    private void setupLocation()
    {
        // Create an instance of GoogleAPIClient.
        if (client == null)
        {
            client = new GoogleApiClient.Builder(this).
                    addConnectionCallbacks(this).
                    addOnConnectionFailedListener(this).
                    addApi(LocationServices.API).
                    build();
        }

        onStart();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        client.connect();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (client.isConnected())
        {
            client.disconnect();
        }
    }

    public void backToMap(View v)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
