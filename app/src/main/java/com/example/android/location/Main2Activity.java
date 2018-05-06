package com.example.android.location;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

     EditText username , password;
      BackgroundWorker bgWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        username = (EditText) findViewById(R.id.person_name_field);
        password = (EditText) findViewById(R.id.person_password_field);
    }


    //tologin method description
    public void toLogin(View view){
        String empusername = username.getText().toString();
        String emppassword = password.getText().toString();
         String type = "login";
       // boolean status = false;
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type , empusername ,emppassword );
      /*String tochk = bgWorker.tosendback();
        if(tochk == "Login Successful"){
            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
            startActivity(intent);
        }*/
    }
   // to send username
    public String toSendUsername()
    {
        String getusername = username.getText().toString();
        return getusername;
    }

}
