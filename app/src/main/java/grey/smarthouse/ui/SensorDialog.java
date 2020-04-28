package grey.smarthouse.ui;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import grey.smarthouse.R;

//import android.app.AlertDialog;
//import android.support.v7.app.AppCompatDialogFragment;

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
