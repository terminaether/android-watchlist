package codes.terminaether.watchlist.feature.discover.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import codes.terminaether.watchlist.R
import codes.terminaether.watchlist.adapters.MediaListAdapter
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.feature.discover.data.repo.DiscoverRepository
import codes.terminaether.watchlist.feature.discover.ui.viewmodels.DiscoverViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

/**
 * The Discover screen allows users to view a list of popular releases from TMDb. The screen is
 * separated into two lists; Movies and TV Shows. The screens can be further sorted by a variety of
 * filters.
 *
 * Created by terminaether on 2019-12-19.
 */
class DiscoverFragment : Fragment(), MediaListAdapter.MediaSaveListener {

    private lateinit var adapter: MediaListAdapter
    private lateinit var discoverViewModel: DiscoverViewModel
    private lateinit var loadingProgressBar: ContentLoadingProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        discoverViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST") // as T
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                //TODO (Code): Find a safer way of providing context, and application to ViewModel
                return DiscoverViewModel(
                    DiscoverRepository(this@DiscoverFragment.context!!),
                    activity!!.application
                ) as T
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

        //Bind Views
        loadingProgressBar = view.findViewById(R.id.pb_discover)
        recyclerView = view.findViewById(R.id.rv_media)
        tabLayout = view.findViewById(R.id.tl_discover)

        recyclerView.adapter = MediaListAdapter(this)
        adapter = recyclerView.adapter as MediaListAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                recyclerView.smoothScrollToPosition(0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //Do Nothing
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabText = tab!!.text
                if (tabText == getString(R.string.tab_item_movies)) {
                    discoverViewModel.refreshDiscoverResults(true)
                } else if (tabText == getString(R.string.tab_item_shows)) {
                    discoverViewModel.refreshDiscoverResults(false)
                }
            }
        })

        discoverViewModel.isLoading.observe(viewLifecycleOwner, Observer { showProgress(it) })

        discoverViewModel.movieList.observe(
            viewLifecycleOwner,
            Observer { handleDiscoverData(it, true) })
        discoverViewModel.showList.observe(
            viewLifecycleOwner,
            Observer { handleDiscoverData(it, false) })

        discoverViewModel.snackbar.observe(
            viewLifecycleOwner,
            Observer { text ->
                text?.let {
                    Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()
                    discoverViewModel.onSnackbarShown()
                }
            })

        discoverViewModel.refreshDiscoverResults(true)
    }

    override fun onListItemSaveClick(media: Media) {
        discoverViewModel.toggleMediaSaved(media)
    }

    private fun handleDiscoverData(discoverResponse: List<Media>, isMovieResponse: Boolean) {
        if (isMovieResponse && discoverViewModel.isObservingMovies) {
            adapter.swapData(discoverResponse)
        } else if (!isMovieResponse && !discoverViewModel.isObservingMovies) {
            adapter.swapData(discoverResponse)
        }
    }

    private fun showProgress(isLoading: Boolean) {
        if (isLoading) {
            loadingProgressBar.show()
            recyclerView.visibility = View.INVISIBLE
        } else {
            loadingProgressBar.hide()
            recyclerView.visibility = View.VISIBLE
        }
    }

}