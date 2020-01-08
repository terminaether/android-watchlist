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
import codes.terminaether.watchlist.data.model.ApiResult
import codes.terminaether.watchlist.data.model.Movie
import codes.terminaether.watchlist.data.model.Show
import codes.terminaether.watchlist.feature.discover.data.model.DiscoverResponse
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

        discoverViewModel.discoverMoviesResult.observe(
            viewLifecycleOwner,
            Observer<ApiResult<DiscoverResponse<Movie>>> { this.handleDiscoverMoviesData(it) })
        discoverViewModel.discoverShowsResult.observe(
            viewLifecycleOwner,
            Observer<ApiResult<DiscoverResponse<Show>>> { this.handleDiscoverShowsData(it) })

        tl_discover_fragment.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                //Do Nothing
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

        discoverViewModel.fetchMovies()
    }

    private fun handleDiscoverMoviesData(discoverResponse: ApiResult<DiscoverResponse<Movie>>) {
        when (discoverResponse) {
            is ApiResult.Success -> {
                Log.d("Attention", "API Success: Movies")
                Log.d(
                    "Attention",
                    "First Movie Result: " + discoverResponse.data!!.results[0].title
                )
            }
            is ApiResult.Error -> Log.d("Attention", "API Error: Movies")
        }
    }

    private fun handleDiscoverShowsData(discoverResponse: ApiResult<DiscoverResponse<Show>>) {
        when (discoverResponse) {
            is ApiResult.Success -> {
                Log.d("Attention", "API Success: Shows")
                Log.d("Attention", "First Show Result: " + discoverResponse.data!!.results[0].name)
            }
            is ApiResult.Error -> Log.d("Attention", "API Error: Shows")
        }
    }

}