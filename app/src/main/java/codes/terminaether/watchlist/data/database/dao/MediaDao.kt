package codes.terminaether.watchlist.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import codes.terminaether.watchlist.data.model.Media

/**
 * Access object for the Media table of the App's database.
 *
 * Created by terminaether on 2020-01-27.
 */
//TODO (Database): Convert all functions to suspend functions
@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscoverResults(media: List<Media>)

    @get:Query("SELECT * FROM Media WHERE title NOT null ORDER BY popularity DESC")
    val discoverMovieResults: LiveData<List<Media>>

    @get:Query("SELECT * FROM Media WHERE name NOT null ORDER BY popularity DESC")
    val discoverShowResults: LiveData<List<Media>>

    @Query("SELECT id FROM Media")
    fun getMediaIds(): List<Int>

    @Query("SELECT id FROM Media WHERE isSaved")
    fun getSavedMediaIds(): List<Int>

    @Update
    fun updateMedia(media: Media)

    @Query("DELETE FROM Media where id = :id")
    fun deleteMediaById(id: Int)

}