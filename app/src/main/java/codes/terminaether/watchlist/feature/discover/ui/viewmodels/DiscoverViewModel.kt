package codes.terminaether.watchlist.feature.discover.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.data.model.Movie
import codes.terminaether.watchlist.data.model.Show
import codes.terminaether.watchlist.data.model.UiState
import codes.terminaether.watchlist.data.repo.SavedMediaRepository
import codes.terminaether.watchlist.feature.discover.data.repo.DiscoverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel to store and manage Discover data.
 *
 * Created by terminaether on 2020-01-06.
 */
class DiscoverViewModel @Inject constructor(
    private val repo: DiscoverRepository,
    application: Application
) :
    AndroidViewModel(application) {

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

    fun saveMedia(itemPosition: Int) {
        //TODO (Code): Refine method
        when (discoverResult.value) {
            is UiState.Success -> {
                val media =
                    (discoverResult.value as UiState.Success<List<Media>>).data[itemPosition]
                if (media is Movie) {
                    SavedMediaRepository(getApplication()).insertMovie(media)
                } else if (media is Show) {
                    SavedMediaRepository(getApplication()).insertShow(media)
                }
            }
        }
    }

}