package com.ashutosh.wallpaperapp.ui.fragments.sub_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashutosh.wallpaperapp.adapter.CategoriesHomeAdapter
import com.ashutosh.wallpaperapp.databinding.FragmentHorizontalCategoryListBinding
import com.ashutosh.wallpaperapp.network.ListStatus
import com.ashutosh.wallpaperapp.ui.WallpapersActivity
import com.ashutosh.wallpaperapp.utils.BounceEdgeEffectFactory
import com.ashutosh.wallpaperapp.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HorizontalCategoryListFragment : Fragment() {
    private lateinit var binding: FragmentHorizontalCategoryListBinding
    private val hclViewModel: CategoryViewModel by viewModels()
    lateinit var homeAdapter: CategoriesHomeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHorizontalCategoryListBinding.inflate(inflater, container, false)
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
                    homeAdapter.notifyItemRangeInserted(it.positionStart, it.itemCount)
                }
                else -> {
                }
            }
        }

        hclViewModel.getCategories()
    }



    private fun setupRecyclerView() {
        homeAdapter = CategoriesHomeAdapter(requireContext(), hclViewModel.list){
            startActivity(Intent(requireContext(), WallpapersActivity::class.java).apply {
                putExtra("category", it)
            })
        }

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = this@HorizontalCategoryListFragment.homeAdapter
        }
        binding.recyclerView.edgeEffectFactory = BounceEdgeEffectFactory(true)

    }
}

//fragment_horizontal_category_list