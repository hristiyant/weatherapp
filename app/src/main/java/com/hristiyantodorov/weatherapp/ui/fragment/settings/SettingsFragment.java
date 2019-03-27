package com.hristiyantodorov.weatherapp.ui.fragment.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.hristiyantodorov.weatherapp.R;
import com.hristiyantodorov.weatherapp.util.SharedPrefUtil;

import java.util.Objects;

import static com.hristiyantodorov.weatherapp.util.Constants.FEEDBACK_KEY;
import static com.hristiyantodorov.weatherapp.util.Constants.LANGUAGE_KEY;

public class SettingsFragment extends PreferenceFragmentCompat {

    private ListPreference list;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        list = (ListPreference) getPreferenceManager().findPreference(this.getResources().getString(R.string.shared_pref_api_content_lang_key));
        list.setEntries(R.array.api_content_language_names);
        list.setEntryValues(R.array.api_content_language_values);
        list.setOnPreferenceChangeListener((preference, o) -> {
            SharedPrefUtil.write(LANGUAGE_KEY, o.toString());
            list.setValue(o.toString());
            Intent intent = Objects.requireNonNull(getActivity()).getIntent();
            getActivity().finish();
            startActivity(intent);
            return false;
        });

        Preference emailPreference = getPreferenceManager().findPreference(FEEDBACK_KEY);
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