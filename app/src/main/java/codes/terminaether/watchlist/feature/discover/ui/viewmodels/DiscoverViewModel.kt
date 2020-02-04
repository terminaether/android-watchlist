package codes.terminaether.watchlist.feature.discover.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.data.repo.SavedMediaRepository
import codes.terminaether.watchlist.feature.discover.data.repo.DiscoverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
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

    private val _isLoading = MutableLiveData<Boolean>()
    private val _snackbar = MutableLiveData<String?>()

    /**
     * Signals the UI to display a spinner if true.
     */
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    val movieList = repo.discoverMovieList
    val showList = repo.discoverShowList
    /**
     * Signals the UI to display a Snackbar with any error messages encountered.
     */
    val snackbar: LiveData<String?>
        get() = _snackbar

    var isObservingMovies = true

    /**
     * Clears the stored error message once the UI has displayed it.
     */
    fun onSnackbarShown() {
        _snackbar.value = null
    }

    fun refreshDiscoverResults(discoverMovies: Boolean) {
        isObservingMovies = discoverMovies
        launchDataLoad {
            repo.refreshDiscoverResults(discoverMovies)
        }
    }

    fun toggleMediaSaved(media: Media) {
        viewModelScope.launch(Dispatchers.IO) {
            when {
                media.isSaved -> SavedMediaRepository(getApplication()).unsaveMedia(media)
                else -> SavedMediaRepository(getApplication()).saveMedia(media)
            }
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                block()
            } catch (error: IOException) {
                _snackbar.value = error.message
            } finally {
                _isLoading.value = false
            }
        }
    }

}