package com.example.nosrick.fatgladius;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.FileOutputStream;

public class GoalActivity extends AppCompatActivity
{
    final int CALORIES_PER_KG = 7700;

    final float SEDENTARY_MULTI = 1.1f;
    final float LIGHT_MULTI = 1.25f;
    final float MODERATE_MULTI = 1.35f;
    final float VERY_MULTI = 1.525f;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_activity);
    }

    public void onClickLetsGoButton(View viewRef)
    {
        String goal = "";
        RadioButton loseRadioButton = findViewById(R.id.loseRadioButton);
        boolean isChecked = loseRadioButton.isChecked();
        if (isChecked)
        {
            goal = "LOSE";
        }
        else
        {
            RadioButton gainRadioButton = findViewById(R.id.gainRadioButton);
            isChecked = gainRadioButton.isChecked();
            if(isChecked)
            {
                goal = "GAIN";
            }
            else
            {
                RadioButton maintainRadioButton = findViewById(R.id.maintainRadioButton);
                isChecked = maintainRadioButton.isChecked();
                if(isChecked)
                {
                    goal = "MAINTAIN";
                }
                else
                {
                    goal = "NONE";
                }
            }
        }

        EditText kgToLoseEditText = findViewById(R.id.kgToLoseEditText);
        int kgToChange = 0;
        try
        {
            kgToChange = Integer.parseInt(kgToLoseEditText.getText().toString());
        }
        catch(Exception E)
        {
            Toast.makeText(this, "Invalid Kgs entered. Is it a whole number?", Toast.LENGTH_LONG).show();
            return;
        }

        EditText levelsEditText = findViewById(R.id.levelsEditText);
        int levels = 0;
        try
        {
            levels = Integer.parseInt(levelsEditText.getText().toString());
        }
        catch(Exception E)
        {
            Toast.makeText(this, "Invalid levels entered. Is it a whole number?", Toast.LENGTH_LONG).show();
            return;
        }

        EditText currentWeightEditText = findViewById(R.id.kgWeightEditText);
        float currentWeight = 0;
        try
        {
            currentWeight = Float.parseFloat(currentWeightEditText.getText().toString());
        }
        catch (Exception E)
        {
            Toast.makeText(this, "Invalid weight entered. Is it a valid number?", Toast.LENGTH_LONG).show();
            return;
        }

        EditText heightEditText = findViewById(R.id.heightEditText);
        float metresTall = 0;
        try
        {
            metresTall = Float.parseFloat(heightEditText.getText().toString());
        }
        catch(Exception E)
        {
            Toast.makeText(this, "Invalid height entered. Is it a valid number?", Toast.LENGTH_LONG).show();
            return;
        }

        String sex = "";
        RadioButton femaleRadioButton = findViewById(R.id.femaleRadioButton);
        RadioButton maleRadioButton = findViewById(R.id.maleRadioButton);
        if (femaleRadioButton.isChecked())
        {
            sex = "FEMALE";
        }
        else if(maleRadioButton.isChecked())
        {
            sex = "MALE";
        }

        int age = 0;
        EditText ageEditText = findViewById(R.id.ageEditText);
        try
        {
            age = Integer.parseInt(ageEditText.getText().toString());
        }
        catch(Exception E)
        {
            Toast.makeText(this, "Invalid age entered. Is it a whole number?", Toast.LENGTH_LONG).show();
            return;
        }

        float activityLevel = 0;
        RadioButton sedentaryRadioButton = findViewById(R.id.sedentaryRadioButton);
        RadioButton lightRadioButton = findViewById(R.id.lightlyActiveRadioButton);
        RadioButton moderateRadioButton = findViewById(R.id.moderatelyActiveRadioButton);
        RadioButton veryRadioButton = findViewById(R.id.veryActiveRadioButton);

        if (sedentaryRadioButton.isChecked())
        {
            activityLevel = SEDENTARY_MULTI;
        }
        else if(lightRadioButton.isChecked())
        {
            activityLevel = LIGHT_MULTI;
        }
        else if(moderateRadioButton.isChecked())
        {
            activityLevel = MODERATE_MULTI;
        }
        else if(veryRadioButton.isChecked())
        {
            activityLevel = VERY_MULTI;
        }

        int BMR = 0;

        int centimetres = 0;

        if (metresTall > 3)
        {
            centimetres = (int)metresTall;
        }
        else
        {
            centimetres = (int)(metresTall * 100);
        }
        if(sex.equals("FEMALE"))
        {
            BMR = FemaleBMR(currentWeight, centimetres, age);
        }
        else
        {
            BMR = MaleBMR(currentWeight, centimetres, age);
        }

        BMR *= activityLevel;

        float kgPerLevel = kgToChange / levels;

        JSONObject profile = new JSONObject();
        try
        {
            profile.put("goal", goal);
            profile.put("kgToChange", kgToChange);
            profile.put("levels", levels);
            profile.put("currentWeight", currentWeight);
            profile.put("metresTall", metresTall);
            profile.put("sex", sex);
            profile.put("age", age);
            profile.put("BMR", BMR);
            profile.put("kgPerLevel", kgPerLevel);
        }
        catch(Exception E)
        {
            Toast.makeText(this, "Something went wrong making your profile.", Toast.LENGTH_LONG).show();
            return;
        }

        try
        {
            FileOutputStream outputStream = openFileOutput("profile.json", Context.MODE_PRIVATE);
            outputStream.write(profile.toString().getBytes());
            outputStream.close();
        }
        catch(Exception E)
        {
            Toast.makeText(this, "Something went wrong saving your profile.", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Your BMR is " + BMR, Toast.LENGTH_LONG).show();
    }

    protected int MaleBMR(float weight, int height, int age)
    {
        return (int)(66 + (13.75f * weight) + (5 * height) - (6.8f * age));
    }

    protected int FemaleBMR(float weight, int height, int age)
    {
        return (int)(665 + (9.6f * weight) + (1.8f * height) - (4.7f * age));
    }
}
