package com.example.greenteam.finalproj;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.content.SharedPreferences;

import android.widget.Switch;

// I followed through Chris Blunt's tutorial found here
// https://www.chrisblunt.com/android-toggling-your-apps-theme/
//
public class themeActivity extends AppCompatActivity {

    Switch themeSwitch;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_scan:

                    Intent intentMain = new Intent(themeActivity.this, MainActivity.class);
                    startActivity(intentMain);
                    return true;
                case R.id.navigation_view:
                    Intent intentView = new Intent(themeActivity.this, editActivity.class);
                    startActivity(intentView);
                    return true;
                case R.id.navigation_edit:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences(getString(R.string.PREFS_NAME), MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(getString(R.string.PREF_DARK_THEME), false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_theme);
        themeSwitch = findViewById(R.id.theme);
        themeSwitch.setChecked(useDarkTheme);
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                toggleTheme(isChecked);
            }
        });

        //okay i tried to attempt to fix the highlighting issue here, but it doesnt work
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void toggleTheme(boolean darkTheme) {
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.PREFS_NAME), MODE_PRIVATE).edit();
        editor.putBoolean(getString(R.string.PREF_DARK_THEME), darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }



}
