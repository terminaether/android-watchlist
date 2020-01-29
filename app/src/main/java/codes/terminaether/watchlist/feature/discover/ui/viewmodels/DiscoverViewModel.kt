package codes.terminaether.watchlist.feature.discover.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.data.repo.SavedMediaRepository
import codes.terminaether.watchlist.feature.discover.data.repo.DiscoverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private var mediaList: List<Media>? = listOf()

    val observableMediaList: LiveData<List<Media>> = liveData {
        var discoverResults: List<Media> = listOf()
        while (true) {
            viewModelScope.launch(Dispatchers.IO) {
                discoverResults = repo.discoverMedia(discoverMovies = true, forceUpdate = false)
            }
            mediaList = discoverResults
            emit(discoverResults)
            delay(5000)
        }
    }

    fun discover(discoverMovies: Boolean, forceUpdate: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun toggleMediaSaved(itemPosition: Int) {
        if (mediaList.isNullOrEmpty()) return
        val media = mediaList!![itemPosition]
        viewModelScope.launch(Dispatchers.IO) {
            when {
                media.isSaved -> SavedMediaRepository(getApplication()).unsaveMedia(media)
                else -> SavedMediaRepository(getApplication()).saveMedia(media)
            }
        }
    }

}