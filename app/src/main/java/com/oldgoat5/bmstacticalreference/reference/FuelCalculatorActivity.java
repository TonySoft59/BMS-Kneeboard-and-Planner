package com.oldgoat5.bmstacticalreference.reference;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.oldgoat5.bmstacticalreference.R;

/*********************************************************************
 * Copyright © Michael Evans - All Rights Reserved.
 *
 * Fuel calculator for the F-16 using ROT.
 *
 * @author Michael Evans
 * @since 5/21/2015
 *********************************************************************/
public class FuelCalculatorActivity extends Activity
{
    private EditText homeAltEditText;
    private EditText tripEditText;
    private RadioButton lowRadioButton;
    private RadioButton medRadioButton;
    private RadioButton hiRadioButton;
    private RadioGroup altitudeRadioGroup;
    private RadioGroup weatherRadioGroup;
    private TextView homeAltTextView;
    private TextView bingoFuelTextView;
    private TextView jokerFuelTextView;
    private TextView totalFuelTextView;
    private TextView tripTextView;
    private View view;

    private int homeAltMiles;
    private int tripMiles;
    private int selectedAltitude;
    private int weatherConditions;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_calculator_activity_layout);

        //todo add variable joker
        homeAltEditText = (EditText) findViewById(R.id.distance_to_alternate_edit_text);
        tripEditText = (EditText) findViewById(R.id.trip_nm_edit_text);

        lowRadioButton = (RadioButton) findViewById(R.id.low_radio_button);
        medRadioButton = (RadioButton) findViewById(R.id.med_radio_button);
        hiRadioButton = (RadioButton) findViewById(R.id.hi_radio_button);

        altitudeRadioGroup = (RadioGroup) findViewById(R.id.alt_radio_group);
        weatherRadioGroup = (RadioGroup) findViewById(R.id.weather_radio_group);

        bingoFuelTextView = (TextView) findViewById(R.id.bingo_result_text_view);
        jokerFuelTextView = (TextView) findViewById(R.id.joker_result_text_view);
        homeAltTextView = (TextView) findViewById(R.id.distance_to_alternate_text_view);
        //totalFuelTextView = (TextView) view.findViewById(R.id.total_fuel_result_text_view);
        //tripTextView = (TextView) view.findViewById(R.id.trip_nm_text_view);

        altitudeRadioGroup.check(R.id.med_radio_button);
        weatherRadioGroup.check(R.id.vmc_radio_button);

        selectedAltitude = 1;
        weatherConditions = 0;

        homeAltEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                Log.d("fuelcalc", "homeAlt.changed");
                Log.d("fuelCAlc", "homealt = " + editable.toString());

                if (editable.toString().length() > 0)
                {
                    try
                    {
                        homeAltMiles = Integer.parseInt(editable.toString());
                        calculateFuel();
                    }
                    catch (NumberFormatException e)
                    {
                        Log.d("fuelCalc", "invalid float homeAlt");
                    }
                }
                else
                    homeAltMiles = 0;
            }
        });

        tripEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                Log.d("fuelCalc", "trip.changed");
                Log.d("fuelCalc", "tripFuel = " + editable.toString());

                if (editable.toString().length() > 0)
                {
                    try
                    {
                        tripMiles = Integer.parseInt(editable.toString());
                        calculateFuel();
                    }
                    catch (NumberFormatException e)
                    {
                        Log.d("fuelcalc", "invalid float in trip");
                    }
                }
                else
                    tripMiles = 0;
            }
        });

        altitudeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                Log.d("fuelCalc", "radioG.changed");
                Log.d("fuelCalc", "i = " + Integer.toString(i));
                switch (i)
                {
                    case R.id.low_radio_button:
                        selectedAltitude = 0; //low
                        break;
                    case R.id.med_radio_button:
                        selectedAltitude = 1; //med
                        break;
                    case R.id.hi_radio_button:
                        selectedAltitude = 2; //high
                        break;
                    default:
                        selectedAltitude = 1;
                        break;
                }
                Log.d("fuelCalc", "radioGChanged selAlt = " + Integer.toString(selectedAltitude));
                calculateFuel();
            }
        });

        weatherRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (i)
                {
                    case R.id.vmc_radio_button:
                        weatherConditions = 400;
                        break;

                    case R.id.imc_radio_button:
                        weatherConditions = 800;
                        break;

                    default:
                        weatherConditions = 0;
                        break;
                }

                calculateFuel();
            }
        });
    }

    private void calculateFuel()
    {
        int total;

        Log.d("fuelCalc", "calcFuel called");
        Log.d("fuelCalc", "tripFuel = " + Integer.toString(tripMiles));
        Log.d("fuelCalc", "homeAltFuel = " + Integer.toString(tripMiles));
        Log.d("fuelCalc", "selectedAltitude= " + Integer.toString(selectedAltitude));

        switch (selectedAltitude)
        {
            case 0:
                total = 1200 + weatherConditions + (tripMiles * 20) + (homeAltMiles * 10); //low
                break;

            case 1:
                total = 1200 + weatherConditions + (tripMiles * 15) + (homeAltMiles * 10); //med
                break;

            case 2:
                total = 1200 + weatherConditions + (tripMiles * 10) + (homeAltMiles * 10); //high
                break;

            default:
                total = 0;
                break;
        }

        bingoFuelTextView.setText(Integer.toString(total) + " lbs");
        jokerFuelTextView.setText(Integer.toString(total + 1000) + " lbs");
        //totalFuelTextView.setText(Integer.toString(5) + " lbs");
    }
}