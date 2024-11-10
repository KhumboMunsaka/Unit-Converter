package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private Button convert;
    private Spinner spinner;
    private Spinner toUnit;
    private EditText userInputValue;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //initialize the button
         convert = findViewById(R.id.button);
        //to get the value entered by the user
        userInputValue = findViewById(R.id.userInputValue);
        spinner = findViewById(R.id.spinner);
        toUnit = findViewById(R.id.toUnit);
        result = findViewById(R.id.result);
        //initialize the units spinner. From official Android documentation
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner toUnit = (Spinner) findViewById(R.id.toUnit);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.units_array,
                android.R.layout.simple_spinner_item
        );
        ArrayAdapter<CharSequence> toAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.units_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner.
        spinner.setAdapter(adapter);
        toUnit.setAdapter(toAdapter);



        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input value
                String inputValueStr = userInputValue.getText().toString();
                if (inputValueStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a value to convert", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Parse the input to a double
                double inputValue = Double.parseDouble(inputValueStr);

                // Get selected units
                String fromUnit = spinner.getSelectedItem().toString();
                String toUnitSelected = toUnit.getSelectedItem().toString();

                // Call the conversion method
                convertUnits(inputValue, fromUnit, toUnitSelected);
            }
        });
    }
    public void convertUnits(double value, String fromUnit, String toUnit) {
        // Initialize the conversion factors
        Map<String, Map<String, Double>> conversionFactors = new HashMap<>();

        // Define conversion factors for each unit
        Map<String, Double> mConversions = new HashMap<>();
        mConversions.put("milimetre", 1000.0);
        mConversions.put("feet", 3.28084);
        mConversions.put("miles", 0.000621371);
        conversionFactors.put("metre", mConversions);

        Map<String, Double> mmConversions = new HashMap<>();
        mmConversions.put("metre", 0.001);
        mmConversions.put("feet", 0.00328084);
        mmConversions.put("miles", 6.2137e-7);
        conversionFactors.put("milimetre", mmConversions);

        Map<String, Double> miConversions = new HashMap<>();
        miConversions.put("metre", 1609.34);
        miConversions.put("milimetre", 1609344.0);
        miConversions.put("feet", 5280.0);
        conversionFactors.put("miles", miConversions);

        Map<String, Double> ftConversions = new HashMap<>();
        ftConversions.put("metre", 0.3048);
        ftConversions.put("milimetre", 304.8);
        ftConversions.put("miles", 0.000189394);
        conversionFactors.put("feet", ftConversions);
        // Check if the units are the same
        if (fromUnit.equals(toUnit)) {
            Toast.makeText(this, "Please select different units", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if fromUnit exists in conversionFactors map
        Map<String, Double> fromUnitConversions = conversionFactors.get(fromUnit);
        if (fromUnitConversions == null) {
            Toast.makeText(this, "Invalid from unit selected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if toUnit exists in the inner map
        Double conversionFactor = fromUnitConversions.get(toUnit);
        if (conversionFactor == null) {
            Toast.makeText(this, "Invalid to unit selected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perform the conversion
        double convertedValue = value * conversionFactor;
        // Display the result
        result.setText(String.format("%.2f %s is equal to %s %s", value, fromUnit, convertedValue, toUnit));

    }

}


