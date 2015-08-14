package com.oldgoat5.bmstacticalreference.missionplanner.level;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oldgoat5.bmstacticalreference.R;

import java.util.HashMap;

/*********************************************************************
 * Copyright © Michael Evans - All Rights Reserved.
 *
 * @author Michael Evans
 * @version 7/17/2015
 *********************************************************************/
public class LevelBombMissionPlannerParametersFragment extends Fragment
{
    private final String[] RELEASE_KTAS_ITEMS = {"---", "450", "500", "550", "600"};

    private ArrayAdapter<String> releaseKtasArrayAdapter;
    private Button calculateButton;
    private EditText approachCourseEditText;
    private EditText releaseAltitudeEditText;
    private EditText targetElevationEditText;
    private HashMap<String, Boolean> inputValidity;
    private TextView minSafeReleaseAltitudeResultTextView;
    private TextView levelDeterminedReleseAltitudeResultTextView;
    private Spinner releaseKtasSpinner;
    private View view;

    private int selectedCloudBase;
    private int selectedConLayer;
    private int selectedTemperature;
    private int selectedWindDirection;
    private int selectedWindSpeed;
    private String selectedSituation;
    private String selectedReleaseSpeed;
    private String selectedWeapon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(
                R.layout.level_bomb_mission_planner_parameters_fragment_layout, container, false);

        Log.d("levelParametersFragment", "onCreateView() called");

        getArgsFromBundle();
        instantiateResources();

        inputValidity = new HashMap<>();

        releaseKtasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                switch (i)
                {
                    case 0:
                        selectedReleaseSpeed = RELEASE_KTAS_ITEMS[i];
                        inputValidity.put("selectedReleaseSpeed", false);
                        break;
                    case 1:
                        selectedReleaseSpeed = RELEASE_KTAS_ITEMS[i];
                        inputValidity.put("selectedReleaseSpeed", true);
                        break;
                    case 2:
                        selectedReleaseSpeed = RELEASE_KTAS_ITEMS[i];
                        inputValidity.put("selectedReleaseSpeed", true);
                        break;
                    case 3:
                        selectedReleaseSpeed = RELEASE_KTAS_ITEMS[i];
                        inputValidity.put("selectedReleaseSpeed", true);
                        break;
                    case 4:
                        selectedReleaseSpeed = RELEASE_KTAS_ITEMS[i];
                        inputValidity.put("selectedReleaseSpeed", true);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                inputValidity.put("selectedReleaseSpeed", false);
            }
        });

        releaseKtasSpinner.setAdapter(releaseKtasArrayAdapter);

        approachCourseEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b /*&& !approachCourseEditText.getText().toString().contains("°")*/)
                {
                    try
                    {
                        String oldText = approachCourseEditText.getText().toString();

                        if (oldText.length() == 3 && !approachCourseEditText.getText().toString().contains("°"))
                        {
                            if (Integer.parseInt(oldText) > 360)
                            {
                                Toast.makeText(
                                        getActivity(), "Invalid Heading, must be < 360", Toast.LENGTH_LONG).show();
                                inputValidity.put("selectedApproachCourse", false);
                            }
                            else
                            {
                                inputValidity.put("selectedApproachCourse", true);
                            }
                            approachCourseEditText.append("°");
                        }
                        else if (oldText.length() == 3 && approachCourseEditText.getText().toString().contains("°"))
                        {
                            if (Integer.parseInt(oldText.substring(0, 3)) > 360)
                            {
                                Toast.makeText(
                                        getActivity(), "Invalid Heading, must be < 360", Toast.LENGTH_LONG).show();
                                inputValidity.put("selectedApproachCourse", false);
                            }
                            else
                            {
                                inputValidity.put("selectedApproachCourse", true);
                            }
                        }
                        else
                        {
                            Toast.makeText(
                                    getActivity(), "Heading must be 3 digits", Toast.LENGTH_LONG).show();
                            inputValidity.put("selectedApproachCourse", false);
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        Toast.makeText(getActivity(), "Invalid Approach Course Heading", Toast.LENGTH_LONG).show();
                        inputValidity.put("selectedApproachCourse", false);
                    }
                }
            }
        });

        releaseAltitudeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    String oldText = releaseAltitudeEditText.getText().toString();

                    try
                    {
                        if (oldText.contains("ft. AGL"))
                        {
                            oldText = oldText.replace("ft. AGL", "");
                            Integer.parseInt(oldText);
                            inputValidity.put("selectedReleaseAltitude", true);
                        }
                        else if (oldText.contains("ft."))
                        {
                            oldText = oldText.replace("ft.", "");
                            Integer.parseInt(oldText);
                            releaseAltitudeEditText.append(" AGL");
                            inputValidity.put("selectedReleaseAltitude", true);
                        }
                        else
                        {
                            Integer.parseInt(oldText);
                            releaseAltitudeEditText.append("ft. AGL");
                            inputValidity.put("selectedReleaseAltitude", true);
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        Toast.makeText(getActivity(), "Invalid Release Altitude", Toast.LENGTH_LONG).show();
                        inputValidity.put("selectedReleaseAltitude", false);
                    }
                }
            }
        });

        targetElevationEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    String oldText = targetElevationEditText.getText().toString();

                    try
                    {
                        if (oldText.contains("ft. MSL"))
                        {
                            oldText = oldText.replace("ft. MSL", "");
                            Integer.parseInt(oldText);
                            inputValidity.put("selectedTargetElevation", true);
                        }
                        else if (oldText.contains("ft."))
                        {
                            oldText = oldText.replace("ft.", "");
                            Integer.parseInt(oldText);
                            targetElevationEditText.append(" MSL");
                            inputValidity.put("selectedTargetElevation", true);
                        }
                        else
                        {
                            Integer.parseInt(oldText);
                            targetElevationEditText.append("ft. MSL");
                            inputValidity.put("selectedTargetElevation", true);
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        Toast.makeText(getActivity(), "Invalid Target Elevation", Toast.LENGTH_LONG).show();
                        inputValidity.put("selectedTargetElevation", false);
                    }
                }
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View pview)
            {
                view.requestFocus();

                if (inputIsValid())
                {
                    try
                    {
                        Log.d("levelparametersfrag",
                                targetElevationEditText.getText().toString().replace("ft. MSL", ""));
                        Log.d("levelparametersfrag",
                                releaseAltitudeEditText.getText().toString().replace("ft. AGL", ""));

                        int fromTargetElev = Integer.parseInt(
                                targetElevationEditText.getText().toString().replace("ft. MSL", ""));
                        int fromReleaseAlt = Integer.parseInt(
                                releaseAltitudeEditText.getText().toString().replace("ft. AGL", ""));

                        Log.d("levelparametersfrag",
                                "targetelev: " + fromTargetElev + "releasealt: " + fromReleaseAlt);

                        levelDeterminedReleseAltitudeResultTextView.setText(
                                (fromTargetElev + fromReleaseAlt) + "ft. MSL");
                        levelDeterminedReleseAltitudeResultTextView.setTextColor(Color.BLACK);
                    }
                    catch (NumberFormatException e)
                    {
                        Toast.makeText(getActivity(), "Bad form args", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }

    /*****************************************************************
     * Sets arguments from level release weather conditions fragment.
     *****************************************************************/
    private void getArgsFromBundle()
    {
        selectedCloudBase = getArguments().getInt("selectedCloudBase");
        selectedConLayer = getArguments().getInt("selectedConLayer");
        selectedTemperature = getArguments().getInt("selectedTemperature");
        selectedWindDirection = getArguments().getInt("selectedWindDirection");
        selectedWindSpeed = getArguments().getInt("selectedWindSpeed");
        selectedSituation = getArguments().getString("selectedSituation");
        selectedWeapon = getArguments().getString("selectedWeapon");
        Log.d("levelparameters", "selectedWeapon = " + selectedWeapon);
    }

    private void instantiateResources()
    {
        calculateButton = (Button) view.findViewById(R.id.level_release_parameters_calculate_button);
        approachCourseEditText = (EditText) view.findViewById(R.id.level_approach_course_edit_text);
        approachCourseEditText.setText("000°");
        minSafeReleaseAltitudeResultTextView = (TextView) view.findViewById(
                R.id.level_min_safe_release_altitude_result_text_view);

        if (selectedWeapon.contains("CBU"))
            minSafeReleaseAltitudeResultTextView.setText("(None for CBU)"); //be specific for user confidence

        if (selectedWeapon.contains("Rockeye"))
            minSafeReleaseAltitudeResultTextView.setText("(None for MK-20D)");

        if (selectedWeapon.contains("GBU"))
            minSafeReleaseAltitudeResultTextView.setText("(None for GBU)");

        releaseAltitudeEditText = (EditText) view.findViewById(R.id.level_release_altitude_agl_edit_text);
        releaseAltitudeEditText.setText("5000ft. AGL");
        targetElevationEditText = (EditText) view.findViewById(R.id.level_target_elevation_edit_text);
        targetElevationEditText.setText("100ft. MSL");
        releaseKtasSpinner = (Spinner) view.findViewById(R.id.level_release_ktas_spinner);
        releaseKtasArrayAdapter = new ArrayAdapter<String>(
                this.getActivity(), android.R.layout.simple_spinner_item, RELEASE_KTAS_ITEMS);
        levelDeterminedReleseAltitudeResultTextView = (TextView) view.findViewById(
                R.id.level_determined_release_altitude_result_text_view);
    }

    private boolean inputIsValid()
    {
        boolean finalValidity = false;



        if (!inputValidity.containsValue(false))
        {
            finalValidity = true;
        }
        else
        {
            Toast.makeText(getActivity(), "Form contains invalid input.", Toast.LENGTH_LONG).show();
        }

        return finalValidity;
    }
}
