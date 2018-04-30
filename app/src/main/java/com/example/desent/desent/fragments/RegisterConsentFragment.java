package com.example.desent.desent.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.desent.desent.R;
import com.example.desent.desent.models.DatabaseHelper;

public class RegisterConsentFragment extends Fragment {


    private SharedPreferences sharedPreferences;
    private DatabaseHelper myDb;
    private CheckBox cbConsent;
    private Boolean consentGiven;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myDb = new DatabaseHelper(getContext());

        getActivity().getTheme().applyStyle(R.style.AppTheme_NoActionBar_AccentColorGreen, true);

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_register_consent, container, false);

        cbConsent = (CheckBox) rootView.findViewById(R.id.cbConsent);
        cbConsent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onCheckboxClicked(compoundButton, b);
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        restorePreferences();
        return rootView;
    }

    private void onCheckboxClicked(CompoundButton compoundButton, boolean b) {
        if (compoundButton.isChecked()) {
            consentGiven = true;
        } else {
            consentGiven = false;
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("pref_key_personal_consent", consentGiven);
        //just to create the key in shared preferences
        editor.putInt("pref_key_personal_score", 0);
        editor.commit();
    }

    private void restorePreferences(){
        consentGiven = sharedPreferences.getBoolean("pref_key_personal_consent", false);
        cbConsent.setChecked(consentGiven);
    }
}
