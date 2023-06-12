package android.tvz.hr.listabiljan.fragments;

import CarAdapter
import android.content.IntentFilter
import android.os.Bundle
import android.tvz.hr.listabiljan.OnCarClickListener
import android.tvz.hr.listabiljan.ShareReceiver
import android.tvz.hr.listabiljan.databinding.ListFragmentBinding
import android.tvz.hr.listabiljan.infrastructure.viewmodels.MainViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private var _binding: ListFragmentBinding? = null

    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var clickListener: OnCarClickListener;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parentActivity = requireContext()
        if (parentActivity is OnCarClickListener) {
            clickListener = parentActivity
        }

        lifecycleScope.launch(Dispatchers.IO) {
            lifecycleScope.launch {
                binding.recyclerView.apply {
                    adapter = CarAdapter(viewModel.getAllCars()) { car ->
                        viewModel.selectItem(car)
                    }

                    layoutManager = LinearLayoutManager(this@ListFragment.requireActivity())
                }
            }
        }

        requireContext().registerReceiver(ShareReceiver(), IntentFilter("android.tvz.hr.listabiljan.BROADCAST"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

