package codes.terminaether.watchlist.feature.saved.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import codes.terminaether.watchlist.R

/**
 * The Saved screen allows users to view a list of titles they have saved to their watchlist. The
 * screen is separated into two lists containing Movies and TV Shows.
 *
 * Created by terminaether on 2019-12-19.
 */
class SavedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

}