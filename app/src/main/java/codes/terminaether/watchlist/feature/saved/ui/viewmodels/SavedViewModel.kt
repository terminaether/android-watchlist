package codes.terminaether.watchlist.feature.saved.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.data.repo.MediaRepository
import kotlinx.coroutines.launch

/**
 * ViewModel to store and manage Saved data and loading states.
 *
 * Created by terminaether on 2020-02-06.
 */
class SavedViewModel(repo: MediaRepository, application: Application) :
    AndroidViewModel(application) {

    //Observable Media list
    val savedMediaList = repo.savedMediaList

    fun toggleMediaSaveState(media: Media) {
        viewModelScope.launch {
            when {
                media.isSaved -> MediaRepository(getApplication()).unsaveMedia(media)
                else -> MediaRepository(getApplication()).saveMedia(media)
            }
        }
    }

}