package com.moises.stocksbaraka.presentation.stocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.moises.stocksbaraka.databinding.FragmentStocksBinding
import com.moises.stocksbaraka.presentation.adapter.MainNewsRecycleViewAdapter
import com.moises.stocksbaraka.presentation.adapter.NewsRecycleViewAdapter
import com.moises.stocksbaraka.presentation.adapter.StocksRecycleViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class StocksFragment : Fragment() {

    private var _binding: FragmentStocksBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StocksViewModel by viewModels()

    @Inject
    lateinit var stocksAdapter: StocksRecycleViewAdapter

    @Inject
    lateinit var mainNewsAdapter: MainNewsRecycleViewAdapter

    @Inject
    lateinit var newsAdapter: NewsRecycleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStocksBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelData()
        setupRecycleView()
        simulateTheUpdateOfStocksPrice()
    }

    private fun simulateTheUpdateOfStocksPrice() {
        //
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    //This loop was created inside a repeatLifecycle to avoid memory leak and waste of resource.
                    //This way when the app is on background this part of the code wont be reached
                    while (true) {
                        delay(1000)
                        if (stocksAdapter.stockList.isNotEmpty()) {
                            viewModel.getStockPrice(stocksAdapter.stockList.random().symbol)
                        }
                    }
                }
            }
        }
    }

    private fun setupRecycleView() {
        binding.rvStocks.adapter = stocksAdapter
        binding.rvMainNews.adapter = mainNewsAdapter
        binding.rvNews.adapter = newsAdapter

        //Include list divider
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rvNews.addItemDecoration(dividerItemDecoration)
        //Bind Swipe to refresh
        binding.swiperefresh.setOnRefreshListener { viewModel.fetchNews(true) }
    }


    private fun observeViewModelData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.stocksState.collectLatest { results ->
                        when (results) {
                            is StocksViewModel.StocksViewState.Success -> {
                                stocksAdapter.submitList(results.stocks)
                            }
                            is StocksViewModel.StocksViewState.Failure -> {
                                context?.let { Toast.makeText(it, results.errorMessage, Toast.LENGTH_LONG).show() }
                            }
                            else -> {}
                        }
                    }
                }
                launch {
                    viewModel.newsState.collectLatest { results ->
                        when (results) {
                            is StocksViewModel.NewsViewState.Success -> {
                                hideLoading()
                                mainNewsAdapter.submitList(results.articles.subList(0, 5))
                                newsAdapter.submitList(results.articles.subList(5, results.articles.size))
                            }
                            is StocksViewModel.NewsViewState.Loading -> {
                                showLoading()
                            }
                            is StocksViewModel.NewsViewState.Failure -> {
                                hideLoading()
                                context?.let { Toast.makeText(it, results.errorMessage, Toast.LENGTH_LONG).show() }
                            }
                            else -> {}
                        }
                    }
                }
                launch {
                    viewModel.singleStockState.collectLatest { result ->
                        when (result) {
                            is StocksViewModel.SingleStockViewState.Success -> {
                                hideLoading()
                                stocksAdapter.updateStockPrice(result.stock)
                            }
                            is StocksViewModel.SingleStockViewState.Loading -> {
                            }
                            is StocksViewModel.SingleStockViewState.Failure -> {
                                context?.let { Toast.makeText(it, result.errorMessage, Toast.LENGTH_LONG).show() }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.swiperefresh.isRefreshing = true
    }

    private fun hideLoading() {
        binding.swiperefresh.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        @BindingAdapter("newsImage")
        fun loadImage(view: ImageView, posterImage: String?) {

            Glide.with(view.context)
                .load(posterImage)
                .into(view)
        }
    }
}