package com.sample.feature.launches

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.sample.feature.launches.databinding.FragmentUpcomingLaunchesBinding
import com.sample.feature.launches.databinding.ItemLaunchBinding
//import com.sample.thespacedevs.TheSpaceDevApp
import com.sample.thespacedevs.services.launch.Results
import com.sample.thespacedevs.directions.LaunchDetails
import com.sample.thespacedevs.directions.Navigator
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class UpcomingLaunchesFragment : DaggerFragment() {


    @Inject
    lateinit var viewModelFactory: UpcomingLaunchesViewModel.VMFactory
    private var _binding: FragmentUpcomingLaunchesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<UpcomingLaunchesViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpcomingLaunchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etSearchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(newText: Editable?) {
                newText?.trim()
                    ?.let {
                        if (it.isEmpty()) {
                            binding.refreshLayout.isEnabled = true
                            viewModel.fetchUpcomingLaunches(false)
                        } else {
                            binding.refreshLayout.isEnabled = false
                            viewModel.search(it.toString())
                        }
                    }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        binding.toolbar.title = getString(R.string.launch_list_title)
        binding.refreshLayout.isRefreshing = true
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(), RecyclerView.VERTICAL
        )
        var launchListAdapter = LaunchListAdapter(mutableListOf())
        binding.launchListView.apply {
            adapter = launchListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(dividerItemDecoration)
            setHasFixedSize(true)
        }
        viewModel.upcomingLaunchesLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                launchListAdapter.submit(it.getOrDefault(emptyList()))
            } else {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.no_network),
                    Snackbar.LENGTH_LONG
                ).show()
            }
            binding.refreshLayout.isRefreshing = false
        })

        binding.refreshLayout.apply {
            setProgressBackgroundColorSchemeColor(resources.getColor(R.color.white))
            setColorSchemeColors(resources.getColor(R.color.colorAccent))
            setOnRefreshListener {
                viewModel.fetchUpcomingLaunches(true)
            }
        }
        viewModel.fetchUpcomingLaunches(false)
    }

    inner class LaunchListAdapter(private val launchItems: MutableList<Results>) :
        RecyclerView.Adapter<LaunchViewHolder>() {
        fun submit(items: List<Results>) {
            val diffResult = DiffUtil.calculateDiff(LaunchItemDiffs(launchItems, items))
            launchItems.clear()
            launchItems.addAll(items)
            diffResult.dispatchUpdatesTo(this)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder =
            LaunchViewHolder(
                ItemLaunchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

        override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
            holder.bind(launchItems[position])
        }

        override fun getItemCount(): Int = launchItems.size

    }

    inner class LaunchViewHolder(private var itembinding: ItemLaunchBinding) :
        RecyclerView.ViewHolder(itembinding.root) {


        fun bind(launch: Results) {
            itembinding.launchTitle.text = launch.name
            itembinding.launchDescription.text = launch.mission?.name
            itembinding.root.setOnClickListener {
                (requireActivity() as Navigator).navigateTo(LaunchDetails(launch))
            }
        }
    }

    inner class LaunchItemDiffs(
        private val oldItems: List<Results>,
        private val newItems: List<Results>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].id == newItems[newItemPosition].id
        }

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].id == newItems[newItemPosition].id
                    && return oldItems[oldItemPosition].name == newItems[newItemPosition].name
                    && return oldItems[oldItemPosition].mission == newItems[newItemPosition].mission
        }

    }
}