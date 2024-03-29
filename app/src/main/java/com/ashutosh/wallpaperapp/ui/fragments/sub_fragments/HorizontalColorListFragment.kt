package com.ashutosh.wallpaperapp.ui.fragments.sub_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashutosh.wallpaperapp.adapter.ColorAdapter
import com.ashutosh.wallpaperapp.databinding.FragmentHorizontalColorListBinding
import com.ashutosh.wallpaperapp.network.ListStatus
import com.ashutosh.wallpaperapp.ui.WallpapersActivity
import com.ashutosh.wallpaperapp.utils.BounceEdgeEffectFactory
import com.ashutosh.wallpaperapp.viewmodel.ColorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HorizontalColorListFragment : Fragment() {
    private lateinit var binding: FragmentHorizontalColorListBinding
    private val hclViewModel: ColorViewModel by viewModels()
    lateinit var adapter: ColorAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHorizontalColorListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        hclViewModel.listObserver.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            when (it.status) {
                ListStatus.INITIAL_LOADING -> {
//                    todo do something if wanted
                }
                ListStatus.INSERTED -> {
                    adapter.notifyItemRangeInserted(it.positionStart, it.itemCount)
                }
                else -> {

                }
            }
        }
        hclViewModel.getColor()
    }

    private fun setupRecyclerView() {
        adapter = ColorAdapter(requireContext(), hclViewModel.list){
            startActivity(Intent(requireContext(), WallpapersActivity::class.java).apply {
                putExtra("color", it)

            })
        }

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = this@HorizontalColorListFragment.adapter
        }
        binding.recyclerView.edgeEffectFactory = BounceEdgeEffectFactory(true)

    }
}

//fragment_horizontal_category_list