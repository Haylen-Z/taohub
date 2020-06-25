package com.github.mrgrtt.taohub.activity;

import android.os.Bundle;

import com.github.mrgrtt.taohub.R;
import com.github.mrgrtt.taohub.fragment.CarFragment;
import com.github.mrgrtt.taohub.fragment.HomeFragment;
import com.github.mrgrtt.taohub.fragment.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {
    private Fragment[] fragments = {
            new HomeFragment(), new CarFragment(), new MineFragment()};
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        for (Fragment f: fragments) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, f)
                    .hide(f)
                    .commit();
        }
        currentIndex = 0;
        getSupportFragmentManager().beginTransaction()
                .show(fragments[0])
                .commit();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        switchFragment(0);
                        return true;
                    case R.id.cart:
                        switchFragment(1);
                        return true;
                    case R.id.mine:
                        switchFragment(2);
                        return true;
                }
                return false;
            }
        });
    }

    private void switchFragment(int i) {
        if (i == this.currentIndex) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .hide(fragments[currentIndex])
                .show(fragments[i])
                .commit();
        this.currentIndex = i;
    }
}
