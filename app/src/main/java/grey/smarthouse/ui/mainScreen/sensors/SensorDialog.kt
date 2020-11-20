package grey.smarthouse.ui.mainScreen.sensors

import android.annotation.TargetApi
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputLayout
import grey.smarthouse.R

class SensorDialog : DialogFragment() {


    @TargetApi(11)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val v = inflater.inflate(R.layout.fragment_dialog_sensor, null)
        val textName = v.findViewById<EditText>(R.id.editTextName)
        val textInput = v.findViewById<TextInputLayout>(R.id.editTextInput)
        val spinner = v.findViewById<Spinner>(R.id.spinnerSensorLocation)
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, listOf("f", "b"))
        spinner.adapter = adapter
        val dialog = AlertDialog.Builder(requireContext())
                .setView(v)
                .setTitle(R.string.sensor)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(R.string.cancel, null)
                .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (textName.text.toString().isEmpty()) {
                    textInput.error = getString(R.string.locationNameError)
                } else {
                    // Clear the error.
//                    locationsVM.positiveDialogListener(oldName, textName.text.toString())
                    textInput.error = null
                    dialog.dismiss()
                }
            }
        }
        return dialog
    }
}
