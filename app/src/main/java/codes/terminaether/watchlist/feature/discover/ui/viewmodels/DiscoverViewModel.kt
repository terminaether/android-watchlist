package codes.terminaether.watchlist.feature.discover.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codes.terminaether.watchlist.data.model.ApiResult
import codes.terminaether.watchlist.data.model.Movie
import codes.terminaether.watchlist.data.model.Show
import codes.terminaether.watchlist.feature.discover.data.model.DiscoverResponse
import codes.terminaether.watchlist.feature.discover.data.repo.DiscoverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel to store and manage Discover data.
 *
 * Created by terminaether on 2020-01-06.
 */
class DiscoverViewModel @Inject constructor(private val repo: DiscoverRepository) : ViewModel() {

    val discoverMoviesResult = MutableLiveData<ApiResult<DiscoverResponse<Movie>>>()
    val discoverShowsResult = MutableLiveData<ApiResult<DiscoverResponse<Show>>>()

    fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.discoverMovies()
            discoverMoviesResult.postValue(result)
        }
    }

    fun fetchShows() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.discoverShows()
            discoverShowsResult.postValue(result)
        }
    }

}