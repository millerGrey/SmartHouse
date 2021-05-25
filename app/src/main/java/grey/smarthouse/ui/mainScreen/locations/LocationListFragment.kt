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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import grey.smarthouse.App
import grey.smarthouse.R
import grey.smarthouse.data.Repository
import grey.smarthouse.data.remote.Requests
import grey.smarthouse.databinding.FragmentDialogLocationBinding
import grey.smarthouse.databinding.FragmentLocationListBinding
import grey.smarthouse.utils.RecyclerViewAdapter
import grey.smarthouse.utils.ViewModelFactory

class LocationListFragment : Fragment() {

    private val locationsVM by lazy {
        ViewModelProvider(
            requireActivity(),
            ViewModelFactory(App.app, Repository(App.app.database, Requests))
        ).get(LocationVM::class.java)
    }
    lateinit var binding: FragmentLocationListBinding
    private val TAG = "LOCATIONS"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLocationListBinding.inflate(inflater, container, false)
        binding.viewModel = locationsVM
        binding.locationRecyclerView.adapter = RecyclerViewAdapter(locationsVM, locationsVM.RVmap)
        binding.locationRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.lifecycleOwner = requireActivity()
        return binding.root
    }

}

class LocationDialog : DialogFragment() {
    private val locationsVM by lazy {
        ViewModelProvider(
            requireActivity(),
            ViewModelFactory(App.app, Repository(App.app.database, Requests))
        ).get(LocationVM::class.java)
    }

    lateinit var binding: FragmentDialogLocationBinding

    @TargetApi(11)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        binding = FragmentDialogLocationBinding.inflate(inflater)
        val locationId = arguments?.getInt("id") ?: -1
        binding.vm = locationsVM
        binding.lifecycleOwner = requireActivity()
        locationsVM.fillDescription(locationId)
        locationsVM.dialogDismiss.observe(this) {
            it?.let {
                dialog?.dismiss()
            }
        }
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setTitle(R.string.location)
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                locationsVM.saveLocation()
            }
        }
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        locationsVM.dismissListener()
        super.onDismiss(dialog)
    }
}
