package com.example.nosrick.fatgladius;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity
{

    JSONObject profile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReadProfile();
    }

    protected void ReadProfile()
    {
        File file = new File(getFilesDir(), "profile.json");
        if(file.exists())
        {
            try
            {
                int bufferLength = (int)file.length();
                byte[] buffer = new byte[bufferLength];
                FileInputStream inputStream = new FileInputStream(file);
                int result = inputStream.read(buffer, 0, bufferLength);
                String jsonString = new String(buffer);
                profile = new JSONObject(jsonString);
            }
            catch (Exception E)
            {
                Toast.makeText(this, "There was a problem loading your profile.", Toast.LENGTH_LONG).show();
                Log.e(this.getClass().toString(), E.getMessage());
            }
        }
        else
        {
            Intent goalIntent = new Intent(this, GoalActivity.class);
            startActivity(goalIntent);
        }
    }
}
