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
import codes.terminaether.watchlist.data.repo.MediaRepository
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
class DiscoverFragment : Fragment(), MediaListAdapter.MediaFavouriteListener {

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
                    MediaRepository(this@DiscoverFragment.context!!),
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
        loadingProgressBar = view.findViewById(R.id.pb_loading)
        recyclerView = view.findViewById(R.id.rv_media)
        tabLayout = view.findViewById(R.id.tl_discover)

        //Create and set RecyclerView's Adapter
        recyclerView.adapter = MediaListAdapter(this)
        adapter = recyclerView.adapter as MediaListAdapter

        //Set listener for TabLayout
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
                    discoverViewModel.refreshDatabase(true)
                } else if (tabText == getString(R.string.tab_item_shows)) {
                    discoverViewModel.refreshDatabase(false)
                }
            }
        })

        //Handle loading state
        discoverViewModel.loading.observe(
            viewLifecycleOwner,
            Observer { isLoading ->
                if (isLoading) loadingProgressBar.show() else loadingProgressBar.hide()
                recyclerView.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
            })

        //Handle API and database operation errors
        discoverViewModel.errorMessage.observe(
            viewLifecycleOwner,
            Observer { errorMessage ->
                errorMessage?.let {
                    Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).show()
                    discoverViewModel.onSnackbarShown()
                }
            })

        //Observe popular media lists
        discoverViewModel.popularMoviesList.observe(
            viewLifecycleOwner,
            Observer { movieList -> handleDiscoverData(movieList, true) })
        discoverViewModel.popularShowsList.observe(
            viewLifecycleOwner,
            Observer { showList -> handleDiscoverData(showList, false) })

        //Refresh the contents of the database when the app starts
        discoverViewModel.refreshDatabase(true)
    }

    override fun onListItemFavouriteClick(media: Media) {
        discoverViewModel.toggleFavourite(media)
    }

    private fun handleDiscoverData(mediaList: List<Media>, isMovieList: Boolean) {
        //Only respond to list updates of the correct type
        if (isMovieList && discoverViewModel.isObservingMovies) {
            adapter.submitList(mediaList)
        } else if (!isMovieList && !discoverViewModel.isObservingMovies) {
            adapter.submitList(mediaList)
        }
    }

}