package grey.smarthouse.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import grey.smarthouse.model.Model;
import grey.smarthouse.R;

/**
 * Created by GREY on 17.05.2018.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    Model mModel;

    ProgressBar PBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        mModel = Model.getInstance(this);
        PBar = (ProgressBar) findViewById(R.id.progressRelaySettings);
        }

    protected void addFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
        PBar.setVisibility(View.INVISIBLE);
    }

}
