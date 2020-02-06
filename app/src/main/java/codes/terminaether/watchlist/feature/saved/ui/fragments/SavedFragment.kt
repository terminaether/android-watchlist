package codes.terminaether.watchlist.feature.saved.ui.fragments

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
import codes.terminaether.watchlist.feature.saved.ui.viewmodels.SavedViewModel

/**
 * The Saved screen allows users to view a list of titles they have saved to their watchlist. The
 * screen is separated into two lists containing Movies and TV Shows.
 *
 * Created by terminaether on 2019-12-19.
 */
class SavedFragment : Fragment(), MediaListAdapter.MediaSaveListener {

    private lateinit var adapter: MediaListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var savedViewModel: SavedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST") // as T
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                //TODO (Code): Find a safer way of providing context, and application to ViewModel
                return SavedViewModel(
                    MediaRepository(this@SavedFragment.context!!),
                    activity!!.application
                ) as T
            }
        })[SavedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Bind Views
        recyclerView = view.findViewById(R.id.rv_media)

        //Create and set RecyclerView's Adapter
        recyclerView.adapter = MediaListAdapter(this)
        adapter = recyclerView.adapter as MediaListAdapter

        savedViewModel.savedMediaList.observe(
            viewLifecycleOwner,
            Observer { savedMediaList -> adapter.submitList(savedMediaList) })
    }

    override fun onListItemSaveClick(media: Media) {
        savedViewModel.toggleMediaSaveState(media)
    }

}