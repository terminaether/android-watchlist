package codes.terminaether.watchlist.feature.discover.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
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

    private val discoverMoviesState = MutableLiveData<DiscoverMoviesResponse>()
    val discoverMovies: LiveData<DiscoverMoviesResponse> get() = discoverMoviesState

    fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repo.discoverMovies()) {
                is ApiResult.Success -> {
                    Log.d("Attention", "Success")

                    discoverMoviesState.value = result.data
                    result.data?.let {
                        Log.d("Attention", "Pages: " + it.totalPages)
                    }
                }
                is ApiResult.Error -> {
                    Log.d("Attention", "Error")
                }
            }
        }
    }

}