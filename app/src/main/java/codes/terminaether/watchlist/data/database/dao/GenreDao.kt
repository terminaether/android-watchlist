package codes.terminaether.watchlist.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import codes.terminaether.watchlist.data.model.Genre

/**
 * Access object for Genres.
 *
 * Created by terminaether on 2020-01-15.
 */
@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGenres(genres: List<Genre>)

    @Query("SELECT name FROM genre WHERE id = :id")
    fun getGenreById(id: Int): String

}