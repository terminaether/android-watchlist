package codes.terminaether.watchlist.data.database.dao

import androidx.room.*
import codes.terminaether.watchlist.data.model.Media

/**
 * Access object for the Media table of the App's database.
 *
 * Created by terminaether on 2020-01-27.
 */
@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiscoverResults(media: List<Media>)

    @Query("SELECT * FROM Media WHERE title != null ORDER BY popularity DESC")
    fun getDiscoverMovieResults(): List<Media>

    @Query("SELECT * FROM Media WHERE name != null ORDER BY popularity DESC")
    fun getDiscoverShowResults(): List<Media>

    @Query("SELECT id FROM Media")
    fun getMediaIds(): List<Int>

    @Query("SELECT id FROM Media WHERE isSaved")
    fun getSavedMediaIds(): List<Int>

    @Update
    fun updateMedia(media: Media)

    @Query("DELETE FROM Media where id = :id")
    fun deleteMediaById(id: Int)

}