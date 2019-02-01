package com.hristiyantodorov.weatherapp.ui.fragment.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.hristiyantodorov.weatherapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private Preference emailPreference;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        emailPreference = getPreferenceManager().findPreference("feedback");
        emailPreference.setOnPreferenceClickListener(preference -> {

            if (preference.getKey().equals("feedback")) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("email"));
                String[] sampleRecipients = {"hrisko_drisko@vba.com", "drisko_hrisko@blabla.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, sampleRecipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, "About WeatherApp");
                intent.putExtra(Intent.EXTRA_TEXT, "-->Enter text here");
                intent.setType("message/rfc822");
                Intent chooser = Intent.createChooser(intent, "Launch Email");
                startActivity(chooser);
                return true;
            }
            return false;
        });
    }
}