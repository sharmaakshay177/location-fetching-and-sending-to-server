package com.example.android.location;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class BackgroundWorker extends AsyncTask<String, Object, String> {
   private Context context;
    //String to_send="";
   private AlertDialog alertDialog;
    BackgroundWorker(Context ctx){
        context = ctx;
    }
    BackgroundWorker bg;
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String result="";
        String login_url = "http://10.0.2.2/login4.php";//if not working replace by local ip address of machine else 10.0.2.2
        String insert_url ="http://10.0.2.2/insert.php";
        if(type.equals("login")){
            try {
                String username = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                 bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String line="";
                while((line = bufferedReader.readLine()) != null ){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {

            }
        }
        else if (type.equals("insert")){
            try {

                String username = params[1];
                String date = params[2];
                String time = params[3];
                String latitude = params[4];
                String longitude = params[5];
                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8")+"&"
                        +URLEncoder.encode("time","UTF-8")+"="+URLEncoder.encode(time,"UTF-8")+"&"
                        +URLEncoder.encode("latitude","UTF-8")+"="+URLEncoder.encode(latitude,"UTF-8")+"&"
                        +URLEncoder.encode("longitude","UTF-8")+"="+URLEncoder.encode(longitude,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String line="";
                while((line = bufferedReader.readLine()) != null ){
                    result = result + line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
               return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {

            }
        }
        return result;
    }

    @Override
    protected void onPreExecute(){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result){

        alertDialog.setMessage(result);//to print the result from server in alert

        alertDialog.show();//to show the response of the server

    }




     //@Override
    /* private void onProgressUpdate(String... values){
        super.onProgressUpdate(values);
    }*/

}
