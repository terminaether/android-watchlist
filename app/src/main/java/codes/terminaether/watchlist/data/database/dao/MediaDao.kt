package codes.terminaether.watchlist.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import codes.terminaether.watchlist.data.model.Media

/**
 * TODO (Documentation): Add file description.
 *
 * Created by terminaether on 2020-01-27.
 */
@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedia(media: Media)

    @Query("DELETE FROM Media where id = :id")
    fun deleteMediaById(id: Int)

    @Query("SELECT id FROM Media")
    fun getMediaIds(): List<Int>

}