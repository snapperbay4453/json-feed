package com.snapperbay4453.jsonfeed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.snapperbay4453.jsonfeed.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
    }

}
