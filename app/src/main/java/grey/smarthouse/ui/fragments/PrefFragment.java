package grey.smarthouse.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import grey.smarthouse.R;

public class PrefFragment extends PreferenceFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
