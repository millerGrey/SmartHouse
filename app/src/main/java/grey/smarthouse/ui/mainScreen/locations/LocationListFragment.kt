package grey.smarthouse.ui.mainScreen.locations

import android.annotation.TargetApi
import android.app.Dialog
import android.content.DialogInterface
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
import grey.smarthouse.App
import grey.smarthouse.R
import grey.smarthouse.data.Repository
import grey.smarthouse.data.remote.Requests
import grey.smarthouse.databinding.FragmentLocationListBinding
import grey.smarthouse.utils.RecyclerViewAdapter
import grey.smarthouse.utils.ViewModelFactory

class LocationListFragment: Fragment() {

    val locationsVM by lazy{ ViewModelProviders.of(requireActivity(), ViewModelFactory(App.app, Repository(App.app.database, Requests))).get(LocationVM::class.java)}
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

class LocationDialog : DialogFragment(), DialogInterface.OnClickListener {


    @TargetApi(11)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val locationsVM by lazy { ViewModelProviders.of(requireActivity(), ViewModelFactory(App.app, Repository(App.app.database, Requests))).get(LocationVM::class.java) }
        val inflater = activity!!.layoutInflater
        val v = inflater.inflate(R.layout.fragment_dialog_location, null)
        val textName = v.findViewById<EditText>(R.id.editTextLocationName)
        val oldName = arguments?.getString("name") ?: ""
        textName.setText(oldName)
        return AlertDialog.Builder(activity!!)
                .setView(v)
                .setTitle(R.string.location)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    locationsVM.positiveDialogListener(oldName, textName.text.toString())
                }
                .setNegativeButton(R.string.cancel, this)
                .create()

    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        dismiss()
    }
}
