package codes.terminaether.watchlist.feature.discover.data.repo

import android.content.Context
import codes.terminaether.watchlist.WatchlistApplication
import codes.terminaether.watchlist.data.model.*
import codes.terminaether.watchlist.data.repo.BaseRepository
import codes.terminaether.watchlist.data.repo.SavedMediaRepository

/**
 * Allows the app to use multiple data sources for the Discover feed.
 *
 * Created by terminaether on 2020-01-03.
 */
class DiscoverRepository(private val context: Context) : BaseRepository() {

    private val discoverService =
        WatchlistApplication.INSTANCE.networkComponent.getDiscoverService()

    //TODO (Code): Media returned should be observable
    suspend fun discoverMovies(): UiState<List<Media>> {
        val response = safeApiCall(
            call = { discoverService.discoverMovies().await() },
            errorMessage = "Error Discovering Movies"
        )

        return when (response) {
            is ApiResult.Success -> {
                val cleanDataSet = removeInvalidData(response.data.results)
                val markedDataSet = markSavedData(cleanDataSet)
                UiState.Success(markedDataSet)
            }
            is ApiResult.Error -> {
                UiState.Error(response.exception)
            }
        }
    }

    suspend fun discoverShows(): UiState<List<Media>> {
        val response = safeApiCall(
            call = { discoverService.discoverShows().await() },
            errorMessage = "Error Discovering Shows"
        )

        return when (response) {
            is ApiResult.Success -> {
                val cleanDataSet = removeInvalidData(response.data.results)
                val markedDataSet = markSavedData(cleanDataSet)
                UiState.Success(markedDataSet)
            }
            is ApiResult.Error -> {
                UiState.Error(response.exception)
            }
        }
    }

    /**
     * Marks Media items that are saved locally.
     * With thanks to Aaron.
     */
    private fun markSavedData(dataSet: List<Media>): List<Media> {
        val savedIds = SavedMediaRepository(context).getMediaIds()
        val iterator = dataSet.iterator()
        iterator.forEach {
            if (savedIds.contains(it.id)) it.isSaved = true
        }

        return dataSet
    }

    /**
     * Removes Media items without a valid ID, Poster Path and Title/Name.
     * With thanks to Conor.
     */
    private fun removeInvalidData(dataSet: List<Media>): List<Media> {
        val cleanDataSet = dataSet.toMutableList()
        cleanDataSet.removeAll { it.id == 0 }
        cleanDataSet.removeAll { it.posterPath.isNullOrBlank() }

        val iterator = cleanDataSet.iterator()
        iterator.forEach {
            if (it is Movie) {
                if (it.title.isNullOrBlank()) iterator.remove()
            } else if (it is Show) {
                if (it.name.isNullOrBlank()) iterator.remove()
            }
        }

        return cleanDataSet
    }

}