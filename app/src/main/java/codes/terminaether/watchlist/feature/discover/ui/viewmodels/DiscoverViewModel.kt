package codes.terminaether.watchlist.feature.discover.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.data.repo.MediaRepository
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel to store and manage Discover data, loading states and any associated errors.
 *
 * Created by terminaether on 2020-01-06.
 */
class DiscoverViewModel @Inject constructor(
    private val repo: MediaRepository,
    application: Application
) :
    AndroidViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String?>()

    /**
     * Observable used to signal the UI to display a spinner if true.
     */
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    /**
     * Observable used to signal the UI to display a Snackbar with any error messages encountered.
     */
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    //Observable Media lists
    val movieList = repo.discoverMovieList
    val showList = repo.discoverShowList

    /**
     * Used to tell the UI which list to respond to once their contents change.
     */
    var isObservingMovies = true

    /**
     * Clears the stored error message once the UI has displayed it.
     */
    fun onSnackbarShown() {
        _errorMessage.value = null
    }

    /**
     * Sets the source of media being observed before launching a data refresh via an API call.
     * This method also handles updating loading status and error messages.
     */
    fun refreshDiscoverFeed(discoverMovies: Boolean) {
        isObservingMovies = discoverMovies
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repo.refreshDiscoverResults(discoverMovies)
            } catch (error: IOException) {
                _errorMessage.value = error.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleMediaSaveState(media: Media) {
        viewModelScope.launch {
            when {
                media.isSaved -> MediaRepository(getApplication()).unsaveMedia(media)
                else -> MediaRepository(getApplication()).saveMedia(media)
            }
        }
    }

}