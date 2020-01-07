package codes.terminaether.watchlist

import android.app.Application
import codes.terminaether.watchlist.di.component.DaggerNetworkComponent
import codes.terminaether.watchlist.di.component.NetworkComponent

/**
 * Global Application class used to initialise dependency injection before the first Activity is
 * displayed.
 *
 * Created by terminaether on 2020-01-07.
 */
class WatchlistApplication : Application() {

    companion object {
        lateinit var INSTANCE: WatchlistApplication
    }

    val networkComponent: NetworkComponent by lazy {
        return@lazy DaggerNetworkComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

}