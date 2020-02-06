package codes.terminaether.watchlist.feature.favourites.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import codes.terminaether.watchlist.data.model.Media
import codes.terminaether.watchlist.data.repo.MediaRepository
import kotlinx.coroutines.launch

/**
 * Responsible for storing and managing Favourited [Media] items to be display to the user.
 *
 * Created by terminaether on 2020-02-06.
 */
class FavouritesViewModel(repo: MediaRepository, application: Application) :
    AndroidViewModel(application) {

    //Observable Media list
    val favouritesList = repo.favouritesList

    fun toggleFavourite(media: Media) {
        viewModelScope.launch {
            when {
                media.isFavourite -> MediaRepository(getApplication()).unfavouriteMedia(media)
                else -> MediaRepository(getApplication()).favouriteMedia(media)
            }
        }
    }

}