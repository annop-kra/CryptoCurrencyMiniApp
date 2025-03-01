package com.ankn.cryptocurrencyminiapp.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankn.cryptocurrencyminiapp.R
import com.ankn.cryptocurrencyminiapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class HomeFragment : Fragment() {

    // ViewBinding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel {
        parametersOf(
            requireActivity(),
            findNavController()
        )
    }
 /*   private lateinit var commentsPagingAdapter: CommentsPagingAdapter*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* commentsPagingAdapter = CommentsPagingAdapter { item ->
            viewModel.onItemClicked(item.name)
        }

        val combinedPagingAdapter = commentsPagingAdapter.withLoadStateFooter(
            footer = CommentsLoadStateAdapter { commentsPagingAdapter.retry() }
        )

        val concatAdapter = ConcatAdapter(combinedPagingAdapter)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = concatAdapter
        }

        lifecycleScope.launch {
            viewModel.itemsPagingDataFlow.collectLatest { pagingData ->
                commentsPagingAdapter.submitData(pagingData)
            }
        }*/
    }
}