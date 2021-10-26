package grey.smarthouse.ui.mainScreen.sensors

import android.annotation.TargetApi
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.observe
import grey.smarthouse.App
import grey.smarthouse.R
import grey.smarthouse.databinding.FragmentDialogSensorBinding
import javax.inject.Inject

class SensorDialog : DialogFragment() {

    @Inject
    lateinit var sensorsVM: SensorsVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorsVM.dialogDismiss.observe(viewLifecycleOwner) {
            it?.let {
                dialog?.dismiss()
            }
        }
    }

    @TargetApi(11)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val binding = FragmentDialogSensorBinding.inflate(inflater)
        sensorsVM = (requireActivity().application as App).appComponent.getSensorsVM()
        binding.viewModel = sensorsVM
        binding.lifecycleOwner = requireActivity()
        val sensorId = arguments?.getInt("num") ?: -1
        sensorsVM.fillDescription(sensorId)
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setTitle(R.string.sensor)
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                sensorsVM.positiveAction()
            }
        }
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        sensorsVM.dismissListener()
        super.onDismiss(dialog)
    }
}
