package grey.smarthouse.ui.mainScreen.sensors

import android.annotation.TargetApi
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import grey.smarthouse.App
import grey.smarthouse.R
import grey.smarthouse.data.Repository
import grey.smarthouse.data.remote.Requests
import grey.smarthouse.databinding.FragmentDialogSensorBinding
import grey.smarthouse.utils.ViewModelFactory

class SensorDialog : DialogFragment() {

    val sensorVM by lazy { ViewModelProviders.of(requireActivity(), ViewModelFactory(App.app, Repository(App.app.database, Requests))).get(SensorsVM::class.java) }
    @TargetApi(11)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val binding = FragmentDialogSensorBinding.inflate(inflater)
        binding.viewModel = sensorVM
        binding.lifecycleOwner = requireActivity()
        val sensorId = arguments?.getInt("num") ?: -1
        sensorVM.fillDescription(sensorId)
        sensorVM.dialogDismiss.observe(this) {
            it?.let {
                dialog?.dismiss()
            }
        }
        val dialog = AlertDialog.Builder(requireActivity())
                .setView(binding.root)
                .setTitle(R.string.sensor)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(R.string.cancel, null)
                .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                sensorVM.saveSensor()
            }
        }
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        sensorVM.dismissListener()
        super.onDismiss(dialog)
    }
}
