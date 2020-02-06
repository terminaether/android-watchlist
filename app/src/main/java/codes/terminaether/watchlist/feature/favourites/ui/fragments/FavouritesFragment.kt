package codes.terminaether.watchlist.feature.favourites.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import codes.terminaether.watchlist.R
import codes.terminaether.watchlist.adapters.MediaListAdapter
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.data.repo.MediaRepository
import codes.terminaether.watchlist.feature.favourites.ui.viewmodels.FavouritesViewModel

/**
 * The Favourites screen allows users to view a list of titles they have saved to their Watchlist.
 * The screen is separated into two lists containing Movies and TV Shows via sticky headers.
 *
 * Created by terminaether on 2019-12-19.
 */
class FavouritesFragment : Fragment(), MediaListAdapter.MediaFavouriteListener {

    private lateinit var adapter: MediaListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var favouritesViewModel: FavouritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favouritesViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST") // as T
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                //TODO (Code): Find a safer way of providing context, and application to ViewModel
                return FavouritesViewModel(
                    MediaRepository(this@FavouritesFragment.context!!),
                    activity!!.application
                ) as T
            }
        })[FavouritesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Bind Views
        recyclerView = view.findViewById(R.id.rv_media)

        //Create and set RecyclerView's Adapter
        recyclerView.adapter = MediaListAdapter(this)
        adapter = recyclerView.adapter as MediaListAdapter

        favouritesViewModel.favouritesList.observe(
            viewLifecycleOwner,
            Observer { favouritesList -> adapter.submitList(favouritesList) })
    }

    override fun onListItemFavouriteClick(media: Media) {
        favouritesViewModel.toggleFavourite(media)
    }

}