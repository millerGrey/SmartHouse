package grey.smarthouse.ui.relaySettingsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import grey.smarthouse.R
import grey.smarthouse.RelaySettingsVM
import grey.smarthouse.databinding.FragmentRelaySetBinding
import java.util.*

/**
 * Created by GREY on 29.04.2018.
 */

class RelaySettingsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(RelaySettingsVM::class.java)
    }
    private lateinit var binding: FragmentRelaySetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val relayId = arguments?.getSerializable(ARG_RELAY_ID) as UUID
        viewModel.start(relayId)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_relay_set, container, false)
        binding = FragmentRelaySetBinding.inflate(inflater,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()
        return binding.root
    }

    companion object {

        private val ARG_RELAY_ID = "relay_id"

        fun newInstance(rId: UUID): RelaySettingsFragment {
            val args = Bundle()
            args.putSerializable(ARG_RELAY_ID, rId)
            val fragment = RelaySettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
