package com.snapperbay4453.jsonfeed.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.snapperbay4453.jsonfeed.R;

public class PreferencesFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
