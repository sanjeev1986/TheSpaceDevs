package com.sample.thespacedevs.feature.spacecrafts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.repositories.RepoResult
import com.sample.thespacedevs.feature.spacecrafts.databinding.FragmentSpacecraftListBinding
import com.sample.thespacedevs.feature.spacecrafts.databinding.ItemVehicleSpacecraftBinding
import com.sample.thespacedevs.services.spacecraft.SpaceCraft
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SpacecraftListFragment : DaggerFragment() {

    /*@Inject
    lateinit var viewModelFactory: SpacecraftListViewModel.Factory

    private var _binding: FragmentSpacecraftListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SpacecraftListViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSpacecraftListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SpacecraftListAdapter(mutableListOf())
        binding.spaceCraftListView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setAdapter(adapter)
        }
        viewModel.spacecraftsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is RepoResult.Success -> {
                    adapter.submit(it.result)
                }
                is RepoResult.Failure -> {

                }
            }
        }
        viewModel.fetchSpacecrafts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class SpacecraftListAdapter(private val currentItems: MutableList<SpaceCraft>) :
        RecyclerView.Adapter<SpacecraftViewHolder>() {
        fun submit(items: List<SpaceCraft>) {
            val diffResult = DiffUtil.calculateDiff(SpacecraftItemDiffs(currentItems, items))
            currentItems.clear()
            currentItems.addAll(items)
            diffResult.dispatchUpdatesTo(this)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpacecraftViewHolder =
            SpacecraftViewHolder(
                ItemVehicleSpacecraftBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: SpacecraftViewHolder, position: Int) {
            holder.bind(currentItems[position])
        }

        override fun getItemCount(): Int = currentItems.size

    }

    inner class SpacecraftViewHolder(private val viewBinding: ItemVehicleSpacecraftBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        private val launchTitle = viewBinding.titleSpacecraft
        private val spacecraftImage = viewBinding.imageSpacecraft

        fun bind(launch: SpaceCraft) {
            launchTitle.text = launch.name
            Glide.with(this@SpacecraftListFragment)
                .load(launch.spacecraft_config.image_url)
                .into(spacecraftImage)
            viewBinding.root.setOnClickListener {

            }
        }
    }

    inner class SpacecraftItemDiffs(
        private val oldItems: List<SpaceCraft>,
        private val newItems: List<SpaceCraft>
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

    }*/
}

