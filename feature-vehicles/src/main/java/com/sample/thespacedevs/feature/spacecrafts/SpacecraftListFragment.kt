package com.sample.thespacedevs.feature.spacecrafts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.repositories.RepoResult
import com.sample.thespacedevs.services.spacecraft.Results
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SpacecraftListFragment : DaggerFragment(R.layout.fragment_spacecraft_list) {

    @Inject
    lateinit var viewModelFactory: SpacecraftListViewModel.Factory

    private val viewModel by viewModels<SpacecraftListViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spaceCraftListView = view.findViewById<RecyclerView>(R.id.spaceCraftListView)
        val adapter = SpacecraftListAdapter(mutableListOf())
        spaceCraftListView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setAdapter(adapter)
        }
        viewModel.spacecraftsLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is RepoResult.Success -> {
                    adapter.submit(it.result)
                }
                is RepoResult.Failure -> {

                }
            }
        })
        viewModel.fetchSpacecrafts()
    }

    inner class SpacecraftListAdapter(private val currentItems: MutableList<Results>) :
        RecyclerView.Adapter<SpacecraftViewHolder>() {
        fun submit(items: List<Results>) {
            val diffResult = DiffUtil.calculateDiff(SpacecraftItemDiffs(currentItems, items))
            currentItems.clear()
            currentItems.addAll(items)
            diffResult.dispatchUpdatesTo(this)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpacecraftViewHolder =
            SpacecraftViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_vehicle_spacecraft, parent, false)
            )

        override fun onBindViewHolder(holder: SpacecraftViewHolder, position: Int) {
            holder.bind(currentItems[position])
        }

        override fun getItemCount(): Int = currentItems.size

    }

    inner class SpacecraftViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {

        private val launchTitle = view.findViewById<TextView>(R.id.titleSpacecraft)
        private val spacecraftImage = view.findViewById<ImageView>(R.id.imageSpacecraft)

        fun bind(launch: Results) {
            launchTitle.text = launch.name
            Glide.with(this@SpacecraftListFragment)
                .load(launch.spacecraft_config.image_url)
                .into(spacecraftImage)
            view.setOnClickListener {
               
            }
        }
    }

    inner class SpacecraftItemDiffs(
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
        }

    }
}