package codes.terminaether.watchlist.feature.trailers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import codes.terminaether.watchlist.R

/**
 * The Trailers screen shows users a list of new trailers based off of their interests, if available.
 *
 * Created by terminaether on 2019-12-20.
 */
class TrailersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trailers, container, false)
    }

}