package grey.smarthouse.ui.mainScreen.sensors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import grey.smarthouse.App
import grey.smarthouse.data.Repository
import grey.smarthouse.data.remote.Requests
import grey.smarthouse.databinding.FragmentSensorListBinding
import grey.smarthouse.utils.RecyclerViewAdapter
import grey.smarthouse.utils.ViewModelFactory


/**
 * Created by GREY on 26.05.2018.
 */

class SensorListFragment : Fragment() {

    val sensorsVM by lazy {
        ViewModelProvider(
            requireActivity(),
            ViewModelFactory(App.app, Repository(App.app.database, Requests))
        ).get(SensorsVM::class.java)
    }
    lateinit var binding: FragmentSensorListBinding
    private val TAG = "SENSORS"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        sensorsVM.editSensorEvent.observe(this) {
//            val args = Bundle()
//            args.putString("description", it)
//            val sensorDialog = SensorDialog()
//            sensorDialog.arguments = args
//            sensorDialog.show(requireActivity().supportFragmentManager, "sensorDialog")
//
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSensorListBinding.inflate(inflater, container, false)
        binding.viewModel = sensorsVM
        binding.sensorRecyclerView.adapter = RecyclerViewAdapter(sensorsVM, sensorsVM.RVmap)
        binding.sensorRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.lifecycleOwner = requireActivity()
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isAdded) {
//            if(isAdded)
//                sensorsVM.updateConfig()
        }
    }

    companion object {
        val ARG_PAGE = "ARG PAGE"

    }
}
