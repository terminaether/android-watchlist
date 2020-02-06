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

/**
 * Responsible for storing and managing the results of calls to TMDb's Discover endpoints via a local
 * database. Also handles loading states and any associated errors.
 *
 * Created by terminaether on 2020-01-06.
 */
class DiscoverViewModel(private val repo: MediaRepository, application: Application) :
    AndroidViewModel(application) {

    private val _loading = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String?>()

    /**
     * Observable used to display a spinner in the UI layer.
     */
    val loading: LiveData<Boolean>
        get() = _loading
    /**
     * Observable used to display a Snackbar in the UI layer with any API or database error messages
     * encountered.
     */
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    //Observable Media lists
    val popularMoviesList = repo.popularMoviesList
    val popularShowsList = repo.popularShowsList

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
     * Sets the type of media being observed before launching a database refresh via an API call.
     * This method also handles the updating of loading status and posting of error messages.
     */
    fun refreshDatabase(refreshMovies: Boolean) {
        isObservingMovies = refreshMovies
        viewModelScope.launch {
            try {
                _loading.value = true
                repo.refreshDatabase(refreshMovies)
            } catch (error: IOException) {
                _errorMessage.value = error.message
            } finally {
                _loading.value = false
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