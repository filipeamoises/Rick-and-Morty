package com.moises.characters.presentation.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.moises.characters.R
import com.moises.characters.databinding.ActivityDetailCharacterBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailCharacterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailCharacterBinding.inflate(layoutInflater)

        setContentView(binding.root)
        navigateToDetailFragment()

    }

    private fun navigateToDetailFragment() {
        val bundle = bundleOf(
            Pair(
                DetailCharacterFragment.CHARACTER_ID_NAV_ARGS, intent.getIntExtra(
                    CHARACTER_ID_EXTRA, -1
                )
            )
        )
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController.setGraph(
            R.navigation.nav_graph_detail_character, bundle
        )
    }

    companion object {
        const val CHARACTER_ID_EXTRA = "character_extra_id"
    }
}