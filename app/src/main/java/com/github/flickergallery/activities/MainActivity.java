package com.github.flickergallery.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.flickergallery.R;
import com.github.flickergallery.fragments.GalleryFragment;
import com.github.flickergallery.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fragSelected(new GalleryFragment());
    }

    private void init() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        initElements();
    }

    private void initElements() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void fragSelected(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_frame,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_gallery:
                        fragSelected(new GalleryFragment());
                        break;
                    case R.id.navigation_search:
                        fragSelected(new SearchFragment());
                        break;
                }
        return true;
    }
}
