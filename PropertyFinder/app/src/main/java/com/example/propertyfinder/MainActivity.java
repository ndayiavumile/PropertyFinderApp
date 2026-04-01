package com.example.propertyfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseFirestore db;
    private Spinner typeSpinner;
    private EditText minPriceEdit, maxPriceEdit;
    private List<Property> propertyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        typeSpinner = findViewById(R.id.type_spinner);
        minPriceEdit = findViewById(R.id.min_price);
        maxPriceEdit = findViewById(R.id.max_price);
        Button filterButton = findViewById(R.id.filter_button);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        filterButton.setOnClickListener(v -> applyFilters());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng easternCape = new LatLng(-32.5, 26.5);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(easternCape, 7f));

        mMap.setOnMarkerClickListener(marker -> {
            Property property = (Property) marker.getTag();
            if (property != null) {
                Intent intent = new Intent(MainActivity.this, PropertyDetailActivity.class);
                intent.putExtra("title", property.getTitle());
                intent.putExtra("price", property.getPrice());
                intent.putExtra("description", property.getDescription());
                startActivity(intent);
            }
            return false;
        });

        loadProperties(null, null, null);
    }

    private void applyFilters() {
        String selectedType = typeSpinner.getSelectedItem().toString();
        String minPriceStr = minPriceEdit.getText().toString();
        String maxPriceStr = maxPriceEdit.getText().toString();

        Double minPrice = minPriceStr.isEmpty() ? null : Double.parseDouble(minPriceStr);
        Double maxPrice = maxPriceStr.isEmpty() ? null : Double.parseDouble(maxPriceStr);
        String type = selectedType.equals("All Types") ? null : selectedType;

        loadProperties(type, minPrice, maxPrice);
    }

    private void loadProperties(String type, Double minPrice, Double maxPrice) {
        Query query = db.collection("properties");

        if (type != null) {
            query = query.whereEqualTo("type", type);
        }

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mMap.clear();
                propertyList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String title = document.getString("name");
                    String priceStr = document.getString("price");
                    String description = document.getString("description");
                    Double lat = document.getDouble("latitude");
                    Double lng = document.getDouble("longitude");
                    String pType = document.getString("type");

                    if (lat != null && lng != null && priceStr != null) {
                        try {
                            // Strip "R" or other currency symbols if present and parse
                            double priceValue = Double.parseDouble(priceStr.replaceAll("[^\\d.]", ""));
                            
                            if ((minPrice == null || priceValue >= minPrice) &&
                                (maxPrice == null || priceValue <= maxPrice)) {
                                
                                Property property = new Property(title, priceStr, description, lat, lng, pType);
                                propertyList.add(property);

                                LatLng location = new LatLng(lat, lng);
                                Marker marker = mMap.addMarker(new MarkerOptions()
                                        .position(location)
                                        .title(title)
                                        .snippet(priceStr));
                                if (marker != null) {
                                    marker.setTag(property);
                                }
                            }
                        } catch (NumberFormatException e) {
                            Log.e("MainActivity", "Error parsing price: " + priceStr);
                        }
                    }
                }
            } else {
                Toast.makeText(MainActivity.this, "Error loading properties", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
