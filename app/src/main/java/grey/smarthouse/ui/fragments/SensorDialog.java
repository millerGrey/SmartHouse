package grey.smarthouse.ui.fragments;

import android.annotation.TargetApi;
//import android.app.AlertDialog;
import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
//import android.support.v7.app.AppCompatDialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.DialogInterface.OnClickListener;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import grey.smarthouse.R;

public class SensorDialog extends DialogFragment implements OnClickListener {


    @NonNull
    @Override
    @TargetApi(11)
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v =inflater.inflate(R.layout.fragment_dialog_sensor,null);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.sensor)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(R.string.cancel, this)
                .create();

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d("dialog", "Dialog 1: onCancel");
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dismiss();
    }

}
