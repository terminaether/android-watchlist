package codes.terminaether.watchlist.feature.discover.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codes.terminaether.watchlist.data.model.ApiResult
import codes.terminaether.watchlist.feature.discover.data.model.DiscoverMoviesResponse
import codes.terminaether.watchlist.feature.discover.data.repo.DiscoverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * TODO: Add file description.
 *
 * Created by terminaether on 2020-01-06.
 */
class DiscoverViewModel @Inject constructor(private val repo: DiscoverRepository) : ViewModel() {

    val discoverMoviesResult = MutableLiveData<ApiResult<DiscoverMoviesResponse>>()

    fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.discoverMovies()
            discoverMoviesResult.postValue(result)
        }
    }

}