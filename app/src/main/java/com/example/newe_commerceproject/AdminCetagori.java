package com.example.newe_commerceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCetagori extends AppCompatActivity {

    private ImageView tshirts, sportsThirts,femaleDresses,sweather;
    private ImageView glasses, hatsCaps, walletsBagsPurses, shoes;
    private ImageView handPhoneHandFree, laptops, watches, mobilePhones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        tshirts = findViewById(R.id.t_shirts);
        sportsThirts = findViewById(R.id.sports_t_shirts);
        femaleDresses = findViewById(R.id.female_dresses);
        sweather = findViewById(R.id.sweathers);
        glasses = findViewById(R.id.glasses);
        hatsCaps =  findViewById(R.id.hats_caps);
        walletsBagsPurses = findViewById(R.id.purses_bag);
        shoes = findViewById(R.id.shoes);
        handPhoneHandFree = findViewById(R.id.heandphoness_handfree);
        laptops = findViewById(R.id.lapton_pc);
        watches = findViewById(R.id.watches);
        mobilePhones = findViewById(R.id.mobilephones);


           tshirts.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCetagori.this, adminAddNewProductActivity.class);
            intent.putExtra("category", "tShirts");
            startActivity(intent);
        });
        sportsThirts.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCetagori.this, adminAddNewProductActivity.class);
            intent.putExtra("category", "SportsTShirts");
            startActivity(intent);
        });
        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCetagori.this, adminAddNewProductActivity.class);
                intent.putExtra("category", "Female Dresses");
                startActivity(intent);
            }
        });
        sweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCetagori.this, adminAddNewProductActivity.class);
                intent.putExtra("category", "tShirts");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCetagori.this, adminAddNewProductActivity.class);
                intent.putExtra("category", "tShirts");
                startActivity(intent);
            }
        });
        handPhoneHandFree.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCetagori.this, adminAddNewProductActivity.class);
            intent.putExtra("category", "tShirts");
            startActivity(intent);
        });
        shoes.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCetagori.this, adminAddNewProductActivity.class);
            intent.putExtra("category", "tShirts");
            startActivity(intent);
        });
        walletsBagsPurses.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCetagori.this, adminAddNewProductActivity.class);
            intent.putExtra("category", "tShirts");
            startActivity(intent);
        });

        handPhoneHandFree.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCetagori.this, adminAddNewProductActivity.class);
            intent.putExtra("category", "tShirts");
            startActivity(intent);
        });
        laptops.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCetagori.this, adminAddNewProductActivity.class);
            intent.putExtra("category", "tShirts");
            startActivity(intent);
        });
        watches.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCetagori.this, adminAddNewProductActivity.class);
            intent.putExtra("category", "tShirts");
            startActivity(intent);
        });
        mobilePhones.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCetagori.this, adminAddNewProductActivity.class);
            intent.putExtra("category", "tShirts");
            startActivity(intent);
        });
    }
    }
