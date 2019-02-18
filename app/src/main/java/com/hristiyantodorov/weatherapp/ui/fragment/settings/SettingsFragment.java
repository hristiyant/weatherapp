package com.hristiyantodorov.weatherapp.ui.fragment.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.hristiyantodorov.weatherapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String FEEDBACK_KEY = "feedback";

    private Preference emailPreference;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        emailPreference = getPreferenceManager().findPreference(FEEDBACK_KEY);
        emailPreference.setOnPreferenceClickListener(preference -> {

            if (preference.getKey().equals(FEEDBACK_KEY)) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setDataAndType(Uri.parse("email"), "message/rfc822");
                String[] sampleRecipients = {"@strings/preference_feedback_sample_email_one", "@strings/preference_feedback_sample_email_two"};
                intent.putExtra(Intent.EXTRA_EMAIL, sampleRecipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, "@strings/preference_feedback_subject_text");
                intent.putExtra(Intent.EXTRA_TEXT, "@strings/preference_feedback_subject_extra_text");
                Intent chooser = Intent.createChooser(intent, "Launch Email");
                startActivity(chooser);
                return true;
            }
            return false;
        });
    }
}