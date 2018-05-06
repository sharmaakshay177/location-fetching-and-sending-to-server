package com.example.android.location;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends Activity {

    Button btnShowLocation;
    Button tostatechange,insertbtn;
    TextView lat_text;
    TextView lon_text;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;
    //activity 2 object
    //Main2Activity toget;
  //  String Username = toget.toSendUsername();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
              //  execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        insertbtn = (Button) findViewById(R.id.insert_btn);
        tostatechange = (Button)findViewById(R.id.login_btn);
        btnShowLocation = (Button) findViewById(R.id.locationbutton);

         lat_text = (TextView) findViewById(R.id.lat_label);
        lon_text = (TextView)findViewById(R.id.long_label);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                     lat_text.setText(""+latitude);
                     lon_text.setText(""+longitude);
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });
        tostatechange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent numbersIntent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(numbersIntent);

            }
        });
    }

     @RequiresApi(api = Build.VERSION_CODES.N)
     public  void toInsert(View view){
         String latitude = lat_text.getText().toString();
         String longitude = lon_text.getText().toString();
        /* Date date = new Date();
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm:ss");
         String date1 = dateFormat.format(date);//simple date format
         String date2 = dateFormat1.format(date);//simple time format*/
        String date1 = get_returndate();
        String date2= get_returntime();
         String type = "insert";
         String username = "root";
         BackgroundWorker backgroundWorker = new BackgroundWorker(this);
         backgroundWorker.execute(type ,username, date1 ,date2, latitude , longitude );

     }

    // @RequiresApi(api = Build.VERSION_CODES.N)
     @RequiresApi(api = Build.VERSION_CODES.N)
     public static String get_returndate() {
         Date date = new Date();
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

         String date1 = dateFormat.format(date);//simple date format

         return date1;
     }

     @RequiresApi(api = Build.VERSION_CODES.N)
     public static String get_returntime(){
         Date date = new Date();
         SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm:ss");
         String date2 = dateFormat1.format(date);//simple time format
         return date2;
     }
}





