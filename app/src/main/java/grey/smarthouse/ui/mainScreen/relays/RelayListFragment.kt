package grey.smarthouse.ui.mainScreen.relays

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import grey.smarthouse.App
import grey.smarthouse.R
import grey.smarthouse.data.Repository
import grey.smarthouse.data.remote.Requests
import grey.smarthouse.databinding.FragmentRelayListBinding
import grey.smarthouse.utils.RecyclerViewAdapter
import grey.smarthouse.utils.ViewModelFactory

/**
 * Created by GREY on 30.04.2018.
 */

class RelayListFragment : Fragment() {

    val relayVM by lazy {
        ViewModelProvider(
            requireActivity(),
            ViewModelFactory(App.app, Repository(App.app.database, Requests))
        ).get(RelaysVM::class.java)
    }
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
        binding = FragmentRelayListBinding.bind(v)
        binding.viewModel = relayVM
        binding.relayRecyclerView.adapter = RecyclerViewAdapter(relayVM, relayVM.RVmap)
        binding.relayRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.lifecycleOwner = requireActivity()
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
//            if(isAdded)
//                relayVM.updateConfig()
        }
    }
}
