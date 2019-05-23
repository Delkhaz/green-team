package com.example.greenteam.finalproj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.view.View;
import android.widget.ListView;
import android.content.Context;
import java.util.ArrayList;
import android.widget.TextView;
import android.util.Log;

public class editActivity extends AppCompatActivity {

    Context mContext;
    ListView listview;
    File[] filesList;
    TextView editText;
    String fileSelected;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_scan:
                    Intent intentMain = new Intent(editActivity.this, MainActivity.class);
                    startActivity(intentMain);
                    return true;
                case R.id.navigation_view:
                    Intent intentView = new Intent(editActivity.this, editActivity.class);
                    startActivity(intentView);
                    item.setChecked(true);
                    return true;
                case R.id.navigation_edit:
                    Intent intentEdit = new Intent(editActivity.this, themeActivity.class);
                    startActivity(intentEdit);
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

        setTitle("Choose a note to edit");
        setContentView(R.layout.activity_edit);
        mContext = this;
         editText = (TextView) findViewById(R.id.editTextView);
         editText.setVisibility(View.INVISIBLE);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        listview = (ListView) findViewById(R.id.notesListView);
        listview.setVisibility(View.VISIBLE);

        loadListView();


    }

    private void loadListView(){
       // String stringList[] = {"one", "two", "three"};
        ArrayList<String> stringList = new ArrayList<String>();
        File dir = new File("/sdcard/", "writtenFiles");

        if(dir.exists()){
            filesList = dir.listFiles();

            //for each file in the given directory, read the first line and add it to the array that the list view is created from
            for(int i = 0; i < filesList.length; i++){
                File currentFile = filesList[i];


                try{
                    StringBuilder text = new StringBuilder();
                    BufferedReader br = new BufferedReader(new FileReader(currentFile));
                    String line = "";
                    line = br.readLine();
                    if(line != null) {
                        text.append(line);
                        text.append('\n');
                      //  stringList.add(currentFile.getName());
                        stringList.add(text.toString());
                    }

                    br.close();


                }catch (Exception e){
                    e.printStackTrace();

                }

            }

        }else{
            //means the directory is empty, so tell user that they dont have any scanned notes yet
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stringList);
        arrayAdapter.setNotifyOnChange(true);
        listview.setAdapter(arrayAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                listview.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.VISIBLE);
                editText.setGravity(View.TEXT_ALIGNMENT_CENTER);
                editText.setTextSize(36);
                fileSelected = filesList[position].toString();
               // File currentFile = new File(fileSelected);

                try{
                    StringBuilder text = new StringBuilder();
                    BufferedReader buff = new BufferedReader(new FileReader(fileSelected));
                    String line = "";
                    while ((line = buff.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }

                    editText.setText(text.toString());
                    buff.close();


                }catch (Exception e){
                    e.printStackTrace();

                }

            }

        });



    }


}
