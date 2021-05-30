package grey.smarthouse.ui.mainScreen.relays

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import grey.smarthouse.App
import grey.smarthouse.R
import grey.smarthouse.databinding.FragmentRelayListBinding
import grey.smarthouse.utils.RecyclerViewAdapter
import javax.inject.Inject

/**
 * Created by GREY on 30.04.2018.
 */

class RelayListFragment : Fragment() {

    @Inject
    lateinit var relaysVM: RelaysVM

    lateinit var binding: FragmentRelayListBinding
    private val TAG = "RELAYS"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("tag", "listFragmentCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_relay_list, container, false)
        relaysVM = (requireActivity().application as App).appComponent.getRelaysVM()
        binding = FragmentRelayListBinding.bind(v)
        with(binding) {
            viewModel = relaysVM
            relaysVM.relayList.value?.let {
                relayRecyclerView.adapter = RecyclerViewAdapter(it)
            }
            relayRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            lifecycleOwner = requireActivity()
            return root
        }
    }
}
