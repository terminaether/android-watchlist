package codes.terminaether.watchlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import codes.terminaether.watchlist.feature.discover.ui.DiscoverFragment
import codes.terminaether.watchlist.feature.saved.ui.SavedFragment
import codes.terminaether.watchlist.feature.trailers.ui.TrailersFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * TODO (Documentation): Add file description.
 *
 * Created by terminaether on 2019-12-05.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(DiscoverFragment())
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.discover -> {
                    loadFragment(DiscoverFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.saved -> {
                    loadFragment(SavedFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.trailers -> {
                    loadFragment(TrailersFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }

            false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

}