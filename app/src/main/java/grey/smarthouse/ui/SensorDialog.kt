package grey.smarthouse.ui

import android.annotation.TargetApi
import android.app.Dialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import grey.smarthouse.R

class SensorDialog : DialogFragment(), OnClickListener {


    @TargetApi(11)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val v = inflater.inflate(R.layout.fragment_dialog_sensor, null)
        return AlertDialog.Builder(activity!!)
                .setView(v)
                .setTitle(R.string.sensor)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(R.string.cancel, this)
                .create()

    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        Log.d("dialog", "Dialog 1: onCancel")
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        dismiss()
    }

}
