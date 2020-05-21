package grey.smarthouse.ui.mainScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import grey.smarthouse.SensorsVM
import grey.smarthouse.data.remote.Requests
import grey.smarthouse.databinding.FragmentSensorListBinding
import grey.smarthouse.utils.RecyclerViewAdapter


/**
 * Created by GREY on 26.05.2018.
 */

class SensorListFragment : Fragment() {

    val sensorsVM by lazy{ ViewModelProviders.of(this).get(SensorsVM::class.java)}
    lateinit var binding: FragmentSensorListBinding
    private var mPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mPage = arguments!!.getInt(ARG_PAGE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentSensorListBinding.inflate(inflater, container, false)
        binding.viewModel = sensorsVM
        binding.sensorRecyclerView.adapter = RecyclerViewAdapter(emptyList())
        binding.sensorRecyclerView.layoutManager = GridLayoutManager(requireActivity(),2)
        binding.lifecycleOwner = requireActivity()
        sensorsVM.updateConfig()
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isAdded) {
            sensorsVM.updateConfig()
        }
    }

    companion object {
        val ARG_PAGE = "ARG PAGE"

        fun newInstance(page: Int): SensorListFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = SensorListFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
