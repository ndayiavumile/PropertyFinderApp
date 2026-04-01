package com.example.propertyfinder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PropertyDetailActivity extends AppCompatActivity {

    private String propertyTitle;
    private SharedPreferences sharedPreferences;
    private Button favButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Property Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView titleView = findViewById(R.id.detail_title);
        TextView priceView = findViewById(R.id.detail_price);
        TextView descriptionView = findViewById(R.id.detail_description);
        favButton = findViewById(R.id.fav_button);

        propertyTitle = getIntent().getStringExtra("title");
        String price = getIntent().getStringExtra("price");
        String description = getIntent().getStringExtra("description");

        titleView.setText(propertyTitle);
        priceView.setText(price);
        descriptionView.setText(description);

        sharedPreferences = getSharedPreferences("favorites", MODE_PRIVATE);

        updateFavButton();

        favButton.setOnClickListener(v -> toggleFavorite());
    }

    private void updateFavButton() {
        boolean isFav = sharedPreferences.getBoolean(propertyTitle, false);
        favButton.setText(isFav ? "Remove from Favorites" : "Add to Favorites");
    }

    private void toggleFavorite() {
        boolean isFav = sharedPreferences.getBoolean(propertyTitle, false);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFav) {
            editor.remove(propertyTitle);
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        } else {
            editor.putBoolean(propertyTitle, true);
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        }
        editor.apply();
        updateFavButton();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
