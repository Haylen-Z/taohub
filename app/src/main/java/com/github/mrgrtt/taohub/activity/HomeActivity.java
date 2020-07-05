package com.github.mrgrtt.taohub.activity;

import android.os.Bundle;

import com.github.mrgrtt.taohub.R;
import com.github.mrgrtt.taohub.fragment.CarFragment;
import com.github.mrgrtt.taohub.fragment.HomeFragment;
import com.github.mrgrtt.taohub.fragment.MineFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {
    private Fragment[] fragments = {
            new HomeFragment(), new CarFragment(), new MineFragment()};
    private String[] titles = {"首页", "购物车", "我的"};
    private int currentIndex;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);

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
        toolbar.setTitle(titles[0]);

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
        toolbar.setTitle(titles[i]);
    }
}
