package com.example.nosrick.fatgladius;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class TitleActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    public void onStartClick(View viewRef)
    {
        File file = new File(getFilesDir(),"profile.json");
        if(file.exists())
        {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        }
        else
        {
            Intent goalSet = new Intent(this, GoalActivity.class);
            startActivity(goalSet);
        }
    }
}
