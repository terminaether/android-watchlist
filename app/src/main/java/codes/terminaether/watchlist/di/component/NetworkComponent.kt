package codes.terminaether.watchlist.di.component

import codes.terminaether.watchlist.di.module.NetworkModule
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
    /**
     * Provides an OkHttpClient.
     */
    fun getOkHttp(): OkHttpClient

    /**
     * Provides Retrofit.
     */
    fun getRetrofit(): Retrofit
}