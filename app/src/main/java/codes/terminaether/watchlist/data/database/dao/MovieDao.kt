package codes.terminaether.watchlist.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import codes.terminaether.watchlist.data.model.Movie

/**
 * Provides access to and methods for [Movie] database.
 *
 * Created by terminaether on 2020-01-16.
 */
@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

}