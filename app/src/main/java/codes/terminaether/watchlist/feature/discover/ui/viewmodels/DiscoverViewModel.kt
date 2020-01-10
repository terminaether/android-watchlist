package codes.terminaether.watchlist.feature.discover.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.data.model.UiState
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

    val discoverResult = MutableLiveData<UiState<List<Media>>>()

    fun fetchMovies() {
        discoverResult.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.discoverMovies()
            discoverResult.postValue(result)
        }
    }

    fun fetchShows() {
        discoverResult.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.discoverShows()
            discoverResult.postValue(result)
        }
    }

}