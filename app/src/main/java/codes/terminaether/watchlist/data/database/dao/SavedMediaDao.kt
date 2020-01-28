package codes.terminaether.watchlist.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import codes.terminaether.watchlist.data.model.Media

/**
 * Access object for the Saved Media table of the App's database.
 *
 * Created by terminaether on 2020-01-27.
 */
@Dao
interface SavedMediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMedia(media: Media)

    @Query("DELETE FROM Media where id = :id")
    fun deleteMediaById(id: Int)

    @Query("SELECT id FROM Media")
    fun getSavedMediaIds(): List<Int>

}