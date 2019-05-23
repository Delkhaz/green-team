package com.example.greenteam.finalproj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;


public class viewActivity extends AppCompatActivity {

   // BottomNavigationView bottomNavView = (BottomNavigationView) findViewById(R.id.navigation);
  //  Menu menu = bottomNavView.getMenu();
   // MenuItem menitem = menu.getItem(1);
    //MenuItem menuItem = menu.getItem(1);
   // MenuItem checkedItem = menitem.setChecked(true);
    //checkedItem.setChecked(true);
  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
          = new BottomNavigationView.OnNavigationItemSelectedListener() {

      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
              case R.id.navigation_scan:

                  Intent intentMain = new Intent(viewActivity.this, MainActivity.class);
                  startActivity(intentMain);

                  return true;
              case R.id.navigation_view:

                  Intent intentView = new Intent(viewActivity.this, editActivity.class);
                  startActivity(intentView);
                  // item.setChecked(true);

                  return true;
              case R.id.navigation_edit:

                  Intent intentEdit = new Intent(viewActivity.this, themeActivity.class);
                  startActivity(intentEdit);
                  return true;
          }
          return false;
      }
  };

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view);

        TextView midTextView;
        midTextView = (TextView) findViewById(R.id.viewMiddleText);
        midTextView.setText("hi, tiny world");
       // setContentView(R.layout.activity_view);
    }

}
