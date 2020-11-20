package grey.smarthouse.ui.mainScreen.locations

import android.annotation.TargetApi
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import grey.smarthouse.App
import grey.smarthouse.R
import grey.smarthouse.data.Repository
import grey.smarthouse.data.remote.Requests
import grey.smarthouse.databinding.FragmentLocationListBinding
import grey.smarthouse.utils.RecyclerViewAdapter
import grey.smarthouse.utils.ViewModelFactory

class LocationListFragment : Fragment() {

    val locationsVM by lazy { ViewModelProviders.of(requireActivity(), ViewModelFactory(App.app, Repository(App.app.database, Requests))).get(LocationVM::class.java) }
    lateinit var binding: FragmentLocationListBinding
    private val TAG = "LOCATIONS"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentLocationListBinding.inflate(inflater, container, false)
        binding.viewModel = locationsVM
        binding.locationRecyclerView.adapter = RecyclerViewAdapter(locationsVM, locationsVM.RVmap)
        binding.locationRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.lifecycleOwner = requireActivity()
        return binding.root
    }

}

class LocationDialog : DialogFragment() {

    @TargetApi(11)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val locationsVM by lazy { ViewModelProviders.of(requireActivity(), ViewModelFactory(App.app, Repository(App.app.database, Requests))).get(LocationVM::class.java) }
        val inflater = activity!!.layoutInflater
        val v = inflater.inflate(R.layout.fragment_dialog_location, null)
        val textName = v.findViewById<EditText>(R.id.editTextName)
        val textInput = v.findViewById<TextInputLayout>(R.id.editTextInput)
        val oldName = arguments?.getString("name") ?: ""
        textName.setText(oldName)
        val dialog = AlertDialog.Builder(activity!!)
                .setView(v)
                .setTitle(R.string.location)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(R.string.cancel, null)
                .create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (textName.text.toString().isEmpty()) {
                    textInput.error = getString(R.string.locationNameError)
                } else {
                    // Clear the error.
                    locationsVM.positiveDialogListener(oldName, textName.text.toString())
                    textInput.error = null
                    dialog.dismiss()
                }
            }
        }
        return dialog
    }
}
