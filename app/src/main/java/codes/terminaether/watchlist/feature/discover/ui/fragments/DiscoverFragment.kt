package codes.terminaether.watchlist.feature.discover.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import codes.terminaether.watchlist.R
import codes.terminaether.watchlist.feature.discover.data.repo.DiscoverRepository
import codes.terminaether.watchlist.feature.discover.ui.viewmodels.DiscoverViewModel

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
        discoverViewModel.fetchMovies()
    }

}