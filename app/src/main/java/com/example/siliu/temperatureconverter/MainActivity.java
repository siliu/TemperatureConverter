package com.example.siliu.temperatureconverter;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText input;
    private TextView output;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.input);
        output = (TextView) findViewById(R.id.output);
        radioGroup = (RadioGroup) findViewById(R.id.ratioGroup);
        history = (TextView) findViewById(R.id.history);
        history.setMovementMethod(new ScrollingMovementMethod());

    }


    // Method for button clicked
    public void buttonClicked(View v) {

        double in = Double.parseDouble(input.getText().toString());
        String out = "";

        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);

        //Convert fahrenheit to celsius
        if (radioButton == (RadioButton) findViewById(R.id.radioButton1)) {
            Log.d(TAG, "************Click RadioButton1 ************");
            out = convertFahrenheitToCelsius(in);
        }

        //Convert celsius to fahrenheit
        if (radioButton == (RadioButton) findViewById(R.id.radioButton2)) {
            Log.d(TAG, "************Click RadioButton2 ************");
            out = convertCelsiusToFahrenheit(in);
        }

        output.setText(out);

        //Append query history to the history field

        String oneQueryHistory = radioButton.getText() + ": " + input.getText() + " -> " + out;
        history.append(oneQueryHistory + "\n");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //Method for radio clicked
    public void radioClicked(View v) {
        //Avoid null RadioButton call
        if (!v.getClass().getSimpleName().equals("RadioButton")) {
            Log.w(TAG, "radioClicked: non-RadioButton " + v.getClass().getSimpleName() + "clicked!");
        }

        String textSelected = ((RadioButton) v).getText().toString();
        Toast.makeText(this, "Function selected: " + textSelected, Toast.LENGTH_SHORT).show();
    }

    //Convert fahrenheit to celsius
    private String convertFahrenheitToCelsius(double f) {
        double c = (f - 32.0) * (5.0 / 9.0);
        DecimalFormat numberFormat = new DecimalFormat("#.0");
        return numberFormat.format(c);
    }

    //Convert celsius to fahrenheit
    private String convertCelsiusToFahrenheit(double c) {
        double f = (c * (9.0 / 5.0)) + 32.0;
        DecimalFormat numberFormat = new DecimalFormat("#.0");
        return numberFormat.format(f);
    }
}
