package grey.smarthouse.ui.mainScreen.locations

import android.annotation.TargetApi
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import grey.smarthouse.App
import grey.smarthouse.R
import grey.smarthouse.databinding.FragmentDialogLocationBinding
import grey.smarthouse.databinding.FragmentLocationListBinding
import grey.smarthouse.utils.RecyclerViewAdapter
import javax.inject.Inject

class LocationListFragment : Fragment() {
    @Inject
    lateinit var locationVM: LocationVM
    lateinit var binding: FragmentLocationListBinding
    private val TAG = "LOCATIONS"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        locationVM = (requireActivity().application as App).appComponent.getLocationVM()
        binding = FragmentLocationListBinding.inflate(inflater, container, false)
        with(binding) {
            viewModel = locationVM
            locationVM.locationsList.value?.let {
                locationRecyclerView.adapter = RecyclerViewAdapter(it)
            }
            locationRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            lifecycleOwner = requireActivity()
            return root
        }
    }
}

class LocationDialog : DialogFragment() {
    @Inject
    lateinit var locationVM: LocationVM

    lateinit var binding: FragmentDialogLocationBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationVM.dialogDismiss.observe(viewLifecycleOwner) {
            it?.let {
                dialog?.dismiss()
            }
        }
    }

    @TargetApi(11)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        binding = FragmentDialogLocationBinding.inflate(inflater)
        val locationId = arguments?.getInt("id") ?: -1
        locationVM = (requireActivity().application as App).appComponent.getLocationVM()
        binding.vm = locationVM
        binding.lifecycleOwner = requireActivity()
        locationVM.fillDescription(locationId)

        val dialog = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setTitle(R.string.location)
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                locationVM.positiveAction()
            }
        }
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        locationVM.dismissListener()
        super.onDismiss(dialog)
    }
}
