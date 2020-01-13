package codes.terminaether.watchlist.feature.discover.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import codes.terminaether.watchlist.R
import codes.terminaether.watchlist.adapters.MediaListAdapter
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.data.model.UiState
import codes.terminaether.watchlist.feature.discover.data.repo.DiscoverRepository
import codes.terminaether.watchlist.feature.discover.ui.viewmodels.DiscoverViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_discover.*

/**
 * The Discover screen allows users to view a list of popular releases from TMDb. The screen is
 * separated into two lists; Movies and TV Shows. The screens can be further sorted by a variety of
 * filters.
 *
 * Created by terminaether on 2019-12-19.
 */
class DiscoverFragment : Fragment() {

    private lateinit var discoverViewModel: DiscoverViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        discoverViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST") // as T
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DiscoverViewModel(DiscoverRepository()) as T
            }
        })[DiscoverViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        discoverViewModel.discoverResult.observe(
            viewLifecycleOwner,
            Observer<UiState<List<Media>>> { this.handleDiscoverData(it) })

        tl_discover_fragment.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                rv_media.smoothScrollToPosition(0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //Do Nothing
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabText = tab!!.text
                if (tabText == getString(R.string.tab_item_movies)) {
                    discoverViewModel.fetchMovies()
                } else if (tabText == getString(R.string.tab_item_shows)) {
                    discoverViewModel.fetchShows()
                }
            }
        })

        rv_media.adapter = MediaListAdapter()

        discoverViewModel.fetchMovies()
    }

    private fun handleDiscoverData(discoverResponse: UiState<List<Media>>) {
        when (discoverResponse) {
            is UiState.Success -> {
                val adapter: MediaListAdapter = rv_media.adapter as MediaListAdapter
                adapter.swapData(discoverResponse.data)
            }
            is UiState.Loading -> Log.d("Attention", "Loading Media")
            is UiState.Error -> Log.d(
                "Attention",
                "Error Loading Media: ${discoverResponse.exception}"
            )
        }
    }

}