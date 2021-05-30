package grey.smarthouse.ui.mainScreen.sensors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import grey.smarthouse.App
import grey.smarthouse.databinding.FragmentSensorListBinding
import grey.smarthouse.utils.RecyclerViewAdapter
import javax.inject.Inject


/**
 * Created by GREY on 26.05.2018.
 */

class SensorListFragment : Fragment() {

    @Inject
    lateinit var sensorsVM: SensorsVM

    lateinit var binding: FragmentSensorListBinding
    private val TAG = "SENSORS"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sensorsVM = (requireActivity().application as App).appComponent.getSensorsVM()
        binding = FragmentSensorListBinding.inflate(inflater, container, false)
        with(binding) {
            viewModel = sensorsVM
            sensorsVM.sensorsList.value?.let {
                sensorRecyclerView.adapter = RecyclerViewAdapter(it)
            }
            sensorRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            lifecycleOwner = requireActivity()
            return root
        }
    }
}
