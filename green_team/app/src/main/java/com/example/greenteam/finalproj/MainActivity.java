package com.example.greenteam.finalproj;
// // This sample uses the Apache HTTP client from HTTP Components (http://hc.apache.org/httpcomponents-client-ga/)
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;



import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import android.util.Log;
import java.io.File;
import java.io.FileWriter;

import android.Manifest;
import java.io.BufferedReader;
import java.io.FileReader;

import android.content.Intent;

//Note.
//https://westus.dev.cognitive.microsoft.com/docs/services/56f91f2d778daf23d8ec6739/operations/56f91f2e778daf14a499e1fc
// api code re-factored and re-written - base code derived from the link above ^

public class MainActivity extends AppCompatActivity {

    TextView enterURL;
    Button scanButton;
    Button saveButton;
    EditText urlEnteredLayout;
    String urlFromInput;
    TextInputLayout urlEnteredTopLayer;
    BottomNavigationView bottomNavBar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_scan:

                    Intent intentMain = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intentMain);

                    return true;
                case R.id.navigation_view:

                        Intent intentView = new Intent(MainActivity.this, editActivity.class);
                        startActivity(intentView);
                       // item.setChecked(true);

                    return true;
                case R.id.navigation_edit:

                    Intent intentEdit = new Intent(MainActivity.this, themeActivity.class);
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
        setTitle("Scan Image");
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        enterURL = (TextView) findViewById(R.id.textView);
        urlEnteredTopLayer = (TextInputLayout) findViewById(R.id.urlEnteredInputLayout);
        urlEnteredLayout = (EditText) findViewById(R.id.urlEnteredEditText);
        urlEnteredLayout.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String inputText = textView.getText().toString();
                    urlFromInput = inputText;

                    // enterURL.setText(inputText);
                }
                return handled;
            }

        });

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                saveButton.setText("Saved!");
            }

        });


        scanButton =  (Button) findViewById(R.id.scanButton);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                //change this whenever it expires
                final String subscriptionKey = "a3868dfb737445838a4b7882651d77ac";
                final String uriBase =
                        "https://westcentralus.api.cognitive.microsoft.com/vision/v2.0/ocr";

                    //fix this eventually back to user input
                 // urlFromInput = "http://3.bp.blogspot.com/-bOnrzrexeOU/UVxwr_oAIYI/AAAAAAAABDg/zpU2kmZKivA/s1600/tumblr_meiiiw8StW1rmazh1o1_1280.jpg";
                 //   urlFromInput = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/" + "Atomist_quote_from_Democritus.png/338px-Atomist_quote_from_Democritus.png";


                //test urls
                // "http://3.bp.blogspot.com/-bOnrzrexeOU/UVxwr_oAIYI/AAAAAAAABDg/zpU2kmZKivA/s1600/tumblr_meiiiw8StW1rmazh1o1_1280.jpg";
                //"https://i.pinimg.com/originals/dd/94/74/dd94741429b504131d9e37998cecb941.jpg";


            /*
            "https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/" +
                    "Atomist_quote_from_Democritus.png/338px-Atomist_quote_from_Democritus.png";
*/


                     JSONObject jsonBody = new JSONObject();
                     try {
                         jsonBody.put("url", urlFromInput);
                     } catch (JSONException e) {
                         System.out.println("error");

                     }

                     final String requestBody = jsonBody.toString();
                     enterURL.setText("Scanning...");
                     Log.d("JSONCREATE", "Requestbody created");
                     JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                             Request.Method.POST,
                             uriBase,
                             jsonBody,
                             new Response.Listener<JSONObject>() {
                                 @TargetApi(Build.VERSION_CODES.M)
                                 @Override
                                 public void onResponse(JSONObject response) {
                                     Log.i("TEST", requestBody.toString());
                                     Log.i("VOLLEY", response.toString());
                                     ArrayList<String> vec = new ArrayList();

                                     ArrayList<String> ArrayList = new ArrayList<>();



                                     try {
                                         JSONArray r = response.getJSONArray("regions");
                                         //JSONObject bbox = r.getJSONObject(0);
                                         //JSONArray lines = bbox.getJSONArray("lines");

                                         JSONArray regions = response
                                                 .getJSONArray("regions")
                                                 .getJSONObject(0)
                                                 .getJSONArray("lines");
                                         for(int i = 0; i < regions.length(); i++){
                                             JSONObject boundingBox = regions.getJSONObject(i);
                                             JSONArray words = boundingBox.getJSONArray("words");
                                             for(int j = 0; j < words.length(); j++){
                                                 String text = words
                                                         .getJSONObject(j)
                                                         .getString("text");
                                                 ArrayList.add(text);
                                             }
                                         }


                                         /*
                                                 .getJSONObject(i)
                                                 .getJSONArray("words")
                                                 .getJSONObject(j)
                                                 .getString("text");
                                                 */

                                         for(int i = 0; i < regions.length(); i++) {
                                             JSONObject bbox = regions.getJSONObject(i);

                                             //should be able to get words
                                             JSONArray words = bbox.getJSONArray("words");
                                             for (int j = 0; j < words.length(); j++) {
                                                 JSONObject text = words.getJSONObject(j);

                                                 //System.out.println(text);

                                                 //one liner...
                                                 //json.getJSONArray("regions").getJSONObject(0).getJSONArray("lines").getJSONObject(i).getJSONArray("words").getJSONObject(j).getString("text");
                                                 String sentence = text.getString("text");
                                                 vec.add(sentence);

                                             }
                                             String wordsFromApi = "";
                                             Log.d("VECSENT", vec.toString());
                                             scanButton.setVisibility(View.INVISIBLE);
                                             urlEnteredLayout.setVisibility(View.INVISIBLE);
                                             urlEnteredTopLayer.setVisibility(View.INVISIBLE);
                                             saveButton.setVisibility(View.VISIBLE);

                                             for(int k = 0; k < vec.size(); k++){
                                                 wordsFromApi += " " + vec.get(k);
                                             }

                                             String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                             requestPermissions(permissions, 1);
                                                File file = new File("/sdcard/", "writtenFiles");
                                                // File file = new File(Context.getFilesDir(),"mydir");
                                                 if(!file.exists()){
                                                     file.mkdir();
                                                 }


                                                 try{
                                                    file.mkdirs();
                                                    file.createNewFile();

                                                     String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date()) + ".txt";
                                                     Log.d("DATEFILENAME", timeStamp);

                                                     File writerFile = new File(file, timeStamp);


                                                   // File writerFile = new File(file, "testfile.txt");
                                                    writerFile.createNewFile();

                                                     FileWriter writer = new FileWriter(writerFile);
                                                     writer.append(wordsFromApi);
                                                     writer.flush();
                                                     writer.close();
                                                     StringBuilder text = new StringBuilder();
                                                     BufferedReader br = new BufferedReader(new FileReader(writerFile));
                                                     String line;
                                                    Log.d("FILENAME", writerFile.getAbsolutePath());
                                                     while ((line = br.readLine()) != null) {
                                                         text.append(line);
                                                         text.append('\n');
                                                     }
                                                     Log.d("FILEREAD", text.toString());
                                                     br.close();




                                                 }catch (Exception e){
                                                     e.printStackTrace();

                                                 }

                                             enterURL.setText(wordsFromApi.toString());

                                         }


                                     } catch(JSONException e){
                                         Log.e("REGION", e.toString());
                                     }

                                 }
                             },
                             new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError error) {
                                     enterURL.setText("Incorrect URL, try again");
                                         Log.e("FIRSTLOG", error.toString());
                                 }
                             }) {
                         @Override
                         public Map<String, String> getHeaders() throws AuthFailureError {
                             HashMap<String, String> headers = new HashMap<>();
                             headers.put("Ocp-Apim-Subscription-Key", subscriptionKey.toString());
                             return headers;
                         }
                     };

                     requestQueue.add(jsonObjReq);


            }
        });

    }

}
