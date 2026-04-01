package com.example.propertyfinder;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        db = FirebaseFirestore.getInstance();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng southAfricaCenter = new LatLng(-30.5595, 22.9375); // Rough center of SA
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(southAfricaCenter, 5));
            mMap = googleMap;

            LatLng southAfricaCente = new LatLng(-30.5595, 22.9375);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(southAfricaCenter, 5));

            // Test property marker
            LatLng property = new LatLng(-26.2041, 28.0473); // Johannesburg
            mMap.addMarker(new MarkerOptions()
                    .position(property)
                    .title("House for Sale")
                    .snippet("Price: R850 000"));
        db.collection("properties")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                        double lat = document.getDouble("latitude");
                        double lng = document.getDouble("longitude");
                        String title = document.getString("title");
                        String price = document.getString("price");

                        LatLng propertyLocation = new LatLng(lat, lng);

                        mMap.addMarker(new MarkerOptions()
                                .position(propertyLocation)
                                .title(title)
                                .snippet("Price: " + price));
                    }
                });
        }

    }
