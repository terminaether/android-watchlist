package codes.terminaether.watchlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import codes.terminaether.watchlist.feature.discover.ui.fragments.DiscoverFragment
import codes.terminaether.watchlist.feature.favourites.ui.fragments.FavouritesFragment
import codes.terminaether.watchlist.feature.trailers.ui.fragments.TrailersFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        findViewById<BottomNavigationView>(R.id.bottom_nav).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.discover -> {
                    loadFragment(DiscoverFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.favourites -> {
                    loadFragment(FavouritesFragment())
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
        transaction.replace(R.id.fl_container, fragment)
        transaction.commit()
    }

}