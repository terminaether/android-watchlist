package codes.terminaether.watchlist.feature.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import codes.terminaether.watchlist.R

/**
 * The Search screen allows users to search TMDb for Movies and TV Shows, which are returned as a
 * single list.
 *
 * Created by terminaether on 2019-12-19.
 */
class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

}