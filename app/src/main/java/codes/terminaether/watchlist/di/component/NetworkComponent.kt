package codes.terminaether.watchlist.di.component

import codes.terminaether.watchlist.WatchlistApplication
import codes.terminaether.watchlist.di.module.NetworkModule
import codes.terminaether.watchlist.feature.discover.data.service.DiscoverService
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Used to create and provide network objects to classes throughout the app.
 *
 * Created by terminaether on 2019-12-31.
 */
@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance watchlistApplication: WatchlistApplication): NetworkComponent
    }

    /**
     * Provides an OkHttpClient.
     */
    fun getOkHttp(): OkHttpClient

    /**
     * Provides Retrofit.
     */
    fun getRetrofit(): Retrofit

    /**
     * Provides DiscoverService.
     */
    fun getDiscoverService(): DiscoverService

}