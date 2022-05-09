package com.moises.characters.presentation.detail

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
import com.bumptech.glide.Glide
import com.moises.characters.R
import com.moises.characters.databinding.FragmentDetailCharacterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailCharacterFragment : Fragment() {

    private var _binding: FragmentDetailCharacterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailCharacterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailCharacterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelData()
        setupToolbar()
        arguments?.getInt("characterIdArgs")?.let { viewModel.getCharacter(it) }
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun observeViewModelData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.characterState.collectLatest { result ->
                        when (result) {
                            is DetailCharacterViewState.Success -> {
                                hideLoading()
                                binding.character = result.character
                            }
                            is DetailCharacterViewState.Failure -> {
                                //hideLoading()
                                context?.let { Toast.makeText(it, result.errorMessage, Toast.LENGTH_LONG).show() }
                            }
                            is DetailCharacterViewState.Loading -> {
                                showLoading()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.scrollView.visibility = View.INVISIBLE
    }

    private fun hideLoading() {
        binding.scrollView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val CHARACTER_ID_NAV_ARGS = "characterIdArgs"

        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(view: ImageView, image: String?) {
            Glide.with(view.context)
                .load(image)
                .into(view)
        }
    }
}