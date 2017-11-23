package com.example.league95.downloadingwebcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    //Create a new class
    //All what an AsyncClass does is run your code on
    //A different thread!
    //It's a good idea to put any code that might take time
    //in a different thread!
    // 1)The first String is a URL
    // 2)Void is the name of the method we may or may not use!
    // 3)
    public class DownloadTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... urls) {

            //We download our web content here
            String result = "";
            //We need a URL
            URL url;
            //And a URL connection
            //Think of it as a browser we just opened
            HttpURLConnection urlConnection;

            try {
                //pass url from execute method
                url = new URL(urls[0]);
                //Now open connection
                //We need to cast from url to httpconnection
                urlConnection = (HttpURLConnection) url.openConnection();
                //Read data from the web, kinda like scanner
                //But instead of System.in, we read from connection
                InputStream in = urlConnection.getInputStream();
                //Reader using our input stream 'in.'
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                //Our data
                int data = inputStreamReader.read();

                while (data != -1)
                {
                    //current character from file
                    char current = (char) data;
                    result+= current;
                    data = inputStreamReader.read();
                }
                return result.toString();

            } catch (Exception e)
            {
                return e.toString();
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask downloadTask = new DownloadTask();
        //Since we are running an Async Task we have to use try catch
        String result;
        try {
            result = downloadTask.execute("https://www.ecowebhosting.co.uk/").get();
            Log.i("Result:", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
