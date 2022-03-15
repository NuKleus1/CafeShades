package com.example.cafeshades.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cafeshades.R;
import com.example.cafeshades.UserPreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (UserPreferences.getPrefInstance(getApplicationContext()).getLoggedInFlag()){
            startActivity(new Intent(MainActivity.this, MainActivitySecond.class));
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main_activity);

    }
}