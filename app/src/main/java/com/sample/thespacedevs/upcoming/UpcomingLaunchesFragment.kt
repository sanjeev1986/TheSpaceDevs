package com.sample.thespacedevs.upcoming

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sample.thespacedevs.R
import com.sample.thespacedevs.TheSpaceDevApp
import com.sample.thespacedevs.api.launch.Results
import kotlinx.android.synthetic.main.fragment_upcoming_launches.*
import javax.inject.Inject

class UpcomingLaunchesFragment : Fragment(R.layout.fragment_upcoming_launches){


    @Inject
    lateinit var viewModelFactory: UpcomingLaunchesViewModel.VMFactory

    private val viewModel by viewModels<UpcomingLaunchesViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        TheSpaceDevApp.getInstance(this).applicationComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etSearchText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(newText: Editable?) {
                newText?.trim()
                    ?.let {
                        if (it.isEmpty()) {
                            refreshLayout.isEnabled = true
                            viewModel.fetchUpcomingLaunches(false)
                        } else {
                            refreshLayout.isEnabled = false
                            viewModel.search(it.toString())
                        }
                    }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        toolbar.title = getString(R.string.launch_list_title)
        refreshLayout.isRefreshing = true
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(), RecyclerView.VERTICAL
        )
        var launchListAdapter = LaunchListAdapter(mutableListOf())
        launchListView.apply {
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
            refreshLayout.isRefreshing = false
        })

        refreshLayout.apply {
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
                LayoutInflater.from(parent.context).inflate(R.layout.item_launch, parent, false)
            )

        override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
            holder.bind(launchItems[position])
        }

        override fun getItemCount(): Int = launchItems.size

    }

    inner class LaunchViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {

        private val launchTitle = view.findViewById<TextView>(R.id.launchTitle)
        private val launchDescription = view.findViewById<TextView>(R.id.launchDescription)

        fun bind(launch: Results) {
            launchTitle.text = launch.name
            launchDescription.text = launch.mission?.name
            view.setOnClickListener {
                findNavController(requireView()).navigate(
                    UpcomingLaunchesFragmentDirections.actionUpcomingLaunchesFragmentToLaunchDetailsFragment(launch)
                )
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