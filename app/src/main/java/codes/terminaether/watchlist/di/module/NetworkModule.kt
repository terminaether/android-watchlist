package codes.terminaether.watchlist.di.module

import codes.terminaether.watchlist.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Contains instructions on how to construct various network objects.`
 *
 * Created by terminaether on 2019-12-31.
 */
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            //Add API key to all API requests
            val newUrl = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Singleton
    @Provides
    internal fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}