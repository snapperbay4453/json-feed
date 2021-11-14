package com.snapperbay4453.jsonfeed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.snapperbay4453.jsonfeed.databinding.ActivityMainBinding;
import com.snapperbay4453.jsonfeed.services.MainService;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private void initializeBackgroundService() {
        Intent mainServiceIntent = new Intent(this, MainService.class);
        mainServiceIntent.putExtra("command", "initialize");
        startService(mainServiceIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        initializeBackgroundService();
    }

}
