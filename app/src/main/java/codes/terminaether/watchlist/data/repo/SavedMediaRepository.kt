package codes.terminaether.watchlist.data.repo

import android.content.Context
import codes.terminaether.watchlist.data.database.AppDatabase
import codes.terminaether.watchlist.data.model.Movie

/**
 * Handles saving media to the app's database, and contains methods for retrieving saved media.
 *
 * Created by terminaether on 2020-01-16.
 */
class SavedMediaRepository(private val context: Context) : BaseRepository() {

    fun insertMovie(movie: Movie) {
        AppDatabase.DATABASE_WRITER.execute {
            AppDatabase.getAppDatabase(context).movieDao().insertMovie(movie)
        }
    }

}