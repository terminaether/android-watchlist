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

    //Private reference to repository data for quick saving to SavedMediaRepository
    private lateinit var mediaList: List<Media>

    val discoverResult = MutableLiveData<UiState<List<Media>>>()

    fun fetchMovies() {
        discoverResult.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.discoverMovies()
            when (result) {
                is UiState.Success -> {
                    mediaList = result.data
                }
            }

            discoverResult.postValue(result)
        }
    }

    fun fetchShows() {
        discoverResult.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.discoverShows()
            when (result) {
                is UiState.Success -> {
                    mediaList = result.data
                }
            }

            discoverResult.postValue(result)
        }
    }

    fun toggleMediaSaved(itemPosition: Int) {
        val media = mediaList[itemPosition]
        //ID is guaranteed to be non-null
        val mediaId = media.id

        viewModelScope.launch(Dispatchers.IO) {
            if (media is Movie) {
                if (media.isSaved) {
                    SavedMediaRepository(getApplication()).deleteMovie(mediaId!!)
                } else {
                    SavedMediaRepository(getApplication()).insertMovie(mediaId!!)
                }
            } else if (media is Show) {
                if (media.isSaved) {
                    SavedMediaRepository(getApplication()).deleteShow(mediaId!!)
                } else {
                    SavedMediaRepository(getApplication()).insertShow(mediaId!!)
                }
            }
        }
    }

}