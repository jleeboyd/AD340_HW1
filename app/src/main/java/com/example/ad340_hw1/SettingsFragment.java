package com.example.ad340_hw1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class SettingsFragment extends Fragment {

    public static final String TAG = SettingsFragment.class.getSimpleName();

    // Simple version
    private EditText editTextReminder;
    private EditText editTextMaxDistance;
    private EditText editTextGender;
    private EditText editTextPrivacy;
    private EditText editTextMaxAge;
    private EditText editTextMinAge;
    private Button buttonSaveSettings;

    private String email;

    private SettingsViewModel settingsViewModel;

    // Complex version with Seekbar, Switch

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_settings, container,  false);

        // Set viewModel
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        Bundle bundle = getArguments(); // Contains signup info ie. Email

        editTextReminder    = view.findViewById(R.id.editTextReminderTime);
        editTextMaxDistance = view.findViewById(R.id.editTextMaxDistance);
        editTextGender      = view.findViewById(R.id.editTextGender);
        editTextPrivacy     = view.findViewById(R.id.editTextPrivacy);
        editTextMaxAge      = view.findViewById(R.id.editTextMaxAge);
        editTextMinAge      = view.findViewById(R.id.editTextMinAge);

        buttonSaveSettings  = view.findViewById(R.id.buttonSaveSettings);

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //As the app is now, it is storing only one email value and subsequently
        // only one settings.class. The email is the testEmail.
        // Goal: We want ANY new user's email signing up to create a NEW db settings
        // to test db data persistence.
        // Therefore we need to allow inserts of data to viewModel if new email, if not
        // then perform an update to viewmodel data!

        // If new user signs up set views based on db data
        if (bundle != null) {

            // Get email from sign up bundle
            email = bundle.getString(Constants.KEY_EMAIL);
            Log.i(TAG, "if");
        }

        // If bundle is null set to default user for testing. TabActivity ie skip sign up
        else {

            email =  getResources().getString(R.string.testEmail);
            Log.i(TAG, "else "+ email);
        }

        // Create the observer to update the UI
        // Anytime new list of Settings, callback executes
        final Observer<List<Settings>> getSettingsObserver = newSettings -> {

            // Null checks
            if(newSettings == null || newSettings.size() <= 0) {
                return;
            }

            // 0 because 1 email is passed in, ie one settings
            Settings settings = newSettings.get(0);

            if (settings == null) {
                return;
            }

            // Code we want to execute when settings change
            editTextReminder.setText(settings.getReminderTime());
            editTextMaxDistance.setText(String.valueOf(settings.getMaxDistance()));
            editTextGender.setText(settings.getGender());
            editTextPrivacy.setText(settings.getPrivacy());
            editTextMaxAge.setText(String.valueOf(settings.getAgeMax()));
            editTextMinAge.setText(String.valueOf(settings.getAgeMin()));
        };

        // Onclick save button update changes to settings based on editText values and send data to db
        buttonSaveSettings.setOnClickListener(v -> {

            Settings settings = new Settings();

            settings.setEmail(email);
            settings.setReminderTime(editTextReminder.getText().toString());

            // Max distance must be greater than 0
            int maxDistance = Integer.parseInt(editTextMaxDistance.getText().toString());

            if (maxDistance > 0 && maxDistance <= 50) {
                settings.setMaxDistance(Integer.parseInt(editTextMaxDistance.getText().toString()));
            }

            else {
                Toast toast = Toast.makeText(view.getContext(), R.string.invalid_distance, Toast.LENGTH_SHORT);
                toast.show();
            }

            settings.setGender(editTextGender.getText().toString());

            // Allow only Private or Public
            String privacy = editTextPrivacy.getText().toString();

            if(privacy.equals("Private") || privacy.equals("Public")) {
                settings.setPrivacy(editTextPrivacy.getText().toString());
            }
            else {
                Toast toast = Toast.makeText(view.getContext(), R.string.invalid_privacy, Toast.LENGTH_SHORT);
                toast.show();
            }

            // Do not allow Max age to be less than minimum age
            int maxAge = Integer.parseInt(editTextMaxAge.getText().toString());
            int minAge = Integer.parseInt(editTextMinAge.getText().toString());

            // Only allow user to set Min age to 18 and above
            if(minAge >= 18 && minAge <= maxAge) {
                settings.setAgeMin(Integer.parseInt(editTextMinAge.getText().toString()));
                settings.setAgeMax(Integer.parseInt(editTextMaxAge.getText().toString()));
            }

            else {
                if(minAge < 18) {
                    Toast toast = Toast.makeText(view.getContext(), R.string.invalid_dob, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(view.getContext(), R.string.invalid_age_range, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            settingsViewModel.updateSettings(view.getContext(), settings);

            editTextReminder.setText(settings.getReminderTime());
            editTextMaxDistance.setText(String.valueOf(settings.getMaxDistance()));
            editTextGender.setText(settings.getGender());
            editTextPrivacy.setText(settings.getPrivacy());
            editTextMaxAge.setText(String.valueOf(settings.getAgeMax()));
            editTextMinAge.setText(String.valueOf(settings.getAgeMin()));

        });

        String[] emails = { email };
        settingsViewModel.loadSettingsById(view.getContext(), emails).observe((LifecycleOwner) view.getContext(), getSettingsObserver);
        return view;
    }
}
