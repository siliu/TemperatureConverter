package com.example.siliu.temperatureconverter;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
    private TextView inputLabel;
    private TextView outputLabel;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.input);
        output = (TextView) findViewById(R.id.output);
        inputLabel = (TextView) findViewById(R.id.inputLabel);
        outputLabel = (TextView) findViewById(R.id.outputLabel);
        radioGroup = (RadioGroup) findViewById(R.id.ratioGroup);
        history = (TextView) findViewById(R.id.history);
        history.setMovementMethod(new ScrollingMovementMethod());

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("HISTORY", history.getText().toString() );

        //Call super at last
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //Call super at first
        super.onRestoreInstanceState(savedInstanceState);

        history.setText(savedInstanceState.getString("HISTORY"));
    }

    // Method for button clicked
    public void buttonClicked(View v) {

        String inputText = input.getText().toString();
        if(TextUtils.isEmpty(inputText)){
            Toast.makeText(this, "No number entered! Please enter a valid number.", Toast.LENGTH_SHORT).show();
            return;
        }

        double in = Double.parseDouble(inputText);
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

        //Set the result to the output field
        output.setText(out);

        //Add query history to the top of history field
        String oneQueryHistory = radioButton.getText().toString() + ": " + input.getText().toString() + " -> " + out;
        String historyText = history.getText().toString();
        history.setText(oneQueryHistory + "\n" + historyText);

    }

    //Method for radio clicked
    public void radioClicked(View v) {
        //Avoid non-RadioButton call
        if (!v.getClass().getSimpleName().equals("RadioButton")) {
            Log.w(TAG, "radioClicked: non-RadioButton " + v.getClass().getSimpleName() + "clicked!");
        }

        String textSelected = ((RadioButton) v).getText().toString();
        Toast.makeText(this, "Function selected: " + textSelected, Toast.LENGTH_SHORT).show();

        inputLabel.setText(textSelected.split("-")[0] + ": ");
        outputLabel.setText(textSelected.split("-")[2] + ": ");

        //Clear the input and output field
        input.setText("");
        output.setText("");

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
